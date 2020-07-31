package com.am.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MillionaireService
{
    private Socket client;

    public void establishConnection(){
        try{
            ServerSocket serverSocket = new ServerSocket(3000);
            client= serverSocket.accept();
            System.out.println("Client is connected");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public Socket getClient() {
        return client;
    }

    public static void main(String[] args) {
        MillionaireService service= new MillionaireService();

        service.establishConnection();

        try(PrintWriter writer= new PrintWriter(service.getClient().getOutputStream(),true);
            BufferedReader reader= new BufferedReader(new InputStreamReader(service.getClient().getInputStream())))
        {
            ServiceAction.startGame(service.getClient(),writer,reader);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
