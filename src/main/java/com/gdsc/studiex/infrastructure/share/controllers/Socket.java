package com.gdsc.studiex.infrastructure.share.controllers;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.gdsc.studiex.infrastructure.share.config.SocketConfig;

import javax.annotation.PreDestroy;

@org.springframework.context.annotation.Configuration
public class Socket {
    private SocketIOServer socketIOServer = null;
    private int eventListenerCount = 0;
    private final int startServerWhenEventListenerCountReach = 2;

    public Socket() {
        Configuration config = new Configuration();
        config.setHostname(SocketConfig.IP);
        config.setPort(SocketConfig.PORT);
        config.setOrigin("*");
        socketIOServer = new SocketIOServer(config);
    }

    public <T> void addEventListener(java.lang.String eventName, java.lang.Class<T> eventClass, com.corundumstudio.socketio.listener.DataListener<T> listener) {
        eventListenerCount++;
        socketIOServer.addEventListener(eventName, eventClass, listener);
        if (eventListenerCount >= startServerWhenEventListenerCountReach)
            socketIOServer.start();
    }

    @PreDestroy
    public void destroy() {
        if (socketIOServer != null)
            socketIOServer.stop();
    }
}
