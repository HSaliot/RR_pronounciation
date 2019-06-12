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
    
    public void initialize(String acousticModel, String dictionary) throws IOException{
        configuration = new Configuration();
        configuration.setAcousticModelPath((acousticModel == null) ? "resource:/edu/cmu/sphinx/models/en-us/en-us" : acousticModel);
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
    
    public List<WordResult> getWordResults(String wav) throws FileNotFoundException{
        recognizer.startRecognition(new FileInputStream(new File("aron.wav")));
        SpeechResult results = recognizer.getResult();
        recognizer.stopRecognition();
        
        return results.getWords();
    }
    
    public List<WordResult> getWordResultsWithAlignment(String wav, String transcript) throws IOException{
        if(aligner == null)
            aligner = new SpeechAligner(configuration.getAcousticModelPath(), configuration.getDictionaryPath(), null);
        return aligner.align(new File(wav).toURI().toURL(), transcript);
    }
}
