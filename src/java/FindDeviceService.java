/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Bean.Email;
import Bean.UserTracking;
import Services.GMapService;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilities.ImageHelper;
import utilities.SendMailHelper;

/**
 *
 * @author Admin
 */
@WebServlet(urlPatterns = {"/FindDevice"})
public class FindDeviceService extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Hello</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Hello at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        String name = request.getParameter("name");
        response.getWriter().append("Hello v3.1 : " + name);

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

        if (request.getParameter("EmailAddress").isEmpty() == false
                && request.getParameter("LocationX").isEmpty() == false
                && request.getParameter("LocationY").isEmpty() == false) {

            UserTracking us = new UserTracking();
            us.setEmailAddress(request.getParameter("EmailAddress"));
            us.setLocationX(request.getParameter("LocationX"));
            us.setLocationY(request.getParameter("LocationY"));
            
            if(request.getParameter("Size")!= null && request.getParameter("Size").isEmpty() == false){
                us.setSize(Integer.parseInt(request.getParameter("Size")));
            }
            if(request.getParameter("Zoom")!= null &&request.getParameter("Zoom").isEmpty() == false){
                us.setZoom(Integer.parseInt(request.getParameter("Zoom")));
            }
            
            Email emi = new Email(us.getEmailAddress());
            emi.setPathFolderAttachImage(getServletContext().getResource("WEB-INF/download/").getPath());

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            try {
                // Download Image:
                String pathSaveFile = emi.getPathFolderAttachImage() + emi.getFileNameAttachment();
                ImageHelper.downloadImage(GMapService.getUrl(us), pathSaveFile);
                // Send to Email:
                if (new File(emi.getPathFolderAttachImage() + emi.getFileNameAttachment()).exists()) {
                    new SendMailHelper().SendMailToFindDevice(emi);
                }
                
                // Delete file after sendmail:
                ImageHelper.deleteImage(pathSaveFile);
                out.print(us);
            } catch (Exception ex) {
                out.print("{\"Error\":\"" + ex.getMessage() + "\"}");
            }

            out.flush();

        }
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
