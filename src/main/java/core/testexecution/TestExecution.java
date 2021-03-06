package core.testexecution;

import core.cfg.CFGNode;
import core.cmd.CommandLine;
import core.node.FileNode;
import core.structureTree.structureNode.SFunctionNode;
import core.testcases.TestCase;
import core.testexecution.coverage.TestCoverage;
import core.testexecution.instrument.Instrument;
import core.utils.CFGUtils;
import core.utils.SearchInSTree;
import core.utils.Utils;

import java.io.IOException;
import java.util.List;

public class TestExecution {
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String N_A = "n_a";
    private List<TestCase> testCaseList;
    private CFGNode rootCFG;
    private SFunctionNode functionNode;

    private Instrument instrument;
    private String status = "N_A";
    private TestCoverage coverage;

    public TestExecution(List<TestCase> testCaseList, CFGNode rootCFG) {
        this.testCaseList = testCaseList;
        this.rootCFG = rootCFG;
    }

    public TestCoverage getCoverage() {
        return coverage;
    }

    public void setCoverage(TestCoverage coverage) {
        this.coverage = coverage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public List<TestCase> getTestCaseList() {
        return testCaseList;
    }

    public void setTestCaseList(List<TestCase> testCaseList) {
        this.testCaseList = testCaseList;
    }

    public CFGNode getRootCFG() {
        return rootCFG;
    }

    public void setRootCFG(CFGNode rootCFG) {
        this.rootCFG = rootCFG;
    }

    public SFunctionNode getFunctionNode() {
        return functionNode;
    }

    public void setFunctionNode(SFunctionNode functionNode) {
        this.functionNode = functionNode;
        functionNode.getAst().setCfg(rootCFG);
    }

    public void execute() {
        for (TestCase testCase : testCaseList) {
            FileNode  fileNode = SearchInSTree.getJavaFileNode(functionNode);
            String absolutePath = fileNode.getAbsolutePath();
            instrument = new Instrument(absolutePath, rootCFG, functionNode, testCase);
            try {
                instrument.markInstrument();
                testCase.setTestPath(instrument.getTestPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (instrument != null) {
                try {
                    String command = "javac " + instrument.getInstrumentPath();
                    CommandLine.executeCommand(command);
                    String folderPath = instrument.getInstrumentFolder().getAbsolutePath();//.replaceAll("\\\\", "/");
                    System.out.println(folderPath);
                    command = "java " + SearchInSTree.getJavaFileNode(functionNode).getName().replaceAll(".java", "");
                    CommandLine.executeCommand(command, folderPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String testPath = instrument.getTestPath();
                try {
                    List<String> linesTestPath = Utils.readFileByLines(testPath);
                    for (String line : linesTestPath) {
                        int start = Utils.getStartValueOfLineInTestPath(line);
                        CFGNode finderCFG = CFGUtils.findCFGNodeByStart(rootCFG, start);
                        if (finderCFG != null) finderCFG.setVisited(true);
                    }
                } catch (IOException e) {
                    System.out.println("Fail to read test path content.");
                    e.printStackTrace();
                }
            }
            setStatus(SUCCESS);
        }
        TestCoverage cov = TestExecutionManager.generateTestCoverage(this);
        setCoverage(cov);
    }

    public void showCoverage() {
        if (coverage == null) return;
        double cov = coverage.getPassNode()* 100.0 /coverage.getTotalNode();
        StringBuilder info = new StringBuilder();
        info.append("Coverage of function ").append(getFunctionNode().getName()).append(": ").append(cov).append("%");
        System.out.println( info);
    }


}
