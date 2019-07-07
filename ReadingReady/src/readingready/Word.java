/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Lorenz
 */
public class Word {
    private String word;
    private int score;
    private ArrayList<String> pronunciations = new ArrayList<>();
    private ArrayList<String> lines = new ArrayList<>();
    private int lastIndex=0;
    
    public Word(String word){
        word = word.replace(".", ""); //replace all . character
        word = word.replace(",", ""); //replace all , character
        word = word.replace("“", ""); //replace all “ character
        word = word.replace("”", ""); //replace all ” character
        word = word.replace("’", ""); //replace all ’ character
        word = word.toLowerCase();
        this.word = word;
        score = rand();
    }
    
    private int rand(){
        Random rand;
        int random = (int) (Math.random()*1000+1);
        if(word.length()>10)
            random+=400;
        else if(word.length()<7)
            random-=200;
        else if(word.length()>7)
            random+=200;
        return random;
    }

    public String getWord(){
        return word;
    }
    
    public int getScore() {
        return score;
    }
    
    public ArrayList<String> getPronunciations(){
        return pronunciations;
    }

    public void addPronunciation(String[] pronunciation) {
        lastIndex++;
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        lines.add(pronunciation[1]);
        if(!Character.isDigit(pronunciation[1].charAt(0)))
            for(int i =1;i<pronunciation.length;i++)
                if(i==pronunciation.length-1)
                stringBuilder.append(pronunciation[i]);
            else
                stringBuilder.append(pronunciation[i]+" ");
        else
            for(int i=2;i<pronunciation.length;i++)
                if(i==pronunciation.length-1)
                stringBuilder.append(pronunciation[i]);
            else
                stringBuilder.append(pronunciation[i]+" ");
        temp = stringBuilder.toString();
        pronunciations.add(temp);
    }

    public void removePronunciation(int i) {
        pronunciations.remove(i);
    }
    
    public int getLastIndex(){
        if(lines.size()==1)
            lastIndex=1;
        else
            lastIndex = lines.get(lines.size()-1).charAt(0);
        return lastIndex;
    }
}

