package extent;

import core.structureTree.structureNode.SFunctionNode;
import core.testcases.TestCase;
import core.utils.Utils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class Exporter {
    private List<TestCase> testCases;
    private Workbook workbook = new HSSFWorkbook();
    private SFunctionNode functionNode;

    public Exporter(List<TestCase> testCases, SFunctionNode functionNode) {
        this.testCases = testCases;
        this.functionNode = functionNode;
    }

    public static String _TEMPLATE_REPORT_PATH = "/test_report_template.xlsx";

    public static void main(String[] args) throws URISyntaxException {
        File file = new File(Exporter.class.getResource(_TEMPLATE_REPORT_PATH).toURI());
        System.out.println(_TEMPLATE_REPORT_PATH);
        System.out.println(Utils.readFileContent(file.getAbsolutePath()));
    }

}
