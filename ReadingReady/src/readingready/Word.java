/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.util.Random;

/**
 *
 * @author Lorenz
 */
public class Word {
    private String word;
    private int score;
            
    
    public Word(String word){
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
    
}
