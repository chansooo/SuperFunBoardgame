import Model.MapCellModel;
import Model.MapModel;
import Model.PlayerModel;
import Model.PositionModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerModelTest {
    @Test
    @DisplayName("Player 생성자 테스트 실행")
    void initializePlayerTest() throws IOException {
        MapModel a = new MapModel(1);
        MapCellModel[][] map = a.getMap();
        PositionModel startpos = new PositionModel(1,0);
        PlayerModel p = new PlayerModel(startpos);
    }

    @Test
    @DisplayName("stayPlayer 테스트 실행")
    void stayPlayerTest() throws Exception {
        MapModel a = new MapModel(0);
        MapCellModel[][] map = a.getMap();
        PositionModel startpos = a.getStartPos();
        PlayerModel p = new PlayerModel(startpos);
        PositionModel temp = p.stayPlayer();
        assertEquals(temp.dx, startpos.dx);
        assertEquals(temp.dy, startpos.dy);
    }



    @Test
    @DisplayName("movePlayer 테스트 실행: 끝난 유저X")
    void movePlayerTest() throws Exception {
        MapModel a = new MapModel(0);
        MapCellModel[][] map = a.getMap();
        PositionModel startpos = a.getStartPos();
        PlayerModel p = new PlayerModel(startpos);
        PositionModel temp = p.movePlayer("RLR", 3, map,false);
        assertEquals(temp.dx, 1);
        assertEquals(temp.dx, 1);
    }

    @Test
    @DisplayName("movePlayer 테스트 실행: 끝난 유저O")
    void movePlayerTest1() throws Exception {
        MapModel a = new MapModel(0);
        MapCellModel[][] map = a.getMap();
        PositionModel startpos = a.getStartPos();
        PlayerModel p = new PlayerModel(startpos);
        PositionModel temp = p.movePlayer("R", 1, map,  true);
        assertEquals(temp.dx, 1);
        //assertEquals(temp.dy, startpos.dy);
    }

    @Test
    @DisplayName("movePlayer 테스트 실행: bridge 통과")
    void movePlayerTest2() throws Exception {
        MapModel a = new MapModel(1);
        MapCellModel[][] map = a.getMap();
        PositionModel startpos = a.getStartPos();
        PlayerModel p = new PlayerModel(startpos);
        PositionModel temp = p.movePlayer("RRDRR", 5, map, false);
        assertEquals(temp.dx, 4);
        assertEquals(temp.dy, 1);
    }

    @Test
    @DisplayName("movePlayer 테스트 실행: command 잘못 입력")
    void movePlayerTest3() throws Exception {
        MapModel a = new MapModel(1);
        MapCellModel[][] map = a.getMap();
        PositionModel startpos = a.getStartPos();
        PlayerModel p = new PlayerModel(startpos);
        PositionModel temp = new PositionModel(1,0);
        try{
            temp = p.movePlayer("RRR", 3, map, false);
        } catch (Exception e){
            assertEquals(p.getPosition().dx, startpos.dx);
        }

    }


}
