package scrabble;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class Scrabble extends JFrame implements TileListener, BoardTileListener {

    @Override
    public void onTileChosen(char c) {
        LetterChosen = c;
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private final LettersUtil lu;
    private JPanel contentPane;
    private char LetterChosen=0;

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
		P1.setVisible(true);
		
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
	}

}
