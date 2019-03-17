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
    <link href="/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="login">
    <h1>Login</h1>
    <form action="LogIn" method="post">
        <p><input type="hidden" name="language" value=${language} /></p>
        <p><input type="text" name="login" maxlength="20" required placeholder="<fmt:message key="key.username"/>"></p>
        <p><input type="password" name="password" required placeholder="<fmt:message key="key.password"/>"></p>
        <p><input type="submit" class="button" value="<fmt:message key="button.submit"/>"> <h4>${notCorrect}</h4> </p>
    </form>
</div>
<div class="changeLanguage">
    <form action="forward" method="get">
        <input type="hidden" name="direction" value="index.jsp" />
        <input type="submit" class="button" name="language" value="RU">
        <input type="submit" class="button" name="language" value="ENG">
    </form>

    <form action="forward" method="get">
        <input type="hidden" name="direction" value="index.jsp"/>

    </form>
</div>
</body>
</html>
