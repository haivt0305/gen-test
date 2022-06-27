package core.utils;

import core.cfg.CFGNode;
import core.cfg.IEnableToEvaluateCoverage;

import java.util.ArrayList;
import java.util.List;

public class CFGUtils {
    public static CFGNode findCFGNodeByStart(CFGNode root, int start) {
        if (root.getStart() == start) return root;
        for (CFGNode child : root.getChildren()) {
            CFGNode find = findCFGNodeByStart(child, start);
            if (find != null) return find;
        }
        return null;
    }

    public static List<IEnableToEvaluateCoverage> findListEvaluateCFGNode(CFGNode root) {
        List<IEnableToEvaluateCoverage> list = new ArrayList<>();
        if (root instanceof IEnableToEvaluateCoverage) list.add((IEnableToEvaluateCoverage) root);
        for (CFGNode child : root.getChildren()) {
            list.addAll(findListEvaluateCFGNode(child));
        }
        return list;
    }
}
