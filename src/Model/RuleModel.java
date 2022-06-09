package Model;

import java.io.IOException;




public class RuleModel {
    public MapModel mapModel;
    public PlayerModel[] players;
    public DiceModel diceModel;
    PositionModel startPositionModel;
    MapCellModel[][] map;

    private boolean playerEnd[];
    private int playerCount;
    private int mapNum;
    private int endCount = 0;

    public RuleModel(int playerCount, int mapNum) throws IOException {
        diceModel = new DiceModel();
        players = new PlayerModel[playerCount];
        this.playerCount = playerCount;
        this.mapNum = mapNum;
        this.mapModel = new MapModel(mapNum);
        this.map = this.mapModel.getMap();
        startPositionModel = this.mapModel.getStartPos();
        setPlayer(playerCount);
        playerEnd = new boolean[playerCount];
    }


    private void setPlayer(int playerNum){
        for(int i=0;i<playerNum;i++){
            players[i] = new PlayerModel(this.startPositionModel);
        }
    }

    public void integratedMove(int curPlayer, String command, int diceNum) throws Exception {
        PositionModel tempPosition = players[curPlayer].movePlayer(command, diceNum,this.map, players[curPlayer].isEnd);
        checkItem(tempPosition, curPlayer);
        addEndPlayerScore(curPlayer);
    }

    public void checkItem(PositionModel positionModel, int playerNum){
        if(this.map[positionModel.dy][positionModel.dx].cellState == 'P'){
            players[playerNum].setScore(1);
        } else if(this.map[positionModel.dy][positionModel.dx].cellState == 'H'){
            players[playerNum].setScore(2);
        } else if(this.map[positionModel.dy][positionModel.dx].cellState == 'S'){
            players[playerNum].setScore(3);
        }
    }

    public boolean checkContinueGame(){
        for(int i=0; i<this.playerCount;i++){
            if(!playerEnd[i]){
                return true;
            }
        }
        for(int i=0; i<playerCount;i++){
            int temp = i+1;
            System.out.println("player" + temp+"의 점수: " + players[i].getScore());
        }
        return false;
    }


    //게임 더 진행할지
    public int checkWinner(){
        for(int i=0; i<this.playerCount;i++){
            if(!playerEnd[i]){
                int tempScore =0;
                int tempPlayer =0;
                for(int j=0; j<playerCount; j++){
                    if(tempScore < players[i].getScore()){
                        tempScore = players[i].getScore();
                        tempPlayer = i+1;
                    }
                }
                return tempPlayer;
            }
        }
        return 0;
    }

    //끝난 사람 점수 올려주기
    public void addEndPlayerScore(int playerNum){
        if(players[playerNum].isEnd && !playerEnd[playerNum]){
            if(endCount == 0){
                players[playerNum].setScore(7);
            } else if(endCount == 1){
                players[playerNum].setScore(3);
            } else if(endCount == 2){
                players[playerNum].setScore(1);
            }
            endCount++;
            playerEnd[playerNum] = true;
        }
    }

    public MapCellModel[][] getMap(){
        MapCellModel[][] returnMap = new MapCellModel[this.mapModel.yCut][this.mapModel.xCut];
        for(int i=0;i<mapModel.yCut; i++){
            for(int j=0; j<mapModel.xCut; j++){
                returnMap[i][j] = new MapCellModel(this.map[i][j]);
            }
        }
        return returnMap;
    }



    public PositionModel getPlayerPosition(int playerNumber){
        return players[playerNumber].getPosition();
    }

}
