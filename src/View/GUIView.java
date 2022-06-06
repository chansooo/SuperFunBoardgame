package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIView {

    public GUIView(){
        new GameInfoFrame();
    }

    static class GameInfoFrame extends JFrame{
        public GameInfoFrame(){
            super("BoardGame");

            JPanel jPanel = new JPanel();

            JLabel jl = new JLabel("<html><body style='text-align:center;'><br /> 사용자 수 &nbsp;&nbsp; /&nbsp;&nbsp;&nbsp;&nbsp; map 번호(0 또는 1) &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;<br />  ( 2 ~ 4 ) &nbsp;&nbsp;&nbsp;&nbsp;  </body></html>", JLabel.CENTER);
            JTextField playerNumTextField = new JTextField(10);
            JTextField mapNumTextField = new JTextField(10);
            JButton submit = new JButton("게임 시작");
//
//            this.setLocationRelativeTo(null);
//            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
            setSize(500, 300);
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int playerNum = Integer.parseInt(playerNumTextField.getText());
                    int mapNum = Integer.parseInt(mapNumTextField.getText());

                }
            });


        }
    }

    class MainGameFrame extends JFrame{

    }
}
