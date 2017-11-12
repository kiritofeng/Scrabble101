package scrabble;

import com.sun.java.swing.plaf.windows.WindowsInternalFrameTitlePane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Player extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int MAX_LETTERS = 7;
	private char[] rack = new char[MAX_LETTERS];
    private LetterTile[] buttonrack = new LetterTile[MAX_LETTERS];
    private JLabel scorelbl;
	private int score=0;
    private int num;

	public Player(Scrabble S, int playernum, char... letters) {
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setBounds(0,0,180,380);
        num = playernum;
        setTitle("Player "+num);
        scorelbl = new JLabel("Score: 0");
        setLayout(new BorderLayout(0,0));
        JPanel tileframe = new JPanel();
        tileframe.setLayout(new GridLayout(4,2));
		for (int i = 0; i < letters.length; i++) {
			rack[i] = letters[i];
            buttonrack[i] = new LetterTile(rack[i]);
            buttonrack[i].addActionListener(new TileEvent(S, buttonrack[i].character) {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    chooseTile();
                }

                @Override
                public void chooseTile() {
                    for(TileListener tl:listeners) {
                        tl.onTileChosen(this.c);
                    }
                }
            });
            tileframe.add(buttonrack[i]);
		}
        add(scorelbl, BorderLayout.NORTH);
        add(tileframe, BorderLayout.CENTER);
	}

    public void setScore(int i) {
        score = i;
        scorelbl.setText("Score: " + score);
    }

    public int getScore() {
        return score;
    }

    public void replaceUsed() {
        
    }

}

interface TileListener {
    void onTileChosen(char c);
}

abstract class TileEvent implements ActionListener {

    protected java.util.List<TileListener>listeners = new ArrayList<>();

    char c;
    public TileEvent(Scrabble S, char c) {
        this.c=c;
        addListener(S);
    }

    public void addListener(TileListener tl) {
        listeners.add(tl);
    }

    public abstract void chooseTile();
}

