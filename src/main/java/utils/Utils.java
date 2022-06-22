package utils;

import node.Node;
import org.eclipse.jdt.core.dom.*;
import structureTree.SNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static SNode parseFolderNodeToSNode(Node Node) {
//        System.out.println("parse " + ASTNode.getName());
        SNode root = Node.parseToSNode();
        for (Node child : Node.getChildren()) {
            System.out.println("parse " + child.getName());
            SNode sNodeChild = Utils.parseFolderNodeToSNode(child);
//            SNode sChild = child.parseToSNode();
            root.getChildren().add(sNodeChild);
//            sNodeChild.setParent(root);
        }
        for (SNode child : root.getChildren()) {
            child.setParent(root);
        }

        return root;
    }

    public static List<ASTNode> getChildren(org.eclipse.jdt.core.dom.ASTNode node) {
        List<ASTNode> children = new ArrayList<>();
        if (node instanceof MethodDeclaration) {
            Block block = ((MethodDeclaration) node).getBody();
            children.add(block);
//            List<Statement> statements = block.statements();
//            for (Statement statement : statements) {
//                if (statement instanceof IfStatement) {
//
//                }
//            }
        }
        else if (node instanceof TypeDeclaration) {
            List<FieldDeclaration> atributes = Arrays.asList(((TypeDeclaration) node).getFields());
            for (FieldDeclaration attribute : atributes) {
                children.add(attribute);
            }

            List<MethodDeclaration> methods = Arrays.asList(((TypeDeclaration) node).getMethods());
            for (MethodDeclaration method : methods) {
                children.add(method);
            }

        }
        else if (node instanceof Block) {
            List<Statement> statements = ((Block) node).statements();
            for (ASTNode statement : statements) {
                children.add(statement);
            }
        }
        else if (node instanceof IfStatement) {
            children.add(((IfStatement) node).getExpression());
            children.add(((IfStatement) node).getThenStatement());
            children.add(((IfStatement) node).getElseStatement());
        }
        else if (node instanceof ExpressionStatement) {
            children.add(((ExpressionStatement) node).getExpression());
        }

        return children;
    }

}
