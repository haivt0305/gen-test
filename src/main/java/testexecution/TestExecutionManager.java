package testexecution;

public class TestExecutionManager {

    public static TestCoverage generateTestCoverage(TestExecution execution) {
        if (execution.getStatus().equals(TestExecution.SUCCESS)) {
            TestCoverage coverage = new TestCoverage(execution);
            return coverage;
        }
        return null;
    }
}
