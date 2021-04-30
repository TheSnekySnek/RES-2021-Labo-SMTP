package ch.heigvd.res.lab4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import ch.heigvd.res.lab4.loader.ConfigLoader;
import ch.heigvd.res.lab4.loader.RecipientsLoader;
import ch.heigvd.res.lab4.model.*;
import ch.heigvd.res.lab4.smtp.SmtpClient;

/**
 * Generates pranks emails
 */
public class Application {

    /**
     * Main methode of the application
     * @param args Not needed
     */
    public static void main(String[] args){
        // Load the config
        Config config;
        try{
            config = ConfigLoader.loadConfig("settings.json");
        } catch (IOException e){
            System.err.println("Failed to load settings file");
            e.printStackTrace();
            return;
        }

        System.out.println("Loaded config");

        // Load the recipients
        Recipients recipients;
        try{
            recipients = RecipientsLoader.loadRecipients(config.getAddressFile());
        } catch (IOException e){
            System.err.println("Failed to load recipients file");
            e.printStackTrace();
            return;
        }

        System.out.println("Loaded recipients");

        // Initialize the smtp object
        SmtpClient smtp = new SmtpClient();
        //smtp.setVerbose(true); // Set it to verbose

        // Connect to the smtp server
        try{
            smtp.connect(config.getSmtpHost(), config.getSmtpPort());
        } catch (IOException e){
            System.err.println("Failed to connect to SMTP server");
            e.printStackTrace();
            return;
        }

        System.out.println("Connected to SMTP server");

        // Iterate over each group
        for (Group group: config.getGroups()) {
            // Select a random message
            Message message = pickRandomMessage(config.getMessages());
            // Create a Mail object
            Mail mail = new Mail(group.getSender(), group.getSenderName(), recipients.pickRecipients(group.getTargetSize()), message.getSubject(), message.getBody());

            // Send the mail
            try{
                smtp.sendEmail(mail, config.getUsername(), config.getPassword(), config.getUseAuth());
                System.out.println("Message from " + group.getSender() + " was sent");
            } catch (Exception e){
                System.err.println("Could not send the message");
                e.printStackTrace();
            }


        }
    }

    /**
     * Picks a random message from the list of messages
     * @param messages list of messages to pick from
     * @return random message
     */
    private static Message pickRandomMessage(ArrayList<Message> messages){
        Random rand = new Random();
        return messages.get(rand.nextInt(messages.size()));
    }
}
