package scrabble;

import java.util.Scanner;
import java.util.*;
import java.lang.Math;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for listeners
import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.*;

public class letters
{
    protected char letter;
    protected int n;

    static List<Character> bag = new ArrayList<Character>();
    private static int[] numLetters = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
                    10, 10, 10, 10, 10, 10, 10 }; //to be updated

    public letters (char c) 
    {
        letter = c;
    }
    
    public letters( int x )
    {
        n = x;
    }
    
//    
    public char letters()
    {
        if( n >= (int) 'A' && n <= (int) 'Z' )
            return (char)(((n - 1) % 26) + 65);
        
        
        return ' ';       
    }
    
     public int points()
    {
        if( letter >= 65 && letter <= 90 )
            return (int) letter - (int) 'A';
        
        return 0;
    }
     
     public void resetBag() {
		for (int i = 0; i < 27; i++) {
			for (int j = 1; j <= numLetters[i]; j++) {
				if (i == 27) {
					bag.add('*');
				} else {
					bag.add((char) (i + 65));
				}
			}
		}
	}
	
	private int getRandom() {
		return (int) (Math.random() * (bag.size() - 1));
	}

	public char removeLetter() {
		int index = getRandom();
		char c = ' ';
		if (index >= 0 && index < bag.size()) {
			c = bag.get(index);
			bag.remove(index);
		}
		return c;
	}
}

class RandomTiles
{
    public int num( int [] array )
     {
         Random r= new Random();
         int t= r.nextInt(100);
         
         for( int x= 0; x < array.length; x ++ )
         {
             if( array[x] == t )
                 t= r.nextInt(100);
         }
         
         return t;
     }
}

class totalTiles
{
    protected char [] tiles = new char [100];
    
    public totalTiles()
    {
        int [] c= null;   
        for( int x= 0; x < 100; x ++ )
        {
            RandomTiles v= new RandomTiles();
            int z= v.num(c);
            c[x] = z;
            letters t= new letters( z );
            tiles[x]= t.letters();
        }
    }
    
    public char [] delete( char [] a, int pos )
    {
        char [] array =  new char [a.length];
        for( int x= 0; x < pos; x ++ )
            array[x]= a[x];
        for( int d= pos; d < array.length - pos; d ++)
            array[d]= a[d + 1];
        return array;
    }
    
    // remove certain number of cards from top of deck
  public char[] remove (char [] a, int tiles)
  {
    //determines number of cards needed to remove from deck or hand or pile
    char temp[] = new char [a.length - tiles];
    for (int x = 0 ; x < temp.length ; x++)
      temp[x] = a[x + tiles];//takes the first number of cards from array of cards and erases them
    
    return temp;
  }
}
/*
public class Scrabble 
{
    
    public static void title( int a, String msg )
    {
        for( int x = 1; x < a; x ++ )
            System.out.print("*");
        
        System.out.println(msg);
        
        for( int y = 1; y < a; y ++ )
            System.out.print("*");
    }

    
    public static void main(String[] args) 
    {
//        Display title and rules
          title(10, "Welcome to Scrabble");
//        shuffle tiles
        totalTiles Stack = new totalTiles();
        
//        display board and points section
//        Determine who goes first with draw
        
//        distribute tiles (10 each)
//        display tiles
//        first player creates a word
//        points are awarded determined by the letters used and the placement of the letters  
//        switch boards to second player
//        second player creates a word
//        points are awarded determined by the letters used and the placement of the letters
//        loop
//        {
//        first player gets a refill of their tiles
//        first player may choose to exchange their hand
//        if true, exchange the amount of letters with the arsenal
//        first player makes a word
//        points are awarded determined by the letters used and the placement of the letters
//        second player gets a refill of their tiles
//        second player may choose to exchange their hand
//        if true, exchange the amount of letters with the arsenal
//        second player makes a word
//        points are awarded determined by the letters used and the placement of the letters
//        }        
    }
    
}
*/
