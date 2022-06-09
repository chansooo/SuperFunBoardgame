package Model;

import java.io.*;



public class MapModel {
    private File f;
    private int mapNum;
    private String[] mapList = {"default.map", "another.map"};
    private MapCellModel[][] map;
    private PositionModel startPos = new PositionModel(0, 0);

    private MapCellModel[][] realMap;
    public int xCut = 100;
    public int yCut = 100;

    public MapModel(int mapNum) throws IOException {
        map = new MapCellModel[100][100];

        this.mapNum = mapNum;

        if(mapNum >= 2 ){
            //jar 파일로 실행할 때
            f = new File("./data/"+(mapNum+1)+".map");
            //코드를 바로 실행할 때
            //f = new File("./src/data/"+(mapNum+1)+".map");
        } else{
            //jar 파일로 실행할 때
            f = new File("./data/"+mapList[mapNum]);
            //코드를 바로 실행할 때
            //f = new File("./src/data/"+mapList[mapNum]);
        }
        this.calculateMap();
        this.readMap();
        this.stabilizaMap();
        this.makeBridge();
    }

//    public MapModel(String mapNum) throws IOException{
//        map = new MapCellModel[100][100];
//
//        this.mapNum = mapNum;
//        f = new File("../data/"+mapList[mapNum]);
//        this.calculateMap();
//        this.readMap();
//        this.stabilizaMap();
//        this.makeBridge();
//    }

    private void calculateMap() throws IOException {
        BufferedReader br1 = new BufferedReader(new FileReader(f));
        //MARK: data로 경로 확인하고 startpoint 맞춰주기
        String curMapCell;
        PositionModel minPositionModel = new PositionModel(0, 0);
        PositionModel tempPositionModel = new PositionModel(0,0);
        for(int step = 0; (curMapCell = br1.readLine())!=null ; step++){
            char temp;
            if (curMapCell.charAt(0) == 'E'){
                break;
            } else{
                if(step == 0){ //시작점 따로 처리
                    temp = curMapCell.charAt(2);
                }else{
                    temp = curMapCell.charAt(4);
                }
                if(temp == 'U'){
                    tempPositionModel.dy -= 1;
                } else if(temp == 'D'){
                    tempPositionModel.dy += 1;
                } else if (temp == 'L'){
                    tempPositionModel.dx -= 1;
                } else if (temp == 'R'){
                    tempPositionModel.dx += 1;
                }else{
                    System.out.println("ERROR: invalid map data");
                }
            }
            //역대 가장 작은 x,y값을 저장
            minPositionModel.dx = Math.min(tempPositionModel.dx, minPositionModel.dx);
            minPositionModel.dy = Math.min(tempPositionModel.dy, minPositionModel.dy);
        }
        br1.close();
        //최소값이 음수가 나오면 그만큼 더해주기
        if(minPositionModel.dx < 0) {
            this.startPos.dx = this.startPos.dx - minPositionModel.dx ;
        }
        if(minPositionModel.dy < 0){
            this.startPos.dy = this.startPos.dy - minPositionModel.dy ;
        }
    }

    private void readMap() throws IOException {
        for (int i=0;i<100;i++){
            for(int j =0; j<100;j++){
                if(map[i][j] == null){
                    map[i][j] = new MapCellModel();
                }
                map[i][j].cellState = 'X';
                map[i][j].index = -1;
            }
        }
        BufferedReader br2 = new BufferedReader(new FileReader(f));
        PositionModel curMapPositionModel = new PositionModel(this.startPos.dy,this.startPos.dx);
        String curMapString;
        for(int step = 0; (curMapString = br2.readLine())!=null ; step++){
            if (curMapString.charAt(0) == 'E'){
                this.map[curMapPositionModel.dy][curMapPositionModel.dx].cellState = curMapString.charAt(0);
                break;
            }
            if(step == 0){ //시작점 따로 처리
                this.map[curMapPositionModel.dy][curMapPositionModel.dx].cellState = 'T'; //take up 의미로 T를 시작점으로. S가 start, saw 두개기 때문에..
                this.map[curMapPositionModel.dy][curMapPositionModel.dx].nextDirection = curMapString.charAt(2);
                this.map[curMapPositionModel.dy][curMapPositionModel.dx].preDirection = 'M';
                this.map[curMapPositionModel.dy][curMapPositionModel.dx].index = step;
            }else{
                this.map[curMapPositionModel.dy][curMapPositionModel.dx].cellState = curMapString.charAt(0);
                this.map[curMapPositionModel.dy][curMapPositionModel.dx].preDirection = curMapString.charAt(2);
                this.map[curMapPositionModel.dy][curMapPositionModel.dx].nextDirection = curMapString.charAt(4);
                this.map[curMapPositionModel.dy][curMapPositionModel.dx].index = step;
            }
            char next = this.map[curMapPositionModel.dy][curMapPositionModel.dx].nextDirection;
            if(next == 'U'){
                curMapPositionModel.dy--;
            } else if(next == 'D'){
                curMapPositionModel.dy++;
            } else if (next == 'L'){
                curMapPositionModel.dx--;
            } else if (next == 'R'){
                curMapPositionModel.dx++;
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

        for(int i=0; i < 100 ; i++){
            for(int j=0; j<100; j++){
                if(map[i][j].cellState == 'X') yCount++;
            }
            if (yCount == 100){
                yCut = i;
                break;
            }
            yCount = 0;
        }
        for(int i=0; i < 100 ; i++){
            for(int j=0; j<100; j++){
                if(map[j][i].cellState == 'X') xCount++;
            }
            if (xCount == 100){
                xCut = i;
                break;
            }
            xCount = 0;
        }
        realMap = new MapCellModel[yCut][xCut];
        //map 복사
        for (int i =0; i< yCut; i++){
            for ( int j =0; j < xCut; j++){
                realMap[i][j] = map[i][j];
            }
        }
    }

    private void makeBridge(){
        for (int i =0; i< yCut; i++){
            for ( int j =0; j < xCut; j++){
                if(realMap[i][j].cellState == 'B'){
                    int k =1;
                    while(true){
                        if(realMap[i][j+k].cellState == 'b'){
                            //realmap[i][j+1] ~ [i][j+k-1]까지 '-'로 바꾸기
                            int p = 1;
                            while(p != k){
                                realMap[i][j+p].cellState = '-';
                                p++;
                            }
                            break;
                        }
                        k++;
                    }
                }
            }
        }
    }

    public int getxCut() {
        return xCut;
    }

    public int getyCut() {
        return yCut;
    }

    public MapCellModel[][] getMap(){
        return realMap;
    }

    public PositionModel getStartPos(){
        return startPos;
    }


}
