import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String filePath = "src\\DataSet.csv";
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                ArrayList<String> row = getStrings(line);

                for (String field : row) {
                    System.out.printf("%-20s", field.length() < 20 ? field : field.substring(0, 19));
                }
                System.out.println();
            }
        }catch (Exception e) {
            System.out.println();
            System.out.println(e.getMessage());
        }

        System.out.println("\n\nWelcome to the MangaHub");
        System.out.println("""
                What would you like to do?
                A. Show the list of available manga
                B. Search for a specific manga
                C. Suggest a manga
                D. Show overall MangaHub statistics
                """);
    }

    private static ArrayList<String> getStrings(String line) {
        ArrayList<String> row = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                row.add(currentField.toString());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }
        row.add(currentField.toString());
        return row;
    }
}