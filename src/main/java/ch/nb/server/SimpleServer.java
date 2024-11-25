package ch.nb.server;

import ch.nb.utils.SimpleLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public static void main(String[] args) {

        int port = 8090;
        boolean isServerListening = true;

        // Use a try-with-resources block to ensure all resources are closed automatically
        try (
                // Create a server socket that listens for incoming connections on the specified port
                ServerSocket serverSocket = new ServerSocket(port);
        ) {
            SimpleLogger.info("[+] Server started on port " + port);

            while (isServerListening) {
                // Wait for a client to connect. This is a blocking call that stops execution until a client connects
                Socket clientSocket = serverSocket.accept();

                // Create a new thread for each client connection
                new SimpleServerThread(clientSocket).start();
            }

        } catch (IOException error) {
            SimpleLogger.error("[-] Error: " + error.getMessage());

            // Kill the server
            System.exit(-1);
        }

    }

}
