package Jeopardy.controller;

import Jeopardy.model.Category;
import Jeopardy.model.Option;
import Jeopardy.model.Question;
import Jeopardy.model.Screen;
import com.opencsv.CSVReader;
import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.request.ThinkMode;
import io.github.ollama4j.models.response.OllamaAsyncResultStreamer;
import io.github.ollama4j.models.response.OllamaResult;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class OllamaInput {
    public Category createCategory(String filePath, String categoryName){
        try(CSVReader settingsInput = new CSVReader(new FileReader(filePath))){
            List<String[]> rows = settingsInput.readAll();

            Category c = new Category(categoryName);
            Ollama ollama = new Ollama();

            rows.remove(rows.getFirst());
            System.out.println(rows.getFirst()[0]);
            String model = rows.getFirst()[0];
            ollama.pullModel(model);

            ollama.setRequestTimeoutSeconds(180);

            for(int i = 1; i <= 7;i++) {

                String prompt = String.format(
                        "Generate a unique, factually accurate Jeopardy-style trivia question about %s worth %d points. " +
                                "Output MUST be exactly one line with this format: " +
                                "\"<question text>\",\"<answer A>\",\"<answer B>\",\"<answer C>\",\"<answer D>\",\"<letter>\" " +
                                "Rules: " +
                                "1. Replace <question text> with your question " +
                                "2. Replace <answer A> through <answer D> with four answer options " +
                                "3. Replace <letter> with A, B, C, or D to indicate the correct answer " +
                                "4. Do NOT include the word 'Question' or 'AnswerA' - use actual content " +
                                "5. Do NOT include a header row " +
                                "6. Ensure exactly 5 commas separate the 6 values " +
                                "7. All values must be enclosed in double quotes with no spaces before/after commas " +
                                "8. ALL answers must be factually correct and verifiable " +
                                "9. The three incorrect answers should be plausible but clearly wrong " +
                                "10. The question should be %s difficulty " +
                                "11. Avoid questions where the answer is stated in the question itself " +
                                "Example: \"What is 2 + 2?\",\"4\",\"3\",\"5\",\"1\",\"A\"",
                        categoryName,
                        i * 100,
                        getDifficultyLevel(i * 100)
                );

                System.out.println(prompt);

                OllamaResult result = ollama.generate(
                        OllamaGenerateRequest.builder()
                                .withModel(model)
                                .withPrompt(prompt)
                                .build(),
                        null
                );

                System.out.println(result.getResponse());

                String resultString = result.getResponse();

                try (CSVReader csvReader = new CSVReader(new StringReader(resultString))) {
                    String[] fields = csvReader.readNext();

                    if (fields != null && fields.length == 6) {
                        String question = fields[0];
                        String answerA = fields[1];
                        String answerB = fields[2];
                        String answerC = fields[3];
                        String answerD = fields[4];
                        String correctAnswer = fields[5];

                        // Create your Screen object or process as needed
                        System.out.println("Parsed successfully:");
                        System.out.println("Q: " + question);
                        System.out.println("A: " + answerA);
                        System.out.println("B: " + answerB);
                        System.out.println("C: " + answerC);
                        System.out.println("D: " + answerD);
                        System.out.println("Correct: " + correctAnswer);

                        List<Option> options = new ArrayList<Option>();
                        Option o1 = new Option('A',answerA);
                        Option o2 = new Option('B',answerB);
                        Option o3 = new Option('C',answerC);
                        Option o4 = new Option('D',answerD);
                        options.add(o1);
                        options.add(o2);
                        options.add(o3);
                        options.add(o4);
                        Question q = new Question(i*100,question,options,correctAnswer.charAt(0));
                        c.addQuestion(q);
                    } else {
                        System.err.println("Invalid CSV format: " + resultString);
                    }
                }
            }

            ProcessBuilder llamaKiller = new ProcessBuilder("taskkill", "/F", "/IM", "ollama.exe");
            llamaKiller.start().waitFor();

            return c;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getDifficultyLevel(int points) {
        if (points <= 200) return "easy";
        if (points <= 400) return "moderate";
        if (points <= 600) return "hard";
        return "extremely-hard";
    }
}
