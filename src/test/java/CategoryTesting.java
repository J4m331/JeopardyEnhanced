import java.util.ArrayList;
import java.util.List;

import Jeopardy.model.Category;
import Jeopardy.model.Option;
import Jeopardy.model.Question;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTesting {
    private Category category;
    private List<Option> options;

    @BeforeEach
    public void setUp() {
        category = new Category("Science");
        options = new ArrayList<>();
        options.add(new Option('A', "Answer A"));
        options.add(new Option('B', "Answer B"));
        options.add(new Option('C', "Answer C"));
        options.add(new Option('D', "Answer D"));
    }

    @Test
    public void testCategoryCreation() {
        assertEquals("Science", category.getName());
        assertTrue(category.getQuestions().isEmpty());
    }

    @Test
    public void testAddQuestion() {
        Question q = new Question(100, "Test?", options, 'A');
        category.addQuestion(q);
        assertEquals(1, category.getQuestions().size());
    }

    @Test
    public void testAddMultipleQuestions() {
        for (int i = 0; i < 5; i++) {
            Question q = new Question((i + 1) * 100, "Question " + i, options, 'A');
            category.addQuestion(q);
        }
        assertEquals(5, category.getQuestions().size());
    }

    @Test
    public void testAllAnsweredWhenEmpty() {
        assertTrue(category.allAnswered());
    }

    @Test
    public void testAllAnsweredWithUnanswered() {
        Question q1 = new Question(100, "Q1", options, 'A');
        Question q2 = new Question(200, "Q2", options, 'B');
        category.addQuestion(q1);
        category.addQuestion(q2);
        
        assertFalse(category.allAnswered());
    }

    @Test
    public void testAllAnsweredWhenAllMarked() {
        Question q1 = new Question(100, "Q1", options, 'A');
        Question q2 = new Question(200, "Q2", options, 'B');
        q1.markAnswered();
        q2.markAnswered();
        category.addQuestion(q1);
        category.addQuestion(q2);
        
        assertTrue(category.allAnswered());
    }
}
