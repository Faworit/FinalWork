<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 17.01.2019
  Time: 21:02
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
    <div class="search">
        <form action="search" method="get">
            <input type="search" name="search" maxlength="50" placeholder="<fmt:message key="key.search"/>">
            <input type="submit" class="button" value="<fmt:message key="key.search"/>">
        </form>
    </div>
    <div class="changeLanguage">
        <form action="/showBook" method="get">
            <input type="submit" class="button" name="language" value="RU">
            <input type="submit" class="button" name="language" value="ENG">
        </form>
    </div>
</div>

<div class="menu1">
    <c:if test="${role eq 'librarian'}">
        <form action="forward" method="get">
            <input type="hidden" name="direction" value="jsp/setUser.jsp" />
            <p><input type="submit" class="button" value="<fmt:message key="button.setUser"/>"></p>

        </form>

        <form action="showAddBookMenu" method="get">
            <p><input type="hidden" name="language" value=${language} /></p>
            <p><input type="submit" class="button" value="<fmt:message key="button.addBook"/>"></p>
        </form>
        <form action="showOrder" method="get">
            <p><input type="hidden" name="language" value=${language}></p>
            <p><input type="submit" class="button" value="<fmt:message key="button.orders"/>"></p>
        </form>
    </c:if>
    <c:if test="${role eq 'reader'}">
        <form action="showOrder" method="get">
            <p><input type="submit" class="button" value="<fmt:message key="button.myOrders"/>"></p>
        </form>
    </c:if>
    <form action="logOut" method="get">
        <input type="submit" class="button" value="<fmt:message key="button.logOut"/>">
    </form>
</div>

<table border="1" bordercolor="#333333">
    <tr>
        <td><fmt:message key="key.title"/></td>
        <td><fmt:message key="key.genre"/></td>
        <td><fmt:message key="key.author"/></td>
        <td><fmt:message key="key.ISBN"/></td>
        <td><fmt:message key="key.quantity"/></td>
        <c:if test="${role eq 'librarian'}">
            <td>
                <fmt:message key="key.format"/>
            </td>
        </c:if>
        <c:if test="${role eq 'reader'}">
            <td>
                <fmt:message key="button.orderBook"/>
            </td>
        </c:if>
    </tr>

    <c:forEach var="books" items="${list}">
        <tr>
            <td>${books.title}</td>
            <td>
                <c:forEach var="genre" items="${books.geners}">
                    ${genre.genreName} <br>
                </c:forEach>
            </td>
            <td>
                <c:forEach var="author" items="${books.authors}">
                    ${author.name}
                    ${author.surname}
                    <br>
                </c:forEach></td>
            <td>${books.ISBN}</td>
            <td>${books.quantity}</td>
            <c:if test="${role eq 'librarian'}">
                <td>
                    <form action="editBookMenu" method="get">
                        <p><input type="hidden" name="ID" value="${books.ID}"></p>
                        <p><input type="submit" class="button" value="<fmt:message key="button.edit"/>"> </p>
                    </form>
                </td>
            </c:if>
            <c:if test="${role eq 'reader'}">
                <td>
                    <form action="makeOrder" method="get">
                        <p><input type="hidden" name="ID" value="${books.ID}"></p>
                        <p><input type="submit" value="<fmt:message key="button.orderBook"/>"></p>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>
