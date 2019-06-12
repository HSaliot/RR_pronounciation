package com.sphinx4;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.result.WordResult;

public class TranscriberDemo {       

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        //configuration.setAcousticModelPath("en-us-adapt"); //adapted
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

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