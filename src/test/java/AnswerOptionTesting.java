import org.junit.jupiter.api.Test;

import Jeopardy.model.Option;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerOptionTesting {

    @Test
    public void testAnswerOptionCreation() {
        Option option = new Option('A', "My First Test Answer");
        assertEquals('A', option.getHeader());
        assertEquals("My First Test Answer", option.getContent());
    }//Test to ensure that options for answers are created correctly.

    @Test
    public void testAnswerOptionToString() {
        Option option2 = new Option('B', "My Second Test Answer");
        assertEquals("B: My Second Test Answer", option2.toString());
    }//Test to ensure that answer options are correctly represented in string form.

    @Test
    public void testMultipleAnswerHeaders() {
        Option optionA = new Option('A', "First");
        Option optionB = new Option('B', "Second");
        assertNotEquals(optionA.getHeader(), optionB.getHeader());
    }//Test to ensure that different answer options have distinct headers.
}