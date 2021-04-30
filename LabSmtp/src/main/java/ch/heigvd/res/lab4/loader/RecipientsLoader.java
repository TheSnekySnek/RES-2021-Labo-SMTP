package ch.heigvd.res.lab4.loader;

import ch.heigvd.res.lab4.model.Recipients;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Loads the list of recipients from a file
 */
public class RecipientsLoader {
    /**
     * Loads the list of recipients from a file
     * @param path Path of the recipients file
     * @return  Recpients object
     * @throws IOException If the file doesn't exist
     */
    public static Recipients loadRecipients(String path) throws IOException {
        Recipients recipients = new Recipients();
        BufferedReader br = new BufferedReader(new FileReader(path));

        // Read each line and add it as a recipient
        while (br.ready()) {
            recipients.addRecipient(br.readLine());
        }

        return recipients;
    }

}
