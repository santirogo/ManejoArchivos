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
import modelo.ManejoArchivos;
import modelo.ManejoArchivosCursos;

/**
 *
 * @author ayoro
 */
public class ServletCusos extends HttpServlet {
    private ManejoArchivos ma;
    private ManejoArchivosCursos mac;
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
            this.ma = ManejoArchivos.getManejoArchivos();
            this.mac = ManejoArchivosCursos.getManejoArchivosCursos();
            
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            String nombre = request.getParameter("name");
            int duracion = Integer.parseInt(request.getParameter("duracion"));
            int idProf = Integer.parseInt(request.getParameter("idProf"));
            long posProf;
            if (idProf == 0) {
                posProf = -1;
            }else{
                posProf = this.ma.buscarProfesor(idProf);
            }
            
            this.mac.crearArchivoClase(codigo, nombre, duracion, idProf, posProf);
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletCusos</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Se ha agregado el curso "+nombre+" con el código "+codigo+", duración: "+duracion+" y profesor con ID "+idProf+"</h1>");
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
