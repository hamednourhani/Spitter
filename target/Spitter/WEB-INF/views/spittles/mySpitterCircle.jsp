<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div id="box border" class="center">

    <div id="header" style="display:inline;">
        <div class="div-border" style="margin: 10px;">
            <h2>My Spitter Circle</h2>
        </div>
    </div>

    <security:authorize access="hasRole('ROLE_SPITTER')">
        <div id="spittleBox" class="div-border-pulled-left" style="margin: 10px;">
            <s:url value="/home" var="spittle_url"/>
            <sf:form modelAttribute="spittle" action="${spittle_url}">
                <label> <s:message text="Enter spittle: "/></label><br>
                <sf:textarea id="spittleTextBox" cssClass="form-control" path="text" rows="3" cols="40"
                             onkeydown="press(event)"/>
                <sf:errors path="text" cssClass="error"/>
                <br>

                <div class="spitItSubmitIt">
                    <input type="submit" value="Spit it!" class="btn btn-small" style="border-color: #757575;"/>
                </div>
            </sf:form>
        </div>

        <script>
            document.getElementById("spittleTextBox").focus();

            function press(e) {
                if ((window.event ? event.keyCode : e.which) == 13) {
                    document.forms[0].submit();
                }
            }
        </script>
    </security:authorize>

        <div id="spittleList" class="div-border-pulled-left fixedheightcontainer"
             style="padding-right: 40px; margin: 10px;">
            <h3>These are the spits in your Spitter circle: </h3>

            <div class="Content">
                <ol>
                    <c:forEach var="spittle" items="${followeeSpittleList}">
                        <li class="wrapWord">
                    <span class="spittleListText">
                        <s:url value="/spitters/profile/${spittle.spitter.userName}" var="userName_url"/>
                        <div class="panel" style="border-color: #296EAB;">
                            <div class="panel-heading" style="border-bottom-color: #64B354;">
                                <a href="${userName_url}"><c:out value="${spittle.spitter.userName}"/></a>
                                -
                                <small><c:out value="${spittle.date}"/></small>
                            </div>
                            <c:out value="${spittle.text}"/>
                        </div>
                    </span>
                        </li>
                    </c:forEach>
                </ol>
            </div>
        </div>

</div>