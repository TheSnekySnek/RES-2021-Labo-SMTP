package ch.heigvd.res.lab4.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a message with a subject and body
 */
public class Message {
    private String subject;
    private String body;

    /**
     * Instantiate a new message
     * @param subject   Message subject
     * @param body      Message body
     */
    public Message(String subject, String body){
        this.subject = subject;
        this.body = body;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getBody(){
        return this.body;
    }
}
