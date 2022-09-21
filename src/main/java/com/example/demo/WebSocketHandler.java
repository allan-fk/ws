package com.example.demo;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

public class WebSocketHandler extends AbstractWebSocketHandler {
    // AbstractWebSocketHandler vous oblige à mettre en œuvre deux méthodes, handleTextMessage et handleBinaryMessagequi sont appelés lorsqu'un nouveau message texte ou binaire est reçu.

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("New Text Message Received");
        System.out.println(message.getPayload());
        // sendMessage => Envoie un message via WS
        if(message.getPayload() == "test") {
            session.sendMessage(message);
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received");
        System.out.println(message);
        session.sendMessage(message);
    }
}