package testdata.normal_datanode;

import testdata.ValueDataNode;

public abstract class NormalDataNode extends ValueDataNode {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
