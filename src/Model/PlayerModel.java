package Model;

import java.util.Stack;

public class PlayerModel {
    public boolean isEnd = false;
    private PositionModel positionModel = new PositionModel(0,0);
    private int bridgePenalty =0;
    private int score = 0;
    private int canMoveNum =0;
//    MapCellModel[][] map;
    //TODO: map을 프로퍼티로 두지 말고 파라미터로 받아서 해결.
    //
    public PlayerModel(PositionModel startPositionModel){
        this.positionModel.dy = startPositionModel.dy;
        this.positionModel.dx = startPositionModel.dx;
  //      this.map = map;
        bridgePenalty = 0;
    }

    //움직일 수 있는 상황인지 체크
    private boolean moveValidateCheck(){
        if(this.isEnd){
            return false;
        }
        return true;
    }

    public PositionModel stayPlayer(){
        if(bridgePenalty >0){
            bridgePenalty--;
        }
        return positionModel;
    }

    public PositionModel movePlayer(String moveDirectionString, int curDiceNum,MapCellModel[][] map, boolean hasFinisher) throws Exception {
        if(!this.moveValidateCheck()){
            return positionModel;
        }
        canMoveNum = curDiceNum - bridgePenalty;
        if( canMoveNum < 0){
            canMoveNum = 0;
        }
        PositionModel result;
        if( moveDirectionString.length() != canMoveNum){
            throw new Exception("ERROR: 갈 수 있는 횟수에 맞게 입력하세요.");
        }
        // 한칸한칸 움직일 수 있는지 없는지 확인
        String tempDirectionString = moveDirectionString;
        if (hasFinisher){
           this.movePlayerWithFinisher(tempDirectionString, moveDirectionString, map);
        } else {
            this.movePlayerNoFinisher(tempDirectionString, moveDirectionString, map);
        }

        return positionModel;

    }

    //끝난 player 없을 때
    private void movePlayerNoFinisher(String tempDirectionString, String moveDirectionString, MapCellModel[][] map) throws Exception {
        int count =0;
        Stack s = new Stack();
        //한 칸씩 고려
        while(!tempDirectionString.equals("")){
            //끝났는지 체크
            //움직여야 하는 칸으로 position 변경
            if( tempDirectionString.charAt(0) == map[positionModel.dy][positionModel.dx].preDirection){
                if(map[positionModel.dy][positionModel.dx].preDirection == 'U'){
                    s.push('U');
                    positionModel.dy--;
                } else if(map[positionModel.dy][positionModel.dx].preDirection == 'D'){
                    s.push('D');
                    positionModel.dy++;
                } else if (map[positionModel.dy][positionModel.dx].preDirection == 'L'){
                    s.push('L');
                    positionModel.dx--;
                } else if (map[positionModel.dy][positionModel.dx].preDirection == 'R'){
                    s.push('R');
                    positionModel.dx++;
                }
            }else if(tempDirectionString.charAt(0) == map[positionModel.dy][positionModel.dx].nextDirection){
                if(map[positionModel.dy][positionModel.dx].nextDirection == 'U'){
                    s.push('U');
                    positionModel.dy--;
                } else if(map[positionModel.dy][positionModel.dx].nextDirection == 'D'){
                    s.push('D');
                    positionModel.dy++;
                } else if (map[positionModel.dy][positionModel.dx].nextDirection == 'L'){
                    s.push('L');
                    positionModel.dx--;
                } else if (map[positionModel.dy][positionModel.dx].nextDirection == 'R'){
                    s.push('R');
                    positionModel.dx++;
                }
            } else if(map[positionModel.dy][positionModel.dx].cellState == 'B'){ //Bridge 처리
                for( int i=1;(moveDirectionString.length() - count - i) >= 0 ; i++){
                    if(map[positionModel.dy][positionModel.dx+i].cellState == 'b'){
                       positionModel.dx += i;
                       count += i;
                       count --;
                       bridgePenalty++;
                       break;
                    }
                    if(tempDirectionString.charAt(0+i) == 'R'){
                        continue;
                    } else{
                        while(!s.empty()){
                            char temp = (char) s.pop();
                            if(temp == 'U'){
                                this.positionModel.dy++;
                            } else if(temp == 'D'){
                                this.positionModel.dy--;
                            }else if(temp == 'L'){
                                this.positionModel.dx++;
                            }else if(temp == 'R'){
                                this.positionModel.dx--;
                            }
                        }
                        throw new Exception("경로를 다시 입력하세요");
                    }
                }
            } else{
                while(!s.empty()){
                    char temp = (char) s.pop();
                    if(temp == 'U'){
                        this.positionModel.dy++;
                    } else if(temp == 'D'){
                        this.positionModel.dy--;
                    }else if(temp == 'L'){
                        this.positionModel.dx++;
                    }else if(temp == 'R'){
                        this.positionModel.dx--;
                    }
                }
                throw new Exception("경로를 다시 입력하세요");
            }

            //끝난 것 처리
            if(this.checkEnd(map)){
                break;
            }

            count++;
            tempDirectionString = moveDirectionString.substring(count);

        }

    }

    //끝난 player가 있을 때
    private void movePlayerWithFinisher(String tempDirectionString, String moveDirectionString, MapCellModel[][] map) throws Exception {
        int count =0;
        Stack s = new Stack();
        //한 칸씩 고려
        while(!tempDirectionString.equals("")){
            //끝났는지 체크
            //움직여야 하는 칸으로 position 변경
            if(tempDirectionString.charAt(0) == map[positionModel.dy][positionModel.dx].nextDirection){
                if(map[positionModel.dy][positionModel.dx].nextDirection == 'U'){
                    positionModel.dy++;
                } else if(map[positionModel.dy][positionModel.dx].nextDirection == 'D'){
                    positionModel.dy--;
                } else if (map[positionModel.dy][positionModel.dx].nextDirection == 'L'){
                    positionModel.dx--;
                } else if (map[positionModel.dy][positionModel.dx].nextDirection == 'R'){
                    positionModel.dx++;
                }
            } else if(map[positionModel.dy][positionModel.dx].cellState == 'B'){ //Bridge 처리
                for( int i=1;(moveDirectionString.length() - count - i) >= 0 ; i++){
                    if(map[positionModel.dy][positionModel.dx+i].cellState == 'b'){
                        positionModel.dx += i;
                        count += i;
                        bridgePenalty++;
                        break;
                    }
                    if(tempDirectionString.charAt(0+i) == 'R'){
                        continue;
                    } else{
                        while(!s.empty()){
                            char temp = (char) s.pop();
                            if(temp == 'U'){
                                this.positionModel.dy++;
                            } else if(temp == 'D'){
                                this.positionModel.dy--;
                            }else if(temp == 'L'){
                                this.positionModel.dx++;
                            }else if(temp == 'R'){
                                this.positionModel.dx--;
                            }
                        }
                        throw new Exception("경로를 다시 입력하세요");
                    }
                }
            } else{
                while(!s.empty()){
                    char temp = (char) s.pop();
                    if(temp == 'U'){
                        this.positionModel.dy++;
                    } else if(temp == 'D'){
                        this.positionModel.dy--;
                    }else if(temp == 'L'){
                        this.positionModel.dx++;
                    }else if(temp == 'R'){
                        this.positionModel.dx--;
                    }
                }
                throw new Exception("경로를 다시 입력하세요");
            }
            //끝난 것 처리
            if(this.checkEnd(map)){
                break;
            }
            //아이템 먹은 것 처리
            count++;
            tempDirectionString = moveDirectionString.substring(count);
        }
    }


    private boolean checkEnd(MapCellModel[][] map){
        if(map[positionModel.dy][positionModel.dx].cellState == 'E') {
            this.isEnd = true;
            return true;
        }
        return false;
    }

    public PositionModel getPosition(){
        return this.positionModel;
    }

    public void setScore(int plus){
        this.score += plus;
    }

    public int getScore(){
        return this.score;
    }

    public int getBridgePenalty(){
        return this.bridgePenalty;
    }

}
