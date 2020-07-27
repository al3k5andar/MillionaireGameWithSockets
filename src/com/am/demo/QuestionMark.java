package com.am.demo;

import java.util.List;

public class QuestionMark
{
    private static int questionId;
    private int id= questionId++;
    private String question;
    private String answer;
    private List<String> possibleAnswers;

    public QuestionMark() {
    }

    public QuestionMark(String question, String answer, List<String> possibleAnswers) {
        this.question = question;
        this.answer= answer;
        this.possibleAnswers= possibleAnswers;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    @Override
    public String toString() {
        return "QuestionMark{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
