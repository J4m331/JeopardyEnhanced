package Jeopardy.model;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    private static int screenNumber;
    private List<Category> categories;

    public Screen(){
        this.categories = new ArrayList<>();
    }

    public void addCategory(Category c){
        this.categories.add(c);
    }

    public int getScreenNumber(){
        return screenNumber;
    }

    public List<Category> getCategories(){
        return categories;
    }

    public boolean allAnswered(){
        for(Category c:categories){
            if(!c.allAnswered()){
                return false;
            }
        }
        return true;
    }

}
