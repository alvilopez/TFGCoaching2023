package es.usal.coaching.email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import es.usal.coaching.entities.Match;
import es.usal.coaching.entities.Player;

@Component
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
      String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("coachingbyalvaro@gmail.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendMatchInfoToPlayer(Match match) {
        for(Player p : match.getLocalTeam().getPlayers()){
           sendSimpleMessage("coachingbyalvaro@gmail.com", "Envio de Prueba","En ese correo se hace una prueba de que el usuario está recibiendo el mensaje.");
        }
    }
}
