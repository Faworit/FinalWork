<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.02.2019
  Time: 12:40
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
<h1>${information}</h1>
<div class="menu1">
    <form action="/showBook" method="get">
        <input type="submit" value="<fmt:message key="button.mainPage"/>">
    </form>
    <form action="logOut" method="get">
        <input type="submit" value="<fmt:message key="button.logOut"/>">
    </form>
</div>
</body>
</html>
