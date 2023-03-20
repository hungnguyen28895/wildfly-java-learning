package org.learning.controller;

import org.learning.entity.Account;
import org.learning.services.BankBean;
import org.learning.services.BankService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/bank"})
public class BankController extends HttpServlet {
    @EJB
    BankService bankService = new BankBean();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        var account = (Account) session.getAttribute("account");
        if (account == null) {
            req.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("account", account);
            req.getServletContext().getRequestDispatcher("/WEB-INF/transfer.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var action = req.getParameter("action");
        var loginAccountNo = req.getParameter("loginAccountNo");
        var pinCode = req.getParameter("pinCode");
        var accountNoFrom = req.getParameter("accountNoFrom");
        var accountNoTo = req.getParameter("accountNoTo");
        var amount = req.getParameter("amount");
        var comment = req.getParameter("comment");
        HttpSession session = req.getSession(true);
        if (action.equalsIgnoreCase("login")) {
            var account = bankService.checkLogin(loginAccountNo, pinCode);
            if (account != null) {
                session.setAttribute("account", account);
                req.setAttribute("account", account);
                req.getServletContext().getRequestDispatcher("/WEB-INF/transfer.jsp").forward(req, resp);
            } else {
                req.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
            }
        } else if (action.equalsIgnoreCase("perform")) {
            var result = bankService.transfer(accountNoFrom, accountNoTo, Float.valueOf(amount), comment);
            if (result) {
                if ((Account) session.getAttribute("account") != null) {
                    var account = bankService.getAccount(accountNoFrom);
                    req.setAttribute("account", account);
                    req.getServletContext().getRequestDispatcher("/WEB-INF/transfer.jsp").forward(req, resp);
                } else {
                    req.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
                }
            }
        }
    }
}