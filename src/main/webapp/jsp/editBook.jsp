<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10.02.2019
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="language"/>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Library</title>
    <link href="/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="userLine">
    <div class="greating">
        <h1 align="center">${surname} ${name} </h1>
    </div>
    <div class="changeLanguage">
        <form action="/showBook" method="get">
            <input type="submit" class="button" name="language" value="RU">
            <input type="submit" class="button" name="language" value="ENG">
        </form>
    </div>
</div>
<div class="menu1">
    <form action="logOut" method="get">
        <input type="submit" class="button" value="<fmt:message key="button.logOut"/>">
    </form>
    <form action="/showBook" method="get">
        <input type="submit" class="button" value="<fmt:message key="button.mainPage"/>">
    </form>

    <form action="removeBook" method="get">
        <input type="hidden" name="ID" value="${book.ID}">
        <button class="buttonDel" onclick="clicked(event)"><fmt:message key="button.delete"/></button>
        <script>
            function clicked(e)
            {
                if(!confirm('Are you sure?'))e.preventDefault();
            }
        </script>
    </form>
</div>


<form action="editBook" method="get">
    <table border="1">
        <tr>
            <td><fmt:message key="key.title"/></td>
            <td><input type="text" name="title" required value="${book.title}"></td>
        </tr>
        <tr>
            <td><fmt:message key="key.genre"/></td>
            <td><c:forEach var="genre" items="${book.geners}">
                <input type="hidden" name="idGenre" value="${genre.IDGenre}">
                <input type="text" name="genre" required value="${genre.genreName}">
                <br>
            </c:forEach>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="key.author"/></td>
            <td><c:forEach var="author" items="${book.authors}">
                <input type="hidden" name="idAuthor" value="${author.IDAuthor}">
                <input type="text" name="name"  value="${author.name}">
                <input type="text" name="surname"  value="${author.surname}">
                <br>
            </c:forEach>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="key.ISBN"/></td>
            <td><input type="text" name="ISBN" required value="${book.ISBN}"></td>
        </tr>
        <tr>
            <td><fmt:message key="key.quantity"/></td>
            <td><input type="text" name="quantity" required value="${book.quantity}"></td>
        </tr>
        <tr>
            <td>
                <input type="hidden" name="ID" value="${book.ID}">
                <input type="submit" class="button" value="<fmt:message key="button.apply"/>">
            </td>
        </tr>
    </table>
</form>



</body>
</html>
