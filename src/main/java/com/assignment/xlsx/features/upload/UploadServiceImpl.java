package com.assignment.xlsx.features.upload;


import com.assignment.xlsx.features.metadata.FileMetadataService;
import com.assignment.xlsx.features.opportunity.OpportunityService;
import com.assignment.xlsx.features.upload.utils.BoundedExcelRange;
import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.exceptions.MissingSheetException;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService {

    private final OpportunityService opportunityService;
    private final FileMetadataService fileMetadataService;

    /**
     * Method that extracts data within the specified range
     * from the specified worksheet
     * from the uploaded file
     * and saves it to db
     *
     * @param file          - xlsx
     * @param range         - Ex. A1:D20
     * @param worksheetName - name of the worksheet
     * @return successfully inserted excel lines count
     */
    @Override
    public long upload(MultipartFile file, String range, String worksheetName) {

        //import excel data
        long insertedCount = 0;

        try {
            insertedCount = importExcelData(file.getInputStream(), range, worksheetName);
        } catch (IOException ioException) {
            log.error("Could not open uploaded file", ioException);
            throw new IllegalArgumentException("Could not open uploaded file.");
        }
        //save file metadata
        fileMetadataService.save(file.getOriginalFilename(), file.getSize(), range, insertedCount);

        return insertedCount;
    }

    /**
     * Method that streams over excel entries
     * in the specified range
     *
     * @param fis
     * @param range
     * @param worksheetName
     * @return number of successfuly imported rows
     */
    long importExcelData(InputStream fis, String range, String worksheetName) {
        //In order to improve memory consumption
        //we're going to stream over 100 rows at a time
        //instead of loading the whole file in memory at once
        try (Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)
                .bufferSize(4096)
                .open(fis)) {

            Sheet sheet = workbook.getSheet(worksheetName);
            BoundedExcelRange excelRange = new BoundedExcelRange(range);

            //Only counting failed rows
            //because the number might be smaller than succeeded rows
            Map<Integer, String> failedRows = new HashMap<>();

            StreamSupport
                    .stream(Spliterators.spliteratorUnknownSize(sheet.rowIterator(), Spliterator.ORDERED), false)
                    //Only parse selected rows
                    .filter(excelRange.isRowInRange())
                    //map row to dto
                    .map(row -> Try.ofSupplier(() -> TransactionDTO.of(excelRange, row))
                            .onFailure((e) -> failedRows.put(row.getRowNum(), e.getMessage())))
                    //only process successfully parsed rows
                    .filter(Try::isSuccess)
                    .map(Try::get)
                    //Save to db
                    .forEach(opportunity -> Try.run(() -> opportunityService.save(opportunity))
                            .onFailure((e) -> failedRows.put(opportunity.getRowNum(), e.getMessage())));

            log.error("Failed rows: {}", failedRows.entrySet().stream().map((entry) -> String.format("Row %s: %s", entry.getKey(), entry.getValue())).collect(Collectors.joining("\n")));
            long totalRows = excelRange.getEnd().getRow() - excelRange.getStart().getRow();

            return totalRows - failedRows.size() + 1;            //+ 1 because it's zero based
        } catch (MissingSheetException missingSheetException) {
            throw new IllegalArgumentException(String.format("%s is not a valid sheet name", worksheetName));
        } catch (IOException ex) {
            throw new IllegalArgumentException("Uploaded file could not be opened as excel.");
        }
    }

}
