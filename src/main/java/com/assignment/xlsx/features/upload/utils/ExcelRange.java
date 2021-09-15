package com.assignment.xlsx.features.upload.utils;

import lombok.Getter;
import org.apache.poi.ss.util.CellAddress;


@Getter
public abstract class ExcelRange {
    protected CellAddress start;
    protected CellAddress end;

    public ExcelRange(String range) {
        String[] splitRange = range.split(":");
        start = new CellAddress(splitRange[0]);
        end = new CellAddress(splitRange[1]);
    }

    @Override
    public String toString() {
        return "[" + start.formatAsString() + ":" + end.formatAsString() + "]";
    }
}
