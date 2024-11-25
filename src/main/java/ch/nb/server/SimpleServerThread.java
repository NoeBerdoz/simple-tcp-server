package ch.nb.server;

import ch.nb.utils.SimpleLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleServerThread extends Thread {

    private Socket socket = null;

    public SimpleServerThread(Socket socket) {
        super("SimpleServerThread");
        this.socket = socket;
    }

    public void run() {

        // Use a try-with-resources block to ensure all resources are closed automatically
        try (
                // Open an output stream, to send data back to the client
                PrintWriter writeToClient = new PrintWriter(socket.getOutputStream(), true);

                /*
                 Open an input stream, to read data from the client.
                 The network sends data as bytes. To work with text, we need to decode those bytes into characters.
                 InputStreamReader handles the conversion from bytes to characters
                */
                BufferedReader readFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            SimpleLogger.info("[+] New thread initialized");

            String clientInput;
            while ((clientInput = readFromClient.readLine()) != null) {
                SimpleLogger.info("[+] New client request: " + clientInput);

                // Send a response back to the client
                writeToClient.println("The number of characters of the given string is: " + clientInput.length());

                if (clientInput.equals("exit")) {
                    SimpleLogger.warning("[+] Closing connection : " + clientInput);
                    break;
                }
            }
            socket.close();
        } catch (IOException error) {
            SimpleLogger.error("[-] Error: " + error.getMessage());
        }
    }
}
