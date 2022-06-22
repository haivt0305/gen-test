package structureTree;

import node.FolderNode;
import parser.ProjectParser;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class SNode {

    private List<SNode> children = new ArrayList<>();
    private SNode parent = null;
    private String name;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SNode getParent() {
        return parent;
    }

    public void setParent(SNode parent) {
        this.parent = parent;
    }

    public List<SNode> getChildren() {
        return children;
    }

    public void setChildren(List<SNode> children) {
        this.children = children;
    }


    public static SNode parse(String projectPath) {
        ProjectParser parser = ProjectParser.getParser();
        FolderNode folderNode = null;
        try {
            parser.doParsing(projectPath, 0, null);
            folderNode = parser.getFolderNode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SNode root = Utils.parseFolderNodeToSNode(folderNode);
//        root.refreshParent();
        return root;
    }

    private void refreshParent() {
        for (SNode node : getChildren()) {
            node.setParent(this);
            node.refreshParent();
        }
    }

    private String info() {
        return "{name='" + name + '\'' +
                '}' + "\n";
    }

    public String getInfo() {
        String info = info();
        for (SNode child : getChildren()) {
            info += child.getInfo();
        }
        return info;
    }
}
