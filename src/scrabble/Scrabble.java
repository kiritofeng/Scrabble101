package scrabble;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Scrabble extends JFrame implements TileListener, BoardTileListener {

    @Override
    public void onTileChosen(char c) {
        LetterChosen = c;
    }

	public void onLocationChosen() {
		if(LetterChosen==0)return;

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private final LettersUtil lu;
    private JPanel contentPane;
    private char LetterChosen=0;
    private Dictionary dict =  Dictionary.getDictionary();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Scrabble frame = new Scrabble();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Scrabble() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 980, 980);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel introScreen = new JPanel();
		contentPane.add(introScreen, "Intro Screen");
		introScreen.setLayout(new BorderLayout(0, 0));
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				cl.show(contentPane, "Game Screen");
			}
		});
		introScreen.add(btnPlay, BorderLayout.CENTER);
		
		JLabel lblScrabble = new JLabel("Scrabble");
		introScreen.add(lblScrabble, BorderLayout.NORTH);
		
		JPanel gameScreen = new JPanel();
		contentPane.add(gameScreen, "Game Screen");
		gameScreen.setLayout(new BorderLayout(0, 0));
		
		Board board = new Board();
		gameScreen.add(board, BorderLayout.CENTER);

		lu = new LettersUtil();
		Player P1 = new Player(this,1,lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter());
		Player P2 = new Player(this,2,lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter());
		JButton btnReturnToMain = new JButton("Return to main menu");
		btnReturnToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				cl.show(contentPane, "Intro Screen");
			}
		});
		gameScreen.add(btnReturnToMain, BorderLayout.SOUTH);
		
		JPanel score = new JPanel();
		gameScreen.add(score, BorderLayout.NORTH);
		for(int i=0;!lu.isEmpty();i=i+1&1) {
			if(i==0) {
				P1.setVisible(true);
				//Verify
				verify(board, dict);
				P1.setVisible(false);
			} else {
				P2.setVisible(true);

				P2.setVisible(false);
			}
		}
	}

	int dfs1(int row, int col, boolean dir, Board b) {
		if(row >= Board.BOARD_SIZE || col >= Board.BOARD_SIZE) return 0;
		if(b.isUpdated(row,col)) {
			return 1+dfs1(dir?row+1:row,dir?col:col+1,dir,b);
		} else
			return 0;
	}

	int dfs2(int row,int col, boolean dir, Board b) {
		//Find the top
		if((dir?row:col)==0) return 0;
		else if(b.getChar(row,col)==0) return (dir?row:col)-1;
		else dfs2(dir?row-1:row,dir?col:col-1,dir,b);
	}

	String dfs3(int row,int col,boolean dir, Board b) {
		if(b.getChar(row,col)==0) return "";
		else return b.getChar(row,col)+dfs3(dir?row-1:row,dir?col:col-1,dir,b);
	}

	public boolean verify(Board b, Dictionary d,int len) {
		for(int i=0;i<Board.BOARD_SIZE;i++) {
			for(int j=0;j<Board.BOARD_SIZE;j++) {
				if(b.isUpdated(i,j)) {
					if(dfs1(i,j,false,b)==len) {
						for(int k=i,top;k<i+len;k++) {
							top = dfs2(k, j, true, b);
							if(!d.isWord(dfs3(k,top,true,b))) return false;
						}
						int top=dfs2(i,j,false,b);
						if(!d.isWord(dfs3(top,j,false,b))) return false;
					} else if(dfs1(i,j,true,b)==len) {
						for(int k=j,top;k<j+len;k++) {
							top = dfs2(j, k, false, b);
							if(!d.isWord(dfs3(top,k,false,b))) return false;
						}
						int top=dfs2(i,j,true,b);
						if(!d.isWord(dfs3(j,top,true,b))) return false;
					} else {
						return false;
					}
				}
			}
		}
	}

}
