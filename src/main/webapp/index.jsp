<%-- 
    Document   : index
    Created on : 11/02/2022, 10:30:11 AM
    Author     : David
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Validar RFC</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container pt-4 col-md-4">
            <h1 class="text-center">Validación de RFC en SAT</h1>
            <form action="upload" method="POST" enctype="multipart/form-data">
                <div class="row">
                    <div class="col">
                        <form-group>
                            <label for="re">RFC</label>
                            <input type="text" class="form-control" name="rfc" value="LSO1306189R5">
                        </form-group>
                        <div class="form-group">
                            <label for="xlsFile">Cargar el Archivo XLS</label>
                            <input type="file" class="form-control-file" name="xlsFile" id="xlsFile" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col p-3">
                        <form-group>
                            <button type="submit" class="btn btn-success btn-block">Validar</button>
                        </form-group>
                    </div>
                </div>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.min.js"></script>
    </body>
</html>
