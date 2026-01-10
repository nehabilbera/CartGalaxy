package com.example.CartGalaxy.common.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailDTO {
    private String recipient;
    private String subject;
    private String text;
}
