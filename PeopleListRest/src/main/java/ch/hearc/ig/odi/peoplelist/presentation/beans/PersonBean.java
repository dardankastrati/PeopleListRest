package ch.hearc.ig.odi.peoplelist.presentation.beans;

import ch.hearc.ig.odi.peoplelist.business.Person;
import ch.hearc.ig.odi.peoplelist.service.Services;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
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

    @Inject
    Services services;
    private List<Person> people;
    private Person currentPerson;
    private int currentPersonID;

    public PersonBean() {
        people = new ArrayList<>();
        currentPerson = new Person();
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
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

//    @NotNull
//    public List getPeopleList() {
//        return services.getPeopleList();
//    }
    /**
     * Retrieves the person object corresponding to the request's parameter id
     *
     */
    public void initPerson() {
        try {
            String idParam = FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap().get("id");
            if (!(idParam == null || idParam.isEmpty())) {
                currentPersonID = Integer.parseInt(idParam);
                currentPerson = services.getPerson(new Long(currentPersonID));

                if (currentPerson == null) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("list.xhtml");
                }

            } else {
                currentPerson = new Person();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initPeople() {
        this.people = services.getPeopleList();
    }

    /**
     * update current editing person
     *
     * @return link to redirect in list page
     */
    public String update() throws ParseException {
        Map<String, String> requestParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = requestParameter.get("updateId");
        String gender = requestParameter.get("form_upd:gender");
        String firstName = requestParameter.get("form_upd:firstName");
        String lastName = requestParameter.get("form_upd:lastName");
        String married = requestParameter.get("form_upd:married");
        String birthDay = requestParameter.get("form_upd:birthDay");

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        Person p = services.getPerson(Long.parseLong(id));
        p.setGender(gender);
        p.setFirstName(firstName);
        p.setLastName(lastName);
        if (married.equals("true")) {
            p.setMarried(true);
        } else {
            p.setMarried(false);
        }
        p.setBirthDate(sdf.parse(birthDay));

        return "list.xhtml?faces-redirect=true";
    }

    /**
     * Save current editing person
     *
     * @return link to redirect in list page
     */
    public String save() throws ParseException {
        Map<String, String> requestParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String gender = requestParameter.get("form_add:gender");
        String firstName = requestParameter.get("form_add:firstName");
        String lastName = requestParameter.get("form_add:lastName");
        String married = requestParameter.get("form_add:married");
        String birthDay = requestParameter.get("form_add:birthDay");
        
        boolean marriedBool = false;
        if (married.equals("true")) {
            marriedBool = true;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        
        services.savePerson(gender, firstName, lastName, marriedBool, sdf.parse(birthDay));
        return "list.xhtml?faces-redirect=true";
    }

    /**
     * Delete person by id
     *
     * @return link to redirect in list page
     */
    public String delete() {
        String idParam = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap().get("deleteId");
        Person perDel = services.getPerson(Long.parseLong(idParam));
        services.deletePerson(perDel);
        return "list.xhtml?faces-redirect=true";
    }
}
