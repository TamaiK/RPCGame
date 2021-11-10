import java.util.Random;
import java.util.Scanner;

public class App {

    private static Random random;
    private static Scanner scanner;

    private static GameResult rpsResult;
    private static GameResult ltwResult;

    enum RPSHand {
        ROCK(1, "グー"), 
        SCISSORS(2, "チョキ"), 
        PAPER(3, "パー");

        public final int number;
        public final String name;

        public static final String NUMBER_RANGE = "1～3の数字";

        private RPSHand(final int setNum, final String setName) {
            number = setNum;
            name = setName;
        }

        public String getInFoString() {
            return String.format("%s：%s", number, name);
        }

        public static RPSHand first() {
            return ROCK;
        }

        public static RPSHand last() {
            return PAPER;
        }

        public static RPSHand getHandFromNumber(int fromNum) {

            for (RPSHand hand : RPSHand.values()) {

                if (hand.number == fromNum) {

                    return hand;
                }
            }

            throw new IllegalArgumentException();
        }

        public static boolean isMatchFromNumber(int fromNum) {

            for (RPSHand hand : RPSHand.values()) {

                if (hand.number == fromNum) {

                    return true;
                }
            }

            return false;
        }
    }

    enum FacingHand {
        UP(1, "上"), 
        DOWN(2, "下"), 
        LEFT(3, "左"), 
        RIGHT(4, "右");

        public final int number;
        public final String name;

        public static final String NUMBER_RANGE = "1～4の数字";

        private FacingHand(final int setNum, final String setName) {
            number = setNum;
            name = setName;
        }

        public String getInFoString() {
            return String.format("%s：%s", number, name);
        }

        public static FacingHand first() {
            return UP;
        }

        public static FacingHand last() {
            return RIGHT;
        }

        public static FacingHand getHandFromNumber(int fromNum) {

            for (FacingHand hand : FacingHand.values()) {

                if (hand.number == fromNum) {

                    return hand;
                }
            }

            throw new IllegalArgumentException();
        }

        public static boolean isMatchFromNumber(int fromNum) {

            for (FacingHand hand : FacingHand.values()) {

                if (hand.number == fromNum) {

                    return true;
                }
            }

            return false;
        }
    }

    enum OffenseOrDefense {
        OFFENSE, DEFENSE,
    }

    enum GameResult {
        NONE, WIN, LOSE, DRAW,
    }

    private static final int OVERALL_WIN_COUNT = 3;

    private static final String MESSAGE_FOR_BLANK = "";
    private static final String MESSAGE_FOR_EXPLANATION_OUTLINE = "あっち向いてホイ勝負";
    private static final String MESSAGE_FORMAT_FOR_EXPLANATION_OVERALL_WIN = "%d勝先取だよ！";

    private static final String MESSAGE_FOR_EXPLANATION_OUTLINE_RPS = "じゃんけんで攻守を決めるよ";
    private static final String MESSAGE_FOR_REQUEST_INPUT_FORMAT_RPS = "グーチョキパーを数字で入力してね";
    private static final String MESSAGE_FORMAT_FOR_ERROR_FORMAT = "注意：出す手は%sで入れてね";

    private static final String MESSAGE_FOR_REQUEST_INPUT_RPS = "最初はぐー、じゃんけん：";
    private static final String MESSAGE_FOR_REQUEST_INPUT_RPS_CONTINUE = "あいこで：";

    private static final String MESSAGE_FORMAT_FOR_PLAYER_HAND = "自分：%s";
    private static final String MESSAGE_FORMAT_FOR_COMPUTER_HAND = "相手：%s";

    private static final String MESSAGE_FOR_RPS_RESULT_WIN = "じゃんけんに勝った！";
    private static final String MESSAGE_FOR_RPS_RESULT_LOSE = "じゃんけんに負けた……";
    private static final String MESSAGE_FOR_RPS_RESULT_DRAW = "あいこだよ！";

    private static final String MESSAGE_FORMAT_FOR_PLAYER_WIN_COUNT = "自分：%d勝";
    private static final String MESSAGE_FORMAT_FOR_COMPUTER_WIN_COUNT = "相手：%d勝";

    private static final String MESSAGE_FORMAT_FOR_OVERALL_WIN = "%d勝したのであなたの勝ち！";
    private static final String MESSAGE_FORMAT_FOR_OVERALL_LOSE = "%d勝されたのであなたの負け……";

    private static final String MESSAGE_FOR_EXPLANATION_OUTLINE_LTW_OFFENSE = "相手の向く方向を当てよう！";
    private static final String MESSAGE_FOR_EXPLANATION_OUTLINE_LTW_DEFENSE = "相手の選ぶ方向から避けよう！";
    private static final String MESSAGE_FOR_REQUEST_INPUT_FORMAT_LTW = "向きは数字で入力してね";
    private static final String MESSAGE_FOR_REQUEST_INPUT_LTW = "あっちむいて：";
    private static final String MESSAGE_FOR_LTW_SHOUT = "ホイ！";

    private static final String MESSAGE_FOR_LTW_OFFENSE_WIN = "当てた！";
    private static final String MESSAGE_FOR_LTW_OFFENSE_LOSE = "外した！";
    private static final String MESSAGE_FOR_LTW_DEFENSE_WIN = "避けた！";
    private static final String MESSAGE_FOR_LTW_DEFENSE_LOSE = "当たった！";
    private static final String MESSAGE_FOR_LTW_RESULT_WIN = "あなたの勝ち！";
    private static final String MESSAGE_FOR_LTW_RESULT_LOSE = "あなたの負け……";

    static {
        init();
    }

    public static void main(String[] args) {

        dispExplanation();
        dispExplanationOverallWin();

        int winCount = 0;
        int loseCount = 0;
        while (canNextGame(winCount, loseCount)) {

            playRPSGame();
            GameResult resultRPS = getGameResultRPS();

            playLTWGame(resultRPS);
            GameResult result = getGameResultLTW();

            if (result == GameResult.WIN) {
                winCount++;
            } else {
                loseCount++;
            }

            dispWinsCount(winCount, loseCount);
        }

        GameResult ovreAllResult = getOvreallResult(winCount, loseCount);
        dispOvreallResult(ovreAllResult);

        fin();
    }

    private static void init() {

        random = new Random();
        scanner = new Scanner(System.in);

        rpsResult = GameResult.NONE;
        ltwResult = GameResult.NONE;
    }

    private static void fin() {
        scanner.close();
    }

    private static void dispExplanation() {
        println(MESSAGE_FOR_EXPLANATION_OUTLINE);
    }

    private static void dispExplanationOverallWin() {
        println(MESSAGE_FORMAT_FOR_EXPLANATION_OVERALL_WIN, OVERALL_WIN_COUNT);
        println(MESSAGE_FOR_BLANK);
    }

    private static boolean canNextGame(int winCount, int loseCount) {
        return winCount < OVERALL_WIN_COUNT && loseCount < OVERALL_WIN_COUNT;
    }

    private static GameResult getGameResultRPS() {
        return rpsResult;
    }

    private static GameResult getGameResultLTW() {
        return ltwResult;
    }

    private static void dispWinsCount(int winCount, int loseCount) {
        println(MESSAGE_FORMAT_FOR_PLAYER_WIN_COUNT, winCount);
        println(MESSAGE_FORMAT_FOR_COMPUTER_WIN_COUNT, loseCount);
        println(MESSAGE_FOR_BLANK);
    }

    private static GameResult getOvreallResult(int winCount, int loseCount) {

        if (winCount == OVERALL_WIN_COUNT) {
            return GameResult.WIN;
        }

        return GameResult.LOSE;
    }

    private static void dispOvreallResult(GameResult ovreAllResult) {

        switch (ovreAllResult) {

        case WIN:
            dispPlayerWin();
            return;

        default:
        case LOSE:
            dispPlayerLose();
            return;
        }
    }

    private static void dispPlayerWin() {
        println(MESSAGE_FORMAT_FOR_OVERALL_WIN, OVERALL_WIN_COUNT);
    }

    private static void dispPlayerLose() {
        println(MESSAGE_FORMAT_FOR_OVERALL_LOSE, OVERALL_WIN_COUNT);
    }

    //

    private static void playRPSGame() {

        rpsResult = GameResult.NONE;
        while (canNextGameRPS(rpsResult)) {

            if (isFirstGame(rpsResult)) {
                dispExplanationRPS();
                dispRequestInputFormatRPS();
            }

            RPSHand comHand = getComputerHandRPS();
            RPSHand playerHand = getPlayerHandRPS(rpsResult);
            dispHandRPS(playerHand, comHand);

            rpsResult = getGameResultRPS(playerHand, comHand);
            dispResultRPS(rpsResult);
        }
    }

    private static boolean canNextGameRPS(GameResult result) {
        return result != GameResult.WIN && result != GameResult.LOSE;
    }

    private static boolean isFirstGame(GameResult result) {
        return result == GameResult.NONE;
    }

    private static void dispExplanationRPS() {
        println(MESSAGE_FOR_EXPLANATION_OUTLINE_RPS);
    }

    private static void dispRequestInputFormatRPS() {

        println(MESSAGE_FOR_REQUEST_INPUT_FORMAT_RPS);

        for (RPSHand hand : RPSHand.values()) {
            println(hand.getInFoString());
        }

        println(MESSAGE_FOR_BLANK);
    }

    private static void dispHandRPS(RPSHand playerHand, RPSHand comHand) {
        println(MESSAGE_FORMAT_FOR_PLAYER_HAND, playerHand.name);
        println(MESSAGE_FORMAT_FOR_COMPUTER_HAND, comHand.name);
    }

    private static GameResult getGameResultRPS(RPSHand playerHand, RPSHand comHand) {

        if (playerHand == comHand) {
            return GameResult.DRAW;
        }

        if ((playerHand == RPSHand.ROCK && comHand == RPSHand.SCISSORS)
                || (playerHand == RPSHand.SCISSORS && comHand == RPSHand.PAPER)
                || (playerHand == RPSHand.PAPER && comHand == RPSHand.ROCK)) {

            return GameResult.WIN;
        }

        return GameResult.LOSE;
    }

    private static RPSHand getComputerHandRPS() {
        int comHandNumber = createRandomNumber(RPSHand.first().number, RPSHand.last().number);
        return RPSHand.getHandFromNumber(comHandNumber);
    }

    private static RPSHand getPlayerHandRPS(GameResult result) {

        RPSHand hand = null;
        while (!isSelectedHand(hand)) {

            if (isFirstGame(result)) {
                dispFirstPhrase();
            } else {
                dispContinuePhrase();
            }

            String input = scanner.nextLine();
            int inputNumber;
            try {
                inputNumber = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                dispErrorFormatRPS();
                continue;
            }

            if (!RPSHand.isMatchFromNumber(inputNumber)) {
                dispErrorFormatRPS();
                continue;
            }

            hand = RPSHand.getHandFromNumber(inputNumber);
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
        println(MESSAGE_FORMAT_FOR_ERROR_FORMAT, RPSHand.NUMBER_RANGE);
        println(MESSAGE_FOR_BLANK);
    }

    private static void dispResultRPS(GameResult result) {

        switch (result) {

        case WIN:
            dispPlayerWinRPS();
            return;

        case LOSE:
            dispPlayerLoseRPS();
            return;

        default:
        case DRAW:
            dispPlayerDrawRPS();
            return;
        }
    }

    private static void dispPlayerWinRPS() {
        println(MESSAGE_FOR_RPS_RESULT_WIN);
        println(MESSAGE_FOR_BLANK);
    }

    private static void dispPlayerLoseRPS() {
        println(MESSAGE_FOR_RPS_RESULT_LOSE);
        println(MESSAGE_FOR_BLANK);
    }

    private static void dispPlayerDrawRPS() {
        println(MESSAGE_FOR_RPS_RESULT_DRAW);
        println(MESSAGE_FOR_BLANK);
    }

    //

    private static void playLTWGame(GameResult resultRPS) {

        ltwResult = GameResult.NONE;

        OffenseOrDefense offenseOrDefense = OffenseOrDefense.DEFENSE;
        if (resultRPS == GameResult.WIN) {
            offenseOrDefense = OffenseOrDefense.OFFENSE;
        }

        dispExplanationLTW(offenseOrDefense);
        dispRequestInputFormatLTW();

        FacingHand comHand = getComputerHandLTW();
        FacingHand playerHand = getPlayerHandLTW();
        dispHandLTW(playerHand, comHand);

        ltwResult = getGameResultLTW(playerHand, comHand, offenseOrDefense);
        dispResultLTW(ltwResult, offenseOrDefense);
    }

    private static void dispExplanationLTW(OffenseOrDefense offenseOrDefense) {

        if (offenseOrDefense == OffenseOrDefense.OFFENSE) {
            dispExplanationLTWOffense();
            return;
        }

        dispExplanationLTWDefense();
    }

    private static void dispExplanationLTWOffense() {
        println(MESSAGE_FOR_EXPLANATION_OUTLINE_LTW_OFFENSE);
    }

    private static void dispExplanationLTWDefense() {
        println(MESSAGE_FOR_EXPLANATION_OUTLINE_LTW_DEFENSE);
    }

    private static void dispRequestInputFormatLTW() {

        println(MESSAGE_FOR_REQUEST_INPUT_FORMAT_LTW);

        for (FacingHand hand : FacingHand.values()) {
            println(hand.getInFoString());
        }

        println(MESSAGE_FOR_BLANK);
    }

    private static FacingHand getComputerHandLTW() {
        int comHandNumber = createRandomNumber(FacingHand.first().number, FacingHand.last().number);
        return FacingHand.getHandFromNumber(comHandNumber);
    }

    private static FacingHand getPlayerHandLTW() {

        FacingHand hand = null;
        while (!isSelectedHand(hand)) {

            dispPhraseLTW();

            String input = scanner.nextLine();
            int inputNumber;
            try {
                inputNumber = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                dispErrorFormatLTW();
                continue;
            }

            if (!FacingHand.isMatchFromNumber(inputNumber)) {
                dispErrorFormatLTW();
                continue;
            }

            hand = FacingHand.getHandFromNumber(inputNumber);
        }

        return hand;
    }

    private static boolean isSelectedHand(FacingHand hand) {
        return hand != null;
    }

    private static void dispPhraseLTW() {
        print(MESSAGE_FOR_REQUEST_INPUT_LTW);
    }

    private static void dispErrorFormatLTW() {
        println(MESSAGE_FORMAT_FOR_ERROR_FORMAT, FacingHand.NUMBER_RANGE);
        println(MESSAGE_FOR_BLANK);
    }

    private static void dispHandLTW(FacingHand playerHand, FacingHand comHand) {
        println(MESSAGE_FOR_LTW_SHOUT);
        println(MESSAGE_FORMAT_FOR_PLAYER_HAND, playerHand.name);
        println(MESSAGE_FORMAT_FOR_COMPUTER_HAND, comHand.name);
        println(MESSAGE_FOR_BLANK);
    }

    private static GameResult getGameResultLTW(FacingHand playerHand, FacingHand comHand,
            OffenseOrDefense offenseOrDefense) {

        if (playerHand == comHand) {

            if (offenseOrDefense == OffenseOrDefense.OFFENSE) {
                return GameResult.WIN;
            }

            return GameResult.LOSE;
        }

        if (offenseOrDefense == OffenseOrDefense.OFFENSE) {
            return GameResult.LOSE;
        }

        return GameResult.WIN;
    }

    private static void dispResultLTW(GameResult result, OffenseOrDefense offenseOrDefense) {

        switch (result) {

        case WIN:
            if (offenseOrDefense == OffenseOrDefense.OFFENSE) {
                dispPlayerWinLTWOffense();
            } else {
                dispPlayerWinLTWDefense();
            }

            dispPlayerWinLTW();
            return;

        default:
        case LOSE:
            if (offenseOrDefense == OffenseOrDefense.OFFENSE) {
                dispPlayerLoseLTWOffense();
            } else {
                dispPlayerLoseLTWDefense();
            }

            dispPlayerLoseLTW();
            return;
        }
    }

    private static void dispPlayerWinLTWOffense() {
        println(MESSAGE_FOR_LTW_OFFENSE_WIN);
    }

    private static void dispPlayerWinLTWDefense() {
        println(MESSAGE_FOR_LTW_DEFENSE_WIN);
    }

    private static void dispPlayerWinLTW() {
        println(MESSAGE_FOR_LTW_RESULT_WIN);
        println(MESSAGE_FOR_BLANK);
    }

    private static void dispPlayerLoseLTWOffense() {
        println(MESSAGE_FOR_LTW_OFFENSE_LOSE);
    }

    private static void dispPlayerLoseLTWDefense() {
        println(MESSAGE_FOR_LTW_DEFENSE_LOSE);
    }

    private static void dispPlayerLoseLTW() {
        println(MESSAGE_FOR_LTW_RESULT_LOSE);
        println(MESSAGE_FOR_BLANK);
    }

    //

    private static void print(String str) {
        System.out.print(str);
    }

    private static void println(String str) {
        System.out.println(str);
    }

    private static void println(String str, Object... args) {
        System.out.println(String.format(str, args));
    }

    private static int createRandomNumber(int min, int max) {

        int range = max - min + 1;
        return random.nextInt(range) + min;
    }
}
