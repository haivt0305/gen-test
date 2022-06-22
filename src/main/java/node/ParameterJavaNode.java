package node;

import lombok.Getter;
import lombok.Setter;
import structureTree.SNode;
import structureTree.normalNode.SNormalCharNode;
import structureTree.normalNode.SNormalDoubleNode;
import structureTree.normalNode.SNormalFloatNode;
import structureTree.normalNode.SNormalIntegerNode;

@Getter
@Setter
public class ParameterJavaNode extends JavaNode {
    protected String type;
    protected boolean isFinal;

    public ParameterJavaNode() {

    }

    public ParameterJavaNode(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ASTParameterJavaNode{" +
                "type='" + type + '\'' +
                '}';
    }

    @Override
    public SNode parseToSNode() {
        SNode sNode = null;
        String type = getType();
        switch (type) {
            case "int": {
                sNode = new SNormalIntegerNode();
                //todo: config here

            }
            case "char" : {
                sNode = new SNormalCharNode();
                //todo: config here

            }
            case "float" : {
                sNode = new SNormalFloatNode();
                //todo: config here

            }
            case "double": {
                sNode = new SNormalDoubleNode();
                //todo: config here

            }
            default: {

            }
        }
        sNode.setName(getName());
        sNode.setType(getType());
        return sNode;
    }
}
