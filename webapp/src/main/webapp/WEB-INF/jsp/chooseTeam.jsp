<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
  <jsp:attribute name="styles">
    <link rel="stylesheet" href="<c:url value="/assets/css/background.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/assets/css/register.css"/>"/>
  </jsp:attribute>
  <jsp:body>
    <c:url value="/processForm" var="postPath"/>

    <div class="contain">
      <div class="translucent"></div>
      <div class="content">

        <h2><spring:message code="chooseTeam"/></h2>

        <form:form modelAttribute="chooseTeamForm" action="${postPath}" method="post">
          <div class="form-group">
            <c:forEach items="${teamList}" var="team">
              <form:radiobutton path="teamChosen" required="true" value="${team.id}"/>${team.name}
              <br>
            </c:forEach>
          </div>

          <div class="right">
            <button type="submit" class="btn btn-success "><spring:message code="start"/> &nbsp; <i class="fa fa-play"></i>
            </button>
          </div>
        </form:form>
      </div>
    </div>
  </jsp:body>
</t:genericpage>

