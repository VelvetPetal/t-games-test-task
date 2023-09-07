package com.example.testtask.controller;

import com.example.testtask.dto.AccessDataDto;
import com.example.testtask.dto.AccessRequestDto;
import com.example.testtask.dto.ResultDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {
    @GetMapping("/")
    private AccessDataDto getDataAfterAccess() {
        return AccessDataDto.builder()
                .id(123)
                .logo("/images/344324.png")
                .name("Networker")
                .expirationTime("2023-09-09")
                .earnedMoney("201 000 р")
                .profitMoney(null)
                .gamerCount("290")
                .viewersCount("15")
                .build();
    }

    @PostMapping("/user")
    private ResultDto createAccess(@RequestBody AccessRequestDto accessRequestDto) {
        return ResultDto.builder().result(true).build();
    }
}
