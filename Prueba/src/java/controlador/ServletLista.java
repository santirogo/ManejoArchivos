package controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ManejoArchivos;
import static modelo.ManejoArchivos.getManejoArchivos;

/**
 *
 * @author ayoro
 */
public class ServletLista extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            long cont = 292;
            ManejoArchivos ma = ManejoArchivos.getManejoArchivos();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletLista</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table>");

            out.println("<tr>");
            out.println("<td>ID</td>");
            out.println("<td>Nombre</td>");
            out.println("<td>Apellido</td>");
            out.println("<td>Ext</td>");
            out.println("</tr>");

            if (ma.getTamanoLista() > 0) {
                for (int i = 292; i < ma.getTamanoLista(); i = i + 88) {
                    out.println("<tr>");
                    out.println("<td>" + ma.leerEntero(cont) + "</td>");
                    cont += 4;
                    char name[] = ma.leerChars(cont);
                    out.println("<td>");
                    for (int j = 0; j < 20; j++) {
                        out.print(name[j]);
                    }
                    out.println("</td>");
                    cont += 40;
                    char lastname[] = ma.leerChars(cont);
                    out.println("<td>");
                    for (int j = 0; j < lastname.length; j++) {
                        out.print(lastname[j]);
                    }
                    out.println("</td>");
                    cont += 40;
                    out.println("<td>" + ma.leerEntero(cont) + "</td>");
                    cont += 4;
                    out.println("</tr>");
                }
            }

            out.println("</table>");
            out.println("<h1>Servlet lista HOLAAAA</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
