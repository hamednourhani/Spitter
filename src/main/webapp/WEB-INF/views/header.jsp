<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                        <label for="remember_me" class="inline" style="color: #858585;">Remember Me</label>
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

    <s:url value="/spittles/mySpitterCircle" var="spitterCircle_url" />
    <a href="${spitterCircle_url}">My Spitter Circle</a>

    <s:url value="/static/j_spring_security_logout" var="logout_url"/>
    <a href="${logout_url}">Logout</a>
    <h2>Hello ${current_user}!</h2>
</security:authorize>