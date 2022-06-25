package parser;

import cfg.CFGNode;
import node.ClassAbstractableElementVisibleElementJavaNode;
import node.FileNode;
import node.Node;
import node.FolderNode;
import structureTree.SNode;
import utils.Utils;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProjectParser {

    private FolderNode folderNode = new FolderNode();
    private String projectPath;
    private static ProjectParser parser = null;


    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public static ProjectParser getParser() {
        if (parser == null){
            parser = new ProjectParser();
        }
        return parser;
    }

    public FolderNode getFolderNode() {
        return folderNode;
    }

    public void setFolderNode(FolderNode folderNode) {
        this.folderNode = folderNode;
    }


    public void doParsing(String path, int level, Node parent) throws IOException {

        setProjectPath(new File(path).getAbsolutePath());
        FolderNode folderNode = new FolderNode();
        folderNode.setName(new File(path).getName());
        if (parent != null) {
            parent.getChildren().add(folderNode);
            folderNode.setParent(parent);
        }
        File mainDir = new File(path);
        folderNode.setAbsolutePath(mainDir.getAbsolutePath());
        if (mainDir.exists() && mainDir.isDirectory()) {

//            for (File file : mainDir.listFiles()) {
//                doParsing(file.getAbsolutePath(), level + 1, folderNode);
//            }
            File[] arr = mainDir.listFiles();
//
//            if (Objects.isNull(arr)) {
//                return;
//            }
//
//            // for-each loop for main directory files
            for (File f : arr) {
                if (f.isFile() && f.getName().endsWith(".java")) {
                    String fileToString = FileService.readFileToString(f.getPath());
                    FileNode fileNode = new FileNode();
                    fileNode.setAbsolutePath(f.getAbsolutePath());
                    fileNode.setName(f.getName());
                    fileNode.setParent(folderNode);
                    folderNode.getChildren().add(fileNode);

                    List<ClassAbstractableElementVisibleElementJavaNode> classes = JavaFileParser.parse(fileToString);
                    CFGNode cfgNode = CFGNode.parserToCFG(fileToString);
                    fileNode.addChildrenFolder(classes);
                    fileNode.setCfg(cfgNode);
                }

                else if (f.isDirectory()) {
                    FolderNode childFolder = new FolderNode();
                    childFolder.setName(f.getName());
                    ProjectParser projectParser = new ProjectParser();
                    projectParser.setFolderNode(childFolder);
                    projectParser.doParsing(f.getPath(), level + 1, null);
                    folderNode.getChildren().add(childFolder);
                }
            }
        }
        this.setFolderNode(folderNode);
    }

    public static SNode parse(String projectPath) {
        ProjectParser parser = ProjectParser.getParser();
        FolderNode folderNode = null;
        try {
            parser.doParsing(projectPath, 0, null);
            folderNode = parser.getFolderNode();
            folderNode.setChildrenAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SNode root = Utils.parseFolderNodeToSNode(folderNode);
        root.setAbsolutePath(folderNode.getAbsolutePath());
        root.setChildrenAbsolutePath();
        return root;
    }
}
