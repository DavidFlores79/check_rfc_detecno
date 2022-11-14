/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author David
 */
public class Request {

    public String postRequest(String rfc) throws IOException {
        
        String resultado = null;
        
        try {

            StringBuilder sb = new StringBuilder();

            URL url = new URL("https://solucionfactible.com/sfic/capitulos/general/validadorRFC.jsp?RFC_FIELD="+rfc+"&SOURCE=textType");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String datos = "";
            connection.setRequestProperty("Content-Type", "text/xml;charset=\"utf-8\"");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Accept", "text/xml");
            connection.setDoOutput(true);

            OutputStream output = connection.getOutputStream();

            output.write(datos.getBytes());
            output.flush();
            output.close();

            BufferedReader br = null;
            if (100 <= connection.getResponseCode() && connection.getResponseCode() <= 399) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8) );
                String strCurrentLine;
                while ((strCurrentLine = br.readLine()) != null) {
                    if(strCurrentLine.contains("<div id=\"columna2\"><span>") && strCurrentLine.contains("Sí")){
                        //System.out.println(strCurrentLine);
                        //resultado = "RFC "+ rfc +" es valido";
                        resultado = "VALIDO";
                    } else if (strCurrentLine.contains("<div id=\"columna2\"><span>") && strCurrentLine.contains("No")){
                        //System.out.println(strCurrentLine);
                        //resultado = "RFC "+ rfc +" NO es valido";
                        resultado = "NO VALIDO";
                    }
                }
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            //System.out.println("Estatus code:" + connection.getResponseCode());
            //System.out.println("Response: " + connection.getResponseMessage());

        } catch (MalformedURLException ex) {
            Logger.getLogger(Controllador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
    }

}
