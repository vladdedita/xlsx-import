package com.assignment.xlsx.features.upload;


import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.exceptions.MissingSheetException;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService {


    private final BookingRepository bookingRepository;

    public static final class ExcelRange {
        CellAddress start;
        CellAddress end;

        ExcelRange(String range) {
            String[] splitRange = range.split(":");
            start = new CellAddress(splitRange[0]);
            end = new CellAddress(splitRange[1]);
            //TODO should validate start & finish
        }

        @Override
        public String toString() {
            return "[" + start.formatAsString() + ":" + end.formatAsString() + "]";
        }
    }

    /**
     * Method that extracts data within the specified range
     * from the specified worksheet
     * from the uploaded file
     * and saves it to db
     *
     * @param file          - xlsx
     * @param range         - Ex. A1:D20
     * @param worksheetName - name of the worksheet
     */
    @Override
    public void upload(MultipartFile file, String range, String worksheetName) {

        try (Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)
                .bufferSize(4096)
                .open(file.getInputStream())) {

            Sheet sheet = workbook.getSheet(worksheetName);
            ExcelRange excelRange = new ExcelRange(range);

            StreamSupport
                    .stream(Spliterators.spliteratorUnknownSize(sheet.rowIterator(), Spliterator.ORDERED), false)
                    //Only parse selected rows
                    .filter(row -> row.getRowNum() >= excelRange.start.getRow())
                    .filter(row -> row.getRowNum() <= excelRange.end.getRow())
                    .map(row -> Try.ofSupplier(() -> RecordDTO.of(excelRange, row))
                            .onFailure((e) -> log.warn(e.getMessage())))
                    //Only process entries that have been parsed successfully
                    .filter(Try::isSuccess)
                    .map(Try::get)
                    //Save to db
                    .map(record -> Booking.builder()
                            .accountExecutive(record.getAccountExecutive())
                            .bookingDate(record.getBookingDate())
                            .opportunityId(record.getOpportunityId())
                            .bookingType(record.getBookingType())
                            .customerName(record.getCustomerName())
                            .product(record.getProduct())
                            .renewable(record.isRenewable())
                            .salesOrganization(record.getSalesOrganization())
                            .team(record.getTeam())
                            .total(record.getTotal())
                            .build())
                    .forEach(booking -> Try.of(() -> bookingRepository.save(booking)).onFailure(e -> log.error(e.getMessage())));

        } catch (IOException ioException) {
            throw new IllegalArgumentException("Could not open excel.");
        } catch (MissingSheetException missingSheetException) {
            throw new IllegalArgumentException(String.format("%s is not a valid sheet name", worksheetName));
        }

    }

}
