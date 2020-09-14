<%-- 
    Document   : clientes
    Created on : 5/09/2020, 12:52:09 PM
    Author     : Adrián
--%>

<%@page import="javax.json.stream.JsonParser"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
      <link href="css/heroic-features.css" rel="stylesheet">
      
   </head>
   <body>
      <br>
      <h1>Lista de Clientes</h1>
      <input id="myInput" type="search" onkeyup='searchTable()' class="light-table-filter" data-table="table-info" placeholder="Buscar...">
      <br>
      <table>
         <tr>
            <th>ID CLIENTE</th>
            <th>NOMBRE</th>
            <th>APELLIDO</th>
            <th>NUMERO DE DOCUMENTO</th>
            <th>TIPO_DOCUMENTO</th>
            <th>NACIONALIDAD</th>
            <th>EMAIL</th>
            <th>TELEFONO</th>
            <th>FECHA DE NACIMIENTO</th>
         </tr>
         <%JSONArray jsonArray = (JSONArray)request.getAttribute("clientes");
            if (jsonArray==null){
                out.println("vacio");
            }else{
                for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject row = jsonArray.getJSONObject(i);
            %>
         <tr>
            <td style="text-align:center"><%=row.getInt("id")%></td>
            <td style="text-align:center"><%=row.getString("nombre")%></td>
            <td style="text-align:center"><%=row.getString("apellido")%></td>
            <td style="text-align:center"><%=row.getString("numeroDocumento")%></td>
            <td style="text-align:center"><%=row.getInt("tipoDocumento")%></td>
            <td style="text-align:center"><%=row.getString("nacionalidad")%></td>
            <td style="text-align:center"><%=row.getString("email")%></td>
            <td style="text-align:center"><%=row.getString("telefono")%></td>
            <td style="text-align:center"><%=row.getString("fechaNacimiento")%></td>
         </tr>
         <%
            }
            }
            %>
      </table>
   </body>
</html>
