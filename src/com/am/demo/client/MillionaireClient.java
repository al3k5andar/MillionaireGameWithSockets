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
            while (!flag){

                StringBuilder sb= new StringBuilder();
                String readerData;
                while (true){

                    if((readerData= reader.readLine())!= null)
                        if(readerData.equals("END"))
                            break;
                        else
                        {
                            sb.append(readerData);
                            sb.append("\n");
                        }
                }
                String questionString= sb.toString();
                System.out.println(questionString);
                if(questionString.contains("Game Over"))
                    break;
                else
                {
                    System.out.println("Enter answer number: ");
                    String answer= scanner.nextLine();
                    writer.println(answer);

                    try{
                        String status;
                        Thread.sleep(2000);
                        while((status= reader.readLine())!= null){
                            if(!status.equals("Game Over")) {
                                if(status.equals("END"))
                                    break;
                                System.out.println(status);
                            }
                            else {
                                System.out.println(status);
                                writer.println("Game Over");
                                flag= true;
                                break;
                            }
                        }
                        System.out.println();
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
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
