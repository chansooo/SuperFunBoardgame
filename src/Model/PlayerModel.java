package Model;

public class PlayerModel {
    boolean isEnd = false;
    private Position position;
    private int bridgePenalty =0;
    int canMoveNum =0;
    MapCell[][] map;
    //TODO: map을 받아서 처음에서 시작.
    //
    public PlayerModel(Position startPosition, MapCell[][] map){
        this.position = startPosition;
        this.map = map;
        bridgePenalty = 0;
    }

    //움직일 수 있는 상황인지 체크
    public boolean moveValidateCheck(){
        if(this.isEnd){
            return false;
        }
        return true;
    }

    public Position stayPlayer(){
        if(bridgePenalty >0){
            bridgePenalty--;
        }
        return position;
    }

    public Position movePlayer(String moveDirectionString, int curDiceNum, boolean hasFinisher) throws Exception {
        canMoveNum = curDiceNum - bridgePenalty;
        Position result;
        if( moveDirectionString.length() != canMoveNum){
            throw new Exception("ERROR: 갈 수 있는 횟수에 맞게 입력하세요.");
        }
        // 한칸한칸 움직일 수 있는지 없는지 확인 (end체크, bridge 체크(이건 여기서??))
        String tempDirectionString = moveDirectionString;
        if (hasFinisher){
           this.movePlayerWithFinisher(tempDirectionString, moveDirectionString);
        } else {
            this.movePlayerNoFinisher(tempDirectionString, moveDirectionString);
        }

        return position;

    }

    //끝난 player 없을 때
    private void movePlayerNoFinisher(String tempDirectionString, String moveDirectionString) throws Exception {
        int count =0;
        //한 칸씩 고려
        while(!tempDirectionString.equals("")){
            //끝났는지 체크
            //움직여야 하는 칸으로 position 변경
            if( tempDirectionString.charAt(0) == this.map[position.dy][position.dx].preDirection){
                if(this.map[position.dy][position.dx].preDirection == 'U'){
                    position.dy--;
                } else if(this.map[position.dy][position.dx].preDirection == 'D'){
                    position.dy++;
                } else if (this.map[position.dy][position.dx].preDirection == 'L'){
                    position.dx--;
                } else if (this.map[position.dy][position.dx].preDirection == 'R'){
                    position.dx++;
                }
            }else if(tempDirectionString.charAt(0) == this.map[position.dy][position.dx].nextDirection){
                if(this.map[position.dy][position.dx].nextDirection == 'U'){
                    position.dy--;
                } else if(this.map[position.dy][position.dx].nextDirection == 'D'){
                    position.dy++;
                } else if (this.map[position.dy][position.dx].nextDirection == 'L'){
                    position.dx--;
                } else if (this.map[position.dy][position.dx].nextDirection == 'R'){
                    position.dx++;
                }
            } else if(this.map[position.dy][position.dx].cellState == 'B'){ //Bridge 처리
                for( int i=1;(moveDirectionString.length() - count - i) >= 0 ; i++){
                    if(map[position.dy][position.dx+i].cellState == 'b'){
                       position.dx += i;
                       count += i;
                       bridgePenalty++;
                       break;
                    }
                    if(tempDirectionString.charAt(0+i) == 'R'){
                        continue;
                    } else{
                        throw new Exception("경로를 다시 입력하세요");
                    }
                }
            } else{
                throw new Exception("경로를 다시 입력하세요");
            }

            //끝난 것 처리
            if(this.checkEnd()){
                break;
            }

            count++;
            tempDirectionString = moveDirectionString.substring(count);

        }

    }

    //끝난 player가 있을 때
    private void movePlayerWithFinisher(String tempDirectionString, String moveDirectionString) throws Exception {
        int count =0;
        //한 칸씩 고려
        while(!tempDirectionString.equals("")){
            //끝났는지 체크
            //움직여야 하는 칸으로 position 변경
            if(tempDirectionString.charAt(0) == this.map[position.dy][position.dx].nextDirection){
                if(this.map[position.dy][position.dx].nextDirection == 'U'){
                    position.dy++;
                } else if(this.map[position.dy][position.dx].nextDirection == 'D'){
                    position.dy--;
                } else if (this.map[position.dy][position.dx].nextDirection == 'L'){
                    position.dx--;
                } else if (this.map[position.dy][position.dx].nextDirection == 'R'){
                    position.dx++;
                }
            } else if(this.map[position.dy][position.dx].cellState == 'B'){ //Bridge 처리
                for( int i=1;(moveDirectionString.length() - count - i) >= 0 ; i++){
                    if(map[position.dy][position.dx+i].cellState == 'b'){
                        position.dx += i;
                        count += i;
                        bridgePenalty++;
                        break;
                    }
                    if(tempDirectionString.charAt(0+i) == 'R'){
                        continue;
                    } else{
                        throw new Exception("경로를 다시 입력하세요");
                    }
                }
            } else{
                throw new Exception("경로를 다시 입력하세요");
            }
            //끝난 것 처리
            if(this.checkEnd()){
                break;
            }

            count++;
            tempDirectionString = moveDirectionString.substring(count);
        }
    }


    private boolean checkEnd(){
        if(map[position.dy][position.dx].cellState == 'E') {
            this.isEnd = true;
            return true;
        }
        return false;
    }




}
