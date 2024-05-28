package ru.ostap.chatservice.handler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MyHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<WebSocketSession, Boolean>());
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private final Map<String, Set<WebSocketSession>> groupSessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, String> msgData = objectMapper.readValue(message.getPayload(), HashMap.class);
        String type = msgData.get("type");
        String sender = msgData.get("sender");

        switch (type) {
            case "register":
                userSessions.put(sender, session);
                break;
            case "private":
                handlePrivateMessage(msgData);
                break;
            case "group":
                handleGroupMessage(msgData);
                break;
            case "create_group":
                handleCreateGroup(msgData);
                break;
        }
    }

    private void handlePrivateMessage(Map<String, String> msgData) throws Exception {
        String sender = msgData.get("sender");
        String recipient = msgData.get("recipient");
        String content = msgData.get("content");

        WebSocketSession recipientSession = userSessions.get(recipient);
        if (recipientSession != null && recipientSession.isOpen()) {
            recipientSession.sendMessage(new TextMessage(sender + ": " + content));
        }
    }

    private void handleGroupMessage(Map<String, String> msgData) throws Exception {
        String sender = msgData.get("sender");
        String groupName = msgData.get("group");
        String content = msgData.get("content");

        Set<WebSocketSession> group = groupSessions.get(groupName);
        if (group != null) {
            for (WebSocketSession groupSession : group) {
                if (groupSession.isOpen() && !groupSession.getId().equals(userSessions.get(sender).getId())) {
                    groupSession.sendMessage(new TextMessage(sender + " (group " + groupName + "): " + content));
                }
            }
        }
    }

    private void handleCreateGroup(Map<String, String> msgData) {
        String groupName = msgData.get("group");
        String[] members = msgData.get("members").split(",");

        Set<WebSocketSession> group = new HashSet<>();
        for (String member : members) {
            WebSocketSession memberSession = userSessions.get(member);
            if (memberSession != null) {
                group.add(memberSession);
            }
        }
        groupSessions.put(groupName, group);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        userSessions.values().remove(session);
        for (Set<WebSocketSession> group : groupSessions.values()) {
            group.remove(session);
        }
    }

}