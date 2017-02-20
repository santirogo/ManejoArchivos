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
public class ServletPunto2 extends HttpServlet {
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletPunto2</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Datos del estudiante 1518</h1>");
            System.out.println("va a entrar");
            long posEstudiante = this.mae.buscarEstudiante(1518);
            System.out.println("ya pasó");
            out.println("<table>");
            
            out.println("<tr>");
            out.println("<td>ID</td>");
            out.println("<td>Nombre</td>");
            out.println("<td>Apellido</td>");
            out.println("<td>Teléfono</td>");
            out.println("</tr>");
            
            out.println("<tr>");
            out.println("<td>"+this.mae.leerEntero(posEstudiante)+"</td>");
            char nombre [] = this.mae.leerChars(posEstudiante+4);
            out.print("<td>");
            for (int i = 0; i < nombre.length; i++) {
                out.print(nombre[i]);
            }
            out.println("</td>");
            char apellido [] = this.mae.leerChars(posEstudiante+44);
            out.print("<td>");
            for (int i = 0; i < nombre.length; i++) {
                out.print(apellido[i]);
            }
            out.println("</td>");
            out.println("<td>"+this.mae.leerEntero(posEstudiante+84)+"</td>");
            out.println("</tr>");
            
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
