package ru.itis.rasimusv;

import java.io.*;
import java.net.Socket;

public class Service extends Thread {

    private final BufferedReader fromClient;
    private final PrintWriter toClient;
    private final Socket client;

    public Service(Socket client) throws IOException {
        this.client = client;
        fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        toClient = new PrintWriter(client.getOutputStream(), true);
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = fromClient.readLine();
                if(message.equals("null")) {
                    this.disconnect();
                    break;
                }
                send("Successfully sent");
                System.out.println("From Client: " + message);
                for (Service service : Server.clients) {
                    if (service != this) {
                        service.send(message);
                    }
                }
            }
        } catch (IOException e) {
            this.disconnect();
        }
    }

    private void send(String msg) {
        toClient.write(msg + "\n");
        toClient.flush();
    }

    private void disconnect() {
        try {
            if(!client.isClosed()) {
                client.close();
                fromClient.close();
                toClient.close();
                for (Service service : Server.clients) {
                    if(service.equals(this)) service.interrupt();
                    Server.clients.remove(this);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}