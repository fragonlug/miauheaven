<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="notification">

    <h2>Notification Information</h2>
	
	
    <table class="table table-striped">
        <tr>
            <th>Title:</th>
            <td><b><c:out value="${notification.title}"/></b></td> 
        </tr>
        <tr>
            <th>Message:</th>
            <td><c:out value="${notification.message}"/></td>
        </tr>
        <c:if test="${not empty notification.url}">
        <tr>
            <th>Link:</th>
            <td><a href='${notification.url}'>Click Here</a></td>
        </tr>
        </c:if>
        <tr>
            <th>Target:</th>
            <td><c:out value="${notification.target}"/></td>
        </tr>
        <tr>
            <th>Date:</th>
            <td><c:out value="${notification.date}"/></td>
        </tr>
  
    </table>
    
    <a href='<spring:url value="/admin/notification/delete/${notification.id}"/>'>Delete Notification</a>
</petclinic:layout>
