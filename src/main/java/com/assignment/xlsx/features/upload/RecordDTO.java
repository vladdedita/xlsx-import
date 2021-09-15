package com.assignment.xlsx.features.upload;

import com.assignment.xlsx.features.upload.model.enums.BookingTypeEnum;
import com.assignment.xlsx.features.upload.model.enums.ProductEnum;
import com.assignment.xlsx.features.upload.model.enums.TeamEnum;
import lombok.ToString;
import lombok.Value;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.UUID;

@Value
@ToString
public class RecordDTO {

    private RecordDTO(UploadServiceImpl.ExcelRange range, Row row) {
        int start = range.start.getColumn();
        int end = range.end.getColumn();

        this.customerName = getCell(row, start).getStringCellValue();
        this.bookingDate = new Date(getCell(row, start + 1).getDateCellValue().getTime());
        this.opportunityId = UUID.fromString(getCell(row, start + 2).getStringCellValue());
        String bookingType = getCell(row, start + 3).getStringCellValue();
        this.bookingType = BookingTypeEnum.valueOf(bookingType.toUpperCase());
        this.total = getCell(row, start + 4).getNumericCellValue();
        this.accountExecutive = getCell(row, start + 5).getStringCellValue();
        this.salesOrganization = getCell(row, start + 6).getStringCellValue();
        String teamName = getCell(row, start + 7).getStringCellValue();
        this.team = TeamEnum.valueOf(teamName);
        String productName = getCell(row, start + 8).getStringCellValue();
        this.product = ProductEnum.valueOf(productName);
        this.renewable = "YES".equals(getCell(row, start + 9).getStringCellValue());
    }


    private Cell getCell(Row row, int i) {
        Cell cell = row.getCell(i);
        if (cell == null)
            throw new IllegalArgumentException(String.format("No value present on position %d on row %d", i, row.getRowNum()));
        return cell;
    }

    public static RecordDTO of(UploadServiceImpl.ExcelRange range, Row row) {
        int start = range.start.getColumn();
        int end = range.end.getColumn();
        if (end - start < 10)
            throw new IllegalArgumentException(String.format("Selected range %s does not cover all data columns", range.toString()));
        return new RecordDTO(range, row);
    }

    String customerName;
    Date bookingDate;
    UUID opportunityId;
    BookingTypeEnum bookingType;
    String accountExecutive;
    String salesOrganization;
    TeamEnum team;
    ProductEnum product;
    double total;
    boolean renewable;
}
