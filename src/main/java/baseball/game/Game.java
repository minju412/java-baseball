package baseball.game;

import static baseball.util.BallGenerator.COMPUTER_BALLS_SIZE;
import static baseball.util.InputValidator.CONTINUE_GAME;

import baseball.message.SystemMessage;
import baseball.util.InputValidator;
import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    public static final int STRIKE_COUNT = 0;
    public static final int BALL_COUNT = 1;

    public void startGame() {
        System.out.println(SystemMessage.START_NUMBER_BASEBALL_GAME);
        do {
            playGame();
        } while (Integer.parseInt(askRestartGameOrEnd()) == CONTINUE_GAME);
    }

    public String askRestartGameOrEnd() {
        System.out.println(SystemMessage.RESTART_GAME_OR_END);
        String answer = Console.readLine();
        if (!InputValidator.checkProgressAnswer(answer)) {
            throw new IllegalArgumentException();
        }
        return answer;
    }

    public void playGame() {
        Ball ball = new Ball();
        List<Integer> computerBalls = ball.getComputerBall();
        List<Integer> strikeAndBallCounts = new ArrayList<>(Arrays.asList(0, 0));
        String resultMessage;
        System.out.println(computerBalls.toString()); // TODO: 삭제하기
        do {
            List<Integer> playerBalls = ball.getPlayerBall();
            calcResult(computerBalls, playerBalls, strikeAndBallCounts);
            resultMessage = getResultMessage(strikeAndBallCounts.get(STRIKE_COUNT), strikeAndBallCounts.get(BALL_COUNT));
            System.out.println(resultMessage);
            updateStrikeAndBallCounts(strikeAndBallCounts, 0, 0);
        } while (!resultMessage.equals(SystemMessage.THREE_NUMBERS_RIGHT_GAME_OVER));
    }

    private void calcResult(List<Integer> computerBalls, List<Integer> playerBalls, List<Integer> strikeAndBallCounts) {
        for (int playerBallsIndex = 0; playerBallsIndex < playerBalls.size(); playerBallsIndex++) {
            for (int computerBallsIndex = 0; computerBallsIndex < computerBalls.size(); computerBallsIndex++) {
                calculateStrikeAndBallCounts(playerBalls, computerBalls, strikeAndBallCounts,
                        playerBallsIndex, computerBallsIndex);
            }
        }
    }

    private void calculateStrikeAndBallCounts(List<Integer> playerBalls, List<Integer> computerBalls,
            List<Integer> strikeAndBallCounts, int playerBallsIndex, int computerBallsIndex) {
        if (playerBalls.get(playerBallsIndex).equals(computerBalls.get(computerBallsIndex))) {
            updateStrikeAndBallCounts(strikeAndBallCounts,
                    getCount(playerBallsIndex == computerBallsIndex, strikeAndBallCounts.get(STRIKE_COUNT)),
                    getCount(playerBallsIndex != computerBallsIndex, strikeAndBallCounts.get(BALL_COUNT)));
        }
    }

    private void updateStrikeAndBallCounts(List<Integer> strikeAndBallCounts, int strikeCount, int ballCount) {
        strikeAndBallCounts.set(STRIKE_COUNT, strikeCount);
        strikeAndBallCounts.set(BALL_COUNT, ballCount);
    }

    private int getCount(boolean condition, int count) {
        if (condition) {
            count++;
        }
        return count;
    }

    private String getResultMessage(int strikeCnt, int ballCnt) {
        if (strikeCnt == COMPUTER_BALLS_SIZE) {
            return SystemMessage.THREE_NUMBERS_RIGHT_GAME_OVER;
        }
        if (strikeCnt > 0 && ballCnt > 0) {
            return ballCnt + SystemMessage.BALL + " " + strikeCnt + SystemMessage.STRIKE;
        }
        if (strikeCnt == 0 && ballCnt > 0) {
            return ballCnt + SystemMessage.BALL;
        }
        if (strikeCnt > 0 && ballCnt == 0) {
            return strikeCnt + SystemMessage.STRIKE;
        }
        return SystemMessage.NOTHING;
    }

}
