import Controller.CLIController;
import Controller.GUIController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("게임 뷰를 선택하세요.");
        System.out.println("GUI: 1, CLI: 2 : ");

        Scanner sc = new Scanner(System.in);
        int GameType = sc.nextInt();

        if(GameType == 1){
            new GUIController();
        }else if(GameType == 2){
            new CLIController();
        }
    }
}
