package scrabble;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.HashSet;

public class Scrabble extends JFrame implements TileListener, BoardTileListener, TurnListener, TileResetListener, TileRedrawListener {

    @Override
    public void onTileChosen(char c, int i) {
        if(!TilesChosen.contains(i)) {
            LetterChosen = c;
            TilesChosen.add(i);
        }
    }

    @Override
	public void onLocationChosen(int row, int col) {
		if(LetterChosen==0)return;
		board.Update(row, col, LetterChosen);
		LetterChosen=0;
		Placed+=1;
	}

	@Override
	public void onReset(int player) {
        board.resetBoard();
        P[player].resetUsed();
        TilesChosen.clear();
        Placed=0;
    }

    @Override
	public void onRedraw(int player) {
        board.resetBoard();
        char[] c = new char[Player.MAX_LETTERS];
        for(int i=0;i<Player.MAX_LETTERS;i++)
            c[i]=lu.getRandomLetter();
        P[player].resetLetters(c);
        P[player].setVisible(false);
        P[player^1].setVisible(true);
        Placed=0;
        TilesChosen.clear();
    }

    @Override
	public void onTurnComplete(int player) {
        int delta = verify(board,dict,Placed);
        System.out.println(delta);
        if(delta==0) {
            //They are wrong
            //Reset _everything_
            board.resetBoard();
            P[player].resetUsed();
        } else {
            board.setBoard();
            char[] c = new char[Placed];
            for(int i=0;i<Placed;i++)
                c[i]=lu.getRandomLetter();
            P[player].replaceUsed(c);
            P[player].setScore(P[player].getScore()+delta);
            P[player].setVisible(false);
            P[player^1].setVisible(true);
        }
        TilesChosen.clear();
        Placed=0;
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private final LettersUtil lu;
    private JPanel contentPane;
    private char LetterChosen=0;
    private int Placed=0;
    private HashSet<Integer>TilesChosen;
    private Dictionary dict =  Dictionary.getDictionary();
    private Board board;
    private Player[] P;

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
        TilesChosen = new HashSet<>();
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
		
		board = new Board(this);
		gameScreen.add(board, BorderLayout.CENTER);

		lu = new LettersUtil();
		P = new Player[2];
		P[0] = new Player(this,0,lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter());
		P[1] = new Player(this,1,lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter(),lu.getRandomLetter());
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
		P[0].setVisible(true);
	}

	int dfs1(int row, int col, boolean dir, Board b) {
		if(row >= Board.BOARD_SIZE || col >= Board.BOARD_SIZE) return 0;
		if(b.getChar(row,col)!=0) {
			return dfs1(dir?row+1:row,dir?col:col+1,dir,b)+(b.isUpdated(row,col)?1:0);
		} else
			return 0;
	}

	int dfs2(int row,int col, boolean dir, Board b) {
		//Find the top
		if((dir?row:col)==0) return 0;
		else if(b.getChar(row,col)==0) return (dir?row:col)+1;
		else return dfs2(dir?row-1:row,dir?col:col-1,dir,b);
	}

	Triple<String,Integer, Integer> dfs3(int row,int col,boolean dir, Board b) {
		if(b.getChar(row,col)==0||(dir?row:col)>=Board.BOARD_SIZE) return new Triple<>("",0,1);
		else {
            Triple<String,Integer,Integer>T = dfs3(dir?row+1:row,dir?col:col+1,dir,b);
		    T.first=b.getChar(row,col)+T.first;
		    switch (b.MULTIPLIERS[row][col]) {
                case Board.DOUBLE_LETTER_BONUS:
                case Board.STAR:
                    T.second+=2*LettersUtil.getValue(b.getChar(row,col));
                    break;
                case Board.TRIPLE_LETTER_BONUS:
                    T.second+=3*LettersUtil.getValue(b.getChar(row,col));
                    break;
                case Board.DOUBLE_WORD_BONUS:
                    T.third*=2;
                    break;
                case Board.TRIPLE_WORD_BONUS:
                    T.third*=3;
                    break;
            }
            return T;
        }
	}

	public int verify(Board b, Dictionary d,int len) {
		for(int i=0;i<Board.BOARD_SIZE;i++) {
			for(int j=0;j<Board.BOARD_SIZE;j++) {
				if(b.isUpdated(i,j)) {
				    int res=0;
					if(dfs1(i,j,false,b)==len) {
						for (int k = i, top; k < Board.BOARD_SIZE; k++) {
							if (!b.isUpdated(k, j)) continue;
							top = dfs2(k, j, true, b);
							Triple<String, Integer, Integer> t = dfs3(k, top, true, b);
							if (t.first.length() > 1) {
								if (!d.isWord(t.first))
									return 0;
								res += t.second * t.third;
							}
						}
						int top = dfs2(i, j, false, b);
						Triple<String, Integer, Integer> t = dfs3(i, top, false, b);
						if (t.first.length() > 1) {
							if (!d.isWord(t.first))
								return 0;
							res += t.second * t.third;
						}
					}
					if(dfs1(i,j,true,b)==len) {
						for(int k=j,top;k<Board.BOARD_SIZE;k++) {
						    if(!b.isUpdated(i,k)) continue;
							top = dfs2(j, k, false, b);
							Triple<String,Integer,Integer>t=dfs3(top,k,false,b);
							if(t.first.length()>1) {
                                if(!d.isWord(t.first)) return 0;
                                res+=t.second*t.third;
                            }
						}
						int top=dfs2(i,j,true,b);
						Triple<String,Integer,Integer>t=dfs3(top,j,true,b);
                        if(t.first.length()>1) {
                            if(!d.isWord(t.first))
                                return 0;
                            res+=t.second*t.third;
                        }
					} else {
						return 0;
					}
					return res;
				}
			}
		}
		return 0;
	}

}

class Triple<A,B,C> {
    A first;
    B second;
    C third;
    public Triple(A a, B b, C c) {
        first = a;
        second = b;
        third = c;
    }
}