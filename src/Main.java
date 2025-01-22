import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        while (!stop) {
            if (chc.equals("Y") || chc.equals("y")) {
                displayList();
                System.out.println("===================================================================================================================================================================");
                System.out.print("""
                        What would you like to do next?
                        A. Search for a specific manga
                        B. Sort Manga
                        C. Suggest a manga
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
    public static void readHeader() throws IOException {
        line = br.readLine();
        header = line.split("@");
    }
    public static void displayHeader(){
        System.out.println("===================================================================================================================================================================");
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
        for (Integer integer : mangasFound) {
            System.out.println(mangaList.get(integer).toString());
        }
        System.out.println();
        System.out.println("Found " + counter + " manga");
        counter = 0;
    }
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
    public static void sortMangaAlphabetically() {
        mangaList.sort(Comparator.comparing(m -> m.getTitle().toLowerCase()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    public static void sortMangaByScore() {
        mangaList.sort((m1, m2) -> Double.compare(m2.getScore(), m1.getScore()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    public static void sortMangaByPopularity() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getPopularity(), m1.getPopularity()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    public static void sortMangaByMembers() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getMembers(), m1.getMembers()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    public static void sortMangaByVotes() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getVotes(), m1.getVotes()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    public static void sortMangaByFavorites() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getFavorites(), m1.getFavorites()));
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga.toString());
        }
        System.out.println("===================================================================================================================================================================");
    }
    public static List<Manga> filterMangaByStatus(String status) {
        return mangaList.stream()
                .filter(manga -> manga.getStatus().equals(status))
                .collect(Collectors.toList());
    }
    public static List<Manga> filterMangaByDemographic(String demographic) {
        return mangaList.stream()
                .filter(manga -> manga.getDemographics().equals(demographic))
                .collect(Collectors.toList());
    }
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
    private static void displayFilteredList(List<Manga> filteredList) {
        displayHeader();
        for (Manga manga : filteredList) {
            System.out.println(manga);
        }
        System.out.println("Found " + filteredList.size() + " mangas");
        System.out.println("===================================================================================================================================================================");
    }
}

