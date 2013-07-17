<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>${spitter.userName} - Spittles</title>
<head>
    <title>Home - Spitter</title>

    <security:authorize access="! isAuthenticated()">
        <h2>Hello Guest!</h2> <br>
        <s:url value="/spitters/edit" var="edit_url"/>
        <a href="${edit_url}">Register</a>
    </security:authorize>

    <s:url value="/home" var="home_url" />
    <a href="${home_url}">Home</a>

    <security:authorize access="isAuthenticated()">
        <security:authentication property="principal.username" var="current_user"/>

        <s:url value="/spitters/${current_user}"
               var="user_profile_url"/>
        <a href="${user_profile_url}">My Spittles</a>
        <a href="/static/j_spring_security_logout">Logout</a>
        <h2>Hello ${current_user}!</h2>
    </security:authorize>

</head>
</head>
<body>

<div id="spittles">
    <h2>Spittles for ${spitter.userName}</h2>

    <table cellspacing="15">
        <c:forEach items="$spittleList" var="spittle">
            <tr>
                <td>
                    <a href="<s:url value="/spitters/${spittle.spitterUserName}" />">${spittle.spitterUserName}</a>
                    <c:out value="${spittle.text}"/> <br>
                    <c:out value="${spittle.date}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>