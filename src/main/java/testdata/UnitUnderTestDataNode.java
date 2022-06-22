package testdata;

import structureTree.structureNode.SFunctionNode;

public class UnitUnderTestDataNode extends DataNode {
    private SFunctionNode functionNode;

    public SFunctionNode getFunctionNode() {
        return functionNode;
    }

    public void setFunctionNode(SFunctionNode functionNode) {
        this.functionNode = functionNode;
    }

    public UnitUnderTestDataNode(SFunctionNode functionNode) {
        this.functionNode = functionNode;
        setName(functionNode.getName());
        setType("Unit under test");
    }
}
