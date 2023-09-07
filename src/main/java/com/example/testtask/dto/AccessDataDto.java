package com.example.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessDataDto {
    private Integer id;
    private String logo;
    private String name;

    private String expirationTime;
    private String earnedMoney;
    private String profitMoney;
    private String gamerCount;
    private String viewersCount;
}
