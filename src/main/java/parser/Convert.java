package parser;

import node.*;
import node.ASTFieldVisibleElementJavaNode;
import node.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class Convert {
    public static List<ASTNode> convertASTListNodeToFieldNode(FieldDeclaration[] fields) {
        List<ASTNode> result = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            List<ASTFieldVisibleElementJavaNode> fieldNodes = ASTFieldVisibleElementJavaNode.setInforFromASTNode(fields[i]);
            result.addAll(fieldNodes);
        }
        return result;
    }

    public static List<ASTNode> convertASTListNodeToMethodNode(MethodDeclaration[] methods, CompilationUnit cu) {
        List<ASTNode> result = new ArrayList<>();
        for (int i = 0; i < methods.length; i++) {
            ASTMethodAbstractableElementVisibleElementJavaNode methodNode = new ASTMethodAbstractableElementVisibleElementJavaNode();
            methodNode.setInforFromASTNode(methods[i], cu);
            result.add(methodNode);
        }
        return result;
    }


    public static List<ASTNode> convertASTListNodeToClassNode(TypeDeclaration[] innerClasses, CompilationUnit cu) {
        List<ASTNode> result = new ArrayList<>();
        for (int i = 0; i < innerClasses.length; i++) {
            ASTClassAbstractableElementVisibleElementJavaNode classNode = new ASTClassAbstractableElementVisibleElementJavaNode();
            classNode.setInforFromASTNode(innerClasses[i], cu);
            result.add(classNode);
        }
        return result;
    }

}
