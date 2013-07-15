<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>

</head>
<body>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="/home">Spitter</a>

            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li>
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
                            <s:url value="/spitters/${current_user}" var="user_profile_url"/>
                            <a href="${user_profile_url}">My Spittles</a>
                        </li>
                        <li>
                            <s:url value="/static/j_spring_security_logout" var="logout_url"/>
                            <a href="${logout_url}">Logout</a>
                        </li>
                    </security:authorize>
                </ul>
                <form class="navbar-form pull-right">
                    <input class="span2" id="username_or_email" name="j_username" type="text" placeholder="Username"/>
                    <input class="span2" id="password" name="j_password" type="password" placeholder="Password"/>
                    <input class="span2" id="remember_me" name="_spring_security_remember_me" type="checkbox"/>
                    <label for="remember_me" class="inline">Remember Me</label>
                    <button type="submit" class="btn">Sign in</button>

                    <script type="text/javascript">
                        document.getElementById('username_or_email').focus();
                    </script>
                </form>
            </div>
        </div>
    </div>
</div>


</body>
</html>