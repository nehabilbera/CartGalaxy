package com.example.CartGalaxy.common.service;

import com.example.CartGalaxy.common.model.SendEmailDTO;

public interface EmailService {
    void sendEmail(SendEmailDTO emailDTO);
}
