/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready.dao;

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
}
