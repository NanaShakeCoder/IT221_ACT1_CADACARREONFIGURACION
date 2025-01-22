/*
INITIAL DOCUMENTATION

This program provides a way to manage a csv file containing raw data about a manga list. The csv file is delimited by an @ symbol and is separated into 8 columns. These
columns contain the manga's title, score, vote, popularity, members, favorites, status, and demographics. The program will have 7 main functions that are displayed on a
menu which will help the user  navigate the program. These functions are displaying the unprocessed list of manga, searching for a manga that contains the keyword, sorting
the manga list, suggesting a manga, filtering the list of manga for a specific keyword, showing the top 10 manga per category. Some of these functions, such as sorting the
manga list, also have submenu for more specific functions like sorting alphabetically.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    //This is where the initialization and declaration of the commonly used variables are found
    static ArrayList<Manga> mangaList = new ArrayList<>();
    static String[] rows = new String[8];
    static String[] header = new String[8];
    static String filePath = "src\\CadaGabrielCarreonZhakFiguracionJacob.csv";
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

    //This is where the program starts. The main method calls these methods to ready the list and to display the menu.
    //The readHeader() method is only there to read the first line of csv file
    public static void main(String[] args) throws IOException {
        readHeader();
        populateList();
        displayMenu();
    }
    //This method displays the main menu of the program and also the banner for the console-based UI
    //It also handles the different cases for each choice the user makes, while other functions also do that for their submenus
    public static void displayMenu() {
        Scanner scn = new Scanner(System.in);
        //MAIN MENU
        System.out.println("""
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⣴⡂⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢼⣷⣿⣿⡰⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⡞⣿⣿⣿⣿⣇⣣⣔⣖⢀⠀⣀⣤⣖⣂⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⡀⠀⠀⠠⣶⣆⢧⣿⣿⣞⣿⣿⣿⣿⣷⣷⣾⣿⣿⣿⢿⢇⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⣤⣤⡀⠀  ⠀⢀⣤⣄⠀⠀⠀⣠⡤⠀⣠⣤⣤⣤⣤⡄⢀⣤⠀⠀⠀⠀⠀⢠⣤⣤⣤⣤⡤⠀⣠⣤⣤⣤⣤⡄ ⢀⣤⡀⠀⠀⢀⣠⡄⠀⣤⣤⣤⣤⣤⡄
                ⠀⡴⣽⣗⡵⣠⢃⣿⣿⣿⣿⣟⡿⣽⣞⡽⣯⣟⣽⣻⣿⣿⣿⣿⠮⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⣿⡇⢀⣴⠟⢻⣿⠀⣠⡾⠋⠀⣰⣿⣭⣭⣭⡭⠁⣼⠏⠀⠀⠀⠀⢠⡿⠉⠉⠉⠉⠁⣰⡟⠉⠉⣹⡟⢀⣾⣿⡇⠀⣠⣾⡿⠁⣼⣯⣭⣭⣭⡍⠀
                ⠀⢇⢿⣿⣿⣿⣿⡿⣿⢿⣧⣯⣽⣿⠿⠻⠿⠿⣮⣿⢾⡽⣿⣿⣳⣾⣿⣿⠕⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣷⠟⠁⠀⢸⣿⡾⠋⠀⠀⢰⣿⣉⣉⣉⡉⠁⣼⣏⣀⣀⣀⢠⣿⣁⣀⣀⣀⠀⣰⣿⣀⣀⣰⡟⢀⣾⠋⣿⣧⠾⢹⡿⠁⣼⣯⣉⣉⣉⡉⠀⠀
                ⠀⠈⢞⣽⣿⡾⣽⣻⣟⡟⣾⡿⠋⠀⠀⠄⡀⢀⠈⠙⢿⣽⣯⢿⣿⣿⣿⣯⢧⡇⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠁⠀⠀⠀⠈⠉⠀⠀⠀⠀⠉⠉⠉⠉⠉⠁⠈⠉⠉⠉⠉⠉⠀⠉⠉⠉⠉⠉⠁⠀⠉⠉⠉⠉⠉⠁⠈⠁⠀⠉⠉⠀⠉⠁⠀⠉⠉⠉⠉⠉⠀⠀⠀
                ⠀⠀⣨⢸⣿⣿⢿⣿⢾⣽⣿⠃⠀⠂⠁⠄⡀⢲⠇⢀⠂⠹⢿⣿⣟⡾⣿⣿⣷⣶⣶⣶⣅⣦⣶⣴⣶⣶⣒⣒⣒⣒⣒⡦⠤⠶⠖⠶⠖⠢⠴⠴⣒⣒⣦⣄⠤⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⡕⣿⣿⣯⢿⣯⢟⣾⣟⠀⠄⠡⢈⠠⠐⣼⠈⢀⠐⡤⠈⠻⣯⣟⣯⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣾⣖⣅⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠾⢿⣿⠿⠿⠇⢠⡿⠿⠿⢿⣿⠃
                ⠀⣠⢜⣸⣿⣟⣯⣿⡾⣿⣿⡄⠈⠄⠂⡀⢂⡏⡄⠂⢠⡇⠐⡀⠈⠻⣯⣿⢯⣿⣿⣿⣿⠿⠿⠟⠻⠛⠻⠻⠿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡞⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⠇⠀⠀⢠⡿⠁⠀ ⢀⣾⠃⠀
                ⢸⢺⣿⣿⣿⢿⣾⣻⡽⣶⣻⣷⡌⢀⠂⠄⢸⡅⠄⠂⢼⠣⠐⢀⠁⠂⠙⢻⣿⡾⠋⠁⠀⠀⢀⠠⠀⠐⠀⠄⠀⡀⠀⡀⠉⠛⠿⣿⠛⠛⠻⣿⣿⣿⣿⣿⣿⣿⣿⣷⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠼⠏⠀⠀⠀⠿⠿⠿⠿⠿⠃⠀⠀
                ⠀⠑⢜⠛⡿⢿⣾⢿⣽⣷⢿⡽⣿⣤⠈⠀⣾⠀⠐⠈⣾⠀⡁⠂⠈⠄⡁⣶⠏⠀⢀⠀⠂⠁⡀⠠⠀⡁⢂⠀⠡⢀⠐⠀⠂⠁⡀⠈⠙⢦⣴⣿⣿⣿⣿⣿⣿⣿⢮⠌⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠈⠲⠥⣿⣿⣷⣿⣻⡽⣟⡿⣷⣌⠃⠈⠄⢡⡟⠂⡀⠡⢈⠀⣼⠇⠀⠀⠂⠀⡐⠀⠄⡐⢀⢀⡀⠄⢂⠀⠄⢂⠈⠀⡐⠀⢠⣾⣿⣿⣿⣿⣿⣿⣿⣞⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣤⣤⣤⣤⣤⣤⡄⠀⣤⠀⠀⠀ ⣤⠄⢠⣤⣤⣤⣤⣤⠀
                ⠀⠀⠀⠀⠀⠑⢪⠕⣟⣿⣷⣿⡿⣽⣻⣿⣷⣈⠀⢲⠇⡀⠄⢁⠠⢰⡏⡀⠐⠈⢀⠐⠀⢁⠠⠐⠀⡀⢀⠐⠀⡈⢀⠠⠀⠐⠀⢠⣿⣿⣿⣿⣿⣿⣿⣿⢮⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⣹⡟⠉⠉⠀⣼⣯⣤⣤⣼⠏⢠⣿⣭⣭⣭⣭⠁⠀
                ⠀⠀⠀⠀⠀⠠⡱⣿⣿⣿⣷⣽⣿⡽⣿⣮⢿⣿⣦⡄⠠⡀⠌⠀⠄⣿⢁⠀⠄⠈⠀⠠⠈⠀⢀⠂⡀⢀⠂⠀⠐⠀⠂⠐⠀⡀⠀⣿⣿⣿⣿⣿⣿⡿⠛⢿⡦⣑⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⡟⠀⠀⠀⣼⠏⠉⠉⣽⡟⢀⣿⣯⣩⣍⣭⠁⠀⠀
                ⠀⠀⠀⠀⠀⠀⠈⠊⠭⢰⣶⡺⢿⣿⣷⣻⣿⡾⣽⣿⣦⡐⢀⠡⢰⡏⠀⠠⠀⠂⠁⡠⢀⣡⡠⠤⠄⣀⡀⠂⠀⠂⠈⠐⠠⠀⠐⣿⣿⣿⣿⣿⡟⠁⠀⠀⠻⣾⢬⢆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⠟⠀⠀⠀⠈⠉⠀⠀⠀⠉⠀⠈⠉⠉⠉⠉⠁⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⢨⢲⣾⣿⣿⣷⣿⣷⣯⣟⣿⣦⡄⢸⡇⠄⠁⢀⠠⠀⣰⣿⣿⣿⣷⣦⡀⠙⢢⡌⠀⠄⠁⠠⠀⠠⠙⠻⠿⠿⠋⠀⠀⠠⢀⠀⠘⣿⣪⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣇⣿⡇⡞⠸⣿⣾⣽⣾⡿⣽⢿⣾⡃⠀⡈⠀⠀⢸⣿⣿⣿⣿⣿⣿⡀⠀⠀⢳⠀⠂⠈⢀⠈⢀⠐⠀⠠⠀⢀⠐⠈⠀⡀⠈⠀ ⠈⢷⡯⡣⠀⠀⠀⠀⠀⠀⠀⠻⣿⣿⠀⠀⢀⣴⣿⠃⣰⡿⠿⠿⣿⡟ ⢀⣾⣿⡀⠀⣼⠏⢀⣾⠿⠿⠿⠿⠃⢠⡿⠿⠿⢿⡿⠁
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⢻⣼⠃⡃⣤⣿⣿⣿⣷⣿⣯⣿⣿⠄⠂⢀⠀⠄⢺⣿⣿⣿⣿⣿⣿⣿⡇⠀⢸⢀⠈⠀⠄⠐⠠⠀⠌⠐⠠⠀⠂⠄⠡⢀⠠⠁⠄⠈⢷⡽⢥⠀⠀⠀ ⠀⣰⡟⣿⢀⣴⢿⣿⠃⣰⡿⠿⠿⢿⡿⢀⣾⠋⢻⣇⣼⠟⢀⣾⠃⠾⢿⣿⠃⢠⡿⠿⠿⢿⣿⠁⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢘⢼⣿⠀⣇⢹⣿⣿⣿⡿⢹⣿⣟⣿⡇⠀⠂⠀⠄⠘⣿⣿⣿⣿⣿⣿⡿⠀⠀⡼⠀⠠⠐⠀⠂⠄⠂⠄⠂⠄⡐⠠⢀⠂⠄⡀⠄⠠⠀⠈⢿⡩⣣⠀⠀⠀⣰⡟⠀⠿⠟⠁⠾⠃⠰⠟⠀⠀⠰⠿⠁⠾⠏⠀⠘⠿⠟⠀⠾⠿⠶⠶⠾⠏⠠⠿⠁⠀⠀⠿⠃⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣸⣿⡀⡘⢄⠙⠛⣛⢋⡆⠀⠉⢹⠀⢀⠀⠂⠈⣆⠘⠻⣛⠛⠛⠋⢀⡠⠊⢠⠴⠀⢀⠐⠈⡀⠁⡈⢀⠂⠀⡁⢀⠈⢀⠀⡐⠀⡀⠂⠈⣷⣣⢣⠐⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡏⣿⡇⠉⠀⠉⠉⠁⢸⣇⠀⠀⣼⣇⡀⠀⠀⣰⡟⠀⠠⠀⠌⠉⠁⢀⠀⠀⢀⠛⠆⠀⠐⠠⠀⠡⠐⠀⠌⠐⠀⠂⠈⠀⠂⠠⠁⠀⠐⠀  ⠸⣯⢋⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⠀⣤⣤⡤⠀⠀⢀⣤⠀⣠⡄⠀⠀⣠⡄⢀⣤⣤⣤⣤⣤⠄
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣹⣻⡄⠀⠀⡀⠠⠀⡉⠛⣿⣟⣻⣟⢿⡟⠋⠀⢀⠀⠂⠀⠂⠄⠠⠐⠈⠀⠄⠠⠐⠈⠄⢂⠡⠠⠈⠄⢂⠡⢀⠁⢂⠡⠐⠠⠈⠠⠀⠀⢹⡟⡰⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀  ⢨⣿⣥⣤⣤⣿⠃⣰⡟⠀⠀⢠⡿⠁⣼⣯⣭⣭⣽⠏⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠡⠭⣿⡄⠀⠀⠄⠠⠀⠄⠸⣯⢿⣼⣫⠇⠀⠀⠄⠀⠄⡁⠠⠀⠄⠠⢀⠁⠠⠀⠄⡈⢀⠠⢀⠀⡁⠠⠀⠄⡀⢈⠀⠠⢀⠁⡀⠁⡀⠁ ⠈⣷⣇⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡿⠉⠉⢉⣿⠃⣰⣿⣤⣤⣤⡿⠁⣼⣯⣭⣭⣽⠏⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠑⡽⣿⣦⠀⡀⢀⠈⠀⡀⠘⠛⠻⠋⠀⠀⠁⠀⠌⠐⠀⢁⠈⠀⡁⠂⠈⡀⠁⠂⠐⠀⠂⠀⠂⢀⠁⠈⡀⠐⠀⡈⠐⠀⠂⢀⠁⢀⠈⠀  ⣻⡿⢰⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠿⠃⠀⠀⠈⠁⠀⠉⠉⠉⠉⠉⠁⠈⠉⠉⠉⠉⠉⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠊⠻⣷⡄⠀⢀⠐⠠⠈⠄⠀⠄⠀⠁⠂⢈⠐⠈⠐⠠⠈⡐⠀⢂⠡⠀⠡⠈⠄⠡⠈⠄⢁⠂⢈⠐⠠⠈⠐⢀⠂⠡⠈⠄⠂⠀⡐⠀ ⢹⣏⡼⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠑⢝⢿⣦⡀⠀⡀⠄⠀⢂⠀⠁⠄⠂⢀⠠⠈⠀⠄⠁⠠⠐⡀⠄⡐⠠⠐⠠⢀⠂⠄⢂⠠⠀⠄⢂⠐⡀⢂⠠⠐⠠⢀⠂⢀⠀ ⠠⢸⣧⠧⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠱⣿⣿⡆⠀⠀⠀⢂⣌⡀⠄⠈⠀⢠⡆⠁⠠⠈⠀⠄⠀⠄⢀⠁⡈⢀⠠⠀⠈⢀⠀⠂⠈⡀⠀⠄⡀⠠⠈⢀⠀⡈⠀⠠⠀   ⢸⣇⡇⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢣⢿⣷⡀⠀⠁⠀⠛⠁⠀⡀⠈⣌⠁⠀⠁⠀⡀⡼⠀⠂⠀⠠⠀⠀⢀⠈⠀⠀⡐⠀⠐⠀⠁⠂⠄⠁⠈⠄⠂⠐⠀⡐⠀    ⣽⡿⢻⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣾⣿⡅⠢⠀⠀⢎⢟⣷⡈⠀⠀⠀⢯⡀⢀⢠⡏⠀⠀⠈⠀⣼⠁⠀⡀⠐⠀⠀⣁⣠⠶⠛⠛⠉⠀⠂⠌⡐⠠⠂⠌⠐⡀⠂⡐⠀⠠⠀  ⣿⣿⡛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡸⣝⡿⠉⢿⡿⡱⣶⣿⣾⠿⣷⡀⠁⠀⠚⡇⠀⣾⠇⠀⠈⢠⡿⢵⡶⢶⣄⠀⢤⣾⠟⠁⠀⡀⠀⠂⠀⠄⡀⠄⠠⢀⠀⠂⠀⠄⢀⠈⠀⣰⣟⣇⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⢧⢿⡇⠀⠈⣿⣽⡿⠋⠀⠀⠈⢳⣆⣈⣼⠇⠀⢸⣇⢀⣼⡿⠁⣾⠙⠀⢿⣾⡟⠁⠀⠀⠐⠀⡀⢁⠈⠐⠀⠈⢀⠀⠀⢠⡼⠃⠀⠈⢠⣿⣿⡜⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣸⣿⢀⠀⠐⠘⣇⠀⠀⠄⠂⠁⠂⡈⢉⠁⠠⠐⠀⡉⠉⠉⠀⠀⣿⢈⠀⠀⢹⡀⠀⢀⠈⠐⠠⠐⠂⠈⠀⡁⠀⠂⠀⣠⠞⠁⠀⠀⣠⣿⡷⡵⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⢫⡟⠀⠈⠀⠌⠉⠀⠠⠀⡀⠂⠄⠐⡀⠀⠄⠐⠀⠠⠁⠐⠈⠀⣿⠀⠈⠀⠄⠳⠀⡀⠀⢂⠠⠀⠄⠁⡀⠀⡀⣤⠞⠁⠈⠀⠀⣴⡿⡿⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣼⣇⠀⠐⡀⢈⠀⡁⢀⠂⠀⠁⠀⠂⠀⠐⠈⢀⠈⠀⠄⡀⠂⠈⣿⠀⠀⠁⡀⠂⠄⠀⢂⠀⢀⣈⣀⡥⠴⠒⠋⠁⠀⡀⠂⣰⡾⠟⠻⢿⣖⠅⣄⣀⡤⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⡘⠿⣦⣤⣤⣤⣤⣤⣤⣌⣤⣡⣌⣤⣡⣌⣤⣤⣌⣤⣤⣤⣤⣼⣿⠀⠀⠁⠀⠂⠐⠀⠂⠈⠠⠐⠀⠐⠀⢀⠀⣂⣠⣥⣾⣯⣤⣤⣤⣤⣾⡿⣷⣷⣿⣯⣧⣪⢔⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠂⠽⠽⠿⠿⠿⠿⠿⠊⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠘⠿⠿⠿⡹⠟⣻⣛⣟⣛⣛⣛⣛⡛⣻⣟⢛⠛⠟⣛⣿⣿⣫⠾⠶⠶⠾⠿⠿⠷⠽⡭⢽⣭⣽⢷⣿⣿⣿⣧⣣⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                """);
        System.out.println("===================================================================================================================================================================");
        System.out.print("Would you like to see the list of available mangas: (Y/N)");
        String chc = scn.nextLine();
        boolean stop = false;
        //This is where the user choices are handled. It is handled by a while loop and nested if, switch, and while statements.
        //The looping of the while statements are handled by their own boolean variables to prevent confusion.
        while (!stop) {
            if (chc.equals("Y") || chc.equals("y")) {
                displayList();
                System.out.println("===================================================================================================================================================================");
                System.out.print("""
                        What would you like to do next?
                        A. Search for a manga
                        B. Sort Manga
                        C. Suggest 5 manga
                        D. Filter
                        E. Show Top 10s
                        F. Exit Program
                        Please Select an Option:
                        """);
                String chc2 = scn.nextLine();
                boolean stop2 = false;
                while (!stop2) {
                    switch (chc2) {
                        case "a":
                        case "A":
                            boolean stop3 = false;
                            System.out.println("===================================================================================================================================================================");
                            searchManga();
                            System.out.print("Would you like to search for another manga? (Y/N)");
                            String chc3 = scn.nextLine();
                            stop2 = repeatingQuestion(scn, stop2, stop3, chc3);
                            break;
                        case "b":
                        case "B":
                            sortManga();
                            break;
                        case "c":
                        case "C":
                            boolean stop4 = false;
                            suggestManga();
                            System.out.print("Would you like to get another 5 suggestions: (Y/N)");
                            String chc4 = scn.nextLine();
                            stop2 = repeatingQuestion(scn, stop2, stop4, chc4);
                            break;
                        case "d":
                        case "D":
                            boolean stop5 = false;
                            while (!stop5) {
                                System.out.print("What filter would you like to apply?\n" +
                                        "A. Status\n" +
                                        "B. Demographic\n" +
                                        "C. Go back to main menu\n" +
                                        "Please select an option: ");
                                String chc5 = scn.nextLine();
                                switch (chc5.toUpperCase()) {
                                    case "A":
                                    case "B":
                                        stop5 = !showFilteredList(chc5);
                                        break;
                                    case "C":
                                        System.out.println("Okay, going back to the main menu!");
                                        stop2 = true;
                                        stop5 = true;
                                        break;
                                    default:
                                        System.out.println("That is not a valid option, please select again.");
                                        break;
                                }
                            }
                            break;
                        case "e":
                        case "E":
                            boolean stop6 = false;
                            showTop10sMenu(scn);
                            System.out.print("Would you like to get another top 10 list: (Y/N)");
                            String chc6 = scn.nextLine();
                            stop2 = repeatingQuestion(scn, stop2, stop6, chc6);
                            break;
                        case "f":
                        case "F":
                            System.out.println("Okay, see you again!");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("===================================================================================================================================================================");
                            System.out.print("That is not a valid option, please select again: Y/N");
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
    //This portion of the program handles the repetitive query if the user chooses to use a function again such as asking the user if they want to search for a manga again
    public static boolean repeatingQuestion(Scanner scn, boolean stop2, boolean stop3, String chc3) {
        while(!stop3){
            if (chc3.equals("Y") || chc3.equals("y")) {
                break;
            }
            else if (chc3.equals("N") ||chc3.equals("n")) {
                System.out.println("Okay, going back to the main menu!");
                stop2 = true;
                stop3 = true;
            } else {
                System.out.println("That is not a valid option, please select again: Y/N");
                chc3 = scn.nextLine();
            }
        }
        return stop2;
    }
    //This method reads the first line of the csv file so that it can head straight to the main data of the csv file
    public static void readHeader() throws IOException {
        line = br.readLine();
        header = line.split("@");
    }
    //This method displays the now read header so that each display of a list can have a header
    public static void displayHeader(){
        System.out.println("===================================================================================================================================================================");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s", header[0], header[1], header[2], header[3], header[4], header[5], header[6], header[7]);
        System.out.println();
    }
    //This method populates the manga list
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
    //This method displays the now read manga list to the user
    public static void displayList() {
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga);
        }
    }
    //This method searches and displays the list of manga found in the list that contains the user's search keyword
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
        for (Integer integer : mangasFound) {
            System.out.println(mangaList.get(integer).toString());
        }
        System.out.println();
        System.out.println("Found " + counter + " manga");
        counter = 0;
    }
    //This method generates random manga suggestion to the user
    public static void suggestManga() {
        Random rand = new Random();
        System.out.println(" Here are 5 manga suggestions for you:");
        displayHeader();
        HashSet<Integer> indices = new HashSet<>();
        while (indices.size() < 5) {
            indices.add(rand.nextInt(mangaList.size()));
        }

        for (int index : indices) {
            System.out.println(mangaList.get(index));
        }
    }
    //This method asks the user what top 10 list they want to see based on the category
    public static void showTop10sMenu(Scanner scn) {
        System.out.println("\nWhat category would you like to see the top 10 mangas by?");
        System.out.println("A. Score");
        System.out.println("B. Vote");
        System.out.println("C. Popularity");
        System.out.println("D. Members");
        System.out.println("E. Favorite");
        System.out.print("Please select an option: ");
        String choice = scn.nextLine().toUpperCase();
        switch (choice) {
            case "A":
                showTop10s("Score");
                break;
            case "B":
                showTop10s("Vote");
                break;
            case "C":
                showTop10s("Popularity");
                break;
            case "D":
                showTop10s("Members");
                break;
            case "E":
                showTop10s("Favorite");
                break;
            default:
                System.out.println("Invalid choice. Returning to menu.");
                break;
        }
    }
    //This method displays the top 10 list based on the user's chosen category
    public static void showTop10s(String category) {
        List<Manga> sortedList = new ArrayList<>(mangaList);
        switch (category) {
            case "Score":
                sortedList.sort((m1, m2) -> Double.compare(m2.getScore(), m1.getScore()));
                break;
            case "Vote":
                sortedList.sort((m1, m2) -> Integer.compare(m2.getVotes(), m1.getVotes()));
                break;
            case "Popularity":
                sortedList.sort((m1, m2) -> Integer.compare(m2.getPopularity(), m1.getPopularity()));
                break;
            case "Members":
                sortedList.sort((m1, m2) -> Integer.compare(m2.getMembers(), m1.getMembers()));
                break;
            case "Favorite":
                sortedList.sort((m1, m2) -> Integer.compare(m2.getFavorites(), m1.getFavorites()));
                break;
        }
        System.out.println("\nTop 10 Mangas by " + category + ":");
        displayHeader();
        for (int i = 0; i < 10 && i < sortedList.size(); i++) {
            System.out.println(sortedList.get(i));
        }
    }
    //This method asks the user how they want to sort the manga list
    public static void sortManga() {
        Scanner scn = new Scanner(System.in);
        boolean validOption = false;
        System.out.println("===================================================================================================================================================================");
        while (!validOption) {
            System.out.println("""
                    How would you like to sort the manga list?
                    A. Alphabetically
                    B. By Score (Highest to Lowest)
                    C. By Votes (Highest to Lowest)
                    D. By Popularity (Highest to Lowest)
                    E. By Amount of Members (Highest to Lowest)
                    F. By Number of Favorites (Highest to Lowest)
                    G. Return to Main Menu
                    Please Select an Option: """);
            String sortOption = scn.next();
            switch (sortOption) {
                case "a":
                case "A":
                    sortMangaAlphabetically();
                    validOption = true;
                    break;
                case "b":
                case "B":
                    sortMangaByScore();
                    validOption = true;
                    break;
                case "c":
                case "C":
                    sortMangaByVotes();
                    validOption = true;
                    break;
                case "d":
                case "D":
                    sortMangaByPopularity();
                    validOption = true;
                    break;
                case "e":
                case "E":
                    sortMangaByMembers();
                    validOption = true;
                    break;
                case "f":
                case "F":
                    sortMangaByFavorites();
                    validOption = true;
                    break;
                case "g":
                case "G":
                    displayMenu();
                    return; // return to main menu
                default:
                    System.out.println("Invalid option. Please select again: (A/B/C/D/E/F/G)");
            }
        }
    }
    //This method sorts the manga alphabetically
    public static void sortMangaAlphabetically() {
        mangaList.sort(Comparator.comparing(m -> m.getTitle().toLowerCase()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    //This method sorts the manga by score from highest to lowest
    public static void sortMangaByScore() {
        mangaList.sort((m1, m2) -> Double.compare(m2.getScore(), m1.getScore()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    //This method sorts the manga by popularity from highest to lowest
    public static void sortMangaByPopularity() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getPopularity(), m1.getPopularity()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    //This method sorts the manga by the number of members from highest to lowest
    public static void sortMangaByMembers() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getMembers(), m1.getMembers()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    //This method sorts the manga by votes from highest to lowest
    public static void sortMangaByVotes() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getVotes(), m1.getVotes()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    //This method sorts the manga by the number of users who tagged it as their favorite from highest to lowest
    public static void sortMangaByFavorites() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getFavorites(), m1.getFavorites()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    //This method filters the manga by status that was selected by the user
    public static List<Manga> filterMangaByStatus(String status) {
        return mangaList.stream()
                .filter(manga -> manga.getStatus().equals(status))
                .collect(Collectors.toList());
    }
    //This method filters the manga by demographic that was selected by the user
    public static List<Manga> filterMangaByDemographic(String demographic) {
        return mangaList.stream()
                .filter(manga -> manga.getDemographics().equals(demographic))
                .collect(Collectors.toList());
    }
    //This method asks the user how they want to filter the manga list
    public static boolean showFilteredList(String filterChoice) {
        Scanner scnn = new Scanner(System.in);
        boolean llStop = false;
        while (!llStop) {
            if (filterChoice.equalsIgnoreCase("A")) { // Status filter
                System.out.println("Select from the status filters\n" +
                        "A. Finished\n" +
                        "B. Publishing\n" +
                        "C. Discontinued\n" +
                        "D. On Hiatus\n" +
                        "E. Go back\n" +
                        "Please choose from the options: ");
                String statusFilter = scnn.nextLine();
                switch (statusFilter.toUpperCase()) {
                    case "A":
                        displayFilteredList(filterMangaByStatus("Finished"));
                        break;
                    case "B":
                        displayFilteredList(filterMangaByStatus("Publishing"));
                        break;
                    case "C":
                        displayFilteredList(filterMangaByStatus("Discontinued"));
                        break;
                    case "D":
                        displayFilteredList(filterMangaByStatus("On Hiatus"));
                        break;
                    case "E":
                        llStop = true;
                        return false;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } else if (filterChoice.equalsIgnoreCase("B")) { // Demographic filter
                System.out.print("""
                    Select from the demographic filters
                    A. Shoujo
                    B. Shounen
                    C. Kids
                    D. Josei
                    E. Seinen
                    F. Go back to the filter menu
                    Please choose from the options:\s""");
                String demographicFilter = scnn.nextLine();
                switch (demographicFilter.toUpperCase()) {
                    case "A":
                        displayFilteredList(filterMangaByDemographic("[Shoujo]"));
                        break;
                    case "B":
                        displayFilteredList(filterMangaByDemographic("[Shounen]"));
                        break;
                    case "C":
                        displayFilteredList(filterMangaByDemographic("['Kids']"));
                        break;
                    case "D":
                        displayFilteredList(filterMangaByDemographic("[Josei]"));
                        break;
                    case "E":
                        displayFilteredList(filterMangaByDemographic("[Seinen]"));
                        break;
                    case "F":
                        llStop = true;
                        return false;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        }
        return true;
    }
    //This method displays the list depending on category and subcategory
    private static void displayFilteredList(List<Manga> filteredList) {
        displayHeader();
        for (Manga manga : filteredList) {
            System.out.println(manga);
        }
        System.out.println("Found " + filteredList.size() + " mangas");
        System.out.println("===================================================================================================================================================================");
    }
}

