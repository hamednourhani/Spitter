<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div id="box border" class="center">

    <div id="header" style="display:inline;">
        <div class="div-border" style="margin: 10px; overflow: hidden;">
            <h2 style="float: left">Looking Spitters ${spitterName} is Following</h2>
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
                            <input type="submit" value="${spitterName}'s Profile"
                                   class="btn" style="border-color: #757575;"/>
                        </form>
                    </security:authorize>
                </li>
            </ul>
        </div>
    </div>

    <div id="followerList" class="div-border-pulled-left fixedheightcontainer"
         style="padding-right: 40px; margin: 10px;">
        <h3>Spitters ${spitterName} is Following: </h3>

        <div class="Content">
            <ol>
                <c:forEach var="followee" items="${followeeList}">
                    <li class="wrapWord">
                    <span class="spittleListText">
                        <s:url value="/spitters/profile/${followee.userName}" var="userName_url"/>
                        <div class="panel" style="border-color: #296EAB;">
                            <a href="${userName_url}"><c:out value="${followee.userName}"/></a>
                            - ${follower.fullName}
                        </div>
                    </span>
                    </li>
                </c:forEach>
            </ol>
        </div>
    </div>
    </div>


</div>