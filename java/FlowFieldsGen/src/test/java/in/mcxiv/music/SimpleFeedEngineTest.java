package in.mcxiv.music;

import in.mcxiv.music.SimpleFeedEngine.NoteSequencer;

import javax.swing.*;

import static in.mcxiv.music.SimpleFeedEngine.NoteSequencer.SEQ_1212;

class SimpleFeedEngineTest {
    public static void main(String[] args) {

        SimpleFeedEngine engine = new SimpleFeedEngine();

        NoteSequencer sequencer = engine.getSequencer(SEQ_1212);

        for (int i = 0; i < 10; i++)
            sequencer.playNote(1, SimpleFeedEngine.NT_MID_C, 64);

        new JFrame() {{
            setSize(200, 200);
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }};
    }
}