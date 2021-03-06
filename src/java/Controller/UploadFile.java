/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.InstructorDB;
import DAO.PartDB;
import DAO.PartFilesDB;
import Model.Instructor;
import Model.PartFiles;
import Model.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author ad
 */
@MultipartConfig
@WebServlet(name = "UploadFile", urlPatterns = {"/UploadFile"})
//@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//        maxFileSize = 1024 * 1024 * 1000, // 1GB
//        maxRequestSize = 1024 * 1024 * 1000) // 1GB
public class UploadFile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;
//        HttpSession session;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

//                Part filePart = request.getPart("file");//Textbox value of name file.
//                String fileName_test = filePart.getSubmittedFileName();
        HttpSession session = request.getSession();
        Model.Part partname = null;
        Model.PartFiles partFiles = null;
        String url = "/teacher";

        User user = (User) session.getAttribute("User");

        String message = "";
//        String requirement = request.getParameter("requirement");
        if (user == null) {
            url = "/sign-in";
        } else if (user.getRole().getRoleId() != 2) {
            url = "/Views/Pages/Home/home.jsp";
        } else {
//            try {
//            int partid = Integer.parseInt((String)request.getParameter("partid"));
            int partid = Integer.parseInt(session.getAttribute("partid").toString());
            partname = PartDB.GetPrtByPartId(partid);
            int reset = 0;
            while (reset == 0 )
            {
                partFiles =  PartFilesDB.getPartFilesByPart(partname);
                if(partFiles != null)
                {
                    try{
                        if(!PartFilesDB.deletePartFiles(partname))
                            message = "L???i x??a th??ng tin file";
                    }
                    catch(Exception ex){
                    }
                }
                else{
                    reset = 1;
                }
            }
            
            

            if (partname == null) {
                message = "Kh??ng c?? ph???n b??i h???c";
                url = "/teacher";
//            } else if (request.getParts() == null) {
//                request.setAttribute("message", "Ch??a ch???n File!!!");
//                request.setAttribute("part", part);
//                getServletContext().getRequestDispatcher(url).forward(request, response);

            } else {
                
                 /*javax.servlet.http.Part doc = request.getPart("document");                 
                
                  String docPath=""; 
                    if (doc != null ) {
                        String fileNameDoc = extractFileName(doc);
                        //????? ko b??? l???i null v???i file
                        try {
                            String applicationPath = getServletContext().getRealPath("");
                            String uploadPath = applicationPath + File.separator + "TestUpload";
                            System.out.println("applicationPath:" + applicationPath);
                            File fileUploadDirectory = new File(uploadPath);
                            if (!fileUploadDirectory.exists()) {
                                fileUploadDirectory.mkdirs();
                            }

                            String savePath = uploadPath + File.separator + fileNameDoc;
                            
//                        message = "aaa" + savePath;
                            String sRootPath = new File(savePath).getAbsolutePath();
                          
                           
                            doc.write(savePath + File.separator);

//                        File fileSaveDir1 = new File(savePath);
                            //path of the image
                           docPath= fileNameDoc;
                           session.setAttribute("fileNameDoc", docPath);
                            partname.setDocument(docPath);
                       
                            if (!PartDB.updatePart(partname)) {
                                message = "L??u t??i li???u kh??ng th??nh c??ng";
                            }
                        } catch (Exception ex) {
                           
                        }  
                       
                     
                    }*/
                    javax.servlet.http.Part video = request.getPart("video");                 
                
                    String videoPart=""; 
                    if (video != null ) {
                        String fileName = extractFileName(video);
                        //????? ko b??? l???i null v???i file
                        try {
                            String applicationPath = getServletContext().getRealPath("");
                            String uploadPath = applicationPath + File.separator + "TestUpload";
                            System.out.println("applicationPath:" + applicationPath);
                            File fileUploadDirectory = new File(uploadPath);
                            if (!fileUploadDirectory.exists()) {
                                fileUploadDirectory.mkdirs();
                            }

                            String savePath = uploadPath + File.separator + fileName;
                            
//                        message = "aaa" + savePath;
                            String sRootPath = new File(savePath).getAbsolutePath();
                          
                           
                            video.write(savePath + File.separator);

//                        File fileSaveDir1 = new File(savePath);
                            //path of the image
                           videoPart=  fileName;
                           session.setAttribute("fileNameVideo", videoPart);
                            partname.setVideo(videoPart);
                       
                            if (!PartDB.updatePart(partname)) {
                                message = "L??u t??i li???u video kh??ng th??nh c??ng";
                            }
                             String uploadFile = request.getServletContext().getRealPath("") + File.separator + "TestUpload";//....WebApplication3\build\web\TestUpload
                            for (Part p : request.getParts()) {
    //                            try{
                                  String filePart = extractFileName(p);
                                  filePart = new File(filePart).getName();
                                  session.setAttribute("document", filePart); 
                                  if(!filePart.equals(videoPart)){
                                  p.write(this.getFolderUpload(uploadFile).getAbsolutePath() + File.separator + filePart);
                                  PartFiles partfiles = new PartFiles(partname, filePart);
                                  if(!PartFilesDB.insertPartFiles(partfiles))
                                  {
                                      message = "L???i th??m";
                                  }
                                  }
//                            }
//                            catch (Exception ex) {
//                                message = "Th??m file b??i h???c kh??ng th??nh c??ng.";
//                        }
                        }
                             url = "/Display_Course_Introduction_Teacher?courseid="+partname.getCourse().getCourseId();
                        } catch (Exception ex) {

                        }
                     
                    }
                    
                    
                 
//                String uploadFile = request.getServletContext().getRealPath("") + File.separator + "TestUpload";//....WebApplication3\build\web\TestUpload
//               
////                try {
//
//                    for (Part p : request.getParts()) {
//
//                        String fileName = extractFileName(p);
//                        // refines the fileName in case it is an absolute path
//                        fileName = new File(fileName).getName();
//                        session.setAttribute("fileName", fileName);
//                        String pt = this.getFolderUpload(uploadFile).getAbsolutePath() + File.separator + fileName;
//
//                        p.write(pt); 
//                        
////                        part.setDocument(docPath);
//                        part.setVideo(fileName);
//                        if (!PartDB.updatePart(part)) {
//                            message = "L??u t??i li???u kh??ng th??nh c??ng";
//                        }
//                        countFile++;
//                    }
//                
////                } catch (Exception ex) {
////                    message = "Ch??a ch???n file b??i h???c.";
////
////                }
//                request.setAttribute("countFile", countFile);

            }
//            }
//            catch(Exception ex)
//            {
//                message="L??u th???t b???i!";
//            }

        }
        if (message.equals("")) {
            message = "L??u th??nh c??ng";
        }
        request.setAttribute("message", message);
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }


    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";

    }

    public File getFolderUpload(String UploadFile) {
        File folderUpload = new File(UploadFile);
        //File folderUpload = new File("E:\\L??p Tr??nh WEB\\TestUpload" + "/Uploads" );//File(System.getProperty("user.dir") + "/Uploads");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }

    private void white(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
