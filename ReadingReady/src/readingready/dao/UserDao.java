/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready.dao;

import javax.persistence.NoResultException;
import readingready.User;

/**
 *
 * @author Hannah Saliot
 */
public class UserDao extends Dao<User>{
    public User findByUName(String uName){
        return getEM().createNamedQuery("User.findByUName", User.class)
                .setParameter("uName", uName)
                .getSingleResult();
    }
    
    public User findAndAuthenticate(String uName, String password){
        try {
            User user = findByUName(uName);
            if(password.equals(user.getPassword()))
                return user;
            else
                return null;
        } catch (NoResultException ex) {
            return null;
        }
    }
}
