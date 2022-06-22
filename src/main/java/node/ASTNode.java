package node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.eclipse.jdt.core.dom.CompilationUnit;
import structureTree.SNode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "json_type")

public class ASTNode implements Serializable {

    private static final long serialVersionUID = -1411216676620846129L;

    public static int countId = 0;
    protected int id;
    protected String name;
    protected int line;

    protected String absolutePath;

    protected int parentNodeId;
    protected int startPosition;
    @JsonIgnore
    protected ASTNode parent;

    protected List<ASTNode> children;


    public static int getCountId() {
        return countId;
    }

    public static void setCountId(int countId) {
        ASTNode.countId = countId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public int getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(int parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public ASTNode getParent() {
        return parent;
    }

    public void setParent(ASTNode parent) {
        this.parent = parent;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public void setChildren(List<ASTNode> children) {
        this.children = children;
    }

    public ASTNode() {
        children = new ArrayList<>();
        countId++;
        this.id = countId;
    }

    public void addChildren(List<ASTNode> children, CompilationUnit cu) {
        for (ASTNode ASTNode : children) {
            int lineNumber = cu.getLineNumber(ASTNode.getStartPosition());
            ASTNode.setLine(lineNumber);
            ASTNode.setParentNodeId(this.getId());
            ASTNode.setParent(this);
            this.children.add(ASTNode);
        }
    }

    public void addChildrenFolder(List<? extends ASTNode> children) {
        for (ASTNode ASTNode : children) {
            ASTNode.setParentNodeId(this.getId());
            ASTNode.setParent(this);
            this.children.add(ASTNode);
        }
    }

    public void setName(String name) {
        this.name = name;
        if (absolutePath == null) {
            setAbsolutePathByName();
        }
    }

    public void setAbsolutePathByName() {
        if (this.parent != null) {
            setAbsolutePath(this.parent.absolutePath + File.separator + this.name);
        }
    }

    /**
     * Get relative path of node
     *
     * @return relative path of node
     */
    @JsonIgnore
    public String getRelativePath() {
        ASTNode root = getRootNode(this);
        if (root != null) {
            return getAbsolutePath().replace(root.getAbsolutePath() + File.separator, "");
        } else return getAbsolutePath();
    }

    /**
     * Get root node of project which contains given input node
     *
     * @param n
     * @return
     */
    public ASTNode getRootNode(ASTNode n) {
        if (n.getParent() != null) {
            return getRootNode(n.getParent());
        }
        return n;
    }

    /**************************************
     * Use getters, setters instead of direct attribute assignment
     * for following methods (due to these methods are not
     * overridden by derive Decorator)
     ***************************************/

    @JsonIgnore
    public List<ASTNode> getAllChildren() {
        return doGetAllChildren(this);
    }

    private List<ASTNode> doGetAllChildren(ASTNode rootASTNode) {
        List<ASTNode> allChildren = new ArrayList<>();
        for (ASTNode child : rootASTNode.getChildren()) {
            allChildren.add(child);
            allChildren.addAll(doGetAllChildren(child));
        }
        return allChildren;
    }

    /******
     *Get Type of ASTNode
     *
     *
     */
    //protected string type;
    @JsonIgnore
    public String getObjectType() {
        Class<?> enclosingClass = this.getClass().getEnclosingClass();
        String nodeClass = enclosingClass != null ? enclosingClass.getSimpleName() : this.getClass().getSimpleName();
        return nodeClass;
    }

    @Override
    public String toString() {
        return "ASTNode{" +
                "name='" + name + '\'' +
                '}';
    }

    public SNode parseToSNode() {
        return null;
    }
}
