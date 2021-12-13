<%-- 
    Document   : index
    Created on : 13 de dez. de 2021, 17:19:33
    Author     : danto
--%>

<%@page import="db.Access"%>
<%@page import="java.util.ArrayList"%>
<% 
    ArrayList<Access> acessos = new ArrayList<Access>();
    Exception erros = null;
    try{
        acessos = Access.getMyAccess((String)session.getAttribute("me.username"));
    }
    catch(Exception ex){
        erros = ex;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% if(erros != null) { %>
            <div>Erro nos meus acessos <%= erros.getMessage() %></div>
        <% } %>
        <% if(session.getAttribute("me.username") != null){ %>
        <table>
            <% for (Access acesso: acessos){ 
            %>
            <tr>
                <td><%= acesso.getUsername() %></td>
                <td><%= acesso.getDatetime() %></td>
            </tr>
            <% } %>
        </table>
        <% } else{ %>
            <h2>Identifique-se para visualizar seus acessos</h2>
        <% }%>
    </body>
</html>
