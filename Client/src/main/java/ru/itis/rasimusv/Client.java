package ru.itis.rasimusv;

import com.beust.jcommander.JCommander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static PrintWriter toServer;
    private static BufferedReader fromServer;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Args argv = new Args();
        JCommander.newBuilder().addObject(argv).build().parse(args);

        try {
            Socket client = new Socket(argv.host, argv.port);
            toServer = new PrintWriter(client.getOutputStream(), true);
            fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
            new Thread(receiverMessagesTask).start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        while (true) {
            sendMessage(scanner.nextLine());
        }
    }

    public static void sendMessage(String message) {
        toServer.println(message);
    }

    private static final Runnable receiverMessagesTask = () -> {
        while (true) {
            try {
                String messageFromServer = fromServer.readLine();
                if (messageFromServer != null) {
                    System.out.println("-> " + messageFromServer);
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    };
}
