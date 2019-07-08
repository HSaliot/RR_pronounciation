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
    private ArrayList<Utterance> utterances = new ArrayList<>();
    private ArrayList<String> strings = new ArrayList<>();
    private String studentName;
    private String selection;
    public void main(String selection,String studentName,String file) throws IOException {
        this.studentName = studentName;
        this.selection = selection;
        String s;
        String command= "pocketsphinx_continuous -hmm en-us-download -infile "+file+" -dict darkchocolate.dict -backtrace yes -fsgusefiller no -bestpath no";
        try {

            // Process provides control of native processes started by ProcessBuilder.start and Runtime.exec.
            // getRuntime() returns the runtime object associated with the current Java application.
            Process p = Runtime.getRuntime().exec(command);

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String[] temp;
            Boolean save=false;
            while ((s = stdError.readLine()) != null) {
                s = s.replaceAll(" +"," ");
                temp = s.split(" ");
                
                if(temp[0].equals("</s>"))
                    save=false;
                if(save.equals(true)&&(!temp[0].equals("<sil>"))){
                    String tempString = temp[0]+" "+temp[1]+" "+temp[2]+" "+temp[4];
                    strings.add(tempString);    
                }
                if(temp[0].equals("<s>"))
                    save=true;
                
            }
        } catch (IOException e) {
        }
        
        Path out = Paths.get("src/readingready/resources/pocketsphinx_output/"+selection+"_"+studentName+".txt");
        if(Files.exists(out))
            Files.write(out,strings,StandardOpenOption.APPEND);
        else
            Files.write(out,strings,Charset.defaultCharset());   
    }
}