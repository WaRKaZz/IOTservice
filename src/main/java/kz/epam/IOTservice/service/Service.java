package kz.epam.IOTservice.service;


import kz.epam.IOTservice.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface Service {
    void execute (HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException;
}
