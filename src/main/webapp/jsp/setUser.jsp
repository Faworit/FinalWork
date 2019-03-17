<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 25.01.2019
  Time: 11:28
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
            <input type="hidden" name="direction" value="jsp/setUser.jsp"/>
            <input type="submit" class="button" name="language" value="ENG" >
            <input type="submit" class="button" name="language" value="RU">
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
<form action="createUser" method="get">
    <table>
        <tr>
            <td><fmt:message key="key.password"/>*
                <br> <fmt:message key="key.passwordFormat"/>
            </td>
            <td><input type="password" name="password" maxlength="6" required></td>
            <td><fmt:message key="key.repeatPassword"/></td>
            <td><input type="password" name="repeatPassword" maxlength="6" required></td>
            <td>${notEqual}</td>
        </tr>
        <tr>
            <td><fmt:message key="key.name"/>*</td>
            <td><input type="text" name="name" required></td>
        </tr>
        <tr>
            <td><fmt:message key="key.surname"/>*</td>
            <td><input type="text" name="surname" required></td>
        </tr>
        <tr>
            <td><fmt:message key="key.mail"/>*
                ${notCorrectMail}
            </td>
            <td><input type="text" name="mail" required></td>
        </tr>
        <tr>
            <td><fmt:message key="key.phone"/>*</td>
            <td><input type="text" name="phone" required></td>
        </tr>
        <tr>
            <td><fmt:message key="key.birthday"/>*</td>
            <td><input type="date" name="birthday" placeholder="YYYY-MM-DD" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" title="Enter a date in this format YYYY-MM-DD"/></td>
        </tr>
        <tr>
            <td><fmt:message key="key.isBlock"/>*</td>
            <td><select required name="block">
                <option></option>
                <option value="n"><fmt:message key="key.nblock"/></option>
                <option value="y"><fmt:message key="key.block"/></option>
            </select></td>
        </tr>
        <tr>
            <td><fmt:message key="key.role"/>*</td>
            <td><select name="role" required>
                <option></option>
                <option disabled><fmt:message key="key.selectRole"/></option>
                <option value="reader" ><fmt:message key="key.reader"/></option>
                <option value="librarian"><fmt:message key="key.librarian"/></option>
            </select></td>
        </tr>
        <tr>
            <td>
                <input type="submit" class="button" value="<fmt:message key="button.addUser"/>">
                <br> ${loginExists}
            </td>
        </tr>
    </table>
</form>
</body>
</html>
