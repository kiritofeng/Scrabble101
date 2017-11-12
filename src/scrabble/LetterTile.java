package scrabble;

import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class LetterTile extends JButton{

    private Image img;
    char character;

    public LetterTile(char c) {
        super();
        character = c;
        try {
            img = ImageIO.read(getClass().getResource(c+".png"));
            setIcon(new ImageIcon(img));
        } catch (IOException e) {
            setText(String.valueOf(c));
        }
    }
}
