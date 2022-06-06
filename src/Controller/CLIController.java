package Controller;

import Model.MapCellModel;
import Model.PositionModel;
import Model.RuleModel;
import View.CLIView;

import java.io.IOException;
import java.util.Scanner;

public class CLIController {
    RuleModel rule;
    CLIView cliView;

    //private MapCellModel[][] curMap;

    private int playerCount;
    private int mapNum;



    public CLIController() throws Exception {
        cliView = new CLIView();
        this.playerCount = cliView.getPlayerCount();
        this.mapNum = cliView.getMapNum();
        rule = new RuleModel(this.playerCount, this.mapNum);
        this.runGame();
    }


    //게임 동작 메소드
    private void runGame() throws Exception {
        int turn = 0;
        int curPlayerNum = 0;
        while(true){
            curPlayerNum = (turn % playerCount);
            //TODO: player 지나간 trace가 그대로 남는 것 없애기
            //맵 띄우기
            drawMap();
            //player 정보 띄우기
            drawPlayerInfo();

            //TODO: player2를 올렸는데도 showcurplayer가 player1로 표시되고
            // map에도 1로 표시됨
            //현재 player 띄우기
            cliView.showCurPlayer(curPlayerNum);

            //move or stay 선택
            this.choiceMoveORStay(curPlayerNum);


            //모든 사람 끝났는지 확인
            if(!rule.checkContinueGame()){
                cliView.showEndGame(rule.checkWinner());
                break;
            }
            turn++;
        }
    }

    //맵 그리기
    public void drawMap(){
        MapCellModel[][] curMap = rule.getMap();
        //this.curMap = rule.getMap();
        for(int i=0; i<rule.mapModel.yCut; i++){
            for(int j=0; j<rule.mapModel.xCut; j++){
                if(curMap[i][j].cellState == 'T'){
                    curMap[i][j].cellState = 'S';
                }
                if(curMap[i][j].cellState == 'X'){
                    curMap[i][j].cellState = ' ';
                }
            }
        }
        // player 띄우기
        for(int i=0; i<playerCount; i++){
            PositionModel temp = rule.players[i].getPosition();
            //PositionModel temp = rule.getPlayerPosition(i);

            curMap[temp.dy][temp.dx].cellState = (char) ('1'+i);
            System.out.println("player" + (i+1) + temp.dx );
            System.out.println("player" + (i+1) + temp.dy );
        }
        //cliView.showMap(this.curMap, rule.mapModel.yCut, rule.mapModel.xCut);
        cliView.showMap(curMap, rule.mapModel.yCut, rule.mapModel.xCut);
    }

    public void choiceMoveORStay(int curPlayer) throws Exception {
        try{
            int choice = cliView.choiceBehavior();
            if(choice == 1){ //stay
                this.stay(curPlayer);
            } else if (choice == 2){
                this.move(curPlayer);
            }
        } catch(Exception e){
            //TODO: 이상한 숫자 입력하면 뜨는 오류 처리
//            int choice = cliView.choiceBehavior();
//            if(choice == 1){ //stay
//                this.stay(curPlayer);
//            } else if (choice == 2){
//                this.move(curPlayer);
//            }
        }

    }

    //move stay 중 move. command입력받고 해당하는 것으로 처리
    public void move(int curPlayer) throws Exception {
        int diceNum = 0;
        int moveCount = 0;
        try{
            System.out.println("move에서 curplayer: " + curPlayer);
            //주사위 숫자, move 띄워주기
            diceNum = rule.diceModel.rollDice();
            moveCount = diceNum - rule.players[curPlayer].getBridgePenalty();
            cliView.showPlayerMoveInfo(diceNum, moveCount);
            // Command 받기
            String command = cliView.receiveCommand();
            // 움직이기
            rule.integratedMove(curPlayer, command, diceNum);
        } catch (Exception e){
            System.out.println("command를 정확히 입력하세요.");
            //TODO: 위의 dicenum을 유지하면서 진행해줘야한다.
            cliView.showPlayerMoveInfo(diceNum, moveCount);
            // Command 받기
            String command = cliView.receiveCommand();
            // 움직이기
            rule.integratedMove(curPlayer, command, diceNum);
        }



    }

    //move stay 중 stay
    public void stay(int curPlayer){
        cliView.showStay();
    }



    //player들 정보 띄우기
    public void drawPlayerInfo(){
        for(int i=0; i<playerCount; i++){
            cliView.showPlayerInfo(i, rule.players[i].getScore(), rule.players[i].getBridgePenalty());
        }
    }

}
