package testexecution;

import cfg.CFGNode;
import structureTree.structureNode.SFunctionNode;
import testcases.TestCase;
import testdata.DataNode;
import testdata.RootDataNode;

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
            RootDataNode rootDataNode = testCase.getRootDataNode();
            for (DataNode dataNode : rootDataNode.getChildren());
        }
    }


}
