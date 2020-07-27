package com.am.demo.service;

import com.am.demo.QuestionMark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Random;

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

    public void sendDataTest(){
        try(PrintWriter writer= new PrintWriter(client.getOutputStream(),true);
            BufferedReader reader= new BufferedReader(new InputStreamReader(client.getInputStream()))){
            Map<Integer, QuestionMark> questionMarkMap= ServiceAction.populateQuestionMarkMap();
            Random random= new Random();
            int wonPrice= 300;
            int mapCapacity= questionMarkMap.size();
            while(true){
                StringBuilder sb= new StringBuilder();

                if(questionMarkMap.isEmpty()) {
                    sb.append("Game Over");
                    sb.append("\n");
                    sb.append("END");
                    writer.println(sb.toString());
                    System.out.println("End");
                    break;
                }
                else
                {
                    int randomNo= random.nextInt(mapCapacity);
                    if(!questionMarkMap.containsKey(randomNo))
                        continue;
                    QuestionMark questionMark= questionMarkMap.get(randomNo);
                    sb.append(questionMark.getQuestion());
                    sb.append("\n\n");

                    for (int i = 0; i < questionMark.getPossibleAnswers().size(); i++) {
                        sb.append(i+1).append(" ").append(questionMark.getPossibleAnswers().get(i)).append("\n");
                    }
                    sb.append("END");
                    writer.println(sb.toString());

                    String incomingAnswer= reader.readLine();
                    String finalAnswer= questionMark.getPossibleAnswers().get(Integer.parseInt(incomingAnswer)-1);
                    if(questionMark.getAnswer().equals(finalAnswer)){
                        StringBuilder stringBuilder= new StringBuilder();
                        stringBuilder.append("Congratulate");
                        stringBuilder.append("\n");
                        stringBuilder.append("You won: ").append(wonPrice).append(" ").append("$");

                        wonPrice*= 2;

                        stringBuilder.append("\n");
                        stringBuilder.append("END");
                        writer.println(stringBuilder.toString());
                        questionMarkMap.remove(randomNo);
                    }
                    else
                    {
                        writer.println("Game Over");
                        break;
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MillionaireService service= new MillionaireService();

        service.establishConnection();

        service.sendDataTest();
    }
}
