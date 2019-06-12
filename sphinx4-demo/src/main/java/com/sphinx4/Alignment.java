package com.sphinx4;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.List;


import edu.cmu.sphinx.api.SpeechAligner;
import edu.cmu.sphinx.util.TimeFrame;
import edu.cmu.sphinx.result.WordResult;
import edu.cmu.sphinx.linguist.acoustic.Unit;


/**
 * This is a simple tool to align audio to text and dump a database
 * for the training/evaluation.
 *
 * You need to provide a model, dictionary, audio and the text to align.
 */
public class Alignment {
	private static final String ACOUSTIC_MODEL_PATH =
            "resource:/edu/cmu/sphinx/models/en-us/en-us";
    private static final String DICTIONARY_PATH =
            "resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict";

    /**
     * @param args acoustic model, dictionary, audio file, text
     */
    public static void main(String args[]) throws Exception {
        // audio and transcript file paths
        File file = new File("aron.wav");
        File transcript_file = new File("transcript.txt");

        // read transcript from file
        BufferedReader transcript_file_reader = new BufferedReader(new FileReader(transcript_file));
        StringBuffer transcript = new StringBuffer();
        String line = null;
        while ((line = transcript_file_reader.readLine()) !=null)
            transcript.append(line).append("\n");

        // perform alignment
        String acousticModelPath =
                (args.length > 2) ? args[2] : ACOUSTIC_MODEL_PATH;
        String dictionaryPath = (args.length > 3) ? args[3] : DICTIONARY_PATH;
        String g2pPath = (args.length > 4) ? args[4] : null;
        SpeechAligner aligner = new SpeechAligner(acousticModelPath, dictionaryPath, g2pPath);
        List<WordResult> results = aligner.align(file.toURI().toURL(), transcript.toString());

        // print out results
        for (WordResult r : results) {
	    	System.out.print(r);
	    	System.out.print("    Score: "+r.getScore());
	    	System.out.println("    Pronunciation : "+r.getPronunciation());
	    }
    }
}
