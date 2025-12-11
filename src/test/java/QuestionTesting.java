import java.util.ArrayList;
import java.util.List;

import Jeopardy.model.Option;
import Jeopardy.model.Question;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionTesting {
    private Question question;
    private List<Option> options;

    @BeforeEach
    public void setUp() {
        options = new ArrayList<>();
        options.add(new Option('A', "Answer A"));
        options.add(new Option('B', "Answer B"));
        options.add(new Option('C', "Answer C"));
        options.add(new Option('D', "Answer D"));
        question = new Question(100, "What is the question?", options, 'A');
    }//Test setup for 100-point question with 4 options (A-D).

    @Test
    public void testQuestionCreation() {
        assertEquals(100, question.getScore());
        assertEquals("What is the question?", question.getQuestion());
        assertEquals('A', question.getCorrectAnswer());
        assertFalse(question.isAnswered());
    }//Test to ensure that a question is created with the correct attributes.

    @Test
    public void testMarkAnswered() {
        assertFalse(question.isAnswered());
        question.markAnswered();
        assertTrue(question.isAnswered());
    }//Test to ensure that marking a question as answered is working correctly.

    @Test
    public void testGetOptions() {
        List<Option> retrievedOptions = question.getOptions();
        assertEquals(4, retrievedOptions.size());
        assertEquals('A', retrievedOptions.get(0).getHeader());
    }//Test to ensure that the options for the question answer are retrieved correctly.

    @Test
    public void testDifferentScoreValues() {
        Question q200 = new Question(200, "Question 2", options, 'B');
        Question q500 = new Question(500, "Question 3", options, 'C');
        
        assertEquals(100, question.getScore());
        assertEquals(200, q200.getScore());
        assertEquals(500, q500.getScore());
    }//Test to ensure that questions with different score values are handled correctly by the game.
}
