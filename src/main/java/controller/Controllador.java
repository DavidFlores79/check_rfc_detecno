package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author David
 */
@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class Controllador extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String rfc = request.getParameter("rfc");
        String resultado = "Error desconocido";
        List miLista = new ArrayList();
        List resultados = new ArrayList();

        // Create path components to save the file
        //final String path = "/java";
        ServletContext servletContext = getServletContext();
        final String path = servletContext.getRealPath("/")+"file-storage";
        System.out.println("path = " + path);
        Part filePart = request.getPart("xlsFile");
        String fileName = filePart.getSubmittedFileName();
        
        File uploads = new File(path); //Carpeta donde se guardan los archivos
        uploads.mkdirs(); //Crea los directorios necesarios
        File fileToSave = new File(path + File.separator + fileName);
        //File file = File.createTempFile("ITSoft-", "-" + fileName, uploads); //Evita que hayan dos archivos con el mismo nombre

        System.out.println("file.toPath() = " + fileToSave.toPath());
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("e = " + e.getMessage());
        } finally {
            OutputStream out = null;
            InputStream filecontent = null;

            try {
                FileInputStream f = new FileInputStream(new File(path + File.separator + fileToSave.getName()));
                XSSFWorkbook libro = new XSSFWorkbook(f);
                XSSFSheet hoja = libro.getSheetAt(0);

                Iterator<Row> filas = hoja.iterator();
                Iterator<Cell> celdas;

                Row fila;
                Cell celda;

                Iterator<Row> filas2 = hoja.iterator();
                Iterator<Cell> celdas2;

                Cell nuevaCelda;
                
                while (filas.hasNext()) {
                    fila = filas.next();
                    Integer numFila = fila.getRowNum();
                    
                    if(numFila != 0 ){
                        System.out.println("Num de Fila = " + numFila);
                        celdas = fila.cellIterator();
                        while (celdas.hasNext()) {
                            celda = celdas.next();
                            if ((celda.getCellType() == Cell.CELL_TYPE_STRING) && (celda.getColumnIndex() == 1)) {

                                miLista.add(celda.getStringCellValue());
                                //enviar consulta para validar RFC
                                Request rq = new Request();
                                resultado = rq.postRequest(celda.getStringCellValue());
                                resultados.add(resultado);
                                System.out.println(celda.getStringCellValue() + " = " + resultado);

                                nuevaCelda = fila.createCell(2);
                                nuevaCelda.setCellValue(resultado);
                            }
                        }
                    }
                }

                f.close();
                //Open FileOutputStream to write updates
                FileOutputStream output_file = new FileOutputStream(new File(path + File.separator + fileToSave.getName()));
                //write changes
                libro.write(output_file);
                //close the stream
                output_file.close();

            } catch (IOException e) {
                System.out.println("Error = " + e.getMessage());
            }
        }

//        try {
//            out = new FileOutputStream(new File(path + File.separator
//                    + fileName));
//            filecontent = filePart.getInputStream();
//
//            int read = 0;
//            final byte[] bytes = new byte[1024];
//
//            while ((read = filecontent.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//            
//        } catch (FileNotFoundException fne) {
//            System.out.println("fne Error= " + fne.getMessage());
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//            if (filecontent != null) {
//                filecontent.close();
//            }
//        }
        request.setAttribute("fileUploaded", "El Archivo " + fileToSave.getName() + " se ha CREADO en la carpeta " + path);
        request.setAttribute("folderName", "file-storage");
        request.setAttribute("fileName", fileToSave.getName());
        request.setAttribute("resultados", resultados);
        request.getRequestDispatcher("views/resultado.jsp").forward(request, response);
    }

}
