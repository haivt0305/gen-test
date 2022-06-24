package cfg;

public class CFGIfStatementNode extends CFGNode{
    @Override
    public String markContent() {
        StringBuilder content = new StringBuilder("");
        content.append(getClass().toString() + "===").append("{StartAt:" + getStart() + "===").append("EndAt:" + getEnd() + "===");
        return super.markContent();
    }
}
