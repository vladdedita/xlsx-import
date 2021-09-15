package com.assignment.xlsx.features.upload;

import com.assignment.xlsx.config.StringBooleanSerializer;
import com.assignment.xlsx.config.StringCurrencySerializer;
import com.assignment.xlsx.features.opportunity.enums.BookingTypeEnum;
import com.assignment.xlsx.features.opportunity.enums.ProductEnum;
import com.assignment.xlsx.features.opportunity.enums.TeamEnum;
import com.assignment.xlsx.features.upload.utils.BoundedExcelRange;
import com.assignment.xlsx.features.upload.utils.ExcelRange;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TransactionDTO {

    public TransactionDTO() {
    }

    private TransactionDTO(BoundedExcelRange range, Row row) {
        int start = range.getStart().getColumn();

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

    public static TransactionDTO of(BoundedExcelRange range, Row row) {
        return new TransactionDTO(range, row);
    }

    String customerName;
    Date bookingDate;
    UUID opportunityId;
    BookingTypeEnum bookingType;
    String accountExecutive;
    String salesOrganization;
    TeamEnum team;
    ProductEnum product;
    @JsonSerialize(using= StringCurrencySerializer.class)
    double total;
    @JsonSerialize(using= StringBooleanSerializer.class)
    boolean renewable;
}
