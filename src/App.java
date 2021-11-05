import java.util.Random;
import java.util.Scanner;

public class App {

    private static Random random;
    private static Scanner scanner;

    enum RPSHand {
        ROOK(1,"グー"),
        SCISSORS(2,"チョキ"),
        PAPER(3,"パー");

        public final int number;
        public final String name;

        public static final String NUMBER_RANGE = "1～3の数字";

        private RPSHand(final int setNum, final String setName){
            number = setNum;
            name = setName;
        }

        public String getInFoString() {
            return String.format("%s：%s", number, name);
        }

        public static RPSHand first(){
            return ROOK;
        }

        public static RPSHand last(){
            return PAPER;
        }

        public static RPSHand getHandFormNumber(int formNum){
            
            for (RPSHand hand : RPSHand.values()) {
                
                if(hand.number == formNum){

                    return hand;
                }
            }

            throw new IllegalArgumentException();
        }

        public static boolean isMatchFromNumber(int formNum) {
            
            for (RPSHand hand : RPSHand.values()) {
                
                if(hand.number == formNum){

                    return true;
                }
            }

            return false;
        }
    }

    enum GameResult {
        NONE,
        WIN,
        LOSE,
        DRAW,
    }

    private static final String MESSAGE_FOR_BLANK = "";
    private static final String MESSAGE_FOR_EXPLANATION_OUTLINE = "じゃんけん勝負";

    private static final String MESSAGE_FOR_REQUEST_INPUT_FORMAT_RPS = "グーチョキパーを数字で入力してね";
    private static final String MESSAGE_FORMAT_FOR_ERROR_FORMAT_RPS = "注意：出す手は%sで入れてね";

    private static final String MESSAGE_FOR_REQUEST_INPUT_RPS = "最初はぐー、じゃんけん：";
    private static final String MESSAGE_FOR_REQUEST_INPUT_RPS_CONTINUE = "あいこで：";

    private static final String MESSAGE_FORMAT_FOR_PLAYER_HAND_RPS = "自分：%s";
    private static final String MESSAGE_FORMAT_FOR_COMPUTER_HAND_RPS = "相手：%s";

    private static final String MESSAGE_FOR_RPS_RESULT_WIN = "あなたの勝ち！";
    private static final String MESSAGE_FOR_RPS_RESULT_LOSE = "あなたの負け……";
    private static final String MESSAGE_FOR_RPS_RESULT_DRAW = "あいこだよ！";


    static {
        init();
    }

    public static void main(String[] args) {
        
        dispExplanation();

        GameResult result = GameResult.NONE;
        while(canNextGame(result)){
            
            if(isFirstGame(result)){
                dispRequestInputFormatRPS();
            }

            int comHandNumber = createRandomNumber(RPSHand.first().number, RPSHand.last().number);
            RPSHand comHand = RPSHand.getHandFormNumber(comHandNumber);
            RPSHand playerHand = getPlayerHand(result);
            dispHand(playerHand, comHand);

            result = getGameResult(playerHand, comHand);
            dispResult(result);
        }

        fin();
    }

    private static void init() {

        random = new Random();
        scanner = new Scanner(System.in);
    }

    private static void fin() {
        scanner.close();
    }

    private static void dispExplanation() {
        println(MESSAGE_FOR_EXPLANATION_OUTLINE);
    }

    private static boolean canNextGame(GameResult result) {
        return result != GameResult.WIN && result != GameResult.LOSE;
    }

    private static boolean isFirstGame(GameResult result) {
        return result == GameResult.NONE;
    }

    private static void dispRequestInputFormatRPS() {

        println(MESSAGE_FOR_REQUEST_INPUT_FORMAT_RPS);

        for (RPSHand hand : RPSHand.values()) {
            println(hand.getInFoString());
        }

        println(MESSAGE_FOR_BLANK);
    }

    private static void dispHand(RPSHand playerHand, RPSHand comHand) {
        println(MESSAGE_FORMAT_FOR_PLAYER_HAND_RPS, playerHand.name);
        println(MESSAGE_FORMAT_FOR_COMPUTER_HAND_RPS, comHand.name);
    }

    private static GameResult getGameResult(RPSHand playerHand, RPSHand comHand) {

        if(playerHand == comHand){
            return GameResult.DRAW;
        }

        if((playerHand == RPSHand.ROOK && comHand == RPSHand.SCISSORS) ||
                (playerHand == RPSHand.SCISSORS && comHand == RPSHand.PAPER) ||
                (playerHand == RPSHand.PAPER && comHand == RPSHand.ROOK)) {
            
            return GameResult.WIN;
        }

        return GameResult.LOSE;
    }

    

    private static RPSHand getPlayerHand(GameResult result) {

        RPSHand hand = null;
        while(!isSelectedHand(hand)){

            if(isFirstGame(result)){
                dispFirstPhrase();
            }
            else{
                dispContinuePhrase();
            }
            
            String input = scanner.nextLine();
            int inputNumber;
            try{
                inputNumber = Integer.parseInt(input);
            }
            catch (NumberFormatException e) {
                dispErrorFormatRPS();
                continue;
            }

            if(!RPSHand.isMatchFromNumber(inputNumber)) {
                dispErrorFormatRPS();
                continue;
            }

            hand = RPSHand.getHandFormNumber(inputNumber);
        }

        return hand;
    }

    private static boolean isSelectedHand(RPSHand hand) {
        return hand != null;
    }

    private static void dispFirstPhrase() {
        print(MESSAGE_FOR_REQUEST_INPUT_RPS);
    }

    private static void dispContinuePhrase() {
        print(MESSAGE_FOR_REQUEST_INPUT_RPS_CONTINUE);
    }

    private static void dispErrorFormatRPS() {
        println(MESSAGE_FORMAT_FOR_ERROR_FORMAT_RPS, RPSHand.NUMBER_RANGE);
        println(MESSAGE_FOR_BLANK);
    }

    

    private static void dispResult(GameResult result) {

        switch(result) {

            case WIN:
                dispPlayerWin();
            return;

            case LOSE:
                dispPlayerLose();
            return;

            default:
            case DRAW:
                dispPlayerDraw();
            return;
        }
    }
    
    private static void dispPlayerWin() {
        println(MESSAGE_FOR_RPS_RESULT_WIN);
    }

    private static void dispPlayerLose() {
        println(MESSAGE_FOR_RPS_RESULT_LOSE);
    }

    private static void dispPlayerDraw() {
        println(MESSAGE_FOR_RPS_RESULT_DRAW);
        println(MESSAGE_FOR_BLANK);
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

    private static int createRandomNumber(int min, int max) {

        int range = max - min + 1;
        return random.nextInt(range) + min;
    }
}
