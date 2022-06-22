import generationStrategy.RandomGenerationStrategy;
import parser.ProjectParser;
import structureTree.SNode;
import structureTree.structureNode.SFunctionNode;
import testcases.TestCase;
import testcases.TestcaseManager;
import utils.SearchInSTree;

import java.io.IOException;
import java.util.List;

public class AppStart {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\haivt\\Desktop\\gen-test-main\\data";
        System.out.println("Start parsing...");
        SNode root = SNode.parse(path);
        System.out.println(root.getInfo());
        List<SFunctionNode> functionNodeList = SearchInSTree.searchSFunction(root);
        for (SFunctionNode functionNode : functionNodeList) {
            List<TestCase> testCaseList = TestcaseManager.createTestcase(functionNode, new RandomGenerationStrategy());
        }
    }
}
