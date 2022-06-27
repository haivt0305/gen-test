import core.cfg.CFGNode;
import core.generationStrategy.RandomGenerationStrategy;
import core.parser.ProjectParser;
import core.structureTree.SNode;
import core.structureTree.structureNode.SFunctionNode;
import core.testcases.TestCase;
import core.testcases.TestcaseManager;
import core.testexecution.TestExecution;
import core.utils.SearchInSTree;

import java.io.IOException;
import java.util.List;

public class AppStart {
    public static void main(String[] args) throws IOException {
        String path = "data";
        System.out.println("Start parsing...");
        SNode root = ProjectParser.parse(path);
        System.out.println(root.getInfo());
        List<SFunctionNode> functionNodeList = SearchInSTree.searchSFunction(root);
        for (SFunctionNode functionNode : functionNodeList) {
            List<TestCase> testCaseList = TestcaseManager.createTestcase(functionNode, new RandomGenerationStrategy());
            CFGNode cfgNode = CFGNode.parseToCFG(functionNode);

            TestExecution tcExecution = new TestExecution(testCaseList, cfgNode);
            tcExecution.setFunctionNode(functionNode);
            tcExecution.execute();
            tcExecution.showCoverage();
        }
    }
}
