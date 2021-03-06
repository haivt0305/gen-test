package extent;

import core.cfg.CFGNode;
import core.parser.ProjectParser;
import core.structureTree.structureNode.SFunctionNode;
import core.testcases.TestCase;
import core.testdata.DataNode;
import core.testdata.SubProgramDataNode;
import core.testdata.ValueDataNode;
import core.testdata.normal_datanode.NormalDataNode;
import core.testexecution.TestExecution;
import core.utils.CFGUtils;
import core.utils.SearchInDataTree;
import core.utils.Utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Exporter {
    private Workbook workbook = null;
    private List<TestCase> testCases;
    private SFunctionNode functionNode;
    private TestExecution execution;
    private int countID = 1;

    private static int runningRow = 8;

    public static int IDCol = 0;
    public static int TESTPATHCol = 1;
    public static String INPUTCol = "C";
    public static String NAMECol = "D";
    public static String INCol = "E";


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
        Sheet sheet = workbook.getSheetAt(0);
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        getCell(sheet, "B1").setCellValue(functionNode.getName());
        getCell(sheet, "B2").setCellValue(testCases.size());
        getCell(sheet, "B3").setCellValue(execution.getCoverage().getResult()*100.0 + "%");
        getCell(sheet, "B4").setCellValue("STATEMENT");
        for (TestCase testCase : testCases) {
            export(testCase);
        }
        write();
    }

    public void write() {
        String projectPath = ProjectParser.getParser().getProjectPath();

        File workspace = new File(new File(projectPath).getParentFile().getAbsolutePath() + "/JGT-workspace");
        if (workspace.mkdir()) {
            System.out.println("Create workspace successful");
        }
        File reportFolder = new File(workspace.getAbsolutePath() + "/report");
        if (reportFolder.mkdir()) {
            System.out.println("Create report folder successful");
        }
        else System.out.println("Report folder is existed");
        File reportFunctionFolder = new File(reportFolder.getAbsolutePath() + "/" + functionNode.getName());
        if (reportFunctionFolder.mkdir()) {
            System.out.println("Create report Function folder successful");
        }
        else System.out.println("Instrument folder is existed");
        String output = functionNode.getName() + "_" /*+ new Random().nextInt()*/ + ".xlsx";
        System.out.println("Exporting to " + output);
        try {
            OutputStream os = new FileOutputStream(reportFunctionFolder.getAbsolutePath() + "/" + output);
            workbook.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void export(TestCase testCase) {
        addID(runningRow);
        genTestPath(testCase, runningRow);
        genArgument(testCase, runningRow);
    }

    public void addID(int rowNum) {
        Sheet sheet = workbook.getSheetAt(0);
        String addr = "A" + String.valueOf(rowNum + 1);
        Cell idCell = getCell(sheet, addr);
        idCell.setCellValue(countID);
        countID++;
    }

    public void genTestPath(TestCase testCase, int rowNum) {
        Sheet sheet = workbook.getSheetAt(0);
        System.out.println("TEST PATH REPORT FOR FUNCTION " + functionNode.getName());
        String testPath = testCase.getTestPath();
        List<String> linesTestPath = null;
        try {
            linesTestPath = Utils.readFileByLines(testPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CFGNode rootCFG = functionNode.getAst().getCfg();
        List<CFGNode> cfgNodes = new ArrayList<>();
        for (String line : linesTestPath) {
            int start = Utils.getStartValueOfLineInTestPath(line);
            CFGNode finderCFG = CFGUtils.findCFGNodeByStart(rootCFG, start);
            if (finderCFG != null) {
                if (!cfgNodes.contains(finderCFG)) cfgNodes.add(finderCFG);
            }
        }
        String content = "";
        for (CFGNode node : cfgNodes) {
            content += "==>" + node.getContentReport()+ "\n";
        }
        System.out.println(content);
        String addr = "B" + String.valueOf(rowNum + 1);
        Cell tpCell = getCell(sheet, addr);
        tpCell.setCellValue(content);
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        tpCell.setCellStyle(style);
    }

    public void genArgument(TestCase testCase, int rowNum) {
        SubProgramDataNode subProgramDataNode = SearchInDataTree.searchSubprogramNode(testCase.getRootDataNode());
        for (DataNode dataNode : subProgramDataNode.getChildren()) {
            if (dataNode instanceof ValueDataNode) {
                rowNum = fillArgDataToCell(dataNode, rowNum);
            }
        }

        runningRow = rowNum;
    }

    public int fillArgDataToCell(DataNode dataNode, int rowNum) {
        Sheet sheet = workbook.getSheetAt(0);
        String name = NAMECol + String.valueOf(rowNum + 1);
        String in = INCol + String.valueOf(rowNum + 1);
        Cell nameCell = getCell(sheet, name);
        Cell inCell = getCell(sheet, in);
        nameCell.setCellValue(dataNode.getName());
        if (dataNode instanceof ValueDataNode) {
            if (dataNode instanceof NormalDataNode) {
                inCell.setCellValue(((NormalDataNode) dataNode).getValue());
            }
        }
        rowNum = rowNum + 1;
        for (DataNode child : dataNode.getChildren()) {
            rowNum = fillArgDataToCell(child, rowNum);
        }
        return rowNum;
    }

    public static Cell getCell(Sheet sheet, String address) {
        CellReference ref = new CellReference(address);
        Row row = sheet.getRow(ref.getRow());
        if (row == null) {
            sheet.createRow(ref.getRow());
            row = sheet.getRow(ref.getRow());
        }
        Cell cell = row.getCell(ref.getCol());

        if (cell == null) {
            if (sheet.getRow(ref.getRow()) == null) sheet.createRow(ref.getRow());
            row = sheet.getRow(ref.getRow());
            row.createCell(ref.getCol());
            cell = row.getCell(ref.getCol());
        }
        return cell;
    }

}
