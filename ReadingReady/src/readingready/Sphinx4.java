/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.result.WordResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Lorenz Canuto
 * @author Hannah Saliot
 */
public class Sphinx4{
    private Configuration configuration;
    private StreamSpeechRecognizer recognizer;
    private InputStream stream;
    
    public void initialize(String acousticModel, String dictionary) throws IOException{
        configuration = new Configuration();
        configuration.setAcousticModelPath((acousticModel == null) ? "resource:/edu/cmu/sphinx/models/en-us/en-us" : acousticModel);
        //configuration.setAcousticModelPath("en-us-adapt"); //adapted
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
    }
    
    public void setAcousticModel(String path){
        configuration.setAcousticModelPath(path);
    }
    
    
        
        
	StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
	InputStream stream = new FileInputStream(new File("aron.wav"));

        recognizer.startRecognition(stream);
	SpeechResult result;
	
    while ((result = recognizer.getResult()) != null) {
	    System.out.format("Hypothesis: %s\n", result.getHypothesis());

	    System.out.println("List of recognized words and their times:");
	    for (WordResult r : result.getWords()) {
	    	System.out.print(r);
	    	System.out.print("    Score: "+r.getScore());
	    	System.out.println("    Pronunciation : "+r.getPronunciation());
	    }
	}
	recognizer.stopRecognition();
    }
    
}
