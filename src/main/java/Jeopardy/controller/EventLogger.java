package Jeopardy.controller;

import java.io.FileWriter;
import java.time.format.DateTimeFormatter;

import Jeopardy.observer.LogEvent;
import Jeopardy.observer.LogObserver;

import java.time.LocalDateTime;

// EventLogger class to log game events to a file
public class EventLogger implements LogObserver{
    private static EventLogger lgInstance;
    private final String logFilePath = "game_event_log.csv";
    private int caseId = 1;

    private EventLogger() {
        // Initialize log file with headers
        try (FileWriter fw = new FileWriter(logFilePath, false)) {
            fw.append("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play\n");
        } catch (Exception e) {
            System.out.println("Error creating log file: " + e.getMessage());
        }
    }

    public static EventLogger getEventLoggerInstance() {
        if (lgInstance == null)
            lgInstance = new EventLogger();
        return lgInstance;
    }

    public void logEvent(LogEvent event) {
        try (FileWriter fw = new FileWriter(logFilePath, true)) {

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            fw.append(caseId + ",")
              .append(event.getPlayerName() + ",")
              .append(format(event.getActivity()) + ",")
              .append(timestamp + ",")
              .append(format(event.getCategory()) + ",")
              .append(event.getQuestionValue() + ",")
              .append(format(event.getAnswerGiven()) + ",")
              .append(format(event.getResult()) + ",")
              .append(format(event.getScoreAfterPlay()) + "\n");

            caseId++;

        } catch (Exception e) {
            System.out.println("Error writing to log: " + e.getMessage());
        }
    }

    private String format(String input) {
        if (input == null) return "";
        if (input.contains(",") || input.contains("\"")) {
            return "\"" + input.replace("\"", "\"\"") + "\"";
        }
        return input;
    }

    @Override
    public void Update(LogEvent logEvent) {
        this.logEvent(logEvent);
    }

    @Override
    public void Update() {

    }
}
