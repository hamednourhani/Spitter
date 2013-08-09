<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="container" class="center">

    <div id="form" class="div-border-pulled-left">
        <h2>Create a free Spitter account</h2>
        <sf:form method="POST" modelAttribute="spitter" action="register">
            <fieldset>

                <div class="input-group" style="margin-bottom: 10px;">
                    <sf:input cssClass="form-control input-small" cssStyle="width: 200px;" path="userName"
                              size="15" id="userName" placeholder="Username"/><br>
                    <sf:errors path="userName" cssClass="error"/>
                </div>

                <div class="input-group" style="margin-bottom: 10px;">
                    <sf:input cssClass="form-control input-small" placeholder="Full Name" cssStyle="width: 200px;"
                              path="fullName"
                              size="15" maxlength="30" id="fullName"/>
                    <small id="userName_msg"> No spaces, please.</small>
                    <br>
                    <sf:errors path="fullName" cssClass="error"/>
                </div>

                <div class="input-group" style="margin-bottom: 10px;">
                    <sf:input path="password" type="password" cssStyle="width: 200px;"
                              cssClass="form-control input-small"
                              size="15" showPassword="false" maxlength="30" id="password" placeholder="Password"/>
                    <small> 6 characters or more</small>
                    <br>
                    <sf:errors path="password" cssClass="error"/>
                </div>

                <div class="input-group" style="margin-bottom: 10px;">
                    <sf:input cssClass="form-control input-small" cssStyle="width: 200px;" path="email"
                              size="30" id="email" placeholder="Email"/>
                    <small> In case you forget something!</small>
                    <br>
                    <sf:errors path="email" cssClass="error"/>
                </div>
                <br>

                <input type="submit" value="Submit!" class="btn" style="border-color: #919191;">

            </fieldset>
        </sf:form>
    </div>
</div>