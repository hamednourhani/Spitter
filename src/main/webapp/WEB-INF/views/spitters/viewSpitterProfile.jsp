<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="center">
    <div id="header" style="display:inline;">
        <div class="div-border" style="margin: 10px; overflow: hidden;">
            <h2 style="float: left">Viewing ${spitterName}'s Profile</h2>

            <ul id="headerList">
                <li>
                    <security:authorize access="isAuthenticated()">
                        <c:url value="/spitters/profile/${spitterName}/follow" var="followUrl" />
                        <form method="link" action="${followUrl}">
                            <input type="submit" value="Follow!" class="btn" style="border-color: #757575;"/>
                        </form>
                    </security:authorize>
                </li>

                <li>
                    <security:authorize access="isAuthenticated()">
                        <c:url value="/spitters/profile/${spitterName}/viewFollowees" var="followeesUrl" />
                        <form method="link" action="${followeesUrl}">
                            <input type="submit" value="Following" class="btn" style="border-color: #757575;"/>
                        </form>
                    </security:authorize>
                </li>

                <li>
                    <security:authorize access="isAuthenticated()">
                        <c:url value="/spitters/profile/${spitterName}/viewFollowers" var="followersUrl" />
                        <form method="link" action="${followersUrl}">
                            <input type="submit" value="Followers" class="btn" style="border-color: #757575;"/>
                        </form>
                    </security:authorize>
                </li>

                <li>
                    <security:authorize access="isAuthenticated()">
                        <c:url value="/spitters/profile/${spitterName}" var="followUrl" />
                        <form method="link" action="${followUrl}">
                            <input id="followButton" type="submit" value="${spitterName}'s Profile"
                                   class="btn" style="border-color: #757575;"/>
                        </form>
                    </security:authorize>
                </li>
            </ul>
        </div>
    </div>

    <security:authorize access="hasRole('ROLE_SPITTER')">
        <div id="spittle" class="div-border-pulled-left" style="margin:10px;">
            <s:url value="/home" var="spittle_url"/>
            <sf:form modelAttribute="spittle" action="${spittle_url}">
                <label> <s:message text="Enter spittle: "/></label><br>
                <sf:textarea id="spittleTextBox" cssClass="form-control" path="text" rows="3" cols="40"
                             onkeydown="press(event)"/>
                <sf:errors path="text" cssClass="error"/><br>

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

        <h3>These are ${spitterName}'s recent spittles: </h3>

        <div class="Content">
            <ol>
                <c:forEach var="spittle" items="${spittles}">
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


<script>

    $(document).ready(function(){
        initialize();
    });

    function initialize(){
        $("#followButton").click(function(event){
                checkIfFollowing()
        });
    }

    function checkIfFollowing(){

        var username = ${spitterName};

        $.ajax({
            type: "POST",
            url: "/spitters/isUserFollowing",
            data: "username=" + username,
            success: function(data){
                window.alert(data);
            }
        });
    }
</script>