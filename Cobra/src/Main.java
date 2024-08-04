import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static int direction = 1; //The snake will start going left

    public static void main(String[] args) {

        JFrame f = new JFrame();
        Painel p = new Painel();

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(850,950);
        f.add(p);
        f.setVisible(true);
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        p.setFocusable(true); //searched
        p.requestFocusInWindow();

        p.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_LEFT) {
                    if(Painel.realTimeDirection != 2) direction = 1; // Prevents the snake goes backwards
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    if(Painel.realTimeDirection != 1)direction = 2;
                } else if (keyCode == KeyEvent.VK_UP) {
                    if(Painel.realTimeDirection != 4)direction = 3;
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    if(Painel.realTimeDirection != 3)direction = 4;
                }
            }
        });
    }
}