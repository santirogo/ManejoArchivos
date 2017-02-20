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
import modelo.ManejoArchivosCursos;

/**
 *
 * @author ayoro
 */
public class ServletPunto3 extends HttpServlet {
    ManejoArchivosCursos mac;
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
            this.mac = ManejoArchivosCursos.getManejoArchivosCursos();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletPunto3</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Listado de cursos de más de 10 dias que no son de fotografia</h1>");
            out.println("<table>");
            
            out.println("<tr>");
            out.println("<td>Codigo</td>");
            out.println("<td>Nombre</td>");
            out.println("<td>Duracion</td>");
            out.println("<td>ID Profesor</td>");
            out.println("</tr>");
            
            
            int cursos [] = this.mac.buscarCursosExceptoDe("fotografia");
            for (int i = 0; i < cursos.length; i++) {
                long posicionCurso = this.mac.buscarPosicionCursos(cursos[i]);
                out.println("<tr>");
                out.println("<td>"+this.mac.leerEntero(posicionCurso)+"</td>");
                out.print("<td>");
                char name [] = this.mac.leerChars(posicionCurso+4);
                for (int j = 0; j < 20; j++) {
                    out.print(name[j]);
                }
                out.println("</td>");
                out.println("<td>"+this.mac.leerEntero(posicionCurso+44)+"</td>");
                out.println("<td>"+this.mac.leerEntero(posicionCurso+48)+"</td>");
                out.println("</tr>");
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
