package cfg;

import java.util.ArrayList;
import java.util.List;

public class CFGNode {
    private CFGNode parent;
    private List<CFGNode> children = new ArrayList<>();
    private boolean isVisited = false;

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
}
