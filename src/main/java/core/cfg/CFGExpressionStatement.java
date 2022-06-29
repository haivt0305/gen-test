package core.cfg;

import core.utils.Utils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IfStatement;

public class CFGExpressionStatement extends CFGNode implements IEnableToEvaluateCoverage {
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

    @Override
    public String getContentReport() {
        ASTNode ast = getAst();
        if (ast instanceof IfStatement) return ((IfStatement) ast).getExpression().toString();
        return getContent();
    }
}
