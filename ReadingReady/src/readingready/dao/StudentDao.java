/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready.dao;

import java.util.List;
import readingready.Student;

/**
 *
 * @author Hannah Saliot
 */
public class StudentDao extends Dao<Student> {
    public List<Student> findByLName(String lName) {
        return getEM().createNamedQuery("Student.findByLName", Student.class)
                .setParameter("lName", lName)
                .getResultList();
    }
    
    public List<Student> findByLevel(int level) {
        return getEM().createNamedQuery("Student.findByLevel", Student.class)
                .setParameter("level", level)
                .getResultList();
    }
}
