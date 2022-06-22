package node;

import structureTree.SNode;
import structureTree.SProjectNode;

public class ASTFileNode extends ASTNode {

    @Override
    public SNode parseToSNode() {
        SNode sNode = new SProjectNode();
        //todo: config more attribution here
        sNode.setName(getName());
        sNode.setType(getObjectType());
        return sNode;
    }
}
