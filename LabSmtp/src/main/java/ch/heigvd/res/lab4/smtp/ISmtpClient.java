package ch.heigvd.res.lab4.smtp;

import ch.heigvd.res.lab4.model.Mail;

import java.io.IOException;

public interface ISmtpClient {
    void connect(String smtpHost, int smtpPort) throws IOException;
    void sendEmail(Mail mail, String username, String password, boolean useAuth) throws Exception;
}
