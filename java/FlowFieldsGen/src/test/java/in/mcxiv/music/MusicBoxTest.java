package in.mcxiv.music;

import javax.swing.*;

class MusicBoxTest {
    public static void main(String[] args) {

        new MusicBox();

        new JFrame() {{
            setSize(200, 200);
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }};
    }
}