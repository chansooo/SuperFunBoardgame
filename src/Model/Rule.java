package Model;

import java.io.IOException;

;


public class Rule {
    MapModel map;
    PlayerModel[] player;
    DiceModel diceModel;
    Position startPosition;

    int playerNum;
    int mapNum;

    public Rule(int playerNum, int mapNum) throws IOException {
        player = new PlayerModel[playerNum];
        this.playerNum = playerNum;
        this.mapNum = mapNum;
        this.setMap(mapNum);
        startPosition = this.map.getStartPos();
        setPlayer(playerNum);
    }

    private void setMap(int mapNum) throws IOException {
        this.map = new MapModel(mapNum);
    }

    private void setPlayer(int playerNum){
        for(int i=0;i<playerNum;i++){
            player[i] = new PlayerModel(this.startPosition, this.map.getMap());
        }
    }

//    private void runGame(){
//        int turn = 0;
//        while(true){
//            turn = turn % this.playerNum;
//            if(player[turn].moveValidateCheck()){
//                player[turn].
//            }
//
//            if()
//        }
//    }


}
