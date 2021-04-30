package ch.heigvd.res.lab4.model;

import java.util.ArrayList;

/**
 * Represents an email to be sent
 */
public class Mail {

    private String      from;
    private String      fromName;
    private Recipients  to;
    private String      subject;
    private String      body;

    /**
     * Instantiate a mail
     * @param from      Sender email
     * @param fromName  Sender name
     * @param to        List of recipients
     * @param subject   Mail subject
     * @param body      Mail body
     */
    public Mail(String from, String fromName, Recipients to, String subject, String body){
        this.from = from;
        this.fromName = fromName;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public String getFrom(){
        return this.from;
    }

    public String getFromName(){
        return this.fromName;
    }

    public Recipients getTo(){
        return this.to;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getBody(){
        return this.body;
    }
}
