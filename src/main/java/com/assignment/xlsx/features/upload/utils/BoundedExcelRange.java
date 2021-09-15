package com.assignment.xlsx.features.upload.utils;

import lombok.Getter;
import org.apache.poi.ss.util.CellAddress;


@Getter
public class BoundedExcelRange extends ExcelRange {

    public BoundedExcelRange(String range, CellAddress startBound, CellAddress endBound) {
        super(range);
        if (this.start.getRow() < startBound.getRow())
            throw new IllegalArgumentException("Excel data should start at row: " + startBound.formatAsString());
        if (this.start.getColumn() < startBound.getColumn())
            throw new IllegalArgumentException("First data column in excel should be at least: " + startBound.formatAsString());
        if (this.end.getColumn() > endBound.getColumn())
            throw new IllegalArgumentException("Last data column in excel should be at least: " + startBound.formatAsString());
    }

}
