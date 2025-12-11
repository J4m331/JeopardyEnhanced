import Jeopardy.controller.CSVInput;
import Jeopardy.model.Category;
import Jeopardy.model.Question;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVInputTesting {
    private static final String TEST_CSV_PATH = "test_questions.csv";

    @BeforeAll
    public static void setupTestCSV() throws IOException {
        try (FileWriter writer = new FileWriter(TEST_CSV_PATH)) {
            writer.write("Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer\n");
            writer.write("Math,100,What is 2+2?,3,4,5,6,B\n");
            writer.write("Math,200,What is 3+3?,5,6,7,8,B\n");
            writer.write("Science,100,What is H2O?,Water,Air,Fire,Earth,A\n");
            writer.write("Science,200,What is CO2?,Water,Carbon Dioxide,Oxygen,Nitrogen,B\n");
        }
    }

    @Test
    public void testCreateCategories() {
        List<Category> categories = CSVInput.createCategories(TEST_CSV_PATH);
        
        assertNotNull(categories);
        assertEquals(2, categories.size());
    }

    @Test
    public void testCategoryNames() {
        List<Category> categories = CSVInput.createCategories(TEST_CSV_PATH);
        
        boolean hasMath = false;
        boolean hasScience = false;
        
        for (Category c : categories) {
            if (c.getName().equals("Math")) hasMath = true;
            if (c.getName().equals("Science")) hasScience = true;
        }
        
        assertTrue(hasMath);
        assertTrue(hasScience);
    }

    @Test
    public void testQuestionsPerCategory() {
        List<Category> categories = CSVInput.createCategories(TEST_CSV_PATH);
        
        for (Category c : categories) {
            assertEquals(2, c.getQuestions().size());
        }
    }

    @Test
    public void testQuestionValues() {
        List<Category> categories = CSVInput.createCategories(TEST_CSV_PATH);
        
        for (Category c : categories) {
            List<Question> questions = c.getQuestions();
            assertEquals(100, questions.get(0).getScore());
            assertEquals(200, questions.get(1).getScore());
        }
    }

    @Test
    public void testQuestionOptions() {
        List<Category> categories = CSVInput.createCategories(TEST_CSV_PATH);
        Category firstCategory = categories.get(0);
        Question firstQuestion = firstCategory.getQuestions().get(0);
        
        assertEquals(4, firstQuestion.getOptions().size());
    }

    @Test
    public void testCorrectAnswers() {
        List<Category> categories = CSVInput.createCategories(TEST_CSV_PATH);
        
        for (Category c : categories) {
            for (Question q : c.getQuestions()) {
                assertTrue(q.getCorrectAnswer() == 'A' || 
                          q.getCorrectAnswer() == 'B' || 
                          q.getCorrectAnswer() == 'C' || 
                          q.getCorrectAnswer() == 'D');
            }
        }
    }
}
