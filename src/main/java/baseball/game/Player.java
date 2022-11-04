package baseball.game;

import baseball.message.SystemMessage;
import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;

public class Player {

    public List<Integer> inputPlayerNumber() {
        System.out.print(SystemMessage.ENTER_NUMBER);
        String playerNumber = Console.readLine();
        return stringToList(playerNumber);
    }

    public String inputContinueOrStop() {
        System.out.println(SystemMessage.START_NEW_GAME_OR_END);
        return Console.readLine();
    }

    private List<Integer> stringToList(String playerInput) {
        List<Integer> playerNumber = new ArrayList<>();
        for (int i = 0; i < playerInput.length(); i++) {
            char current = playerInput.charAt(i);
            playerNumber.add(Character.getNumericValue(current));
        }
        return playerNumber;
    }
}
