package scrabble;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private JPanel tileframe;
	private int score=0;
    private int num;

	public Player(final Scrabble S, int playernum, char... letters) {
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setBounds(0,0,180,380);
        num = playernum;
        setTitle("Player "+num);
        scorelbl = new JLabel("Score: 0");
        setLayout(new BorderLayout(0,0));
        tileframe = new JPanel();
        tileframe.setLayout(new GridLayout(4,2));
		for (int i = 0; i < letters.length; i++) {
			rack[i] = letters[i];
            buttonrack[i] = new LetterTile(rack[i]);
            final LetterTile lt = buttonrack[i];
            buttonrack[i].addActionListener(new TileEvent(S, rack[i], i) {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    lt.setUsed(true);
                    chooseTile();
                }

                @Override
                public void chooseTile() {
                    for(TileListener tl:listeners) {
                        tl.onTileChosen(this.c, this.i);
                    }
                }
            });
            tileframe.add(buttonrack[i]);
		}
		JButton submit = new JButton();
		try {
            submit.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("submit.png"))));
        } catch (IOException ioe) {
        }
		submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                S.onTurnComplete(playernum);
            }
        });
        JButton reset = new JButton();
        try {
            reset.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("undo.png"))));
        } catch (IOException e) {
        }
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                S.onReset(playernum);
            }
        });
        JButton redraw = new JButton();
        try {
            redraw.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("exchange.png"))));
        } catch (IOException e) {
        }
        redraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                S.onRedraw(playernum);
                tileframe.removeAll();
                tileframe.setLayout(new GridLayout(4,2));
                for(int i=0;i<MAX_LETTERS;i++) {
                    tileframe.add(buttonrack[i]);
                    buttonrack[i].addActionListener(new TileEvent(S, rack[i], i) {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            chooseTile();
                        }

                        @Override
                        public void chooseTile() {
                            for(TileListener tl:listeners) {
                                tl.onTileChosen(this.c, this.i);
                            }
                        }
                    });
                }
            }
        });
        add(scorelbl, BorderLayout.NORTH);
        add(tileframe, BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2));
        buttons.add(submit);
        buttons.add(redraw);
        buttons.add(reset);
        add(buttons,BorderLayout.SOUTH);
	}

    public void setScore(int i) {
        score = i;
        scorelbl.setText("Score: " + score);
        scorelbl.repaint();
    }

    public int getScore() {
        return score;
    }

    public int getNumberUsed() {
	    int used=0;
	    for(int i=0;i<MAX_LETTERS;i++) {
	        if(buttonrack[i].getUsed())
	            used++;
        }
        return used;
    }

    public void resetLetters(char ... newLetters) {
        for(int i=0;i<MAX_LETTERS;i++)
            buttonrack[i].setUsed(true);
        replaceUsed(newLetters);
    }

    public void replaceUsed(char ... newLetters) {
        tileframe.removeAll();
        tileframe.setLayout(new GridLayout(4, 2));
        for(int i=0,j=0;i<MAX_LETTERS;i++) {
            if(buttonrack[i].getUsed()) {
                rack[i] = newLetters[j++];
                buttonrack[i] = new LetterTile(rack[i]);
            }
            tileframe.add(buttonrack[i]);
        }
        tileframe.repaint();
    }

    public void resetUsed() {
	    for(LetterTile lt:buttonrack)
	        lt.setUsed(false);
    }

}

interface TileListener {
    void onTileChosen(char c, int i);
}

abstract class TileEvent implements ActionListener {

    protected java.util.List<TileListener>listeners = new ArrayList<>();

    protected char c;
    protected int i;
    public TileEvent(Scrabble S, char c, int i) {
        this.c=c;
        this.i=i;
        addListener(S);
    }

    public void addListener(TileListener tl) {
        listeners.add(tl);
    }

    public abstract void chooseTile();
}

interface TurnListener {
    void onTurnComplete(int i);
}

interface TileResetListener {
    void onReset(int i);
}

interface TileRedrawListener {
    void onRedraw(int i);
}