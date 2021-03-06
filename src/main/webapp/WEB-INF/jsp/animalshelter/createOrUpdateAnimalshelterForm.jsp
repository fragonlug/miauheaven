<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="animalshelter">
    <h2>
        <c:if test="${animalshelter['new']}">New </c:if> Animal Shelter
    </h2>
    <form:form modelAttribute="animalshelter" class="form-horizontal" id="add-animalshelter-form">
        <input type="hidden" name="id" value="${owner.id}" />
    
        <div class="form-group has-feedback">
            <petclinic:inputField label="Name" name="name"/>
            <petclinic:inputField label="CIF" name="cif"/>
            <petclinic:inputField label="Place" name="place"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${animalshelter['new']}">
                        <button class="btn btn-default" type="submit">Add Animal Shelter</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Animal Shelter</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
