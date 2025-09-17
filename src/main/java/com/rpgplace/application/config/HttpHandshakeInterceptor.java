package com.rpgplace.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        logger.info("==================== BEFORE HANDSHAKE ====================");
        logger.info("Request URI: {}", request.getURI());
        logger.info("Remote Address: {}", request.getRemoteAddress());
        logger.info("Request Headers:");
        request.getHeaders().forEach((key, value) -> logger.info("    {} : {}", key, value));
        logger.info("========================================================");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
        logger.info("==================== AFTER HANDSHAKE =====================");
        logger.info("Request URI: {}", request.getURI());
        if (exception == null) {
            logger.info("Handshake successful!");
        } else {
            logger.error("Handshake failed with exception: {}", exception.getMessage());
        }
        logger.info("Response Headers:");
        response.getHeaders().forEach((key, value) -> logger.info("    {} : {}", key, value));
        logger.info("========================================================");
    }
}
