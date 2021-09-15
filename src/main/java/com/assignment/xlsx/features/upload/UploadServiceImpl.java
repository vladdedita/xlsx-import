package com.assignment.xlsx.features.upload;


import com.assignment.xlsx.features.opportunity.OpportunityService;
import com.assignment.xlsx.features.upload.utils.BoundedExcelRange;
import com.assignment.xlsx.features.upload.utils.ExcelRange;
import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.exceptions.MissingSheetException;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService {


    private final OpportunityService opportunityService;

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
            BoundedExcelRange excelRange = new BoundedExcelRange(range, new CellAddress("B3"), new CellAddress("K3"));

            StreamSupport
                    .stream(Spliterators.spliteratorUnknownSize(sheet.rowIterator(), Spliterator.ORDERED), false)
                    //Only parse selected rows
                    .filter(row -> row.getRowNum() >= excelRange.getStart().getRow())
                    .filter(row -> row.getRowNum() <= excelRange.getEnd().getRow())
                    .map(row -> Try.ofSupplier(() -> TransactionDTO.of(excelRange, row))
                            .onFailure((e) -> log.warn(e.getMessage())))
                    //Only process entries that have been parsed successfully
                    .filter(Try::isSuccess)
                    .map(Try::get)
                    //Save to db
                    .forEach(opportunity -> Try.run(() -> opportunityService.save(opportunity))
                            .onFailure(e -> log.warn(e.getMessage())));

        } catch (IOException ioException) {
            throw new IllegalArgumentException("Could not open excel.");
        } catch (MissingSheetException missingSheetException) {
            throw new IllegalArgumentException(String.format("%s is not a valid sheet name", worksheetName));
        }

    }

}
