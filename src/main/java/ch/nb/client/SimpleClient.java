package ch.nb.client;

import ch.nb.utils.SimpleLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) throws IOException {

        String host = "127.0.0.1";
        int port = 8090;

        try (
            Socket socket = new Socket(host, port);

            // "serverOutput" will send user input to the server line by line.
            // Auto-flush is enabled for immediate delivery.
            PrintWriter writeToServer = new PrintWriter(socket.getOutputStream(), true);

            // "serverInput" reads responses from the server, one line at a time.
            BufferedReader readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Reads input that the user is making in the console
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
