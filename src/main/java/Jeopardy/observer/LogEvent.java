package Jeopardy.observer;

// LogEvent class using Builder pattern
public class LogEvent {
    private String playerName;
    private String activity;
    private String category;
    private int questionValue;
    private String answerGiven;
    private String result;
    private String scoreAfterPlay; // renamed for clarity

    // Builder Pattern to avoid huge constructors
    public static class Builder {
        private final LogEvent event = new LogEvent();

        public Builder playerName(String name) { event.playerName = name; return this; }
        public Builder activity(String a) { event.activity = a; return this; }
        public Builder category(String c) { event.category = c; return this; }
        public Builder questionValue(int q) { event.questionValue = q; return this; }
        public Builder answerGiven(String a) { event.answerGiven = a; return this; }
        public Builder result(String r) { event.result = r; return this; }
        public Builder scoreAfterPlay(String s){ event.scoreAfterPlay = s; return this; }

        public LogEvent build() { return event; }
    }

    // Getters
    public String getPlayerName() { return playerName; }
    public String getActivity() { return activity; }
    public String getCategory() { return category; }
    public int getQuestionValue() { return questionValue; }
    public String getAnswerGiven() { return answerGiven; }
    public String getResult() { return result; }
    public String getScoreAfterPlay() { return scoreAfterPlay; }
}
