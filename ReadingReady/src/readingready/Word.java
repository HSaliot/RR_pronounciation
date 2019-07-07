/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.util.ArrayList;

/**
 *
 * @author Lorenz
 */
public class Word {
    private String word;
    private ArrayList<String> pronunciations = new ArrayList<>();
    private ArrayList<String> lines = new ArrayList<>();
    private int lastIndex;
    
    public Word(String word){
        word = word.replace(".", ""); //replace all . character
        word = word.replace(",", ""); //replace all , character
        word = word.replace("“", ""); //replace all “ character
        word = word.replace("”", ""); //replace all ” character
        word = word.replace("’", ""); //replace all ’ character
        word = word.toLowerCase();
        this.word = word;
    }

    public String getWord(){
        return word;
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
    
    public void setLastIndex(){
        if(lines.size()==0)
            lastIndex=0;
        else if(Character.isDigit(lines.get(lines.size()-1).charAt(0))){
            String[] tmp = lines.get(lines.size()-1).split("\\)");
            String temp = tmp[0];
            lastIndex = Integer.parseInt(temp);
        }
        else
            lastIndex=1;
    }
    public int getLastIndex(){
        return lastIndex;
    }
    public void increaseLastIndex(){
        lastIndex =lastIndex+1;
    }
}

