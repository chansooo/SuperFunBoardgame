package Model;

public class Rule {
    MapModel map;
    PlayerModel player[];
    DiceModel diceModel;

    int playerNum;

    public Rule(int playerNum, ){
        map = new MapModel();
        player =
    }

    private void setPlayer(int playerNum){
        for(int i=0;i<playerNum;i++){
            PlayerModel curPlayer = new PlayerModel();
            player[i] = curPlayer;

        }
    }

    public void setTurn(){
        //TODO: 내 턴 왔을 때 진행되는 플로우 여기에
        this.checkEnd();
        this.rollDice();
        this.movePlayer();
    }
}
