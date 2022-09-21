package com.example.demo;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebSocketHandler extends AbstractWebSocketHandler {
    // AbstractWebSocketHandler vous oblige à mettre en œuvre deux méthodes,
    // handleTextMessage et handleBinaryMessagequi sont appelés lorsqu'un nouveau
    // message texte ou binaire est reçu.

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // System.out.println("New Text Message Received");
        System.out.println(message.getPayload());
        // sendMessage => Envoie un message via WS
        if (message.getPayload().equalsIgnoreCase("test")) {
            session.sendMessage(message);
            byte[] fileToArrayOfBytes = Files.readAllBytes(Paths.get("./testMoi.json"));
            System.out.println("Ws1Application.java");
            int fileSize = fileToArrayOfBytes.length;
            // int nbrOfArr = fileSize / 7000;

            List<byte[]> multiArr = new ArrayList<byte[]>();

            // int index = 0;
            int nextIndex = 7000;
            Gson gson = new Gson();
            for (int i = 0; i < fileSize; i += nextIndex) {
                if (i == 7000) {
                    nextIndex = fileSize - i;
                }
                System.out.println(i);
                byte[] a = Arrays.copyOfRange(fileToArrayOfBytes, i, i + nextIndex);
                multiArr.add(a);

                // String s = new String(multiArr);
                String json = gson.toJson(multiArr);
                session.sendMessage(new TextMessage(json));
            }

            // System.out.println(fileToArrayOfBytes);
            // System.out.println(fileToArrayOfBytes.length);
            // Path currentRelativePath = Paths.get("");
            // String s = currentRelativePath.toAbsolutePath().toString();
            // System.out.println("Current absolute path is: " + s);

            // String s = new String(fileToArrayOfBytes);
            // // System.out.println(s);
            // Gson gson = new Gson();
            // String json = gson.toJson(s);
            // System.out.println(json);
            // System.out.println("pouet");
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received");
        System.out.println(message);
        session.sendMessage(message);
    }
}