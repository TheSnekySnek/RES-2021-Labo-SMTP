package ch.heigvd.res.lab4.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents a list of recipients
 */
public class Recipients {
    private ArrayList<String> recipients = new ArrayList<>();

    public Recipients(){}

    /**
     * Instantiate a new Recipient list form an ArrayList
     * @param recipients List of recipients
     */
    public Recipients(ArrayList<String> recipients){
        this.recipients = recipients;
    }

    public void addRecipient(String recipient){
        this.recipients.add(recipient);
    }

    public ArrayList<String> getRecipients(){
        return this.recipients;
    }

    /**
     * Picks a number of recipients from the list
     * @param n number to pick
     * @return New Recipients object
     */
    public Recipients pickRecipients(int n){
        Collections.shuffle(this.recipients);
        return new Recipients(new ArrayList<>(this.recipients.subList(0, n)));
    }
}
