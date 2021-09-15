package com.assignment.xlsx.features.upload;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UploadController.class)
class UploadControllerTest {

    public static final String APPLICATION_VND_MS_EXCEL = "application/vnd.ms-excel";
    @Autowired
    MockMvc mvc;

    @MockBean
    UploadService uploadService;

    MockMultipartFile validFile = new MockMultipartFile("test-file", "test.xlsx", APPLICATION_VND_MS_EXCEL, "test".getBytes());
    MockMultipartFile invalidFile = new MockMultipartFile("test-invalid-file", "test.pdf", "application/pdf", "test".getBytes());

    @BeforeEach
    public void init() {
        doNothing().when(uploadService).upload(isA(MultipartFile.class), isA(String.class), isA(String.class));
    }

    @SneakyThrows
    @Test
    void upload_validParams_shouldSucceed() {

        MockHttpServletRequestBuilder request = multipart("/api/upload?range=A1:D20&worksheet=test")
                .file("file", validFile.getBytes())
                .contentType(APPLICATION_VND_MS_EXCEL);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(result.getResponse().getContentAsString(), "Upload success"));

    }

//    @SneakyThrows
//    @Test
//    void upload_invalidContentType_shouldFail() {
//        MockHttpServletRequestBuilder request = multipart("/api/upload?range=A1:D20&worksheet=test")
//                .file("file", invalidFile.getBytes())
//                .contentType("application/pdf");
//        mvc.perform(request)
//                .andExpect(status().is4xxClientError())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMediaTypeNotSupportedException));
//
//    }

    @SneakyThrows
    @Test
    void upload_noRange_shouldFail() {
        MockHttpServletRequestBuilder request = multipart("/api/upload?worksheet=test")
                .file("file", validFile.getBytes())
                .contentType(APPLICATION_VND_MS_EXCEL);

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @SneakyThrows
    @Test
    void upload_noWorksheet_shouldFail() {
        MockHttpServletRequestBuilder request = multipart("/api/upload?range=A1:D20")
                .file("file", validFile.getBytes())
                .contentType(APPLICATION_VND_MS_EXCEL);

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"A1:", "aA1", "A1A2", "A1:A2a", "1A1:A2", "A1:1A2"})
    void upload_rangeInvalid_shouldFail(String range) {
        MockHttpServletRequestBuilder request = multipart(String.format("/api/upload?range=%s&worksheet=test", range))
                .file("file", validFile.getBytes())
                .contentType(APPLICATION_VND_MS_EXCEL);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));

    }
}