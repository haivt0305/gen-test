package extent;

import core.structureTree.structureNode.SFunctionNode;
import core.testcases.TestCase;
import core.testexecution.TestExecution;
import core.utils.Utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public class Exporter {
    private Workbook workbook = null;
    private List<TestCase> testCases;
    private SFunctionNode functionNode;
    private TestExecution execution;

    public static String _TEMPLATE_REPORT_PATH = "test_report_template.xlsx";

    public Exporter(List<TestCase> testCases, TestExecution execution, SFunctionNode functionNode) {
        this.testCases = testCases;
        this.execution = execution;
        this.functionNode = functionNode;
        try {
            InputStream excelFile = getFileFromResourceAsStream(_TEMPLATE_REPORT_PATH);
            workbook = new XSSFWorkbook(excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = Exporter.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found " + fileName);
        } else {
            return inputStream;
        }
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public SFunctionNode getFunctionNode() {
        return functionNode;
    }

    public void setFunctionNode(SFunctionNode functionNode) {
        this.functionNode = functionNode;
    }

    public TestExecution getExecution() {
        return execution;
    }

    public void setExecution(TestExecution execution) {
        this.execution = execution;
    }

    public void export() {

    }

    public static void main(String[] args) throws URISyntaxException {
        File file = new File(Exporter.class.getResource(_TEMPLATE_REPORT_PATH).toURI());
        System.out.println(_TEMPLATE_REPORT_PATH);
        System.out.println(Utils.readFileContent(file.getAbsolutePath()));
    }

}
