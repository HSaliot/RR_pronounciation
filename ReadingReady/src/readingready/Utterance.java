/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

/**
 *
 * @author lorenz
 */
public class Utterance {
    
    private String word;
    private int start;
    private int end;
    private int ascr;
    
    public Utterance(String word){
        word = word.replace(".", ""); //replace all . character
        word = word.replace(",", ""); //replace all , character
        word = word.replace("“", ""); //replace all “ character
        word = word.replace("”", ""); //replace all ” character
        word = word.replace("’", ""); //replace all ’ character
        word = word.toLowerCase();
        this.word = word;
    }
    
    public Utterance(String word, int start, int end, int ascr){
        word = word.replace(".", ""); //replace all . character
        word = word.replace(",", ""); //replace all , character
        word = word.replace("“", ""); //replace all “ character
        word = word.replace("”", ""); //replace all ” character
        word = word.replace("’", ""); //replace all ’ character
        word = word.toLowerCase();
        this.word=word;
        this.start=start;
        this.end=end;
        this.ascr=ascr;
    }
    
    public String getWord() {
    return word;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getAscr() {
        return ascr;
    }
}
