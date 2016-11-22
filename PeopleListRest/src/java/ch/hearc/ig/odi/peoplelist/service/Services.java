package ch.hearc.ig.odi.peoplelist.service;

import ch.hearc.ig.odi.peoplelist.business.Person;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

/**
 *
 * @author julien.plumez
 */
@SessionScoped
@Stateful	// Chaque instance sera diff�rente des autres
@Named(value = "services")
public class Services implements Serializable{
    
    // Permet de stocker la liste de personnes de telle mani�re � simuler une base de donn�es
    private Map<Long, Person> people;
    
    // Compteur permettant d'avoir des IDs uniques lors de l'ajout de nouvelles personnes
    private Long currentMaxId = 1l;
    
    public Services(){
        // Cr�e 6 personnes � chaque cr�ation de session pour avoir une base sur laquelle travailler
        try {
            people = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            
            Person currentPerson = new Person();
            currentPerson.setId(1l);
            currentPerson.setGender("male");
            currentPerson.setFirstName("Harvey");
            currentPerson.setLastName("Specter");
            currentPerson.setBirthDate(sdf.parse("22.01.1972"));
            currentPerson.setMarried(false);
            people.put(1l, currentPerson);
            
            currentPerson = new Person();
            currentPerson.setId(2l);
            currentPerson.setGender("male");
            currentPerson.setFirstName("Michael");
            currentPerson.setLastName("Ross");
            currentPerson.setBirthDate(sdf.parse("27.08.1981"));
            currentPerson.setMarried(true);
            people.put(2l, currentPerson);
            
            currentPerson = new Person();
            currentPerson.setId(3l);
            currentPerson.setGender("male");
            currentPerson.setFirstName("Louis");
            currentPerson.setLastName("Litt");
            currentPerson.setBirthDate(sdf.parse("12.06.1970"));
            currentPerson.setMarried(false);
            people.put(3l, currentPerson);
            
            currentPerson = new Person();
            currentPerson.setId(4l);
            currentPerson.setGender("female");
            currentPerson.setFirstName("Rachel");
            currentPerson.setLastName("Zane");
            currentPerson.setBirthDate(sdf.parse("04.08.1981"));
            currentPerson.setMarried(true);
            people.put(4l, currentPerson);
            
            currentPerson = new Person();
            currentPerson.setId(5l);
            currentPerson.setGender("female");
            currentPerson.setFirstName("Donna");
            currentPerson.setLastName("Paulsen");
            currentPerson.setBirthDate(sdf.parse("06.12.1972"));
            currentPerson.setMarried(false);
            people.put(5l, currentPerson);
            
            currentPerson = new Person();
            currentPerson.setId(6l);
            currentPerson.setGender("female");
            currentPerson.setFirstName("Jessica");
            currentPerson.setLastName("Pearson");
            currentPerson.setBirthDate(sdf.parse("25.04.1969"));
            currentPerson.setMarried(false);
            people.put(6l, currentPerson);
            
            currentMaxId = 6l;
        } catch (ParseException ex) {
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Sauvegarde une nouvelle personne dans la liste, et retourne l'id de ladite nouvelle personne
     * @param gender Genre de la personne
     * @param firstName Pr�nom de la personne
     * @param lastName Nom de la personne
     * @param married La personne est-elle mari�e ?
     * @param birthDate Date de naissance de la personne
     * @return L'ID de la nouvelle personne
     */
    @NotNull
    public Long savePerson(String gender, String firstName, String lastName, Boolean married, Date birthDate){
        Person person = new Person();
        
        currentMaxId++;
        
        person.setId(currentMaxId);
        person.setGender(gender);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthDate(birthDate);
        person.setMarried(married);
        
        people.put(currentMaxId, person);
        
        return currentMaxId;
    }
    
    public void deletePerson(Person person){
        people.remove(person.getId());
    }
    
    @NotNull
    public Person getPerson(Long id){
        return people.get(id);
    }
    
    @NotNull
    public Map<Long, Person> getPeople(){
        return people;
    }
    
    @NotNull
    public List<Person> getPeopleList(){
        return new ArrayList(people.values());
    }
}
