package core.parser;

import com.sun.istack.internal.NotNull;
import core.cfg.*;
import core.utils.Utils;
import org.eclipse.jdt.core.dom.*;

import java.util.Arrays;
import java.util.List;

public class ASTHelper {

    protected static List<String> primitiveTypes = Arrays.asList("boolean", "short", "int", "long", "float", "double", "void");
    protected static List<String> javaLangTypes = Arrays.asList("Boolean", "Byte", "Character.Subset", "Character.UnicodeBlock", "ClassLoader", "Double",
            "Float", "Integer", "Long", "Math", "Number", "Object", "Package", "Process", "Runtime",
            "Short", "String", "StringBuffer", "StringBuilder", "System", "Thread", "ThreadGroup",
            "Throwable", "Void");


    public static String getFullyQualifiedName(Type type, CompilationUnit cu) {
        if (type.isParameterizedType()) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return getFullyQualifiedTypeName(parameterizedType, cu);
        } else if (type.isArrayType()) {
            ArrayType arrayType = (ArrayType) type;
            return getFullyQualifiedTypeName(arrayType, cu);
        } else
            return getFullyQualifiedTypeName(type.toString(), cu);
    }

    protected static String getFullyQualifiedTypeName(ParameterizedType parameterizedType, CompilationUnit cu) {
        String result = "";
        String type = parameterizedType.getType().toString();
        result += getFullyQualifiedTypeName(type, cu) + "<";

        List args = parameterizedType.typeArguments();
        if (args.size() == 1) {
            String argQualifiedName = getFullyQualifiedTypeName(args.get(0).toString(), cu);
            result += argQualifiedName;
        } else {
            for (Object arg : args) {
                String argQualifiedName = getFullyQualifiedTypeName(arg.toString(), cu);
                result += argQualifiedName + ",";
            }
            result = result.substring(0, result.length() - 1);
        }
        result += ">";
        return result;
    }

    protected static String getFullyQualifiedTypeName(ArrayType arrayType, CompilationUnit cu) {
        String result = "";
        result += getFullyQualifiedTypeName(arrayType.getElementType().toString(), cu);
        for (Object dimen : arrayType.dimensions()) {
            result += dimen.toString();
        }
        return result;
    }
    protected static String getFullyQualifiedTypeName(String typeName, CompilationUnit cu) {
        // input is null or input is already a fully qualified type
        if (typeName == null || typeName.contains(".")) return typeName;

        // is primitive type?
        if (primitiveTypes.contains(typeName)) return typeName;

        // find in import statements
        for (Object o : cu.imports()) {
            if (o instanceof ImportDeclaration) {
                ImportDeclaration id = (ImportDeclaration) o;
                String idStr = id.getName().getFullyQualifiedName();
                if (idStr.endsWith("." + typeName)) {
                    return idStr;
                }
            }
        }

        // find in java.lang package
        if (javaLangTypes.contains(typeName)) {
            return "java.lang." + typeName;
        }

        PackageDeclaration packageDeclaration = cu.getPackage();
        if (packageDeclaration == null) {
            return typeName;
        }
        else {
            return packageDeclaration.getName() + "." + typeName;
        }
    }

    public static void generateCFGTreeFromASTNode(ASTNode astNode, @NotNull CFGNode rootCFG) {

        List<ASTNode> children = Utils.getChildren(astNode);
        for (ASTNode node : children) {
            CFGNode cfgChild = null;
            if (node instanceof IfStatement) {
                cfgChild = new CFGIfStatementNode();
            }
            else if (node instanceof TypeDeclaration || node instanceof MethodDeclaration) {
                cfgChild = new CFGStartNode();
            }
            else if (node instanceof FieldDeclaration) {
                cfgChild = new CFGNode();
            }
            else if (node instanceof Block) {
                cfgChild = new CFGBlock();
            }
            else if (node instanceof ExpressionStatement) {
                cfgChild = new CFGExpressionStatement();
            }
            else if (node instanceof Expression) {
                cfgChild = new CFGExpression();
            }
            else if (node instanceof ReturnStatement) {
                cfgChild = new CFGReturnStatement();
            }
            if (cfgChild != null) {
                cfgChild.setContent(node.toString());
                cfgChild.setStart(node.getStartPosition());
                cfgChild.setEnd(node.getStartPosition() + node.getLength());
                cfgChild.setAst(node);

                cfgChild.setParent(rootCFG);
                rootCFG.getChildren().add(cfgChild);
                generateCFGTreeFromASTNode(node, cfgChild);
            }
        }

    }

}
