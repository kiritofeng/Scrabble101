package scrabble;

import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class LetterTile extends JButton{

    private Image img;
    private char character;
    private boolean isUsed;

    public LetterTile(char c) {
        super();
        character = c;
        isUsed = false;
        try {
            img = ImageIO.read(getClass().getResource(c+".png"));
            setIcon(new ImageIcon(img));
        } catch (IOException e) {
            setText(String.valueOf(c));
        }
    }

    public void setUsed(boolean b){
        isUsed = b;
        //setEnabled(b);
    }

    public boolean getUsed() {
        return isUsed;
    }
}
