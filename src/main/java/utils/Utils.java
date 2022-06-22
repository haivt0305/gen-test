package utils;

import node.ASTNode;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import structureTree.SNode;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static SNode parseFolderNodeToSNode(ASTNode ASTNode) {
//        System.out.println("parse " + ASTNode.getName());
        SNode root = ASTNode.parseToSNode();
        for (ASTNode child : ASTNode.getChildren()) {
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

    public static void getChildren(org.eclipse.jdt.core.dom.ASTNode node) {
        if (node != null) {
            List<org.eclipse.jdt.core.dom.ASTNode> children = new ArrayList<>();
            List list = node.structuralPropertiesForType();
            for (int i = 0; i < list.size(); i++) {
                Object child = node.getStructuralProperty((StructuralPropertyDescriptor) list.get(i));
                if (child instanceof org.eclipse.jdt.core.dom.ASTNode) {
                    children.add((org.eclipse.jdt.core.dom.ASTNode) child);
                }
                if (children.get(0) != null) {
                    String c = children.toString();
//                    results.append("Children Node: " + c + "\n");
                    System.out.println("Children Node: " + c + "\n");
                    getChildren(children.get(0));
                }
            }
        }    else {
            return;
        }
    }
}
