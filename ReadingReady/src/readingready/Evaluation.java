/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import edu.cmu.sphinx.api.SpeechResult;
import java.util.List;



/**
 *
 * @author Hannah Saliot
 */
public class Evaluation {
    private String student;
    private String selection;
    private List<SpeechResult> evaluatedSentences;
    private String dateRecorded;
    private int ID;
    
    public Evaluation(String student, String selection, List<SpeechResult> evaluatedSentences){
        this.student = student;
        this.selection = selection;
        this.dateRecorded = "06/13/19";
        this.evaluatedSentences = evaluatedSentences;
        this.ID = 0;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public List<SpeechResult> getEvaluatedSentences() {
        return evaluatedSentences;
    }

    public void setEvaluatedSentences(List<SpeechResult> evaluatedSentences) {
        this.evaluatedSentences = evaluatedSentences;
    }

    public String getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(String dateRecorded) {
        this.dateRecorded = dateRecorded;
    }
    public int getID(){
        return ID;
    }
    
    

    
    
    
    
    
    
    
}
