package ru.itis.rasimusv;

import com.beust.jcommander.JCommander;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static List<Service> clients = new ArrayList<>();

    public static void main(String [] args) {
        Args argv = new Args();
        JCommander.newBuilder().addObject(argv).build().parse(args);

        try {
            ServerSocket server = new ServerSocket(argv.port);
            while (true) {
                clients.add(new Service(server.accept()));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
