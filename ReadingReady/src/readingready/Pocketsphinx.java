/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 *
 * @author lorenz
 */
public class Pocketsphinx {
    private ArrayList<String> strings = new ArrayList<>();
    
    private String strTrained = "models/en-us-both80";
    private String strUntrained = "models/en-us-untrained";
    
    public void evaluateNormal(String path, String wav, Boolean trained, String passage) throws IOException {
        String s,acousticModel;
        if(trained)
            acousticModel = strTrained;
        else
            acousticModel = strUntrained;
        String command= "pocketsphinx_continuous -hmm "+acousticModel+" -infile " +
                path + "/wavs/" + wav + ".wav" + " -dict dict/"+passage+".dict -backtrace yes -fsgusefiller no -bestpath no";
        
        try {

            // Process provides control of native processes started by ProcessBuilder.start and Runtime.exec.
            // getRuntime() returns the runtime object associated with the current Java application.
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String[] temp;
            String sentenceTemp = "*** ";
            Boolean save=false;
            strings.add("***"+stdInput.readLine()+"***");
            
            while ((s = stdError.readLine()) != null) {
                s = s.replaceAll(" +"," ");
                temp = s.split(" ");
                
                if(temp[0].equals("</s>")){
                    save=false;
                }
                if(save.equals(true)&&(!temp[0].equals("<sil>"))&&(!temp[0].equals("INFO:"))){
                    String tempString = temp[0]+" "+temp[1]+" "+temp[2]+" "+temp[4];
                    strings.add(tempString);
                    sentenceTemp = sentenceTemp + temp[0]+" ";
                }
                if(temp[0].equals("<s>"))
                    save=true;
                
            }
            sentenceTemp = sentenceTemp + "***";
            sentenceTemp = sentenceTemp.replace("(2)", "").replace("(3)", "").replace("<s> ", "");
            strings.set(0, sentenceTemp);
        } catch (IOException e) {
        }
        
        Path out = Paths.get(path + "/resultNormal.txt");
        if(Files.exists(out))
            Files.write(out,strings,StandardOpenOption.APPEND);
        else
            Files.write(out,strings,Charset.defaultCharset());   
    }
    
    public void evaluateForced(String path, String i, String selection,boolean trained, String passage) throws IOException {
        String s,acousticModel;
        if(trained)
            acousticModel = strTrained;
        else
            acousticModel = strUntrained;
        String command= "pocketsphinx_continuous -hmm "+acousticModel+" -infile " + path + "/wavs/" + i + ".wav" + " -jsgf " +
                "src/readingready/resources/selections/" + selection.replace(" ", "").toLowerCase() + "/jsgf/" + i + ".jsgf" + 
                " -dict dict/"+passage+"dict -backtrace yes -fsgusefiller no -bestpath no";
        try {

            // Process provides control of native processes started by ProcessBuilder.start and Runtime.exec.
            // getRuntime() returns the runtime object associated with the current Java application.
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String[] temp;
            Boolean save=false;
            String sentenceTemp = "*** ";
            strings.add("***"+stdInput.readLine()+"***");
            
            while ((s = stdError.readLine()) != null) {
                s = s.replaceAll(" +"," ");
                temp = s.split(" ");
                
                if(temp[0].contains("INFO:")){
                    save=false;
                }
                if(save.equals(true)){
                    if(temp[0].equals("sil"));
                    else if(temp[0].equals("(NULL)"));
                    else{
                    String tempString = temp[0]+" "+temp[1]+" "+temp[2]+" "+temp[4];
                    strings.add(tempString);
                    sentenceTemp = sentenceTemp + temp[0]+" ";
                    }
                }
                if(temp[0].contains("word"))
                    save=true;
                
            }
            sentenceTemp = sentenceTemp + "***";
            sentenceTemp = sentenceTemp.replace("(2)", "").replace("(3)", "").replace("<s> ", "").replace("INFO: ", "");
            strings.set(0, sentenceTemp);
        } catch (IOException e) {
        }
        
        Path out = Paths.get(path + "/resultForced.txt");
        if(Files.exists(out))
            Files.write(out,strings,StandardOpenOption.APPEND);
        else
            Files.write(out,strings,Charset.defaultCharset());   
    }
}