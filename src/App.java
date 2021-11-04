import java.util.Random;
import java.util.Scanner;

public class App {

    private static Random random;
    private static Scanner scanner;

    enum R_P_S {
        ROOK(1,"グー"),
        SCISSORS(2,"チョキ"),
        PAPER(3,"パー");

        private R_P_S(final int num, final String name){

        }
    }

    static {
        init();
    }

    public static void main(String[] args) {
        


        fin();
    }

    private static void init() {

        random = new Random();
        scanner = new Scanner(System.in);
    }

    private static void fin() {
        scanner.close();
    }



    private static void print(String str){
        System.out.print(str);
    }

    private static void println(String str){
        System.out.println(str);
    }

    private static void println(String str, Object... args){
        System.out.println(String.format(str, args));
    }
}
