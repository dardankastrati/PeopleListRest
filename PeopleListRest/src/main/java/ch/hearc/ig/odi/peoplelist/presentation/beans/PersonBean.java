
package ch.hearc.ig.odi.peoplelist.presentation.beans;

import ch.hearc.ig.odi.peoplelist.business.Person;
import ch.hearc.ig.odi.peoplelist.service.Services;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dardan.kastrati
 */
@Named("personBean")
@ViewScoped
public class PersonBean implements Serializable {

    @Inject Services services;
    private Map<Long, Person> people;
    private Person currentPerson;
    private int currentPersonID;
    
    public PersonBean() {
    }

    public Map<Long, Person> getPeople() {
        return people;
    }

    public void setPeople(Map<Long, Person> people) {
        this.people = people;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }

    public int getCurrentPersonID() {
        return currentPersonID;
    }

    public void setCurrentPersonID(int currentPersonID) {
        this.currentPersonID = currentPersonID;
    }
    
    @NotNull
    public List getPeopleList() {
        return services.getPeopleList();
    }
    
    /**
     * Retrieves the person object corresponding to the request's parameter id
     *
     */
    public void initPerson() {
        String idParam = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap().get("id");
        if (!(idParam == null || idParam.isEmpty())) {
            currentPersonID = Integer.parseInt(idParam);
            currentPerson = services.getPerson(new Long(currentPersonID));
        }
    }
    
    /**
     * update current editing person
     * @param Person person to update
     * @return link to redirect in list page
     */
    public String update(Person person) {
        Person p = services.getPerson(person.getId());
        p = currentPerson;
        return "list.xhtml?faces-redirect=true";
    }
    
    /**
     * Save current editing person
     * @param Person person to save
     * @return link to redirect in list page
     */
    public String save(Person person) {
        services.savePerson(person.getGender(), person.getFirstName(), person.getLastName(), person.isMarried(), person.getBirthDate());
        return "list.xhtml?faces-redirect=true";
    }
    
    /**
     * Delete person
     * @param Person person to delete
     * @return link to redirect into list page
     */
    public String delete(Person person){
        services.deletePerson(person);
        return "list.xhtml?faces-redirect=true";
    }
}
