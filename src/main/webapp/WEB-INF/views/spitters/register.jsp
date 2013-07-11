<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Registration - Spitter</title>

    <security:authorize access="! isAuthenticated()">
        <h2>Hello Guest!</h2><br>
        <s:url value="/spitters/register" var="register_url"/>
        <a href="${register_url}">Register</a>
    </security:authorize>

    <s:url value="/home" var="home_url"/>
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
<body>

<div>
    <h2>Create a free Spitter account</h2>
    <sf:form method="POST" modelAttribute="spitter">
        <fieldset>
            <table cellspacing="0">
                <tr>
                    <th><label for="fullName"> Full name:</label></th>
                    <td>
                        <sf:input path="fullName" size="15" id="fullName"/><br>
                        <sf:errors path="fullName" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <th><label for="userName">Username: </label></th>
                    <td>
                        <sf:input path="userName" size="15" maxlength="30" id="userName"/>
                        <small id="userName_msg">No spaces, please.</small>
                        <br>
                        <sf:errors path="userName" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <th><label for="password">Password:</label></th>
                    <td>
                        <sf:password path="password" size="15" showPassword="false" maxlength="30" id="password"/>
                        <small>6 characters or more</small>
                        <br>
                        <sf:errors path="password" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <th><label for="email"> Email address:</label></th>
                    <td>
                        <sf:input path="email" size="30" id="email"/>
                        <small>In case you forget something</small>
                        <sf:errors path="email" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <th><input type="submit" value="Submit!"></th>
                </tr>
            </table>
        </fieldset>
    </sf:form>
</div>
</body>
</html>