package cfg;

import utils.Utils;

public class CFGExpressionStatement extends CFGNode {
    @Override
    public String markContent(String testPath) {
        StringBuilder content = new StringBuilder("");
        content.append(getClass().getSimpleName()).append("{StartAt:" + getStart() + ",").append("EndAt:" + getEnd());
//        content.append(toString());
        return Utils.getWriteToTestPathContent(String.valueOf(content), testPath);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
