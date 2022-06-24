package utils;

import node.FileNode;
import node.Node;
import structureTree.SNode;
import structureTree.structureNode.SFunctionNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchInSTree {

    public static List<SFunctionNode> searchSFunction(SNode root) {
        List<SFunctionNode> functionNodeList = new ArrayList<>();
        if (root instanceof SFunctionNode) functionNodeList.add((SFunctionNode) root);
        for (SNode child : root.getChildren()) {
            functionNodeList.addAll(searchSFunction(child));
        }
        return functionNodeList;
    }

    public static FileNode getJavaFileNode(SFunctionNode functionNode) {
        Node node = functionNode.getAst();
        while (!(node instanceof FileNode)) {
            node = node.getParent();
        }
        return (FileNode) node;
    }
}
