/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
    private ArrayList<String> raw = new ArrayList<>();
    
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

    public void addPronunciation(String[] pronunciation,Boolean created) {
        lastIndex++;
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        if(created){
            System.out.print(pronunciation);
            for(int i=0;i<pronunciation.length;i++)
                if(i==pronunciation.length-1)
                stringBuilder.append(pronunciation[i]);
            else
                stringBuilder.append(pronunciation[i]+" ");
        } else {
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
        }
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
    public void setPronounciations(String title,String dict) throws FileNotFoundException, IOException{
        boolean exist;
        
        if(dict.equals("cmudict-en-us.dict"))
            exist = false;
        else 
            exist = true;
        
        String[] words=null;  //Intialize the word Array
        File open = new File(dict);

        FileReader fr = new FileReader(open);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String s;     
        boolean found = false;   
        String delimiters = "[ \\(\\s+]";
        while((s=br.readLine())!=null)   //Reading Content from the file
        {
            words=s.split(delimiters);  //Split the word using space
             
            if (words[0].equals(word))   //Search for the given word
            {                
                addPronunciation(words,false);
                raw.add(s);
            }
        }
        if(!exist&&!isInDictionary(title))
            toDictionary(title);
        fr.close();
    }
    public boolean isInDictionary(String title) throws IOException{
        boolean in = false;
        Path out = Paths.get(title.replace(" ", "").toLowerCase()+".dict");
        if(Files.exists(out)){
            File open = new File(out.toString());
            FileReader fr = new FileReader(open);  //Creation of File Reader object
            BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
            String s;     
            boolean found = false;   
            String delimiters = "[ \\(\\s+]";
            String[] words;
            while((s=br.readLine())!=null)   //Reading Content from the file
            {
                words=s.split(delimiters);  //Split the word using space

                if (words[0].equals(word))   //Search for the given word
                {                
                    in=true;
                }
            }
            fr.close();
        }
        else
            in = false;
        return in;
    }
    public void toDictionary(String title) throws IOException{
        Path out = Paths.get(title.replace(" ", "").toLowerCase()+".dict");

        if(Files.exists(out))
            Files.write(out,raw,StandardOpenOption.APPEND);
        else
            Files.write(out,raw,Charset.defaultCharset());   

    }
}

