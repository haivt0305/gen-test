package utils;

import node.FileNode;
import node.Node;
import org.eclipse.jdt.core.dom.*;
import structureTree.SNode;
import structureTree.structureNode.SFunctionNode;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static SNode parseFolderNodeToSNode(Node node) {
//        System.out.println("parse " + ASTNode.getName());
        SNode root = node.parseToSNode();
        if (root == null) return null;
        for (Node child : node.getChildren()) {
            System.out.println("parse " + child.getName());
            SNode sNodeChild = Utils.parseFolderNodeToSNode(child);
            if (sNodeChild != null) {
                root.getChildren().add(sNodeChild);
            }
        }
        for (SNode child : root.getChildren()) {
            child.setParent(root);
        }

        return root;
    }

    public static List<ASTNode> getChildren(org.eclipse.jdt.core.dom.ASTNode node) {
        List<ASTNode> children = new ArrayList<>();
        if (node instanceof MethodDeclaration) {
            Block block = ((MethodDeclaration) node).getBody();
            children.add(block);
//            List<Statement> statements = block.statements();
//            for (Statement statement : statements) {
//                if (statement instanceof IfStatement) {
//
//                }
//            }
        }
        else if (node instanceof TypeDeclaration) {
            List<FieldDeclaration> atributes = Arrays.asList(((TypeDeclaration) node).getFields());
            for (FieldDeclaration attribute : atributes) {
                children.add(attribute);
            }

            List<MethodDeclaration> methods = Arrays.asList(((TypeDeclaration) node).getMethods());
            for (MethodDeclaration method : methods) {
                children.add(method);
            }

        }
        else if (node instanceof Block) {
            List<Statement> statements = ((Block) node).statements();
            for (ASTNode statement : statements) {
                children.add(statement);
            }
        }
        else if (node instanceof IfStatement) {
            children.add(((IfStatement) node).getExpression());
            children.add(((IfStatement) node).getThenStatement());
            children.add(((IfStatement) node).getElseStatement());
        }
        else if (node instanceof ExpressionStatement) {
            children.add(((ExpressionStatement) node).getExpression());
        }

        return children;
    }

    public static String readFileContent(String path) {
        String content = "";
        File fileToRead = new File(path);

        try(FileReader fileStream = new FileReader( fileToRead );
            BufferedReader bufferedReader = new BufferedReader( fileStream ) ) {

            String line = null;

            while( (line = bufferedReader.readLine()) != null ) {
                //do something with line
                content += line + "\n";
            }

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }

        return content;
    }

    public static void writeToFile(String content, String absolutePath) {
        try {
            //Whatever the file path is.
            File statText = new File(absolutePath);
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(content);
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file " + absolutePath);
        }
    }

    public static String insertString(
            String originalString,
            String stringToBeInserted,
            int index)
    {

        // Create a new string
        String newString = originalString.substring(0, index + 1)
                + stringToBeInserted
                + originalString.substring(index + 1);

        // return the modified String
        return newString;
    }

    public static String getPreviousWhiteSpace(String str, int from) {
        String res = "";
        while (str.charAt(from) == ' ') {
            res += " ";
            from --;
        }
        return res;
    }

    public static String getWriteToTestPathContent(String content, String absolutePath) {
        return "try{\n" +
                "        //Specify the file name and path here\n" +
                "        File file =new File(\"" + absolutePath.replaceAll("\\\\", "/")+ "\");\n" +
                " \n" +
                "        /* This logic is to create the file if the\n" +
                "         * file is not already present\n" +
                "         */\n" +
                "        if(!file.exists()){\n" +
                "           file.createNewFile();\n" +
                "        }\n" +
                " \n" +
                "        //Here true is to append the content to file\n" +
                "        FileWriter fw = new FileWriter(file,true);\n" +
                "        //BufferedWriter writer give better performance\n" +
                "        BufferedWriter bw = new BufferedWriter(fw);\n" +
                "        bw.write(\"" +content+ "\" + \"\\n\");\n" +
                "        //Closing BufferedWriter Stream\n" +
                "        bw.close();\n" +
                " \n" +
                "    System.out.println(\"Data successfully appended at the end of file\");\n" +
                " \n" +
                "      }catch(IOException ioe){\n" +
                "         System.out.println(\"Exception occurred:\");\n" +
                "         ioe.printStackTrace();\n" +
                "       }";
//        return "try {\n" +
//                "            //Whatever the file path is.\n" +
//                "            File statText = new File(\"" + absolutePath.replaceAll("\\\\", "/") + "\");\n" +
//                "            FileOutputStream is = new FileOutputStream(statText);\n" +
//                "            OutputStreamWriter osw = new OutputStreamWriter(is);\n" +
//                "            Writer w = new BufferedWriter(osw);\n" +
//                "            w.write(\""+ content + "\");\n" +
//                "            w.close();\n" +
//                "        } catch (IOException e) {\n" +
//                "            System.err.println(\"Problem writing to the file \" + \"" + absolutePath.replaceAll("\\\\", "/")
//                + "\");\n" +
//                "        }";
    }


    public static String importFileLibrary() {
        return "import java.io.*;";
    }

}
