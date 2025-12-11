package Jeopardy.controller;

import com.opencsv.*;

import Jeopardy.model.Category;
import Jeopardy.model.Option;
import Jeopardy.model.Question;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSVInput {

    public static List<Category> createCategories(String filePath){
        try(CSVReader dataInput = new CSVReader(new FileReader(filePath))){
            List<String[]> rows = dataInput.readAll();
            rows.remove(rows.getFirst()); //Removes Headers

            List<Category> categories = new ArrayList<>();

            for(String[] row:rows){ //Creates Categories, ensures there are no duplicate categories
                String name = row[0];
                boolean exists = false;
                for(Category c:categories){
                    if(Objects.equals(c.getName(), name))
                        exists = true;
                }
                if(!exists){
                    Category category = new Category(name);
                    categories.add(category);
                }
            }

            for(String[] row:rows){ //Creates Questions and options for the questions
                Option A = new Option('A', row[3]);
                Option B = new Option('B', row[4]);
                Option C = new Option('C', row[5]);
                Option D = new Option('D', row[6]);

                List<Option> options = new ArrayList<Option>();
                options.add(A);
                options.add(B);
                options.add(C);
                options.add(D);

                Question question = new Question(Integer.parseInt(row[1]), row[2], options, row[7].charAt(0));

                for(Category c:categories){
                    if(c.getName().equals(row[0]))
                        c.addQuestion(question);
                }
            }

            return categories;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
