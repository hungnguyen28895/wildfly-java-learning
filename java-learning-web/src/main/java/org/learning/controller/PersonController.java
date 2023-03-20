package org.learning.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
        if (action == null)
        {
            getAllPerson(req);
            redirectToSearchPage(req, resp);
        }
        else if (action.equalsIgnoreCase("update"))
        {
            getPerson(resp, id);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        personEntityService.setType(Person.class);
        var action = req.getParameter("action");
        var name = req.getParameter("name");
        var personId = req.getParameter("id");

        if (action.equalsIgnoreCase("add"))
        {
            var phoneNumbers = new ArrayList<>(getPhoneNumberList(req, 2));
            createPerson(name, phoneNumbers);
            getAllPerson(req);
        }
        else if (action.equalsIgnoreCase("update"))
        {
            var phoneNumbers = new ArrayList<>(getPhoneNumberList(req, 2));
            updatePerson(personId, name, phoneNumbers);
            getAllPerson(req);
        }
        else if (action.equalsIgnoreCase("search"))
        {
            searchPerson(req, name);
        }
        else if (action.equalsIgnoreCase("delete"))
        {
            deletePerson(Long.valueOf(personId));
            getAllPerson(req);
        }
        redirectToSearchPage(req, resp);
    }

    private void searchPerson(HttpServletRequest req, String name) {
        String stringQuery = "SELECT distinct p FROM Person p LEFT JOIN FETCH p.phones WHERE p.name LIKE :name";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "%" + name + "%");

        var persons = personEntityService.getListEntityByQuery(stringQuery, parameters);
        req.setAttribute("persons", persons);
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

    private void deletePerson(Long id) {
        Person person = personEntityService.getEntityById(id);
        if (person == null) {
            return;
        }

        personEntityService.deleteEntity(id);
    }

    private void getAllPerson(HttpServletRequest req) {
        var persons = personEntityService.getAllEntity();
        req.setAttribute("persons", persons);
    }

    private void getPerson(HttpServletResponse resp, String id) throws IOException {
        var person = personEntityService.getEntityById(Long.valueOf(id));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(person);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    private void redirectToSearchPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/WEB-INF/SearchPerson.jsp").forward(req, resp);
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