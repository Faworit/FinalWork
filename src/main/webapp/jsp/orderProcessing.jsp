<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 21.02.2019
  Time: 15:40
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
        <form action="/perform" method="get">
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
</div>

<table border="1" width="1000px">
    <tr>
        <td><fmt:message key="key.idOrder"/></td>
        <td><fmt:message key="key.bookTitle"/></td>
        <td><fmt:message key="key.orderDate"/></td>
        <td><fmt:message key="key.acceptedDate"/></td>
        <td><fmt:message key="key.returnDate"/></td>
        <td><fmt:message key="key.actuallyReturned"/></td>
        <td><fmt:message key="key.giveAway"/></td>
        <c:if test="${role eq 'librarian'}">
            <td><fmt:message key="key.receiver"/></td>
        </c:if>
        <td><fmt:message key="key.state"/></td>
        <c:if test="${role eq 'librarian'}">
            <td><fmt:message key="key.action"/></td>
        </c:if>
    </tr>
    <form action="editBooking" method="get">
        <tr>
            <td>${booking.orderID}</td>
            <td>${booking.bookTitle}</td>
            <td>
                <c:if test="${empty booking.orderDate}">
                    <input type="date" name="orderDate" placeholder="YYYY-MM-DD" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" title="Enter a date in this format YYYY-MM-DD"/>
                </c:if>
                <c:if test="${not empty booking.orderDate}">
                    ${booking.orderDate}
                    <input type="hidden" name="orderDate" value="${booking.orderDate}"/>
                </c:if>
            </td>
            <td>${booking.acceptedDate}</td>
            <td>
                <c:if test="${empty booking.returnDate}">
                <input type="date" name="returnDate" placeholder="YYYY-MM-DD" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" title="Enter a date in this format YYYY-MM-DD"/>
                </c:if>
                <c:if test="${not empty booking.returnDate}">
                    ${booking.returnDate}
                    <input type="hidden" name="orderDate" value="${booking.orderDate}"/>
                </c:if>
            </td>
            <td>
                <c:if test="${empty booking.actuallyReturn}">
                    <input type="date" name="actuallyReturn" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" title="Enter a date in this format YYYY-MM-DD"/>
                </c:if>
                <c:if test="${not empty booking.actuallyReturn}">
                    ${booking.actuallyReturn}
                </c:if>
            </td>
            <c:if test="${role eq 'librarian'}">
                <td>
                    ${booking.librarian.name}
                    ${booking.librarian.surname}
                </td>
            </c:if>
            <td>
                ${booking.reader.name}
                ${booking.reader.surname}
            </td>
            <td>
                <select name="status">
                    <option selected disabled><fmt:message key="key.selectStatus"/></option>
                    <c:forEach var="status" items="${list}">
                        <option>${status}</option>
                    </c:forEach>
                 </select>
            </td>
            <td>
                <p><input type="submit" class="button" value="<fmt:message key="key.apply"/>"><h4>${error}</h4></p>
            </td>
        </tr>
    </form>
</table>
</body>
</html>
