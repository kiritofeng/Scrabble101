package scrabble;

import java.awt.*;
import javax.swing.JButton;
public class BoardTile extends JButton {
    private Image icon;
    private boolean finalized;
    protected String text;
    private static final Color[]cols = {null, Color.blue, Color.blue, Color.red, Color.red, Color.red};
    private static final String[]txt= {"", "2L", "3L", "2W", "3W", "*"};
    public BoardTile(int type) {
        super(txt[type]);
        if(type!=0)
            setBackground(cols[type]);
    }
}

interface BoardTileListener {
    void onLocationChosen();
}
