package scrabble;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Board extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int BOARD_SIZE = 15;
	public static final int DOUBLE_LETTER_BONUS = 1;
	public static final int TRIPLE_LETTER_BONUS = 2;
	public static final int DOUBLE_WORD_BONUS = 3;
	public static final int TRIPLE_WORD_BONUS = 4;
	public static final int BINGO_BONUS = 50;

	public static byte[][] MULTIPLIERS = { { 4, 0, 0, 1, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4 },
			{ 0, 3, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 3, 0 }, { 0, 0, 3, 0, 0, 0, 1, 0, 1, 0, 0, 0, 3, 0, 0 },
			{ 1, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 1 }, { 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0 },
			{ 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0 }, { 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0 },
			{ 4, 0, 0, 1, 0, 0, 0, 50, 0, 0, 0, 1, 0, 0, 4 }, { 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0 },
			{ 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0 }, { 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0 },
			{ 1, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 1 }, { 0, 0, 3, 0, 0, 0, 1, 0, 1, 0, 0, 0, 3, 0, 0 },
			{ 0, 3, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 3, 0 }, { 4, 0, 0, 1, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4 } };

	private char[][] board_letters;
    private BoardTile[][] board_buttons;
    private boolean[][]isNew;

	Board(char[][] cs) {		
		board_letters = cs;
        board_buttons = new BoardTile[BOARD_SIZE][BOARD_SIZE];
        isNew = new boolean[BOARD_SIZE][BOARD_SIZE];
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
                board_buttons[row][col] = new BoardTile(MULTIPLIERS[row][col]);
                add(board_buttons[row][col]);
			}
		}
	}

	Board() {
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
		board_letters = new char[BOARD_SIZE][BOARD_SIZE];
        board_buttons = new BoardTile[BOARD_SIZE][BOARD_SIZE];
        isNew = new boolean[BOARD_SIZE][BOARD_SIZE];
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				board_letters[row][col] = ' ';
                board_buttons[row][col] = new BoardTile(MULTIPLIERS[row][col]);
                board_buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                    }
                });
                add(board_buttons[row][col]);
			}
		}
	}

	public char getChar(int row, int col) {
		return board_letters[row][col];
	}

	public boolean isUpdated(int row, int col) {
	    return isNew[row][col];
    }


}
