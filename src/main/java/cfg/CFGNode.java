package cfg;

import org.eclipse.jdt.core.dom.*;
import parser.ASTHelper;
import structureTree.structureNode.SFunctionNode;

import java.util.ArrayList;
import java.util.List;

public class CFGNode {
    private int start;
    private int end;
    private String content = "";

    public CFGNode(ASTNode ast) {
        this.ast = ast;
        setStart(ast.getStartPosition());
        setEnd(ast.getStartPosition() + ast.getLength());
    }

    public ASTNode getAst() {
        return ast;
    }

    public void setAst(ASTNode ast) {
        this.ast = ast;
    }

    private ASTNode ast;
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    private CFGNode parent;
    private List<CFGNode> children = new ArrayList<>();
    private boolean isVisited = false;

    public CFGNode(){}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CFGNode getParent() {
        return parent;
    }

    public void setParent(CFGNode parent) {
        this.parent = parent;
    }

    public List<CFGNode> getChildren() {
        return children;
    }

    public void setChildren(List<CFGNode> children) {
        this.children = children;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public static CFGNode parseToCFG(SFunctionNode functionNode) {
        ASTNode astNode = functionNode.getAst().getAstNode();
        CFGNode cfgNode = new CFGStartNode(astNode);
        ASTHelper.generateCFGTreeFromASTNode(astNode, cfgNode);
        return cfgNode;
    }

    public static CFGNode parserToCFG(String sourceCode) {
        CFGNode cfg = new CFGNode();

        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(sourceCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        ASTVisitor visitor = new ASTVisitor() {
            @Override
            public boolean visit(TypeDeclaration node) {
                ASTHelper.generateCFGTreeFromASTNode(node, cfg);
                return true;
            }
        };

        cu.accept(visitor);

        return cfg;
    }

    public String markContent() {
        return "";
    }
}
