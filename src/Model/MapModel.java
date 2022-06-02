package Model;

import java.io.*;

class MapCell{
    char cellState;
    char preDirection;
    char nextDirection;
}

public class MapModel {
    private File f;
    private int mapNum;
    private final String[] mapList = {"default.map", "another.map"};
    //TODO: map 크기 제한이 있는지..
    private MapCell[][] map = new MapCell[100][100];
    private Position startPos = new Position(0, 0);

    private MapCell[][] realMap;

    public MapModel(int mapNum) throws IOException {
        this.mapNum = mapNum;
        f = new File("./src/data/"+mapList[mapNum]);
        this.calculateMap();
        this.readMap();
        this.stabilizaMap();
    }

    private void calculateMap() throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(f));
        //MARK: data로 경로 확인하고 startpoint 맞춰주기
        String curMapCell;
        Position calculateMin = new Position(0, 0);
        for(int step = 0; (curMapCell = br1.readLine())!=null ; step++){
            char temp;
            if (curMapCell.charAt(0) == 'E'){
                System.out.println("맵의 시작점이 종점입니다.");
            } else{
                if(step == 0){ //시작점 따로 처리
                    temp = curMapCell.charAt(2);
                }else{
                    temp = curMapCell.charAt(4);
                }
                if(temp == 'U'){
                    calculateMin.dy += 1;
                } else if(temp == 'D'){
                    calculateMin.dy -= 1;
                } else if (temp == 'L'){
                    calculateMin.dx -= 1;
                } else if (temp == 'R'){
                    calculateMin.dx += 1;
                }else{
                    System.out.println("ERROR: invalid map data");
                }
            }
        }
        br1.close();
        if(calculateMin.dx < 0) {
            this.startPos.dx = this.startPos.dx - calculateMin.dx + 1;
        }
        if(calculateMin.dy < 0){
            this.startPos.dy = this.startPos.dy - calculateMin.dy + 1;
        }
    }

    private void readMap() throws IOException {
        for (int i=0;i<100;i++){
            for(int j =0; j<100;j++){
                map[i][j].cellState = 'X';
            }
        }
        BufferedReader br2 = new BufferedReader(new FileReader(f));
        Position curMapPosition = new Position(this.startPos.dy,this.startPos.dx);
        String curMapString;
        for(int step = 1; (curMapString = br2.readLine())!=null ; step++){

            if(step == 1){ //시작점 따로 처리
                this.map[curMapPosition.dy][curMapPosition.dx].cellState = 'T'; //take up 의미로 T를 시작점으로. S가 start, saw 두개기 때문에..
                this.map[curMapPosition.dy][curMapPosition.dx].nextDirection = curMapString.charAt(2);
            }else{
                this.map[curMapPosition.dy][curMapPosition.dx].cellState = curMapString.charAt(0);
                this.map[curMapPosition.dy][curMapPosition.dx].preDirection = curMapString.charAt(2);
                this.map[curMapPosition.dy][curMapPosition.dx].nextDirection = curMapString.charAt(4);
            }
            char next = this.map[curMapPosition.dy][curMapPosition.dx].nextDirection
            if(next == 'U'){
                curMapPosition.dy++;
            } else if(next == 'D'){
                curMapPosition.dy--;
            } else if (next == 'L'){
                curMapPosition.dx--;
            } else if (next == 'R'){
                curMapPosition.dx++;
            }else {
                System.out.println("ERROR: invalid map data");
            }
        }
        br2.close();
    }

    // 저장된 map에서 자를 부분 자르기
    //0부터 100까지 진행하면서 모든 column이나 row가 X로 돼있으면 거기 전까지 다 잘라버리자
    private void stabilizaMap(){

        int xCount =0;
        int yCount =0;
        int xCut=100;
        int yCut=100;

        for(int i=0; i < 100 ; i++){
            for(int j=0; j<100; j++){
                if(map[i][j].cellState == 'X') xCount++;
            }
            if (xCount == 100){
                xCut = i;
                break;
            }
        }
        for(int i=0; i < 100 ; i++){
            for(int j=0; j<100; j++){
                if(map[j][i].cellState == 'X') yCount++;
            }
            if (yCount == 100){
                yCut = i;
                break;
            }
        }
        yCut--;
        xCut--;
        realMap = new MapCell[yCut][xCut];
        //map 복사
        for (int i =0; i< yCut; i++){
            for ( int j =0; j < xCut; j++){
                realMap[j][i] = map[j][i];
            }
        }
    }

    public MapCell[][] getMap(){
        return realMap;
    }



}
