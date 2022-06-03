import Model.MapCell;
import Model.MapModel;
import Model.PlayerModel;
import Model.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerModelTest {
    @Test
    @DisplayName("Player 생성자 테스트 실행")
    void initializePlayerTest() throws IOException {
        MapModel a = new MapModel(1);
        MapCell[][] map = a.getMap();
        Position startpos = new Position(1,0);
        PlayerModel p = new PlayerModel(startpos, map);
    }

    @Test
    @DisplayName("stayPlayer 테스트 실행")
    void stayPlayerTest() throws Exception {
        MapModel a = new MapModel(0);
        MapCell[][] map = a.getMap();
        Position startpos = a.getStartPos();
        PlayerModel p = new PlayerModel(startpos, map);
        Position temp = p.stayPlayer();
        assertEquals(temp.dx, startpos.dx);
        assertEquals(temp.dy, startpos.dy);
    }



    @Test
    @DisplayName("movePlayer 테스트 실행: 끝난 유저X")
    void movePlayerTest() throws Exception {
        MapModel a = new MapModel(0);
        MapCell[][] map = a.getMap();
        Position startpos = a.getStartPos();
        PlayerModel p = new PlayerModel(startpos, map);
        Position temp = p.movePlayer("RLR", 3, false);
        assertEquals(temp.dx, 1);
        assertEquals(temp.dx, 1);
    }

    @Test
    @DisplayName("movePlayer 테스트 실행: 끝난 유저O")
    void movePlayerTest1() throws Exception {
        MapModel a = new MapModel(0);
        MapCell[][] map = a.getMap();
        Position startpos = a.getStartPos();
        PlayerModel p = new PlayerModel(startpos, map);
        Position temp = p.movePlayer("RRD", 3, true);
        assertEquals(temp.dx, 2);
        assertEquals(temp.dx, 2);
    }
}
