package ch.heigvd.res.lab4.model;

import java.util.ArrayList;

/**
 * Represents a group with a sender and a number of recipients
 */
public class Group {
    private String  sender;
    private String  senderName;
    private int     targetSize;

    /**
     * Instantiate a new group
     * @param sender        Sender email
     * @param senderName    Sender name
     * @param targetSize    Number of recipients
     */
    public Group(String sender, String senderName, int targetSize){
        this.sender = sender;
        this.senderName = senderName;
        this.targetSize = targetSize;
    }

    public String getSender(){
        return this.sender;
    }

    public String getSenderName(){
        return this.senderName;
    }

    public int getTargetSize(){
        return this.targetSize;
    }

}
