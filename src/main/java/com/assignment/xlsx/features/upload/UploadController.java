package com.assignment.xlsx.features.upload;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping(consumes = "application/vnd.ms-excel")
    public ResponseEntity<String> upload(@RequestPart MultipartFile file, @RequestParam String range, @RequestParam String worksheet) {
        uploadService.upload(file, range, worksheet);
        return ResponseEntity.ok("Upload success");
    }
}
