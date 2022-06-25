package cfg;

import utils.Utils;

public class CFGReturnStatement extends CFGNode implements IEnableToEvaluateCoverage{
    String returnType = "";

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    @Override
    public String markContent(String testPath) {
        StringBuilder content = new StringBuilder("");
        content.append(getStart()).append(getClass().getSimpleName()).append("{StartAt:" + getStart() + ",").append("EndAt:" + getEnd());
//        content.append(toString());
        return Utils.getWriteToTestPathContent(String.valueOf(content), testPath);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
