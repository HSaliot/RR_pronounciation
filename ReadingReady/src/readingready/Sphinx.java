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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    
    public List<WordResult> getWordResults(String wav) throws FileNotFoundException, IOException{
        initialize();
        recognizer.startRecognition(new FileInputStream(new File("src/readingready/resources/"+"aron.wav")));
        SpeechResult results = recognizer.getResult();
        recognizer.stopRecognition();
        
        return results.getWords();
    }
    
    public SpeechResult getSpeechResult(String wav) throws FileNotFoundException, IOException{
        initialize();
        recognizer.startRecognition(new FileInputStream(new File("src/readingready/resources/"+"aron.wav")));
        SpeechResult results = recognizer.getResult();
        recognizer.stopRecognition();
        
        return results;
    }
    
    public List<WordResult> getWordResultsWithAlignment(String wav, String transcript) throws IOException{
        if(aligner == null)
            aligner = new SpeechAligner(configuration.getAcousticModelPath(), configuration.getDictionaryPath(), null);
        return aligner.align(new File(wav).toURI().toURL(), transcript);
    }
    
    public void makeReport(List<WordResult> wordResults, String selection, String studentName) throws IOException{
        ArrayList<String> strings = new ArrayList<>();
        String sentence = "";
        strings.add("");
        for(int i = 0 ; i < wordResults.size() ; i++){
            if(wordResults.get(i).getWord().getSpelling().equals("<sil>"))
            ;
            else{
            String string = wordResults.get(i).getWord().getSpelling()+" "+wordResults.get(i).getTimeFrame().getStart()+" "+wordResults.get(i).getTimeFrame().getEnd()+" "+wordResults.get(i).getScore();
            strings.add(string);
            sentence = sentence + " " + wordResults.get(i).getWord().getSpelling();
            }
        }
        strings.add("");
        strings.set(0, "***"+sentence+"***");
        Path out = Paths.get("src/readingready/resources/"+selection+"_"+studentName+".txt");
        if(Files.exists(out))
            Files.write(out,strings,StandardOpenOption.APPEND);
        else
            Files.write(out,strings,Charset.defaultCharset());
    }
}
