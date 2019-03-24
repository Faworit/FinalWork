<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06.02.2019
  Time: 12:43
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
    <script src="/js/clearInput.js"></script>
    <script src="/js/insertIn.js"></script>
</head>
<body>
<div class="userLine">
    <div class="greating">
        <h1 align="center">${surname} ${name} </h1>
    </div>
    <div class="changeLanguage">
        <form action="/showAddBookMenu" method="get">
            <input type="hidden" name="jspname" value="jsp/creatingBook.jsp"/>
            <input type="submit" class="button" name="language" value="RU">
            <input type="submit" class="button" name="language" value="ENG" >
        </form>
    </div>
</div>
<div class="menu1">
    <form action="forward" method="get">
        <input type="hidden" name="direction" value="addGenre.jsp">
        <input type="submit" class="button" value="<fmt:message key="button.addGenre"/>">
    </form>
    <form action="forward" method="get">
        <input type="hidden" name="direction" value="addAuthor.jsp">
        <input type="submit" class="button" value="<fmt:message key="button.addAuthor"/>">
    </form>
    <form action="logOut" method="get">
        <input type="submit" class="button" value="<fmt:message key="button.logOut"/>">
    </form>
    <form action="/showBook" method="get">
        <input type="submit" class="button" value="<fmt:message key="button.mainPage"/>">
    </form>
</div>

<form action="createBook" method="get">
    <table>
        <tr>
            <td><fmt:message key="key.titleRU"/>*</td>
            <td><input type="text" name="titleRU" maxlength="200" required></td>
        </tr>
        <tr>
            <td><fmt:message key="key.titleENG"/>*</td>
            <td><input type="text" name="titleENG" maxlength="200" required></td>
        </tr>
        <tr>
            <td><fmt:message key="key.ISBN"/></td>
            <td><input type="text" name="ISBN" maxlength="17"></td>
        </tr>
        <tr>
            <td><fmt:message key="key.number"/></td>
            <td><input type="text" name="number"></td>
        </tr>
        <tr>
            <td><fmt:message key="key.quantity"/>*</td>
            <td><input type="text" name="quantity" required></td>
        </tr>

        <tr>
            <td><fmt:message key="key.genre"/>*</td>
            <td>
                <select onclick="insertIn('select[name=s1]', 'genreField')" name="s1">
                    <option><fmt:message key="key.selectGenre"/></option>
                    <c:forEach var="genres" items="${list}">
                        <option>${genres.genreName}</option>
                    </c:forEach>
                </select>
                <input type="text" multiple id="genreField" name="genre" value="">
            </td>
            <td>
                <input type="button" class="button" onclick="delGenre('genreField')" id="clear" value="clear">
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="key.author"/>*
            </td>
            <td>
                <select onclick="insertIn('select[name=s2]', 'authorField')"  name="s2">
                    <option><fmt:message key="key.selectAuthor"/></option>
                    <c:forEach var="authors" items="${listAuthors}">
                        <option>${authors.name} ${authors.surname}</option>
                    </c:forEach>
                </select>
                <input type="text" multiple id="authorField" name="author" value="">
            </td>
            <td>
                <input type="button" class="button" onclick="delAuthor()" id="clearButton" value="clear">


               <%-- разобраться с вынососм скриптов, а именно при выносе кнопка срабатывает только со второго раза--%>

                <script type="text/javascript">
                    document.getElementById("clearButton").onclick = function(){
                        document.getElementById("authorField").value = "";
                    }
                </script>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" class="button" value="<fmt:message key="button.addBook"/>">
            </td>
            <td><h4>${alreadyExists}</h4></td>
        </tr>
    </table>
</form>
</body>
</html>




