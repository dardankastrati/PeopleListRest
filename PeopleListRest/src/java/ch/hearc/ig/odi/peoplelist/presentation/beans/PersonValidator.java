/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.odi.peoplelist.presentation.beans;

import ch.hearc.ig.odi.peoplelist.business.Person;
import ch.hearc.ig.odi.peoplelist.service.Services;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

/**
 *
 * @author dardan.kastrati
 */
@Named(value = "personValidator")
@SessionScoped
public class PersonValidator implements Serializable {

    @Inject Services services;
    
    /**
     * Creates a new instance of PersonValidator
     */
    public PersonValidator() {
    }
    
    @AssertTrue
    public boolean personNotExist(Person newPerson){
        boolean personNotExist = true;
        List<Person> listPerson = new ArrayList<>();
        listPerson = services.getPeopleList();
        
        for(Person p : listPerson){
            if(p.getFirstName().equals(newPerson.getFirstName())){
                if(p.getLastName().equals(newPerson.getLastName())){
                    if(p.getBirthDate().equals(newPerson.getBirthDate())){
                        personNotExist = false;
                    }
                }
            }
        }
        
        return personNotExist;
    }
}
