package parser;


import node.ASTFolderNode;
import org.junit.Test;

import java.io.IOException;

public class ProjectParserTest {
    @Test
    public void testProjectParser () throws IOException {
        ASTFolderNode folderNode = ProjectParser.parse("data/");
        System.out.println(folderNode.toString());
    }
}