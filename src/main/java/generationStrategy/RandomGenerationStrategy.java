package generationStrategy;

import com.sun.istack.internal.NotNull;
import structureTree.SNode;
import structureTree.normalNode.*;
import structureTree.structureNode.SAbstractableNode;
import structureTree.structureNode.SClassNode;
import structureTree.structureNode.SFunctionNode;
import structureTree.structureNode.SStructNode;
import testcases.TestCase;
import testdata.*;
import testdata.normal_datanode.*;
import testdata.structure_datanode.ClassDataNode;
import testdata.structure_datanode.StructDataNode;
import utils.SearchInSTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenerationStrategy extends GenerationStrategy {

    private Random random = new Random();

    public List<TestCase> generate(SFunctionNode functionNode) {
        List<TestCase> testCaseList = new ArrayList<>();
        //todo:Code your strategy here
        TestCase testCase = new TestCase(functionNode);
        RootDataNode rootDataNode = new RootDataNode();
        GlobalDataNode globalDataNode = new GlobalDataNode();
        UnitUnderTestDataNode unitUnderTestDataNode = new UnitUnderTestDataNode(functionNode);

        rootDataNode.getChildren().add(globalDataNode);
        rootDataNode.getChildren().add(unitUnderTestDataNode);
        generateGlobalDataNode(functionNode, globalDataNode);
        generateDataForDataNode(functionNode, unitUnderTestDataNode);

        testCase.setRootDataNode(new RootDataNode());
        return testCaseList;
    }

    public void generateDataForDataNode(SNode sNode, @NotNull DataNode dataNode) {
        if (dataNode == null) {
            dataNode = getSuitableDataNode(sNode);
        }
        for (int i = 0; i < sNode.getChildren().size(); i++) {
            DataNode child = getSuitableDataNode(sNode.getChildren().get(i));
            dataNode.getChildren().add(child);
            generateDataForDataNode(sNode.getChildren().get(i), child);
        }
        if (dataNode instanceof UnitUnderTestDataNode) {
            ReturnDataNode returnDataNode = new ReturnDataNode();
            String type = ((UnitUnderTestDataNode)dataNode).getFunctionNode().getAst().getReturnType();
            returnDataNode.setType(type);
            if (!type.equals("")) {
                returnDataNode.setValue(generateRandomValue(returnDataNode));
                dataNode.getChildren().add(returnDataNode);
            }
        }
    }

    private DataNode getSuitableDataNode(SNode sNode) {
        DataNode dataNode = null;
        if (sNode instanceof SNormalNode) {
            if (sNode instanceof SNormalCharNode) {
                dataNode = new NormalCharDataNode();
            }
            else if (sNode instanceof SNormalIntegerNode) {
                dataNode = new NormalIntDataNode();
            }
            else if (sNode instanceof SNormalDoubleNode) {
                dataNode = new NormalDoubleDataNode();
            } else if (sNode instanceof SNormalFloatNode) {
                dataNode = new NormalFloatDataNode();
            }
            dataNode.setName(sNode.getName());
            dataNode.setType(sNode.getType());
            if (dataNode instanceof NormalDataNode) {
                ((NormalDataNode)dataNode).setValue(generateRandomValue(dataNode));
            }
        }
        else if (sNode instanceof SAbstractableNode) {
            if (sNode instanceof SClassNode) {
                dataNode = new ClassDataNode();
            }
            else if (sNode instanceof SStructNode) {
                dataNode = new StructDataNode();
            }
            else if (sNode instanceof SFunctionNode) {
                dataNode = new UnitUnderTestDataNode((SFunctionNode) sNode);
            }

        }
        return dataNode;
    }

    public void generateGlobalDataNode(SFunctionNode functionNode, GlobalDataNode globalDataNode) {
        SNode parent = functionNode.getParent();
        for (SNode child : parent.getChildren()) {
            if (child instanceof SFunctionNode) {
                continue;
            }
            DataNode dataNode = getSuitableDataNode(child);
            generateDataForDataNode(child, dataNode);
            globalDataNode.getChildren().add(dataNode);
        }
    }

    public String generateRandomValue(DataNode dataNode) {
        String type = dataNode.getType();
        switch (type) {
            case "int": return String.valueOf(random.nextInt());
            case "double": return String.valueOf(random.nextDouble());
            case "float": return String.valueOf(random.nextFloat());
            case "char": return String.valueOf(random.nextInt(124));
            default: return "";
        }
    }
}
