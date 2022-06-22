package structureTree.structureNode;

import node.ASTMethodAbstractableElementVisibleElementJavaNode;

public class SFunctionNode extends SAbstractableNode {

    private ASTMethodAbstractableElementVisibleElementJavaNode ast;

    public ASTMethodAbstractableElementVisibleElementJavaNode getAst() {
        return ast;
    }

    public void setAst(ASTMethodAbstractableElementVisibleElementJavaNode ast) {
        this.ast = ast;
    }
}
