package Jeopardy.model;

import java.util.ArrayList;
import java.util.List;

//Category class to hold a list of questions under a specific category name
public class Category {
    private String name;
    private List<Question> questions;

    public Category(String name){
        this.name = name;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question q){
        this.questions.add(q);
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions(){
        return questions;
    }

    public boolean allAnswered(){
        for(Question q:questions){
            if(!q.isAnswered()){
                return false;
            }
        }
        return true;
    }
}
