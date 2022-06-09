package View;

import Model.MapCellModel;

import javax.crypto.spec.PSource;
import java.util.Scanner;

public class CLIView {
    Scanner sc;
    public CLIView(){
        sc = new Scanner(System.in);
    }

    public int getPlayerCount(){
        System.out.println("player 인원 입력 ( 2 ~ 4): ");
        return sc.nextInt();
    }

    public int getMapNum(){
        System.out.println("Map 선택 (1 ~ 2) :");
        System.out.println("(다른 맵을 원하면 숫자.map으로 이름을 변경하고 숫자를 입력하세요.)");
        return (sc.nextInt()-1);
    }

    public void showMap(MapCellModel[][] curMap, int yScale, int xScale){
        System.out.println("현재 Map: ");
        for(int i=0;i< yScale;i++){
            for(int j =0; j<xScale;j++){
                System.out.print(curMap[i][j].cellState);
            }
            System.out.println();
        }
    }


    public int choiceBehavior(){
        System.out.println("숫자를 입력하세요(1: stay, 2: move) :");
        return sc.nextInt();
    }

    public void showStay(){
        System.out.println("stay 선택했습니다.");
        System.out.println("턴을 넘기고 다리 카드를 하나 반납합니다.");
    }

    public String receiveCommand(){
        System.out.println("Command를 입력하세요(R, L, U, D): ");
        return sc.next();
    }

    public void showEndGame(int winner){
        winner++;
        System.out.println("승자: player" + winner);
        System.out.println("게임이 끝났습니다.");

    }

    public void showPlayerInfo(int playerNum, int score, int bridgeCard){
        System.out.println("Player" + (playerNum+1));
        System.out.println("Score: "+ score);
        System.out.println("Bridge card: "+ bridgeCard);
        System.out.println("");
    }

    public void showCurPlayer(int curPlayerNum){
        System.out.println("Current Player: " + (curPlayerNum+1));
    }

    public void showPlayerMoveInfo(int diceNum, int moveCount){
        System.out.println("주사위 숫자: " +diceNum);
        System.out.println("입력 가능한 command 개수: " + moveCount);
    }
}
