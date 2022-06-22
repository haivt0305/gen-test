package parser;

import cfg.CFGNode;
import node.ASTClassAbstractableElementVisibleElementJavaNode;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;

public class JavaFileParser {

    public static ArrayList<ASTClassAbstractableElementVisibleElementJavaNode> parse(String sourceCode) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(sourceCode.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        ArrayList<ASTClassAbstractableElementVisibleElementJavaNode> classes = new ArrayList<>();

        cu.accept(new ASTVisitor() {
            public boolean visit(TypeDeclaration node) {
                ASTClassAbstractableElementVisibleElementJavaNode classNode = new ASTClassAbstractableElementVisibleElementJavaNode();
                if (node != null) {
                    classNode.setInforFromASTNode(node, cu);
                    classes.add(classNode);
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
            public boolean visit(Block node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(BreakStatement node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(CastExpression node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(ClassInstanceCreation node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(ConditionalExpression node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(DoStatement node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(EmptyStatement node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(ExpressionMethodReference node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(ExpressionStatement node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(FieldDeclaration node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(ForStatement node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(IfStatement node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(ImportDeclaration node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(InfixExpression node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(NumberLiteral node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(ReturnStatement node) {
                return super.visit(node);
            }


            @Override
            public boolean visit(ThisExpression node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(ThrowStatement node) {
                return super.visit(node);
            }

            @Override
            public boolean visit(TryStatement node) {
                return super.visit(node);
            }
        };

        cu.accept(visitor);

        return cfg;
    }



}
