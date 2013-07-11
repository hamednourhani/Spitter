<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Login - Spitter</title>

    <h2>Sign into Spitter</h2>
</head>
<body>
<div id="login">
    <s:url var="authUrl" value="/static/j_spring_security_check"/>
    <form method="post" class="signin" action="${authUrl}">
        <fieldset>
            <table cellspacing="0">
                <tr>
                    <th><label for="username">Username</label></th>
                    <td><input id="username" name="j_username" type="text"/></td>
                </tr>
                <tr>
                    <th><label for="password">Password</label></th>
                    <td>
                        <input id="password" name="j_password" type="password"/>
                        <small><a href="/account/resend_password">Forgot password?</a></small>
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

</div>


</body>
</html>