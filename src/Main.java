import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Manga> mangaList = new ArrayList<>();
    static String[] rows = new String[8];
    static String title = "Title@ Score@ Vote@ Popularity@ Members@ Favorite@ Status@ Demographics";
    public static void main(String[] args) throws IOException {
        populateList();
        displayMenu();
        System.out.println();
        displayList();

    }
    //DISPLAY MAIN MENU
    public static void displayMenu(){
        Scanner scn = new Scanner(System.in);
        //MAIN MENU
        System.out.println("\n\nWelcome to the MangaHub");
        System.out.print("""
                What would you like to do?
                A. Show the list of available manga
                B. Sort Manga
                C. Search for a specific manga
                C. Suggest a manga
                D. Show overall MangaHub statistics
                Please select an option:\s
               \s""");
        String chc = scn.next();
        //SWITCH STATEMENTS FOR LETTING USERS CHOOSE
        switch (chc) {
            case "a":
            case "A":
                break;
            case "b":
            case "B":
                break;
            case "c":
            case "C":
                System.out.println("Input manga title here: ");
                scn.nextLine();
                String key = scn.nextLine().toLowerCase();
                displayTitle(title);
                for (Manga manga : mangaList) {
                    if (manga.getTitle().toLowerCase().startsWith(key)) {
                        System.out.println(manga);
                    }
                }
                break;
            case "d":
            case "D":
                break;
        }
    }

    public static void displayTitle(String title) {
        String[] titles = title.split("@");
        for (String s : titles) {
            System.out.printf("%-20s", s);
        }
        System.out.println();
    }

    public static void populateList() throws IOException {
        String filePath = "src\\DataSet.csv";
        String line;
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        br.readLine();
        while ((line = br.readLine()) != null) {
            rows = line.split("@");
            mangaList.add(new Manga(rows[0],
                    Double.parseDouble(rows[1]),
                    Integer.parseInt(rows[2]),
                    Integer.parseInt(rows[3]),
                    Integer.parseInt(rows[4]),
                    Integer.parseInt(rows[5]),
                    rows[6],
                    rows[7]));

        }
    }
    public static void displayList(){
        displayTitle(title);
        for (Manga manga : mangaList) {
            if (manga.getTitle().length() < 20) {
                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s", manga.getTitle(), manga.getScore(), manga.getVotes(), manga.getPopularity(), manga.getMembers(), manga.getFavorites(), manga.getStatus(), manga.getDemographics());
            } else {
                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s", manga.getTitle().substring(0,19), manga.getScore(), manga.getVotes(), manga.getPopularity(), manga.getMembers(), manga.getFavorites(), manga.getStatus(), manga.getDemographics());
            }
            System.out.println();
        }
    }
}