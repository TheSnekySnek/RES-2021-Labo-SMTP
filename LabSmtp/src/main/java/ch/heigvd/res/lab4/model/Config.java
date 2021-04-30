package ch.heigvd.res.lab4.model;

import java.util.ArrayList;

/**
 * Config for the application
 */
public class Config {
    private String  addressFile;

    private String  smtpHost;
    private int     smtpPort;

    private boolean useAuth;
    private String  username;
    private String  password;

    ArrayList<Group>    groups = new ArrayList<>();
    ArrayList<Message>  messages = new ArrayList<>();

    /**
     * Instantiate a new config
     * @param addressFile   Location of the recipients file
     * @param smtpHost      Smtp server host
     * @param smtpPort      Smtp server port
     * @param useAuth       If we should use authentication
     * @param username      Login username
     * @param password      Login password
     */
    public Config(String addressFile, String smtpHost, int smtpPort, boolean useAuth,String username, String password){
        this.addressFile = addressFile;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.useAuth = useAuth;
        this.username = username;
        this.password = password;
    }

    public void addGroup(Group group){
        this.groups.add(group);
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }

    public ArrayList<Group> getGroups(){
        return this.groups;
    }

    public ArrayList<Message> getMessages(){
        return this.messages;
    }

    public String getAddressFile(){
        return this.addressFile;
    }

    public String getSmtpHost(){
        return this.smtpHost;
    }

    public int getSmtpPort(){
        return this.smtpPort;
    }

    public boolean getUseAuth(){
        return this.useAuth;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
}
