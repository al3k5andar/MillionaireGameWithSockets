package com.am.demo.service;

import com.am.demo.QuestionMark;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceAction
{
    public static Map<Integer, QuestionMark> populateQuestionMarkMap(){
        Map<Integer,QuestionMark> questionMarkMap= new HashMap<>();

        try(BufferedReader questionAndAnswer= new BufferedReader(new FileReader(new File("questions.txt")));
            BufferedReader answers= new BufferedReader(new FileReader(new File("answers.txt")))){

            String questionData;
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
                System.out.println(questionMark);
                questionMarkMap.put(questionMark.getId(),questionMark);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return questionMarkMap;
    }

    private static String getUserInput(BufferedReader reader) throws IOException
    {
        return reader.readLine();
    }

}
