package parser;

import cfg.CFGNode;
import node.ClassAbstractableElementVisibleElementJavaNode;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class JavaFileParser {

    public static ArrayList<ClassAbstractableElementVisibleElementJavaNode> parse(String sourceCode) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(sourceCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        ArrayList<ClassAbstractableElementVisibleElementJavaNode> classes = new ArrayList<>();
        cu.accept(new ASTVisitor() {
            public boolean visit(TypeDeclaration node) {
                ClassAbstractableElementVisibleElementJavaNode classNode = new ClassAbstractableElementVisibleElementJavaNode();
                if (node != null) {
                    classNode.setInforFromASTNode(node, cu);
                    classes.add(classNode);
                    CFGNode cfg = new CFGNode();
                    ASTHelper.generateCFGTreeFromASTNode(node, cfg);
                    return false;
                }
                return true;
            }

        });
        ASTVisitor visitor = new ASTVisitor() {
            @Override
            public boolean visit(IfStatement node) {
                return super.visit(node);
            }
        };
        cu.accept(visitor);
        return classes;
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



}
