/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechAligner;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.result.WordResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Lorenz Canuto
 * @author Hannah Saliot
 */
public class Sphinx{
    private Configuration configuration;
    private StreamSpeechRecognizer recognizer;
    private SpeechAligner aligner;

    public void initialize() throws IOException{
        configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        //configuration.setAcousticModelPath("en-us-adapt"); //adapted
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        recognizer = new StreamSpeechRecognizer(configuration);
    }
    
    public void setAcousticModel(String path) throws IOException{
        configuration.setAcousticModelPath(path);
        recognizer = new StreamSpeechRecognizer(configuration);
    }
    
    public void setDictionary(String path) throws IOException{
        configuration.setDictionaryPath(path);
        recognizer = new StreamSpeechRecognizer(configuration);
    }
    
    public void evaluateForced(String path, int i, String selection) throws FileNotFoundException, IOException{
        initialize();

        String filename = "src/readingready/resources/selections/" + selection + "/passage.txt";
        File open = new File(filename);
        FileReader fr = new FileReader(open);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object

        String passage = "";
        String s;
        while((s = br.readLine()) != null){
            passage = passage + s;
        }
        br.close();
        fr.close();
        
        String[] sentences = passage.toLowerCase().split("\\.");

        passage = sentences[i];
        passage = passage.replace(",", "");
        System.out.println("sentence:\n" + passage);
        String iString = String.format("%02d.wav", i);
        if(aligner == null)
            aligner = new SpeechAligner(configuration.getAcousticModelPath(), configuration.getDictionaryPath(), null);
        String strPath = path + "/wavs/" + iString;
        System.out.println(strPath);
        
        File file = new File(strPath);
        List<WordResult> results = aligner.align(file.toURI().toURL(), passage);
        
        makeReport(results, path, true);

   }
    
    public void evaluateNormal(String path, String iString) throws IOException{
        initialize();
        recognizer.startRecognition(new FileInputStream(new File(path + "/wavs/" + iString + ".wav")));
        SpeechResult results = recognizer.getResult();
        recognizer.stopRecognition();
        
        makeReport(results.getWords(), path, false);
    }
    
    public void makeReport(List<WordResult> wordResults, String path, boolean isAligned) throws IOException{
        ArrayList<String> strings = new ArrayList<>();
        String sentence = "";
        strings.add("");
        System.out.print(wordResults.size());
        for(int i = 0 ; i < wordResults.size() ; i++){
          
            if(wordResults.get(i).getWord().getSpelling().equals("<sil>"))
            ;
            else{
                System.out.println(wordResults.get(i).toString());
            String string = wordResults.get(i).getWord().getSpelling()+" "+wordResults.get(i).getTimeFrame().getStart()+" "+wordResults.get(i).getTimeFrame().getEnd()+" "+wordResults.get(i).getScore();
            strings.add(string);
            sentence = sentence + " " + wordResults.get(i).getWord().getSpelling();
            }
        }
        strings.add("");
        strings.set(0, "***"+sentence+"***");
        Path out = (isAligned) ? Paths.get(path + "/resultForced.txt") : Paths.get(path + "/resultNormal.txt");
        
        if(Files.exists(out))
            Files.write(out,strings,StandardOpenOption.APPEND);
        else
            Files.write(out,strings,Charset.defaultCharset());
    }
}
