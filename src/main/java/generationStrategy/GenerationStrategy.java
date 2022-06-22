package generationStrategy;

import structureTree.structureNode.SFunctionNode;
import testcases.TestCase;

import java.util.ArrayList;
import java.util.List;

public abstract class GenerationStrategy {

    private List<TestCase> listTestCaseGenerated = new ArrayList<>();

    public abstract List<TestCase> generate(SFunctionNode functionNode);

    public List<TestCase> getListTestCaseGenerated() {
        return listTestCaseGenerated;
    }

    public void setListTestCaseGenerated(List<TestCase> listTestCaseGenerated) {
        this.listTestCaseGenerated = listTestCaseGenerated;
    }
}
