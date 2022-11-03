package com.ahad.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FetchFile")
public class FileServlet extends HttpServlet {

	private static final long serialVersionUID = 2673128113945913382L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {	
        String filename = request.getParameter("fileName");
        File file = new File("G:/eclips-spring-mvc/travel-vlog-content/"+filename);
        System.out.println("file servlet : "+filename+"locatoin "+file.getPath());
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }

}