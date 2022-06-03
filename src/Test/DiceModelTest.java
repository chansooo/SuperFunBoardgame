import Model.DiceModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DiceModelTest {
    @Test
    @DisplayName("Roll Dice 테스트 실행")
    void rollDiceTest(){
        DiceModel a = new DiceModel();
        System.out.println(a.rollDice());
    }
}
