<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title></title>
</head>
<body>

<div style="float:left">
    <h3>Lets see what the recent spits are: </h3>
    <ol class="spittle-list">
        <c:forEach var="spittle" items="${followeeSpittleList}">
            <s:url value="/spitters/{spitterName}" var="spitter_url">
                <s:param name="spitterName" value="${spittle.spitterUserName}"/>
            </s:url>

            <li>
            <span class="spittleListText">
                <s:url value="/spitters/${spittle.spitterUserName}" var="userName_url"/>
                <a href="${userName_url}"><c:out value="${spittle.spitterUserName}"/></a>
                - <c:out value="${spittle.text}"/> <br>
                <small><c:out value="${spittle.date}"/></small>
            </span>
            </li>
        </c:forEach>
    </ol>
</div>

</body>
</html>