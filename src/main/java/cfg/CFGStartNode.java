package cfg;

import org.eclipse.jdt.core.dom.ASTNode;

public class CFGStartNode extends CFGNode{

    public CFGStartNode(){}
    public CFGStartNode(ASTNode ast) {
        super(ast);
    }
}
