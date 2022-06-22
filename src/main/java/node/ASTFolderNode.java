package node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import structureTree.SNode;
import structureTree.SProjectNode;


import java.util.ArrayList;
import java.util.List;


public class ASTFolderNode extends ASTNode {

    @JsonIgnore
    public List<ASTClassAbstractableElementVisibleElementJavaNode> getClassNodes() {
        List<ASTClassAbstractableElementVisibleElementJavaNode> result = new ArrayList<>();
        for (ASTNode child : this.getChildren()) {
            if (child instanceof ASTClassAbstractableElementVisibleElementJavaNode)
                result.add((ASTClassAbstractableElementVisibleElementJavaNode) child);
            else if (child instanceof ASTFolderNode) {
                result.addAll(((ASTFolderNode) child).getClassNodes());
            }
        }
        return result;
    }

    @JsonIgnore
    public ArrayList<ASTRelationshipNode> getClassRelationships() {
        ArrayList<ASTRelationshipNode> ASTRelationshipNodeList = new ArrayList<ASTRelationshipNode>();
        List<ASTClassAbstractableElementVisibleElementJavaNode> classes = this.getClassNodes();

        for (ASTClassAbstractableElementVisibleElementJavaNode cd : classes) {
            if (cd.getParentClass() != null) {
                int keySuperClass = this.findIdByQualifiedName(cd.getParentClass(), classes);
                if (keySuperClass != -1) {
                    ASTRelationshipNode r = new ASTRelationshipNode();
                    r.setFrom(cd.getId());
                    r.setTo(keySuperClass);
                    r.setRelationship(ASTRelationshipNode.CLASS_EXTENSION);
                    ASTRelationshipNodeList.add(r);
                }
            }

            if (cd.getInterfaceList() != null) {
                for (String s : cd.getInterfaceList()) {
                    int keySuperInterface = this.findIdByQualifiedName(s, classes);
                    if (keySuperInterface != -1) {
                        ASTRelationshipNode r = new ASTRelationshipNode();
                        r.setFrom(cd.getId());
                        r.setTo(keySuperInterface);
                        if (cd.isInterface()) r.setRelationship(ASTRelationshipNode.CLASS_EXTENSION);
                        else r.setRelationship(ASTRelationshipNode.CLASS_IMPLEMENTATION);
                        ASTRelationshipNodeList.add(r);
                    }
                }
            }
        }
        return ASTRelationshipNodeList;
    }

    public int findIdByQualifiedName(String name, List<ASTClassAbstractableElementVisibleElementJavaNode> classes) {
        if (classes.size() > 0) {
            for (ASTClassAbstractableElementVisibleElementJavaNode cd : classes) {
                if (name.equals(cd.getQualifiedName())) return cd.getId();
            }
            return -1;
        }
        return -1;
    }

    @Override
    public SNode parseToSNode() {
        SNode sNode = new SProjectNode();
        //todo: config more attribution here
        sNode.setName(getName());
        sNode.setType(getObjectType());
        return sNode;
    }
}
