<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>${username} profile - Spitter</title>

    <security:authorize access="! isAuthenticated()">
        <h2>Hello Guest!</h2><br>
        <s:url value="/spitters/register" var="register_url"/>
        <a href="${register_url}">Register</a>
    </security:authorize>

    <s:url value="/home" var="home_url" />
    <a href="${home_url}">Home</a>

    <security:authorize access="isAuthenticated()">
        <security:authentication property="principal.username" var="current_user"/>

        <s:url value="/spitters/${current_user}"
               var="user_profile_url"/>
        <a href="${user_profile_url}">My Spittles</a>

        <s:url value="/static/j_spring_security_logout" var="logout_url"/>
        <a href="${logout_url}">Logout</a>
        <h2>Hello ${current_user}!</h2>
    </security:authorize>

</head>
<body>

<div>
    <security:authorize access="hasRole('ROLE_SPITTER')">
        <s:url value="../home" var="spittle_url"/>
        <sf:form modelAttribute="spittle" action="${spittle_url}">
            <sf:label path="text"> <s:message text="Enter spittle: "/></sf:label>
            <sf:textarea path="text" rows="2" cols="40"/>
            <sf:errors path="text"/>
            <br>

            <div class="spitItSubmitIt">
                <input type="submit" value="Spit it!" class="status-btn round-btn disabled"/>
            </div>
        </sf:form>
    </security:authorize>
</div>

<div id="userSpits" style="float:left">

    <s:url value="/follow" var="follow_url"/>
    <a href="${follow_url}">Follow ${spitterName}</a> <br/> <br/>

    <h3>These are ${spitterName}'s recent spittles: </h3>
    <ol class="spittle-list">
        <c:forEach var="spittle" items="${spittles}">
            <li>
            <span class="spittleListText">
                <c:out value="${spittle.spitter.userName}"/>
                - <c:out value="${spittle.text}"/> <br>
                <small><c:out value="${spittle.date}"/></small>
            </span>
            </li>
        </c:forEach>
    </ol>

</div>


<div id="login" style="float:right">
    <security:authorize access="!isAuthenticated()">
        ${message}
        <s:url var="authUrl" value="/static/j_spring_security_check"/>
        <form method="post" class="signin" action="${authUrl}">
            <fieldset>
                <table>
                    <tr>
                        <th><label for="username_or_email">Username or Email</label></th>
                        <td><input id="username_or_email" name="j_username" type="text"/></td>
                    </tr>
                    <tr>
                        <th><label for="password">Password </label></th>
                        <td>
                            <input id="password" name="j_password" type="password"/>
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td>
                            <input id="remember_me" name="_spring_security_remember_me" type="checkbox"/>
                            <label for="remember_me" class="inline">Remember Me</label>
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td><input name="commit" type="submit" value="Sign in"/></td>
                    </tr>
                </table>
            </fieldset>
        </form>

        <script type="text/javascript">
            document.getElementById('username_or_email').focus();
        </script>

    </security:authorize>

</div>


</body>
</html>