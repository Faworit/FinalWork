<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.03.2019
  Time: 21:35
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
        <form action="forward" method="get">
            <input type="hidden" name="direction" value="jsp/creatingGenre.jsp" />
            <input type="submit" class="button" name="language" value="RU">
            <input type="submit" class="button" name="language" value="ENG" >
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
</div>
<form action="addAuthor" method="get">
    <table>
        <tr>
            <td><fmt:message key="key.authorName"/>*</td>
            <td><input type="text" name="name" required></td>
        </tr>
        <tr>
            <td><fmt:message key="key.authorSurname"/>*</td>
            <td><input type="text" name="surname" required></td>
        </tr>
        <tr>
            <td>
                <input type="submit" class="button" value="<fmt:message key="button.addAuthor"/>">
                <h4>${authorError}</h4>
            </td>
        </tr>
    </table>
</form>

</body>
</html>
