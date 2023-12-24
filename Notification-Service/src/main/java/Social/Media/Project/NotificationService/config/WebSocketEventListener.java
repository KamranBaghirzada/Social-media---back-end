package Social.Media.Project.NotificationService.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    private final Map<String, String> userSessions = new ConcurrentHashMap<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        Principal principal = (Principal) SimpMessageHeaderAccessor.wrap(event.getMessage()).getSessionAttributes().get("user");
        String userId = principal.getName();
        String sessionId = SimpMessageHeaderAccessor.wrap(event.getMessage()).getSessionId();
        userSessions.put(sessionId, userId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = SimpMessageHeaderAccessor.wrap(event.getMessage()).getSessionId();
        userSessions.remove(sessionId);
    }

    public boolean isUserOnline(String userId) {
        return userSessions.containsValue(userId);
    }
}
