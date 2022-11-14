<%-- 
    Document   : resultado
    Created on : 11/02/2022, 10:36:23 AM
    Author     : David
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultado</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container pt-4 col-md-4">
            <h1 class="text-center">Resultado - Descargar Archivo</h1>
            <!--<p>Soy uno de los resultados ${resultados}</p>-->
            <!--<p>${fileUploaded}</p>-->
        </div>
        <div class="container pt-4 col-md-4">
            <p>Puede descargar su archivo en el siguiente link <a href="${pageContext.request.contextPath}/${folderName}/${fileName}">${fileName}</a></p>
        </div>
        
        
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.min.js"></script>
    </body>
</html>
