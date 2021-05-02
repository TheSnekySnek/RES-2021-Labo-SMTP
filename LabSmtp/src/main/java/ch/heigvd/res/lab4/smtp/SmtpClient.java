package ch.heigvd.res.lab4.smtp;

import ch.heigvd.res.lab4.model.Mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Smtp client
 */
public class SmtpClient implements ISmtpClient{
    private Socket socket;
    private boolean sentHello = false;
    private PrintWriter out;
    private BufferedReader in;
    private boolean verbose = false;

    /**
     * Sets the verbosity of the sending
     * @param verbose true for verbosity
     */
    public void setVerbose(boolean verbose){
        this.verbose = verbose;
    }

    /**
     * Connect to the specified Smtp server
     * @param host Smtp host
     * @param port Smtp port
     * @throws IOException If it failed to connect
     */
    public void connect(String host, int port) throws IOException{
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Sends an email via the smtp server
     * @param mail Mail object containing the mail information
     * @param user username for auth, can be empty
     * @param password password for auth, can be empty
     * @param useAuth if we should authenticate
     */
    public void sendEmail(Mail mail, String user, String password, boolean useAuth) throws Exception {
        // Check if we already contacted sent an email
        if(!sentHello){
            this.sendHello();
            sentHello = true;
        }

        // Authenticate if we need to
        if(useAuth){
            this.authenticate(user, password);
        }

        this.setFrom(mail.getFrom());

        this.setTo(mail.getTo().getRecipients());

        this.setDataAndSend(mail.getSubject(), mail.getBody(), mail.getFrom(), mail.getFromName(), mail.getTo().getRecipients());
    }

    /**
     * Sends the EHLO to the server, Should only be done for the first mail
     * @throws IOException If it failed to read the line
     * @throws Exception If the server returned an error
     */
    private void sendHello() throws IOException, Exception{
        String line = in.readLine();
        if(verbose)
            System.out.println(line);
        if(!line.startsWith("220"))
            throw new Exception(line);
        out.write("EHLO test.com\r\n");
        out.flush();

        while ((line = in.readLine()) != null) {
            if(verbose)
                System.out.println(line);
            if(line.startsWith("250-")){
                // We don't need that yet
            }
            else if (line.startsWith("250")) {
                // This is the last entry so we can exit
                break;
            } else {
                // This is an error
                throw new Exception("Error while receiving EHLO: " + line);
            }
        }
    }

    /**
     * Authenticate using AUTH LOGIN
     * @param username login username
     * @param password login password
     * @throws IOException If it failed to read the line
     * @throws Exception If the server returned an error
     */
    private void authenticate(String username, String password) throws IOException, Exception{
        out.write("AUTH LOGIN\r\n");
        out.flush();

        String line = in.readLine();
        if(verbose)
            System.out.println(line);
        if(!line.startsWith("334"))
            throw new Exception("Failed to start auth with error: " + line);

        out.write(username + "\r\n");
        out.flush();

        line = in.readLine();
        if(verbose)
            System.out.println(line);
        if(!line.startsWith("334"))
            throw new Exception("Failed to set username with error: " + line);

        out.write(password + "\r\n");
        out.flush();

        line = in.readLine();
        if(verbose)
            System.out.println(line);
        if(!line.startsWith("235"))
            throw new Exception("Failed to login with error: " + line);
    }

    /**
     * Set the sender of the mail
     * @param from email of the sender
     * @throws IOException If it failed to read the line
     * @throws Exception If the server returned an error
     */
    private void setFrom(String from) throws IOException, Exception {
        out.write("MAIL FROM: <"+from+">\r\n");
        out.flush();

        String line = in.readLine();
        if(verbose)
            System.out.println(line);
        if(!line.startsWith("250"))
            throw new Exception("Failed to set sender: " + line);

    }

    /**
     * Send the recipients of the mail
     * @param to List of recipient emails
     * @throws IOException If it failed to read the line
     * @throws Exception If the server returned an error
     */
    private void setTo(ArrayList<String> to) throws IOException, Exception {
        for (String recipient:to) {

            out.write("RCPT TO: <"+recipient+">\r\n");
            out.flush();

            String line = in.readLine();
            if(verbose)
                System.out.println(line);
            if(!line.startsWith("250"))
                throw new Exception("Failed to set recipient: " + line);
            if(verbose)
                System.out.println("Added recipient: " + recipient);
        }
    }

    /**
     * Sets the subject and body of the mail then sends it
     * @param subject mail subject
     * @param body mail body
     * @param from sender email
     * @param fromName sender name
     * @param to recipients
     * @throws IOException If it failed to read the line
     * @throws Exception If the server returned an error
     */
    private void setDataAndSend(String subject, String body, String from, String fromName, ArrayList<String> to) throws IOException, Exception {
        out.write("DATA\r\n");
        out.flush();

        String line = in.readLine();
        if(verbose)
            System.out.println("DATA:" + line);

        if(!line.startsWith("354"))
            throw new Exception("Failed to send Data: " + line);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss +0200");

        String b64Subject = Base64.getEncoder().encodeToString(subject.getBytes());
        out.write("Date: " + date.format(myFormatObj) + "\r\n");
        out.write("From: " + fromName +"<"+from+ ">\r\n");
        out.write("To: " + to.get(0));
        for(int i = 1; i < to.size(); i++){
            out.write("," + to.get(i));
        }
        out.write("\r\n");
        out.write("Subject: =?utf-8?B?"+ b64Subject +"?=\r\n");
        out.write("Content-Type: text/html; charset=\"UTF-8\";\r\n\r\n");
        out.write(body +"\r\n.\r\n");
        out.flush();

        line = in.readLine();
        if(verbose)
            System.out.println(line);
        if(!line.startsWith("250"))
            throw new Exception("Failed to send mail: " + line);
    }

}
