import Model.MapCellModel;
import Model.MapModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MapModelTest {
    @Test
    @DisplayName("map 생성자 테스트 실행")
    void initializeMapTest() throws IOException {
        MapModel a = new MapModel(1);
    }

    @Test
    @DisplayName("default.map테스트 실행")
    void getMapTest0() throws IOException {
        String testMap[] = new String[]{
                "XXXXCCHXXXXXX",
                "TCCXPXCXXXXXX",
                "XXB-bXCXXXXXX",
                "XXSXCXCXCCHXX",
                "XXCXCXB-bXCXX",
                "XXCXCXCXCXCXE",
                "XXCXB-bXCXCXC",
                "XXCCHXCXCXPXC",
                "XXXXXXSXCXB-b",
                "XXXXXXPXB-bXC",
                "XXXXXXCXCXCXC",
                "XXXXXXCXHXHXP",
                "XXXXXXCCCXCCS"
        };
        MapModel a = new MapModel(0);
        MapCellModel[][] b = a.getMap();
        int xCut = a.getxCut();
        int yCut = a.getyCut();
        for(int i=0;i< yCut;i++){
            for(int j =0; j<xCut;j++){
                System.out.print(b[i][j].cellState);
            }
            System.out.println();
        }
        for(int i=0;i<yCut;i++){
            for(int j=0; j<xCut;j++){
                assertEquals(testMap[i].charAt(j), b[i][j].cellState);
            }
        }

    }

    @Test
    @DisplayName("another.map테스트 실행")
    void getMapTest1() throws IOException {
        String testMap[] = new String[]{
                "TCCXE",
                "XXB-b",
                "XXSXC",
                "XXB-b",
                "XXCXC",
                "XXCXC",
                "XXCCH"
        };
        MapModel a = new MapModel(1);
        MapCellModel[][] b = a.getMap();
        int xCut = a.getxCut();
        int yCut = a.getyCut();

        for(int i=0;i<yCut;i++){
            for(int j=0; j<xCut;j++){
                assertEquals(testMap[i].charAt(j), b[i][j].cellState);
            }
        }

    }
}
