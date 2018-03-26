<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="/styles/main.css">
    <link rel="icon" href="/images/favicon.ico">
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
</head>
<body>
<header>
    <img src="<c:url value='/images/logo.jpg'/>" alt="logo" width="58"/>
    <h1>Record Logo</h1>
    <h2>Fresh Song</h2>
</header>
<nav id="nav_bar">
    <ul>
        <li><a href="<c:url value='/admin' />">Admin</a> </li>
        <%--<li><a href="<c:url value='/user/deleteCookies'/> "/>Delete Cookies </li>--%>
        <%--<li><a href="<c:url value='/order/showCart' />"/>Show Cart </li>--%>
    </ul>
</nav>
