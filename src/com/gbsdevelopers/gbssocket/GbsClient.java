package com.gbsdevelopers.gbssocket;

import java.io.*;
import java.net.*;

/**
 * This class contains everything is required to handle sending and receiving messages via TCP Socket
 */
public class GbsClient {
    /**
     * Port on which the server is running
     */
    private int serverPort;

    /**
     * Host on which the server is running
     */
    private String hostName;

    /**
     * Setter for serverPort
     * @param serverPort Port on which the server is running
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * Getter for serverPort
     * @return Port on which the server is running
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Setter for hostName
     * @param hostName Host on which the server is running
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Getter for hostName
     * @return Host on which the server is running
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Default constructor. Used when we want "clean" object
     */
    public GbsClient() {
        this.serverPort = 0;
        this.hostName = "";
    }

    /**
     * Constructor that allows to set serverPort and hostName during construction.
     * @param serverPort Port on which the server is running
     * @param hostName Host on which the server is running
     */
    public GbsClient(int serverPort, String hostName) {
        this.serverPort = serverPort;
        this.hostName = hostName;
    }

    /**
     * Function which sends a request and then receives it.
     * @param messageToSend Message to be sent.
     * @return Message to be recievied.
     */
    public GbsMessage executeRequest(GbsMessage messageToSend)
    {
        Socket socket = null;
        ObjectOutputStream toServer = null;
        ObjectInputStream fromServer = null;
        GbsMessage replyMessage = null;
        try {
            socket = new Socket(hostName, serverPort);
            toServer = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            toServer.writeObject(messageToSend);
            toServer.flush();
            fromServer = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            replyMessage = (GbsMessage) fromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        finally {
			if(socket != null) {
				try {
					socket.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
        return replyMessage;
    }
}