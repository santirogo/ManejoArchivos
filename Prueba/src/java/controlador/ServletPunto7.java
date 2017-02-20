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
import modelo.ManejoArchivosCursos;
import modelo.ManejoArchivosEstudiantes;
import modelo.ManejoArchivosInscripciones;

/**
 *
 * @author ayoro
 */
public class ServletPunto7 extends HttpServlet {
    private ManejoArchivosCursos mac;
    private ManejoArchivosInscripciones mai;
    private ManejoArchivosEstudiantes mae;
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
            this.mai = ManejoArchivosInscripciones.getManejoArchivosInscripciones();
            this.mae = ManejoArchivosEstudiantes.getManejoArchivosEstudiantes();
            /* TODO output your page here. You may use following sample code. */
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletPunto7</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<table>");
            
            out.println("<tr>");
            out.println("<td>ID</td>");
            out.println("<td>Nombre</td>");
            out.println("<td>Apellido</td>");
            out.println("<td>Telefono</td>");
            out.println("</tr>");
            
            int cursos [] = this.mac.buscarCursos(33);
            for (int i = 0; i < cursos.length; i++) {
                int estudiantes[] = this.mai.buscarEstudiante(cursos[i]);
                for (int j = 0; j < estudiantes.length; j++) {
                    out.println("<tr>");
                    long posEst = this.mae.buscarEstudiante(estudiantes[j]);
                    out.println("<td>"+this.mae.leerEntero(posEst)+"</td>");
                    char name [] = mae.leerChars(posEst+4);
                    out.print("<td>");
                    for (int k = 0; k < 20; k+=2) {
                        out.print(name[k]);
                    }
                    out.println("</td>");
                    char lastname[] = mae.leerChars(posEst+44);
                    out.print("<td>");
                    for (int k = 0; k < 20; k+=2) {
                        out.print(lastname[k]);
                    }
                    out.println("</td>");
                    out.println("<td>"+mae.leerEntero(posEst+84)+"</td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");
            out.println("<h1>Servlet ServletPunto7 at " + request.getContextPath() + "</h1>");
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
