import Jeopardy.controller.ReportGenerator;
import Jeopardy.model.Player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ReportGeneratorTest {
    private List<Player> players;
    private static final String REPORT_FILE = "test_jeopardyGameReport.txt";
    private static final String LOG_FILE = "test_game_event_log.csv";

    @BeforeAll
    static void setupTestLog() throws IOException {
        //Placeholder log file creation for testing
        //Test players are Alice, Bob and Charlie.
        try (FileWriter writer = new FileWriter(LOG_FILE)) {
            writer.write("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play\n");
            writer.write("1,System,Start Game,2024-01-01 10:00:00,,0,,,\n");
            writer.write("2,System,Select Player Count,2024-01-01 10:00:05,,0,3,,\n");
            writer.write("3,System,File Load,2024-01-01 10:00:10,,0,,Success,\n");
            writer.write("4,Alice,Create Player,2024-01-01 10:00:15,,0,Alice,,\n");
            writer.write("5,Bob,Create Player,2024-01-01 10:00:20,,0,Bob,,\n");
            writer.write("6,Charlie,Create Player,2024-01-01 10:00:25,,0,Charlie,,\n");
            writer.write("7,Alice,Answering Question,2024-01-01 10:01:00,Math,100,Answer A,CORRECT,100\n");
            writer.write("8,Bob,Answering Question,2024-01-01 10:02:00,Science,200,Answer B,CORRECT,200\n");
            writer.write("9,Charlie,Answering Question,2024-01-01 10:03:00,History,300,Answer C,INCORRECT,-300\n");
        }
    }

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player(1, "Alice"));
        players.add(new Player(2, "Bob"));
        players.add(new Player(3, "Charlie"));
        
        //Example scores
        players.get(0).addToScore(500);
        players.get(1).addToScore(300);
        players.get(2).addToScore(700);
    }

    @AfterEach
    void cleanup() {
        File reportFile = new File(REPORT_FILE);
        if (reportFile.exists()) {
            reportFile.delete();
        }
    }

    @Test
    void testGenerateTextReport() {
        System.out.println("Testing report generation...");
        try {
            ReportGenerator.generateTextReport(REPORT_FILE, players, LOG_FILE);
            System.out.println("SUCCESS: Report generated successfully");
        } catch (Exception e) {
            System.err.println("ERROR: Problem generating report: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testReportFileCreated() {
        System.out.println("Testing report file creation...");
        try {
            ReportGenerator.generateTextReport(REPORT_FILE, players, LOG_FILE);
            File reportFile = new File(REPORT_FILE);
            assertTrue(reportFile.exists());
            System.out.println("SUCCESS: Report file exists at: " + reportFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error: Problem creating report file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testReportContainsTitle() throws Exception {
        System.out.println("Testing report title...");
        try {
            ReportGenerator.generateTextReport(REPORT_FILE, players, LOG_FILE);
            
            try (BufferedReader br = new BufferedReader(new FileReader(REPORT_FILE))) {
                String content = br.lines().reduce("", (a, b) -> a + "\n" + b);
                assertTrue(content.contains("Jeopardy Game Summary"));
                System.out.println("SUCCESS: Report contains title 'Jeopardy Game Summary'.");
            }
        } catch (Exception e) {
            System.err.println("ERROR: Problem checking report title: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testReportContainsAllPlayers() throws Exception {
        System.out.println("Testing player names in report...");
        try {
            ReportGenerator.generateTextReport(REPORT_FILE, players, LOG_FILE);
            
            try (BufferedReader br = new BufferedReader(new FileReader(REPORT_FILE))) {
                String content = br.lines().reduce("", (a, b) -> a + "\n" + b);
                assertTrue(content.contains("Alice"));
                assertTrue(content.contains("Bob"));
                assertTrue(content.contains("Charlie"));
                System.out.println("SUCCESS: All players found: Alice, Bob, Charlie.");
            }
        } catch (AssertionError e) {
            System.err.println("ERROR: Not all players found in report");
            System.err.println("Expected: Alice, Bob, Charlie");
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR: Problem reading report: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testReportContainsScores() throws Exception {
        System.out.println("Testing scores in report...");
        try {
            ReportGenerator.generateTextReport(REPORT_FILE, players, LOG_FILE);
            
            try (BufferedReader br = new BufferedReader(new FileReader(REPORT_FILE))) {
                String content = br.lines().reduce("", (a, b) -> a + "\n" + b);
                assertTrue(content.contains("500"));
                assertTrue(content.contains("300"));
                assertTrue(content.contains("700"));
                System.out.println("SUCCESS: All scores found: 500, 300, 700.");
            }
        } catch (AssertionError e) {
            System.err.println("ERROR: Not all scores found in report");
            System.err.println("Expected scores: 500, 300, 700");
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR: Problem reading report: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testReportContainsFinalScoresSection() throws Exception {
        System.out.println("Testing 'Final Scores' section...");
        try {
            ReportGenerator.generateTextReport(REPORT_FILE, players, LOG_FILE);
            
            try (BufferedReader br = new BufferedReader(new FileReader(REPORT_FILE))) {
                String content = br.lines().reduce("", (a, b) -> a + "\n" + b);
                assertTrue(content.contains("Final Scores:"));
                System.out.println("SUCCESS: 'Final Scores:' section found.");
            }
        } catch (AssertionError e) {
            System.err.println("ERROR: 'Final Scores:' section not found.");
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR: Problem reading report: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testReportContainsSessionRundown() throws Exception {
        System.out.println("Testing 'Session Rundown' section...");
        try {
            ReportGenerator.generateTextReport(REPORT_FILE, players, LOG_FILE);
            
            try (BufferedReader br = new BufferedReader(new FileReader(REPORT_FILE))) {
                String content = br.lines().reduce("", (a, b) -> a + "\n" + b);
                assertTrue(content.contains("Session Rundown:"));
                System.out.println("SUCCESS: 'Session Rundown:' section found.");
            }
        } catch (AssertionError e) {
            System.err.println("ERROR: 'Session Rundown:' section not found.");
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR: Problem reading report: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testReportWithSinglePlayer() {
        System.out.println("Testing report with single player...");
        try {
            List<Player> singlePlayer = new ArrayList<>();
            singlePlayer.add(new Player(1, "Solo"));
            singlePlayer.get(0).addToScore(1000);
            
            ReportGenerator.generateTextReport(REPORT_FILE, singlePlayer, LOG_FILE);
            System.out.println("SUCCESS: Single player report generated successfully.");
        } catch (Exception e) {
            System.err.println("ERROR: Problem generating single player report: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testReportFileNotEmpty() {
        System.out.println("Testing report file content...");
        try {
            ReportGenerator.generateTextReport(REPORT_FILE, players, LOG_FILE);
            
            File reportFile = new File(REPORT_FILE);
            assertTrue(reportFile.length() > 0);
            System.out.println("SUCCESS: Report file is not empty (size: " + reportFile.length() + " bytes).");
        } catch (AssertionError e) {
            System.err.println("ERROR: Report file is empty.");
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR: Problem checking report file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testReportWithNegativeScores() {
        System.out.println("Testing report with negative scores...");
        try {
            players.add(new Player(4, "Dave"));
            players.get(3).addToScore(-100);
            
            ReportGenerator.generateTextReport(REPORT_FILE, players, LOG_FILE);
            
            File reportFile = new File(REPORT_FILE);
            assertTrue(reportFile.exists());
            assertTrue(reportFile.length() > 0);
            System.out.println("SUCCESS: Report handles negative scores correctly.");
        } catch (Exception e) {
            System.err.println("ERROR: Problem handling negative scores: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testReportContainsTurnInformation() throws Exception {
        System.out.println("Testing turn information in report...");
        try {
            ReportGenerator.generateTextReport(REPORT_FILE, players, LOG_FILE);
            
            try (BufferedReader br = new BufferedReader(new FileReader(REPORT_FILE))) {
                String content = br.lines().reduce("", (a, b) -> a + "\n" + b);
                assertTrue(content.contains("Turn"));
                System.out.println("SUCCESS: Turn information found in report.");
            }
        } catch (AssertionError e) {
            System.err.println("ERROR: Turn information not found in report.");
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR: Problem reading report: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}