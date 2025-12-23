package Jeopardy.controller;

import Jeopardy.model.Category;
import Jeopardy.model.Option;
import Jeopardy.model.Question;
import Jeopardy.observer.Observer;
import Jeopardy.view.AddModelPromptWindow;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsIO {
    public void createSettings(Observer o){
        File settingsFile = new File("settings.csv");

        if(settingsFile.exists()){
            System.out.println("File Exists");
            return;
        }

        AddModelPromptWindow modelPrompt = new AddModelPromptWindow(o);

        String AiModel = modelPrompt.getModelString();
        //modelPrompt.dispose();

        try(CSVWriter settingsWriter = new CSVWriter(new FileWriter("settings.csv"))){
            settingsWriter.writeNext(new String[]{"AiModel"});
            settingsWriter.writeNext(new String[]{AiModel});
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void readSettings(Observer o){
        try(CSVReader settingInput = new CSVReader(new FileReader("settings.csv"))){
            List<String[]> rows = settingInput.readAll();
            rows.remove(rows.getFirst()); //Removes Headers

            //List<Category> categories = new ArrayList<>();
            //OllamaInput ollamaInput = new OllamaInput();
            //ollamaInput.createCategory("settings.csv");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
