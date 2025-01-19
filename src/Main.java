import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        String filePath = "src\\DataSet.csv";
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                for(int i = 0; i <= 7; i++) {

                        System.out.printf("%-20s", row[i]);
                }
                System.out.println();
            }
        }catch (Exception e) {

        }

        System.out.println("\n\nWelcome to the MangaHub");
        System.out.println("What would you like to do?" +
                "A. Show the list of available manga\n" +
                "B. Search for a specific manga\n" +
                "C. Suggest a manga\n" +
                "D. Show overall MangaHub statistics\n");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}