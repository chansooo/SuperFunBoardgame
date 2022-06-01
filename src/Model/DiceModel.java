package Model;

public class DiceModel {
    int DiceNum;

    public DiceModel(){

    }

    public int rollDice(){
        this.DiceNum = (int)(Math.random()*6);
        return this.DiceNum;
    }
}
