package ch.nb.client;

import ch.nb.utils.SimpleLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) {

        String host = "127.0.0.1";
        int port = 8090;

        // try-with-resources block, automatically closes resources when done.
        try (
                Socket socket = new Socket(host, port);

                // open an output stream, will send user input to the server line by line.
                // Auto-flush is enabled for immediate delivery.
                PrintWriter writeToServer = new PrintWriter(socket.getOutputStream(), true);

                // open an input stream, reads responses from the server, one line at a time.
                BufferedReader readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // open an input stream, reads input that the user is making in the console.
                BufferedReader readFromUser = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = readFromUser.readLine()) != null) {
                writeToServer.println(userInput);

                SimpleLogger.info("[+] SERVER RESPONSE: " + readFromServer.readLine());
            }
        } catch (IOException error) {
            SimpleLogger.error("[-] Error: " + error.getMessage());
        }

    }
}
