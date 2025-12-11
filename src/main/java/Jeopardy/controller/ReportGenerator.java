package Jeopardy.controller;

import com.opencsv.*;

import Jeopardy.model.Player;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

// Generates a simple TXT summary report containing final scores and turn-by-turn log summary.
public class ReportGenerator {

    public static void generateTextReport(String path, List<Player> players, String sessionSummaryFile) {
        try (FileWriter fw = new FileWriter(path, false)) {
            fw.append("Jeopardy Game Summary\n");
            fw.append("=====================\n\n");

            fw.append("Final Scores:\n");
            for (Player p : players) {
                fw.append(String.format("%s : %d\n", p.getName(), p.getScore()));
            }

            fw.append("\nSession Rundown:\n");

            //Uses newly created event log to write report
            try(CSVReader summary = new CSVReader(new FileReader(sessionSummaryFile))){
                List<String[]> rows = summary.readAll();
                for(int i = 0; i < (4 + players.size()); i++)
                    rows.remove(rows.getFirst()); //Removes first 4 rows, unnecessary data and header as well as player creation

                int turn = 1;
                for(String[] row:rows){
                    fw.append("Turn " + turn + "\n");
                    fw.append(row[1] + " picked category " + row[4] + " for " + row[5] + "\n");
                    fw.append(row[1] + " answered " + row[6] + " which was " + row[7] + "\n");
                    fw.append(row[1] + " currently has " + row[8] + " points.\n\n\n");
                    turn++;
                }

            } catch (Exception e){
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}
