package com.assignment.xlsx.features.upload;


import com.assignment.xlsx.validation.ValidExcelRange;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("api/upload")
@RequiredArgsConstructor
@Validated
public class UploadController {

    private final UploadService uploadService;


    //Commented to work with POSTMAN
    //    @PostMapping(consumes = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @PostMapping
    public ResponseEntity<String> upload(@RequestPart MultipartFile file,
                                         @Valid @ValidExcelRange @RequestParam String range,
                                         @RequestParam String worksheet) {
        long insertCount = uploadService.upload(file, range, worksheet);
        return ResponseEntity.ok(String.format("Successfully uploaded excel and imported %d entries", insertCount));
    }
}
