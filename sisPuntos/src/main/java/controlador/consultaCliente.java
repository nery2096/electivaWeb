/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import REST.clienteREST;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import utils.funciones;

/**
 *
 * @author Adrián
 */
public class consultaCliente extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String nombre=request.getParameter("nombre");
        String apellido=request.getParameter("apellido");
        String bday=request.getParameter("bday");
        funciones f = new funciones();
        if(nombre!=null){
            JSONArray jsonArray;
            clienteREST c = new clienteREST();
            jsonArray = new JSONArray(c.findAll(String.class));
            jsonArray=f.returnSearch(jsonArray,nombre);
            request.setAttribute("clientes",f.returnSearch(jsonArray,nombre));
            try (PrintWriter out = response.getWriter()) {
                out.println(jsonArray);
            }
        }else{
            JSONArray jsonArray;
            clienteREST c = new clienteREST();
            jsonArray = new JSONArray(c.findAll(String.class));
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println(jsonArray);
            }
            /*request.setAttribute("clientes",jsonArray);
            RequestDispatcher rd;
            rd = request.getRequestDispatcher("clientes.jsp");
            if(rd!=null){
                rd.forward(request, response);*/
            }
        }
    
    
    
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
