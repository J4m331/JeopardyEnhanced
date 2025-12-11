package Jeopardy.model;

public class Option {
    private char header;
    private String content;

    public Option(char header, String content){
        this.header = header;
        this.content = content;
    }

    public char getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }
    
    @Override
    public String toString() {
        return header + ": " + content;
    }

}
