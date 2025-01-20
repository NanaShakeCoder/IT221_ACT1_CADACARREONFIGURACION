import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Manga> mangaList = new ArrayList<>();
    static String[] rows = new String[8];
    static String[] header = new String[8];
    static String filePath = "src\\DataSet.csv";
    static String line;
    static BufferedReader br;
    static {
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static int counter = 0;

    public static void main(String[] args) throws IOException {
        readHeader();
        populateList();
        displayMenu();

    }
    //DISPLAY MAIN MENU
    public static void displayMenu() {
        Scanner scn = new Scanner(System.in);
        //MAIN MENU
        System.out.println("Welcome to the MangaHub");
        System.out.print("Would you like to see the list of available mangas: (Y/N)");
        String chc = scn.nextLine();
        boolean stop = false;
        while (!stop) {
            if (chc.equals("Y") || chc.equals("y")) {
                displayList();
                System.out.println("""
                        \nWhat would you like to do next?
                        A. Search for a specific manga
                        B. Sort Manga
                        C. Suggest a manga
                        D. Show Top 10s
                        E. Exit Program
                        Please Select an Option: """);
                String chc2 = scn.nextLine();
                while (!stop) {
                    switch (chc2) {
                        case "a":;
                        case "A":
                            while (!stop){
                                searchManga();
                                System.out.print("Would you like to search for another manga: (Y/N)");
                                String chc3 = scn.nextLine();
                                if (chc3.equals("Y") || chc3.equals("y")) {
                                    searchManga();
                                }
                                else if (chc3.equals("N") ||chc3.equals("n")) {
                                    stop = true;
                                } else {
                                    System.out.println("That is not a valid option, please select again: Y/N");
                                    chc3 = scn.nextLine();
                                }
                            }
                            break;
                        case "b":;
                        case "B":;
                        case "c":;
                        case "C":;
                        case "d":;
                        case "D":;
                        case "e":;
                        case "E":
                            System.exit(0);
                            break;
                        default:
                            System.out.println("That is not a valid option, please select again: Y/N");
                            chc2 = scn.nextLine();
                    }
                }
            } else if (chc.equals("N") || chc.equals("n")) {
                System.out.println("Okay, see you again!");
                System.exit(0);
            } else {
                System.out.println("That is not a valid option, please select again: Y/N");
                chc = scn.nextLine();
            }
        }
    }

    public static void readHeader() throws IOException {
        line = br.readLine();
        header = line.split("@");
    }
    public static void displayHeader(){
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s", header[0], header[1], header[2], header[3], header[4], header[5], header[6], header[7]);
        System.out.println();
    }

    public static void populateList() throws IOException {
        while ((line = br.readLine()) != null) {
            rows = line.split("@");
            mangaList.add(new Manga(rows[0].replace("\"", ""),
                    Double.parseDouble(rows[1]),
                    Integer.parseInt(rows[2]),
                    Integer.parseInt(rows[3]),
                    Integer.parseInt(rows[4]),
                    Integer.parseInt(rows[5]),
                    rows[6],
                    rows[7]));

        }
    }
    public static void displayList() {
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga);
        }
    }
    public static void searchManga() {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Input manga title here: ");
        String key = scnr.nextLine().toLowerCase();
        ArrayList<Integer> mangasFound = new ArrayList<>();
        for (int i = 0; i < mangaList.size(); i++) {
            if (mangaList.get(i).getTitle().toLowerCase().contains(key)) {
                mangasFound.add(i);
                counter++;
            }
        }

        displayHeader();
        for (int i = 0; i < mangasFound.size(); i++) {
            System.out.println(mangaList.get(mangasFound.get(i)).toString());
        }
        System.out.println();
        System.out.println("Found " + counter + " manga");
        counter = 0;
    }
}