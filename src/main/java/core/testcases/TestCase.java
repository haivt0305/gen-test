package core.testcases;

import core.structureTree.structureNode.SFunctionNode;
import core.testdata.RootDataNode;

public class TestCase {
    private RootDataNode rootDataNode;
    private SFunctionNode functionNode;
    private String nameOfTestcase;
    private int IDofTestcase;
    private String status = "N_A";
    private String testPath;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestPath() {
        return testPath;
    }

    public void setTestPath(String testPath) {
        this.testPath = testPath;
    }

    public int getIDofTestcase() {
        return IDofTestcase;
    }

    public void setIDofTestcase(int IDofTestcase) {
        this.IDofTestcase = IDofTestcase;
    }

    public String getNameOfTestcase() {
        return nameOfTestcase;
    }

    public void setNameOfTestcase(String nameOfTestcase) {
        this.nameOfTestcase = nameOfTestcase;
    }

    public TestCase(SFunctionNode functionNode) {
        this.functionNode = functionNode;
    }

    public RootDataNode getRootDataNode() {
        return rootDataNode;
    }

    public void setRootDataNode(RootDataNode rootDataNode) {
        this.rootDataNode = rootDataNode;
    }

    public SFunctionNode getFunctionNode() {
        return functionNode;
    }

    public void setFunctionNode(SFunctionNode functionNode) {
        this.functionNode = functionNode;
    }
}
