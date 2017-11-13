package scrabble;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BoardTile extends JButton {
    private Image icon;
    private boolean finalized;
    protected String text;
    private static final Color[]cols = {Color.white, new Color(174,220,236), new Color(2,93,229), new Color(254,176,204), new Color(242,66,104), new Color(253,175,201)};
    private static final String[]txt= {"", "2L", "3L", "2W", "3W", "★"};
    public BoardTile(int type) {
        super(txt[type>=txt.length?txt.length-1:type]);
        setBackground(cols[type>=txt.length?txt.length-1:type]);
    }

    public void setLetter(char c) {
        text=String.valueOf(c);
        try {
            icon = ImageIO.read(getClass().getResource(c+".png"));
            setIcon(new ImageIcon(icon));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finalize() {
        finalized = true;
    }
}

interface BoardTileListener {
    void onLocationChosen();
}

abstract class BoardEvent {
    protected java.util.List<BoardTileListener>listeners=new ArrayList<>();

    public BoardEvent() {

    }

    public void addListener(BoardTileListener btl) {
        listeners.add(btl);
    }

    public abstract void idef();
}