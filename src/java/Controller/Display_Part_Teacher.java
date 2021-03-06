/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ChapDB;
import DAO.CourseDB;
import DAO.ExcerciseDB;
import DAO.PartDB;
import Model.Chap;
import Model.Course;
import Model.Excercise;
import Model.Part;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ad
 */
@WebServlet(name = "Display_Part_Teacher", urlPatterns = {"/Display_Part_Teacher"})
public class Display_Part_Teacher extends HttpServlet {

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
        
        Part part = null;
        int courseid = -1;
        int chaporder = -1;
        int partorder = -1;

        String url = "/Views/Pages/Course/Part_Teacher.jsp";

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");

//        String requirement = request.getParameter("requirement");
         if (user == null ) {
            url = "/sign-in";
        }
        else if(user.getRole().getRoleId()!=2)
        {
             url = "/Views/Pages/Home/home.jsp";
        } else {
                try {
                    courseid = Integer.parseInt(request.getParameter("courseid"));
                    chaporder = Integer.parseInt(request.getParameter("chapid"));
                    partorder = Integer.parseInt(request.getParameter("partid"));
                } catch (Exception ex) {
                     request.setAttribute("message", "Kh??ng t??m th???y Ch????ng ????? th??m b??i h???c!");
                }

                Course course = CourseDB.GetCourseByCourseId(courseid);
                Chap chap = ChapDB.getChapOfCourseByOrder(course, chaporder);
                part = PartDB.getPartByCourseAndChap(course, chap, partorder);
                
                        
                if (part == null) {
                    request.setAttribute("message", "Kh??ng t??m th???y Ch????ng ????? th??m b??i h???c!");
                    url= "/"+ (String)request.getParameter("previousPage");
                } 
                else if(!CourseDB.courseOfTeacherExists(course.getCourseId(), user))
                {
                     request.setAttribute("message", "B???n kh??ng c?? kh??a h???c n??y!");
                     url="/teacher";
                     
                }else {

                    session.setAttribute("part", part);
                }
             
            }
        getServletContext().getRequestDispatcher(url).forward(request, response);
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
