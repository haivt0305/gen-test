package testexecution;

import cfg.CFGNode;
import testcases.TestCase;
import testdata.DataNode;
import testdata.RootDataNode;

import java.util.List;

public class TestExecution {
    private List<TestCase> testCaseList;
    private CFGNode rootCFG;

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
