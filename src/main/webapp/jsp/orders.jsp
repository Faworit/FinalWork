<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 16.03.2019
  Time: 14:58
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
        <form action="/showOrder" method="get">
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
    <fmt:message key="key.sortBy"/>
    <form action="onlyNew" method="get">
        <input type="submit" class="button" name="status" value="<fmt:message key="key.onlyNew"/>">
        <input type="submit" class="button" name="status" value="<fmt:message key="key.rejection"/>">
        <input type="submit" class="button" name="status" value="<fmt:message key="key.inProgress"/>">
        <input type="submit" class="button" name="status" value="<fmt:message key="key.issue"/>">
        <input type="submit" class="button" name="status" value="<fmt:message key="key.finished"/>">
    </form>
    <c:if test="${role eq 'reader'}">
        <form action="showOrder" method="get">
            <p><input type="submit" class="button" value="<fmt:message key="button.myOrders"/>"></p>
        </form>
    </c:if>
    <c:if test="${role eq 'librarian'}">
        <form action="showOrder" method="get">
            <p><input type="hidden" name="language" value=${language}></p>
            <p><input type="submit" class="button" value="<fmt:message key="button.orders"/>"></p>
        </form>
    </c:if>
</div>

<table border="1" width="1000px">
    <tr>
        <td><fmt:message key="key.idOrder"/> </td>
        <td><fmt:message key="key.title"/></td>
        <td><fmt:message key="key.orderDate"/></td>
        <td><fmt:message key="key.acceptedDate"/></td>
        <td><fmt:message key="key.returnDate"/></td>
        <td><fmt:message key="key.actuallyReturned"/></td>
        <td><fmt:message key="key.state"/></td>
        <c:if test="${role eq 'librarian'}">
            <td><fmt:message key="key.receiver"/></td>
            <td><fmt:message key="key.giveAway"/></td>
            <td><fmt:message key="button.perform"/></td>
        </c:if>
        <c:if test="${role eq 'reader'}">
            <td><fmt:message key="key.receiver"/></td>
        </c:if>
    </tr>
    <c:forEach var="booking" items="${list}">
        <tr>
            <td>${booking.id}</td>
            <td>${booking.bookTitle}</td>
            <td>${booking.orderDate}</td>
            <td>${booking.acceptedDate}</td>
            <td>${booking.returnDate}</td>
            <td>${booking.actuallyReturn}</td>
            <td>${booking.state}</td>
            <c:if test="${role eq 'reader'}">
                <td>${booking.reader.name}</td>
            </c:if>
            <c:if test="${role eq 'librarian'}">
                <td>
                    ${booking.reader.name}
                    ${booking.reader.surname}
                </td>
                <td>
                    ${booking.librarian.name}
                    ${booking.librarian.surname}
                </td>
            </c:if>
            <c:if test="${booking.state ne'finished' && booking.state ne'завершен' && role eq 'librarian'}">
            <td>
                <form action="perform" method="get">
                    <input type="hidden" name="userName" value="${booking.librarian.name}">
                    <input type="hidden" name="idOrder" value="${booking.id}">
                    <input type="submit" class="button" value="<fmt:message key="button.perform"/>">
                </form>
            </td>
            </c:if>
            <c:if test="${booking.state eq'in progress' || booking.state eq'в процессе' && role eq 'reader'}">
                <td>
                    <form action="confirm" method="get">
                        <input type="hidden" name="idOrder" value="${booking.id}">
                        <input type="submit" class="button" value="<fmt:message key="button.confirmReceipt"/>">
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>
