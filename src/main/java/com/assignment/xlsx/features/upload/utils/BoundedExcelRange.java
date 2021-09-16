package com.assignment.xlsx.features.upload.utils;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;

import java.util.function.Predicate;


@Getter
public class BoundedExcelRange extends ExcelRange {

    private static final CellAddress LEFTMOST_LIMIT = new CellAddress("B3");
    private static final CellAddress RIGHTMOST_LIMIT = new CellAddress("K3");

    public BoundedExcelRange(String range, CellAddress startBound, CellAddress endBound) {
        super(range);
        if (this.start.getRow() < startBound.getRow())
            throw new IllegalArgumentException("Excel data should start at row: " + startBound.formatAsString());
        if (this.start.getColumn() < startBound.getColumn())
            throw new IllegalArgumentException("First data column in excel should be at least: " + startBound.formatAsString());
        if (this.end.getColumn() > endBound.getColumn())
            throw new IllegalArgumentException("Last data column in excel should be at least: " + startBound.formatAsString());
    }

    public BoundedExcelRange(String range) {
        super(range);
        if (this.start.getRow() < LEFTMOST_LIMIT.getRow())
            throw new IllegalArgumentException("Excel data should start at row: " + LEFTMOST_LIMIT.formatAsString());
        if (this.start.getColumn() < LEFTMOST_LIMIT.getColumn())
            throw new IllegalArgumentException("First data column in excel should be at least: " + LEFTMOST_LIMIT.formatAsString());
        if (this.end.getColumn() > RIGHTMOST_LIMIT.getColumn())
            throw new IllegalArgumentException("Last data column in excel should be at least: " + LEFTMOST_LIMIT.formatAsString());
    }

    public Predicate<Row> isRowInRange() {
        return row -> row.getRowNum() >= this.getStart().getRow() && row.getRowNum() <= this.getEnd().getRow();
    }

}
