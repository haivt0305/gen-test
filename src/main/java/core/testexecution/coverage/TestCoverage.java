package core.testexecution.coverage;

import core.cfg.CFGNode;
import core.cfg.IEnableToEvaluateCoverage;
import core.testexecution.TestExecution;
import core.utils.CFGUtils;

import java.util.List;

public class TestCoverage {
    private TestExecution execution;
    private double totalNode;
    private double passNode;
    private double result;

    public TestCoverage(TestExecution execution) {
        this.execution = execution;
        evaluate();
    }

    public TestExecution getExecution() {
        return execution;
    }

    public void setExecution(TestExecution execution) {
        this.execution = execution;
    }

    public double getTotalNode() {
        return totalNode;
    }

    public void setTotalNode(double totalNode) {
        this.totalNode = totalNode;
    }

    public double getPassNode() {
        return passNode;
    }

    public void setPassNode(double passNode) {
        this.passNode = passNode;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public void evaluate() {
        if (execution == null) return;
        CFGNode cfg = execution.getRootCFG();
        List<IEnableToEvaluateCoverage> totalList = CFGUtils.findListEvaluateCFGNode(cfg);
        int countPass = 0;
        for (IEnableToEvaluateCoverage node : totalList) {
            if (node instanceof CFGNode) {
                if (((CFGNode) node).isVisited()) countPass++;
            }
        }
        setTotalNode(totalList.size());
        setPassNode(countPass);
        setResult(passNode/totalNode);
    }
}
