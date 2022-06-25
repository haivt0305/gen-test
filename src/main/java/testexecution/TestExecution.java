package testexecution;

import cfg.CFGNode;
import node.FileNode;
import structureTree.structureNode.SFunctionNode;
import testcases.TestCase;
import utils.SearchInSTree;

import java.util.List;

public class TestExecution {
    private List<TestCase> testCaseList;
    private CFGNode rootCFG;
    private SFunctionNode functionNode;

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
    }

    public TestExecution(List<TestCase> testCaseList, CFGNode rootCFG) {
        this.testCaseList = testCaseList;
        this.rootCFG = rootCFG;
    }

    public void execute() {
        for (TestCase testCase : testCaseList) {
            FileNode  fileNode = SearchInSTree.getJavaFileNode(functionNode);
            String absolutePath = fileNode.getAbsolutePath();
            Instrument instrument = new Instrument(absolutePath, rootCFG, functionNode, testCase);
            try {
                instrument.markInstrument();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }




}
