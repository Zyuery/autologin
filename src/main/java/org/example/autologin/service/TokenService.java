package org.example.autologin.service;

public interface TokenService {
    boolean isShortTokenValid(String shortToken, String fileId, String username);
    String refreshShortToken(String longToken, String fileId, String username);
}
