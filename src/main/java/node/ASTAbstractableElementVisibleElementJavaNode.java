package node;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ASTAbstractableElementVisibleElementJavaNode extends ASTVisibleElementJavaNode {

    protected boolean isAbstract = false;

    @JsonProperty("isAbstract")
    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }
}
