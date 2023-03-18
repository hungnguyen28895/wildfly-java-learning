package org.learning.controller;

import org.learning.entity.Person;
import org.learning.entity.Phone;
import org.learning.services.EntityService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/person"})
public class PersonController extends HttpServlet {

    @EJB
    EntityService<Person> personEntityService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var action = req.getParameter("action");
        var id = req.getParameter("id");
        personEntityService.setType(Person.class);
        if (action.equalsIgnoreCase("search"))
        {
            redirectToSearchPage(req, resp);
        }
        else if (action.equalsIgnoreCase("update"))
        {
            redirectToUpdatePage(req, resp, id);
        }
        else if (action.equalsIgnoreCase("create"))
        {
            req.getServletContext().getRequestDispatcher("/CreatePerson.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        personEntityService.setType(Person.class);
        var action = req.getParameter("action");
        var name = req.getParameter("name");
        var personId = req.getParameter("id");

        if (action.equalsIgnoreCase("ADD"))
        {
            var phoneNumbers = new ArrayList<>(getPhoneNumberList(req, 2));
            createPerson(name, phoneNumbers);
            redirectToSearchPage(req, resp);
        }
        else if (action.equalsIgnoreCase("update"))
        {
            var phoneNumbers = new ArrayList<>(getPhoneNumberList(req, 2));
            updatePerson(personId, name, phoneNumbers);
            redirectToSearchPage(req, resp);
        }
        else if (action.equalsIgnoreCase("search"))
        {
            searchPerson(req, resp, name);
        }

        else if (action.equalsIgnoreCase("delete"))
        {
            deletePerson(req, resp, Long.valueOf(personId));
        }
    }

    private void searchPerson(HttpServletRequest req, HttpServletResponse resp, String name) throws ServletException, IOException {
        String stringQuery = "SELECT distinct p FROM Person p LEFT JOIN FETCH p.phones WHERE p.name LIKE :name";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "%" + name + "%");

        var persons = personEntityService.getListEntityByQuery(stringQuery, parameters);
        req.setAttribute("persons", persons);
        req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private void createPerson(String name, ArrayList<String> phoneNumbers) {
        Person person = new Person();
        person.setName(name);
        addPhonesToPerson(person, phoneNumbers);

        personEntityService.addEntity(person);
    }

    private void updatePerson(String personId, String name, ArrayList<String> phoneNumbers) {
        Person person = personEntityService.getEntityById(Long.valueOf(personId));
        person.setName(name);
        var phones = new ArrayList<>(person.getPhones());

        for (Phone phone : phones) {
            person.removePhone(phone);
        }
        addPhonesToPerson(person, phoneNumbers);

        personEntityService.updateEntity(person);
    }

    private void deletePerson(HttpServletRequest req, HttpServletResponse resp, Long id) throws ServletException, IOException {
        Person person = personEntityService.getEntityById(id);
        if (person == null) {
            return;
        }

        personEntityService.deleteEntity(id);
        redirectToSearchPage(req, resp);
    }

    private void getAllPerson(HttpServletRequest req) {
        var persons = personEntityService.getAllEntity();
        req.setAttribute("persons", persons);
    }

    private void getPerson(HttpServletRequest req, String id) {
        var person = personEntityService.getEntityById(Long.valueOf(id));
        req.setAttribute("person", person);
    }

    private void redirectToSearchPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getAllPerson(req);
        req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private void redirectToUpdatePage(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        getPerson(req, id);
        req.getServletContext().getRequestDispatcher("/UpdatePerson.jsp").forward(req, resp);
    }

    private void addPhonesToPerson(Person person, ArrayList<String> phoneNumbers) {
        for (String phoneNumber : phoneNumbers) {
            Phone phone = new Phone();
            phone.setNumber(phoneNumber);
            person.addPhone(phone);
        }
    }

    private ArrayList<String> getPhoneNumberList(HttpServletRequest req, int quantity) {
        ArrayList<String> phoneNumbers = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            String number = req.getParameter("number"+i);
            if (!number.isEmpty()) {
                phoneNumbers.add(number);
            }
        }

        return phoneNumbers;
    }
}