package com.example.testtask.controller;

import com.example.testtask.dto.PaymentForm;
import com.example.testtask.dto.PaymentMethod;
import com.example.testtask.dto.ProductType;
import com.example.testtask.dto.ResultDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.matchesRegex;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private PaymentForm paymentForm;

    @BeforeEach
    void setUp() {
        paymentForm = PaymentForm.builder()
                .productId(4324)
                .productType(ProductType.GAME_SESSION)
                .method(PaymentMethod.RFCARD)
                .promoCode("1234")
                .build();
    }

    @AfterEach
    void tearDown() {
        paymentForm = null;
    }

    @Test
    public void handlePaymentFormTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/payment/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentForm)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResultDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResultDto.class);
        assertTrue(result.isResult());
        assertNull(result.getError());
        assertNotNull(result.getPaymenturl());
        assertThat(result.getPaymenturl(), matchesRegex("https://bank-dai-deneg-bustro/\\d+"));
        assertNotNull(result.getExpirationTime());
        assertThat(LocalDate.now(), lessThan(result.getExpirationTime()));
    }
}
