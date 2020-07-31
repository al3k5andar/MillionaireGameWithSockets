package com.am.demo.service;

import com.am.demo.QuestionMark;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ServiceAction
{
    public static Map<Integer, QuestionMark> populateQuestionMarkMap(){
        Map<Integer,QuestionMark> questionMarkMap= new HashMap<>();

        try(BufferedReader questionAndAnswer= new BufferedReader(new FileReader(new File("questions.txt")));
            BufferedReader answers= new BufferedReader(new FileReader(new File("answers.txt")))){

            String questionData;
            System.out.println("\nQuestions and answers: \n");

            while((questionData= questionAndAnswer.readLine())!= null)
            {
                List<String> answerList= new ArrayList<>();
                String[] questionDataArray= questionData.split("->");
                int id= Integer.parseInt(questionDataArray[0]);
                String question= questionDataArray[1];
                String answer= questionDataArray[2];

                String possibleAnswers;
                while((possibleAnswers= answers.readLine())!= null){
                    String[] possibleAnswersArray= possibleAnswers.split("\\.");
                    if(id== Integer.parseInt(possibleAnswersArray[0])){
                        answerList.add(possibleAnswersArray[1]);
                        answerList.add(possibleAnswersArray[2]);
                        answerList.add(possibleAnswersArray[3]);
                        answerList.add(possibleAnswersArray[4]);
                        break;
                    }
                }
                QuestionMark questionMark= new QuestionMark(question,answer,answerList);
                System.out.println(questionMark.getId()+ " "+questionMark.getQuestion()+" "+questionMark.getAnswer());
                questionMarkMap.put(questionMark.getId(),questionMark);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return questionMarkMap;
    }
    public static void startGame(Socket client, PrintWriter writer, BufferedReader reader)
    {
        try{
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
                    StringBuilder stringBuilder= new StringBuilder();

                    if(questionMark.getAnswer().equals(finalAnswer))
                    {
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
                        stringBuilder.append("Game Over");
                        stringBuilder.append("\n");
                        stringBuilder.append("END");
                        writer.println(stringBuilder.toString());
                        break;
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static String getUserInput(BufferedReader reader) throws IOException
    {
        return reader.readLine();
    }

}
