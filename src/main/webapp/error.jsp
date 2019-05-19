<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 25.03.2019
  Time: 19:46
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
    <title>error</title>
    <link href="/css/error.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="login">
    <p class="photo"> <img src = "/css/img/error.jpg" width="600" height="500"/></p>
    <form action="/jsp/forward" method="get">
        <input type="hidden" name="direction" value="index.jsp" />
        <input type="submit" class="button" value="<fmt:message key="button.mainPage"/>">
    </form>
</div>
</body>
</html>
