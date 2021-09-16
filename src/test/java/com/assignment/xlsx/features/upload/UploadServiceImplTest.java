package com.assignment.xlsx.features.upload;

import com.assignment.xlsx.features.metadata.FileMetadataService;
import com.assignment.xlsx.features.opportunity.OpportunityService;
import com.assignment.xlsx.features.upload.dto.TransactionDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UploadServiceImplTest {

    private static final String EXCEL_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    @Captor
    ArgumentCaptor<TransactionDTO> captor;
    @Captor
    ArgumentCaptor<String> nameCaptor;
    @Captor
    ArgumentCaptor<Long> sizeCaptor;
    @Captor
    ArgumentCaptor<String> rangeCaptor;
    @Captor
    ArgumentCaptor<Long> countCaptor;
    @Mock
    private OpportunityService opportunityService;
    @Mock
    private FileMetadataService fileMetadataService;
    @InjectMocks
    private UploadServiceImpl uploadServiceImpl;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @SneakyThrows
    void importExcelData_shouldSucceed() {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.xlsx")) {
            doNothing().when(opportunityService).save(captor.capture());
            long result = uploadServiceImpl.importExcelData(is, "B4:K4", "MOCK_DATA_47");
            assertEquals("6de40407-3e0f-4792-a851-45cec0f2db2f", captor.getValue().getOpportunityId().toString());
            assertEquals(1, result);
        }
    }

    @Test
    @SneakyThrows
    void importExcelData_failedSave_shouldReturn0() {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.xlsx")) {

            doThrow(IllegalArgumentException.class).when(opportunityService).save(any());
            long result = uploadServiceImpl.importExcelData(is, "B4:K4", "MOCK_DATA_47");
            assertEquals(0, result);
        }
    }

    @Test
    @SneakyThrows
    void upload_shouldSucceed() {
        MockMultipartFile validFile = new MockMultipartFile("file", Thread.currentThread().getContextClassLoader().getResourceAsStream("test.xlsx"));
        doNothing().when(fileMetadataService).save(nameCaptor.capture(), sizeCaptor.capture(), rangeCaptor.capture(), countCaptor.capture());

        long result = uploadServiceImpl.upload(validFile, "B4:K4", "MOCK_DATA_47");
        assertEquals(1, result);
        assertEquals(validFile.getOriginalFilename(), nameCaptor.getValue());
        assertEquals(validFile.getSize(), sizeCaptor.getValue());
        assertEquals("B4:K4", rangeCaptor.getValue());
        assertEquals(1, countCaptor.getValue());
    }
}