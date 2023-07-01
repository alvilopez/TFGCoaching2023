package es.usal.coaching.email;

import org.springframework.stereotype.Component;

import es.usal.coaching.entities.Match;

@Component
public interface EmailService {
    
    void sendSimpleMessage(String to, String subject, String text);

    void sendMatchInfoToPlayer(Match match);
}
