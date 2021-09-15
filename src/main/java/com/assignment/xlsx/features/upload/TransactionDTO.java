package com.assignment.xlsx.features.upload;

import com.assignment.xlsx.config.StringBooleanSerializer;
import com.assignment.xlsx.config.StringCurrencySerializer;
import com.assignment.xlsx.features.opportunity.enums.BookingTypeEnum;
import com.assignment.xlsx.features.opportunity.enums.ProductEnum;
import com.assignment.xlsx.features.opportunity.enums.TeamEnum;
import com.assignment.xlsx.features.upload.utils.BoundedExcelRange;
import com.assignment.xlsx.features.upload.utils.ExcelRange;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.Optional;
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

        this.rowNum = row.getRowNum();
        this.customerName = getCell(row, start).map(Cell::getStringCellValue).orElse(null);
        this.bookingDate = getCell(row, start + 1)
                .map(Cell::getDateCellValue)
                .orElse(null);
        this.opportunityId = getCell(row, start + 2)
                .map(Cell::getStringCellValue)
                .map(UUID::fromString)
                .orElse(null);

        this.bookingType = getCell(row, start + 3)
                .map(Cell::getStringCellValue)
                .map(String::toUpperCase)
                .map(BookingTypeEnum::valueOf)
                .orElse(null);

        this.total = getCell(row, start + 4)
                .map(Cell::getNumericCellValue)
                .orElse(null);
        this.accountExecutive = getCell(row, start + 5)
                .map(Cell::getStringCellValue)
                .orElse(null);
        this.salesOrganization = getCell(row, start + 6).map(Cell::getStringCellValue).orElse(null);
        this.team = getCell(row, start + 7).map(Cell::getStringCellValue).map(TeamEnum::valueOf).orElse(null);

        this.product = getCell(row, start + 8).map(Cell::getStringCellValue).map(ProductEnum::valueOf).orElse(null);
        this.renewable = "YES".equals(getCell(row, start + 9).map(Cell::getStringCellValue).orElse(null));
    }


    private Optional<Cell> getCell(Row row, int i) {
        Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
//        if (cell == null)
//            throw new IllegalArgumentException(String.format("No value present on position %d on row %d", i, row.getRowNum()));
        return Optional.ofNullable(cell);
    }

    public static TransactionDTO of(BoundedExcelRange range, Row row) {
        return new TransactionDTO(range, row);
    }

    @JsonIgnore
    Integer rowNum;

    String customerName;
    Date bookingDate;
    UUID opportunityId;
    BookingTypeEnum bookingType;
    String accountExecutive;
    String salesOrganization;
    TeamEnum team;
    ProductEnum product;
    @JsonSerialize(using = StringCurrencySerializer.class)
    Double total;
    @JsonSerialize(using = StringBooleanSerializer.class)
    boolean renewable;
}
