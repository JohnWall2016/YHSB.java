package cn.yhsb.base.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excels {

    public enum Type {
        XLS, XLSX, AUTO
    }

    public static Workbook load(String fileName, Type type) {
        if (type == Type.AUTO) {
            var fn = fileName.toLowerCase();
            type = fn.endsWith(".xls") ? Type.XLS
                    : fn.endsWith(".xlsx") ? Type.XLSX : Type.AUTO;
        }
        try {
            switch(type) {
            case XLS:
                return new HSSFWorkbook(
                        Files.newInputStream(Paths.get(fileName)));
            case XLSX:
                return new XSSFWorkbook(
                        Files.newInputStream(Paths.get(fileName)));
            case AUTO:
            }
        } catch (IOException e) {
            throw new ExcelException(e);
        }
        throw new UnsupportedOperationException("Unknown excel type");
    }

    public static Workbook load(String fileName) {
        return load(fileName, Type.AUTO);
    }

    public static void save(Workbook wb, String fileName) {
        try (var out = Files.newOutputStream(Paths.get(fileName))) {
            wb.write(out);
        } catch (IOException e) {
            throw new ExcelException(e);
        }
    }

    public static Row createRow(Sheet sheet, int targetRowIndex,
            int sourceRowIndex, boolean clearValue) {
        if (targetRowIndex == sourceRowIndex)
            throw new ExcelException(
                    "sourceIndex and targetIndex cannot be same");

        var newRow = sheet.getRow(targetRowIndex);
        var srcRow = sheet.getRow(sourceRowIndex);

        if (newRow != null) {
            sheet.shiftRows(targetRowIndex, sheet.getLastRowNum(), 1, true,
                    false);
        }

        newRow = sheet.createRow(targetRowIndex);
        newRow.setHeight(srcRow.getHeight());

        for (var idx = srcRow.getFirstCellNum(); idx < srcRow
                .getPhysicalNumberOfCells(); idx++) {
            var srcCell = srcRow.getCell(idx);
            if (srcCell == null) {
                continue;
            }

            var newCell = newRow.createCell(idx);

            newCell.setCellStyle(srcCell.getCellStyle());
            newCell.setCellComment(srcCell.getCellComment());
            newCell.setHyperlink(srcCell.getHyperlink());

            switch(srcCell.getCellType()) {
            case NUMERIC:
                newCell.setCellValue(
                        clearValue ? 0 : srcCell.getNumericCellValue());
                break;
            case STRING:
                newCell.setCellValue(
                        clearValue ? "" : srcCell.getStringCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(srcCell.getCellFormula());
                break;
            case BLANK:
                newCell.setBlank();
                break;
            case BOOLEAN:
                newCell.setCellValue(
                        clearValue ? false : srcCell.getBooleanCellValue());
                break;
            case ERROR:
                newCell.setCellErrorValue(srcCell.getErrorCellValue());
                break;
            default:
                break;
            }
        }
        var merged = new CellRangeAddressList();
        for (var i = 0; i < sheet.getNumMergedRegions(); i++) {
            var address = sheet.getMergedRegion(i);
            if (sourceRowIndex == address.getFirstRow()
                    && sourceRowIndex == address.getLastRow()) {
                merged.addCellRangeAddress(targetRowIndex,
                        address.getFirstColumn(), targetRowIndex,
                        address.getLastColumn());
            }
        }
        for (var region : merged.getCellRangeAddresses()) {
            sheet.addMergedRegion(region);
        }
        return newRow;
    }

    public static Row getOrCopyRowFrom(Sheet sheet, int targetRowIndex,
            int sourceRowIndex, boolean clearValue) {
        if (targetRowIndex == sourceRowIndex)
            return sheet.getRow(sourceRowIndex);
        else {
            if (sheet.getLastRowNum() >= targetRowIndex)
                sheet.shiftRows(targetRowIndex, sheet.getLastRowNum(), 1, true,
                        false);
            return createRow(sheet, targetRowIndex, sourceRowIndex, clearValue);
        }
    }

    public static Row getOrCopyRowFrom(Sheet sheet, int targetRowIndex,
            int sourceRowIndex) {
        return getOrCopyRowFrom(sheet, targetRowIndex, sourceRowIndex, false);
    }

    public static void copyRowsFrom(Sheet sheet, int start, int count,
            int srcRowIdx, boolean clearValue) {
        sheet.shiftRows(start, sheet.getLastRowNum(), count, true, false);
        for (var i = 0; i < count; i++) {
            createRow(sheet, start + i, srcRowIdx, clearValue);
        }
    }

    public static void copyRowsFrom(Sheet sheet, int start, int count,
            int srcRowIdx) {
        copyRowsFrom(sheet, start, count, srcRowIdx, false);
    }

    public static Iterator<Row> getRowIterator(Sheet sheet, int start,
            int end) {
        return new Iterator<Row>() {
            private int index = Math.max(0, start);
            private int last = Math.min(end - 1, sheet.getLastRowNum());

            @Override
            public boolean hasNext() {
                return index <= last;
            }

            @Override
            public Row next() {
                return sheet.getRow(index++);
            }
        };
    }

}