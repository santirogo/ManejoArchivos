/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ManejoArchivosEstudiantes;

/**
 *
 * @author ayoro
 */
public class ServletPunto9 extends HttpServlet {
    ManejoArchivosEstudiantes mae;
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
            this.mae = ManejoArchivosEstudiantes.getManejoArchivosEstudiantes();
            long cont = 292;
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletPunto9</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Listado de los estudiantes</h1>");
            out.println("<table>");
            
            out.println("<tr>");
            out.println("<td>ID</td>");
            out.println("<td>Nombre</td>");
            out.println("<td>Apellido</td>");
            out.println("<td>Teléfono</td>");
            out.println("</tr>");
            if (mae.getTamanoLista() > 0) {
                for (int i = 292; i < mae.getTamanoLista(); i+=88) {
                    out.println("<tr>");
                    out.println("<td>"+this.mae.leerEntero(cont)+"</td>");
                    cont += 4;
                    char nombre [] = this.mae.leerChars(cont);
                    out.print("<td>");
                    for (int j = 0; j < nombre.length; j++) {
                        out.print(nombre[j]);
                    }
                    out.println("</td>");
                    cont += 40;
                    char apellido [] = this.mae.leerChars(cont);
                    out.print("<td>");
                    for (int j = 0; j < nombre.length; j++) {
                        out.print(apellido[j]);
                    }
                    out.println("</td>");
                    cont += 40;
                    out.println("<td>"+this.mae.leerEntero(cont)+"</td>");
                    cont += 4;
                    out.println("</tr>");
                }
            }
            
            out.println("</table>");
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
