package node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.jdt.core.dom.*;
import parser.ASTHelper;
import parser.Convert;
import structureTree.SNode;
import structureTree.structureNode.SClassNode;
import structureTree.structureNode.SInterfaceNode;


import java.util.ArrayList;
import java.util.List;



public class ASTClassAbstractableElementVisibleElementJavaNode extends ASTAbstractableElementVisibleElementJavaNode {

    protected boolean isInterface;
    protected String parentClass;
    protected List<String> interfaceList;
    protected String qualifiedName;

    protected int numOfmethod;
    protected int numOfvariable;
    protected int line;

    public String getParentClass() {
        return parentClass;
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    public List<String> getInterfaceList() {
        return interfaceList;
    }

    public void setInterfaceList(List<String> interfaceList) {
        this.interfaceList = interfaceList;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public int getNumOfmethod() {
        return numOfmethod;
    }

    public void setNumOfmethod(int numOfmethod) {
        this.numOfmethod = numOfmethod;
    }

    public int getNumOfvariable() {
        return numOfvariable;
    }

    public void setNumOfvariable(int numOfvariable) {
        this.numOfvariable = numOfvariable;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public void setLine(int line) {
        this.line = line;
    }

    public ASTClassAbstractableElementVisibleElementJavaNode() {
        super();
        interfaceList = new ArrayList<>();
    }

    @JsonProperty("isInterface")
    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    @JsonIgnore
    public List<ASTMethodAbstractableElementVisibleElementJavaNode> getMethodList() {
        List<ASTMethodAbstractableElementVisibleElementJavaNode> result = new ArrayList<>();
        for (ASTNode child : this.getChildren()) {
            if (child instanceof ASTMethodAbstractableElementVisibleElementJavaNode)
                result.add((ASTMethodAbstractableElementVisibleElementJavaNode) child);
        }
        return result;
    }

    @JsonIgnore
    public List<ASTFieldVisibleElementJavaNode> getFieldList() {
        List<ASTFieldVisibleElementJavaNode> result = new ArrayList<>();
        for (ASTNode child : this.getChildren()) {
            if (child instanceof ASTFieldVisibleElementJavaNode)
                result.add((ASTFieldVisibleElementJavaNode) child);
        }
        return result;
    }

    @Override
    public String toString() {
        return "ASTClassAbstractableElementVisibleElementJavaNode {" +
                "visibility=" + this.getVisibility() +
//                ", type='" + type + '\'' +
                ", name=" + this.name +
                ", isStatic=" + this.isStatic() +
                ", isAbstract=" + this.isAbstract() +
                ", isFinal=" + this.isFinal() +
                ", isInterface=" + isInterface +
                ", parentClass='" + parentClass + '\'' +
                ", interfaceList=" + interfaceList +
                '}';
    }

    public void setInforFromASTNode(TypeDeclaration node, CompilationUnit cu) {
        if (node.isInterface() == true) this.setInterface(true);
        this.setStartPosition(node.getStartPosition());
        //lay ten
        if (node.getName() != null) {
            if (node.getName().getIdentifier() != null) {
                String name = node.getName().getIdentifier();
                this.setName(name);
            }
        }

        PackageDeclaration packageDeclaration = cu.getPackage();
        if (packageDeclaration != null) {
            if (packageDeclaration.getName() != null) {
                if (packageDeclaration.getName().getFullyQualifiedName() != null) {
                    this.setQualifiedName(packageDeclaration.getName().getFullyQualifiedName() + "." + name);
                }
            }
        } else {
            this.setQualifiedName(name);
        }

        this.setVisibility(DEFAULT_MODIFIER);
        List modifiers = node.modifiers();
        if (modifiers.size() == 0) {
            this.setVisibility(DEFAULT_MODIFIER);
        } else {
            for (Object o : modifiers) {
                if (o instanceof Modifier) {
                    Modifier m = (Modifier) o;
                    if (m.getKeyword() != null) {
                        if (m.getKeyword().toFlagValue() == Modifier.ModifierKeyword.PUBLIC_KEYWORD.toFlagValue()) {
                            this.setVisibility(PUBLIC_MODIFIER);
                        } else if (m.getKeyword().toFlagValue() == Modifier.ModifierKeyword.STATIC_KEYWORD.toFlagValue()) {
                            this.setStatic(true);
                        } else if (m.getKeyword().toFlagValue() == Modifier.ModifierKeyword.ABSTRACT_KEYWORD.toFlagValue()) {
                            this.setAbstract(true);
                        } else if (m.getKeyword().toFlagValue() == Modifier.ModifierKeyword.FINAL_KEYWORD.toFlagValue()) {
                            this.setFinal(true);
                        } else if (m.getKeyword().toFlagValue() == Modifier.ModifierKeyword.PRIVATE_KEYWORD.toFlagValue()) {
                            this.setVisibility(PRIVATE_MODIFIER);
                        } else if (m.getKeyword().toFlagValue() == Modifier.ModifierKeyword.PROTECTED_KEYWORD.toFlagValue()) {
                            this.setVisibility(PROTECTED_MODIFIER);
                        } else {
                            this.setVisibility(DEFAULT_MODIFIER);
                        }

                    }
                }
            }
        }

        FieldDeclaration[] fieldList = node.getFields();
        List<ASTNode> fieldASTNodes = Convert.convertASTListNodeToFieldNode(fieldList);
        this.addChildren(fieldASTNodes, cu);
        MethodDeclaration[] methodList = node.getMethods();
        List<ASTNode> methodASTNodes = Convert.convertASTListNodeToMethodNode(methodList, cu);

        this.addChildren(methodASTNodes, cu);

        //TODO lay cac class con ben trong
        TypeDeclaration[] classList = node.getTypes();
        List<ASTNode> innerClassASTNode = Convert.convertASTListNodeToClassNode(classList, cu);
        this.addChildren(innerClassASTNode, cu);

        //lay superClass
        Type superClassType = node.getSuperclassType();
        if (superClassType != null && superClassType instanceof SimpleType) {
            SimpleType superSimpleClassType = (SimpleType) superClassType;
            if (superSimpleClassType.getName() != null) {
                String fullname = ASTHelper.getFullyQualifiedName(superClassType, cu);
                this.setParentClass(fullname);
            }
        }

        //lay danh sach cac superInterface
        List superInterfaceList = node.superInterfaceTypes();
        if (superInterfaceList.size() > 0) {
            ArrayList<String> interfaceNameList = new ArrayList<String>();
            for (Object o : superInterfaceList) {
                if (o instanceof SimpleType) {
                    SimpleType intefaceType = (SimpleType) o;
                    String fullInterfaceName = ASTHelper.getFullyQualifiedName(intefaceType, cu);
                    interfaceNameList.add(fullInterfaceName);
                }
            }
            this.setInterfaceList(interfaceNameList);
        }

    }

    @Override
    public SNode parseToSNode() {
        SNode sNode = null;
        if (isInterface) {
            sNode = new SInterfaceNode();
            sNode.setType(getObjectType());
        }
        else sNode = new SClassNode();
        sNode.setName(getName());
        sNode.setType(getObjectType());
        //todo: config here

        return sNode;
    }
}
