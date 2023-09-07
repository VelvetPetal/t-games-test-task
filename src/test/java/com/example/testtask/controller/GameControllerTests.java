package com.example.testtask.controller;

import com.example.testtask.dto.AccessDataDto;
import com.example.testtask.dto.AccessRequestDto;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.matchesRegex;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private AccessRequestDto accessRequestDto;

    @BeforeEach
    void setUp() {
        accessRequestDto = AccessRequestDto.builder()
                .gameId(123)
                .startTime(LocalDateTime.of(2023, 7, 7, 19, 0))
                .validityPeriod("полгода")
                .price(1200)
                .promoCode("1234")
                .build();
    }

    @AfterEach
    void tearDown() {
        accessRequestDto = null;
    }

    @Test
    public void createAccessTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/game/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accessRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResultDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResultDto.class);
        assertTrue(result.isResult());
        assertNull(result.getError());
    }

    @Test
    public void getDataAfterAccessTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/game/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        AccessDataDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), AccessDataDto.class);
        assertNotNull(result.getId());
        assertNotNull(result.getLogo());
        assertThat(result.getLogo(), matchesRegex("/images/\\d+(.png|.jpe?g)"));
        assertNotNull(result.getExpirationTime());
        if (!result.getExpirationTime().equals("Доступ истёк")) {
            assertThat(LocalDate.now(), lessThan(LocalDate.parse(result.getExpirationTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        }
        if (result.getEarnedMoney() != null) {
            assertThat(result.getEarnedMoney(), matchesRegex("[\\d ]+ р"));
        }
        if (result.getProfitMoney() != null) {
            assertThat(result.getProfitMoney(), matchesRegex("[\\d ]+ р"));
        }
        assertNotNull(result.getGamerCount());
        assertThat(result.getGamerCount(), matchesRegex("\\d+"));
        assertNotNull(result.getViewersCount());
        assertThat(result.getViewersCount(), matchesRegex("\\d+"));
    }

}
