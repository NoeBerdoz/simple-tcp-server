package ch.nb.server;

import ch.nb.utils.SimpleLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public static void main(String[] args) {

        int port = 8090;

        // Use a try-with-resources block to ensure all resources are closed automatically
        try (
                // Create a server socket that listens for incoming connections on the specified port
                ServerSocket serverSocket = new ServerSocket(port);

                // Wait for a client to connect. This is a blocking call that stops execution until a client connects
                Socket clientSocket = serverSocket.accept();

                // Open an output stream, to send data back to the client
                PrintWriter writeToClient = new PrintWriter(clientSocket.getOutputStream(), true);

                /*
                 Open an input stream, to read data from the client.
                 The network sends data as bytes. To work with text, we need to decode those bytes into characters.
                 InputStreamReader handles the conversion from bytes to characters
                */
                BufferedReader readFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            SimpleLogger.info("[+] Server started on port " + port);

            String clientInput;
            while ((clientInput = readFromClient.readLine()) != null) {
                SimpleLogger.info("[+] CLIENT REQUEST: " + clientInput);

                // Send a response back to the client
                writeToClient.println("The number of characters of the given string is: " + clientInput.length());
            }

        } catch (IOException error) {
            SimpleLogger.error("[-] Error: " + error.getMessage());
        }

    }

}
