package parser;

import cfg.CFGNode;
import node.ASTClassAbstractableElementVisibleElementJavaNode;
import node.ASTNode;
import node.ASTFolderNode;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProjectParser {

    private ASTFolderNode folderNode = new ASTFolderNode();
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

    public ASTFolderNode getFolderNode() {
        return folderNode;
    }

    public void setFolderNode(ASTFolderNode folderNode) {
        this.folderNode = folderNode;
    }

    public static ASTFolderNode parse(String rootPath) throws IOException {
        ProjectParser projectParser = new ProjectParser();
        projectParser.doParsing(rootPath, 0, null);
        return projectParser.folderNode;
    }

    public void doParsing(String path, int level, ASTNode parent) throws IOException {
        ASTFolderNode folderNode = new ASTFolderNode();
        folderNode.setAbsolutePath(path);
        folderNode.setName(new File(path).getName());
        if (parent != null) {
            parent.getChildren().add(folderNode);
            folderNode.setParent(parent);
        }
        File mainDir = new File(path);
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
                    List<ASTClassAbstractableElementVisibleElementJavaNode> classes = JavaFileParser.parse(fileToString);
                    CFGNode cfgNode = JavaFileParser.parserToCFG(fileToString);
                    folderNode.addChildrenFolder(classes);
                }
                else if (f.isDirectory()) {
                    ASTFolderNode childFolder = new ASTFolderNode();
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


}
