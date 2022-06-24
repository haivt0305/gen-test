package testexecution;

import cfg.CFGNode;
import parser.ProjectParser;
import structureTree.structureNode.SFunctionNode;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Instrument {
    private String sourcePath;
    private CFGNode cfgNode;
    private String instrumentPath;
    private SFunctionNode functionNode;

    private File instrumentFolder;

    public Instrument(String sourcePath, CFGNode cfgNode, SFunctionNode functionNode) {
        this.sourcePath = sourcePath;
        this.cfgNode = cfgNode;
        this.functionNode = functionNode;
    }

    public SFunctionNode getFunctionNode() {
        return functionNode;
    }

    public void setFunctionNode(SFunctionNode functionNode) {
        this.functionNode = functionNode;
    }

    public File getInstrumentFolder() {
        return instrumentFolder;
    }

    public void setInstrumentFolder(File instrumentFolder) {
        this.instrumentFolder = instrumentFolder;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public CFGNode getCfgNode() {
        return cfgNode;
    }

    public void setCfgNode(CFGNode cfgNode) {
        this.cfgNode = cfgNode;
    }

    public String getInstrumentPath() {
        return instrumentPath;
    }

    public void setInstrumentPath(String instrumentPath) {
        this.instrumentPath = instrumentPath;
    }

    public void markInstrument() throws IOException {
        File source = new File(sourcePath);
        createInstrumentPath();
        File clone = new File(instrumentPath);
        clone.setWritable(true);

    }

    private void createInstrumentPath() throws IOException {
        File folder = new File(sourcePath).getParentFile();
        String projectPath = ProjectParser.getParser().getProjectPath();

        File workspace = new File(new File(projectPath).getParentFile().getAbsolutePath() + "/JGT-workspace");
        if (workspace.mkdir()) {
            System.out.println("Create workspace successful");
        }
        else System.out.println("Workspace is existed");
        File instrumentFolder = new File(workspace.getAbsolutePath() + "/instrument");
        if (instrumentFolder.mkdir()) {
            System.out.println("Create instrument folder successful");
        }
        else System.out.println("Instrument folder is existed");
        setInstrumentFolder(instrumentFolder);

        File clone = new File(instrumentFolder.getAbsolutePath() + "/" + functionNode.getName() + "_clone.java");
        if (clone.createNewFile()) {
            System.out.println("Create instrument function successful");
        }
        else System.out.println("Instrument function is existed");
        setInstrumentPath(clone.getAbsolutePath());
    }

    public void generateTestPath() {
        StringBuilder content = new StringBuilder("");
        List<CFGNode> listCFG = transferCFGTreeToList(cfgNode);

        String sourceContent = Utils.readFileContent(sourcePath);
        String cloneContent = "";


        File source = new File(sourcePath);
        File clone = new File(instrumentPath);
        System.out.println();
    }

    public List<CFGNode> transferCFGTreeToList(CFGNode rootCFG) {
        List<CFGNode> list = new ArrayList<>();
        List<CFGNode> children = rootCFG.getChildren();
        for (int i = children.size() - 1; i >= 0; i--) {
            list.addAll(transferCFGTreeToList(children.get(i)));
        }
        list.add(rootCFG);
        return list;
    }

}
