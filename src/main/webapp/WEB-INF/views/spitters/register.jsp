<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Registration - Spitter</title>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0-rc1/css/bootstrap.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0-rc1/js/bootstrap.min.js"></script>
</head>
<body>

<div class="navbar navbar-inverse navbar-static-top">
    <div class="navbar-inner">
        <div class="container">
            <div class="nav navbar-nav">
                <a class="navbar-brand" href="/home">Spitter</a>
                <ul class="nav navbar-nav">
                    <li class="active">
                        <s:url value="/home" var="home_url"/>
                        <a href="${home_url}">Home</a>
                    </li>
                    <security:authorize access="! isAuthenticated()">
                        <li>
                            <s:url value="/spitters/register" var="register_url"/>
                            <a href="${register_url}">Register</a>
                        </li>
                    </security:authorize>
                    <security:authorize access="isAuthenticated()">
                        <li>
                            <security:authentication property="principal.username" var="current_user"/>
                            <s:url value="/spitters/${current_user}" var="user_profile_url"/>
                            <a href="${user_profile_url}">My Spittles</a>
                        </li>
                        <li>
                            <s:url value="/static/j_spring_security_logout" var="logout_url"/>
                            <a href="${logout_url}">Logout</a>
                        </li>
                    </security:authorize>
                </ul>
            </div>

            <div style="float:right">
                <security:authorize access="!isAuthenticated()">
                    <s:url var="authUrl" value="/static/j_spring_security_check"/>
                    <form class="navbar-form" method="post" action="${authUrl}">
                        <input class="span2" id="username_or_email" name="j_username" type="text"
                               placeholder="Username"/>
                        <input class="span2" id="password" name="j_password" type="password" placeholder="Password"/>
                        <input class="span2" id="remember_me" name="_spring_security_remember_me" type="checkbox"/>
                        <label for="remember_me" class="inline">Remember Me</label>
                        <button name="commit" type="submit" class="btn" value="Sign in">Sign in</button>
                        <!--<input name="commit" type="submit" value="Sign in"/> -->

                        <script type="text/javascript">
                            document.getElementById('username_or_email').focus();
                        </script>
                    </form>
                </security:authorize>
            </div>

        </div>
    </div>
</div>

<div>
    <h2>Create a free Spitter account</h2>
    <sf:form method="POST" modelAttribute="spitter" action="spitters/register">
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