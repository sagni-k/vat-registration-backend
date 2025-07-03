package com.nic.vat.registration.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class EconomicActivityDTO {
    private String activityCode;
    private List<String> roles;
}

