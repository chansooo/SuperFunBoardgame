package Model;

public class DiceModel {
    int DiceNum;

    public DiceModel(){
        this.rollDice();
    }

    public int rollDice(){
        while(true){
            this.DiceNum = (int)(Math.random()*6);
            if(this.DiceNum != 0){
                break;
            }
        }

        return this.DiceNum;
    }
}
