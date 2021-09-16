package com.assignment.xlsx.features.opportunity;

import com.assignment.xlsx.features.opportunity.enums.BookingTypeEnum;
import com.assignment.xlsx.features.opportunity.enums.ProductEnum;
import com.assignment.xlsx.features.opportunity.enums.TeamEnum;
import com.assignment.xlsx.features.upload.TransactionDTO;
import com.assignment.xlsx.features.upload.UploadController;
import com.assignment.xlsx.features.upload.UploadService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OpportunityController.class)
class OpportunityControllerTest {


    @Autowired
    MockMvc mvc;

    @MockBean
    OpportunityService opportunityService;


    @SneakyThrows
    @Test
    void filter() {
        TransactionDTO transactionDTO = new TransactionDTO(1, "test", new Date(), UUID.randomUUID(), BookingTypeEnum.NEW, "test account", "test sales", TeamEnum.EAST, ProductEnum.ALLOY, 9999.999, true);
        List<TransactionDTO> toReturn = Collections.nCopies(10, transactionDTO);

        doReturn(toReturn).when(opportunityService).filter(any());
        MockHttpServletRequestBuilder request = get("/api/opportunity")
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(toReturn.size())));
    }
}