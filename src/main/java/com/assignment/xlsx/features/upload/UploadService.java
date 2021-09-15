package com.assignment.xlsx.features.upload;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

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
    void upload(MultipartFile file, String range, String worksheetName);
}
