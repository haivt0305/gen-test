package core.utils;

import core.cfg.CFGNode;
import core.cfg.CFGReturnStatement;
import core.cfg.IEnableToEvaluateCoverage;

import java.util.ArrayList;
import java.util.Comparator;
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

    public static List<CFGReturnStatement> findCFGReturnStatementDecreaseSort(CFGNode root) {
        List<CFGReturnStatement> list = new ArrayList<>();
        if (root instanceof CFGReturnStatement) list.add((CFGReturnStatement) root);
        for (CFGNode child : root.getChildren()) {
            list.addAll(findCFGReturnStatementDecreaseSort(child));
        }
        list.sort(new Comparator<CFGReturnStatement>() {
            @Override
            public int compare(CFGReturnStatement o1, CFGReturnStatement o2) {
                if (o1.getStart() == o2.getStart()) return 0;
                return (o1.getStart() > o2.getStart()) ? -1 : 1;
            }
        });
        return list;
    }
}
