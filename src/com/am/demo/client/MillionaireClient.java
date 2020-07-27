package com.am.demo.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MillionaireClient
{
    private Socket client;
    private Scanner scanner;

    public void establishConnection(){
        try{
            client= new Socket("localhost", 3000);
            scanner= new Scanner(System.in);
            System.out.println("Connected to service");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void receiveDataTest(){
        try(BufferedReader reader= new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter writer= new PrintWriter(client.getOutputStream(),true)){
            boolean flag= false;
            while (true){

                String readerData;
                while (true){
                    if((readerData= reader.readLine())!= null)
                        if(readerData.equals("END")){
                            break;
                        }
                        if(readerData.equals("Game Over")){
                            flag= true;
                            break;
                        }
                    System.out.println(readerData);
                }
                if (flag)
                    break;
                else {
                    System.out.println("Enter answer number: ");
                    String answer= scanner.nextLine();
                    writer.println(answer);

                    String status= reader.readLine();
                    System.out.println(status);
                }
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        MillionaireClient client= new MillionaireClient();
        client.establishConnection();
        client.receiveDataTest();
    }
}
