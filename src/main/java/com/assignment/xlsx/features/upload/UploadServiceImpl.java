package com.assignment.xlsx.features.upload;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService{
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

    }
}
