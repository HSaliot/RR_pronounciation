/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready.dao;

import readingready.Evaluation;

/**
 *
 * @author Hannah Saliot
 */
public class EvaluationDao extends Dao<Evaluation>{
    public Evaluation findNewest(){
        return getEM().createNamedQuery("Evaluation.findMostRecent", Evaluation.class).getResultList().get(0);
    }
}
