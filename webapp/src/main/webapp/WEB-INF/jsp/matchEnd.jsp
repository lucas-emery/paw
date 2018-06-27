<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:masterpage>
  <jsp:attribute name="styles">
    <link rel="stylesheet" href="<c:url value="/assets/css/match.css"/>"/>
  </jsp:attribute>
  <jsp:body>
    <div class="animated fadeIn p-5">
      <div class="row">
        <div class="col-6">
          <div class="card">
            <div class="card-body">
              <table class="table bg-white">

                <thead class="green">
                <tr>
                  <th scope="col"><spring:message code="home"/></th>
                  <th scope="col" colspan="2"><spring:message code="score"/></th>
                  <th scope="col"><spring:message code="away"/></th>
                </tr>
                </thead>

                <tbody>

                <c:forEach items="${matches}" var="match">
                  <tr>
                    <td>${match.home.name}</td>
                    <td>${match.homeScore}</td>
                    <td>${match.awayScore}</td>
                    <td>${match.away.name}</td>
                  </tr>
                </c:forEach>

                </tbody>

              </table>
            </div>
          </div>
        </div>

        <div class="col-6">
          <div class="card">
            <div class="card-body">

              <div class="row green center big-height big-padding">

                <div class="col">
                  <h5><c:out value="${match.home.name}"/></h5>
                </div>
                <div class="col">
                  <h5><spring:message code="homeAndAwayScore" arguments="${match.homeScore},${match.awayScore}"/></h5>
                </div>
                <div class="col">
                  <h5><c:out value="${match.away.name}"/></h5>
                </div>
              </div>

              <div class="container center">
                <h5><c:out value="${stadium.name}"/></h5>
              </div>

              <h6><spring:message code="goals"/></h6>

              <table class="table width60 table-borderless table-center">

                <tbody>

                <c:forEach items="${match.events}" var="event">
                  <c:if
                      test="${event.type == 'HOMESCORE' || event.type == 'AWAYSCORE' || event.type == 'YELLOW_CARD' || event.type == 'RED_CARD'}">
                    <tr>
                      <td>
                        <c:if test="${event.p1.teamId == match.homeId}">
                          <spring:message code="event.${event.type}" arguments="${event.minute},${event.p1.name}"/>
                        </c:if>
                      </td>
                      <td>
                        <c:if test="${event.p1.teamId == match.awayId}">
                          <spring:message code="event.${event.type}" arguments="${event.minute},${event.p1.name}"/>
                        </c:if>
                      </td>
                    </tr>
                  </c:if>
                </c:forEach>

                </tbody>
              </table>

                <%-- Esto de playerStats por ahora no
                <h6><spring:message code="matchStats"/> </h6>

                <table class="table width60 table-borderless table-center">
                    <tbody>

                    <tr>
                        <td><c:out value="${homeSaves}"/></td> 
                        <td><spring:message code="saves"/> </td> 
                        <td><c:out value="${awaySaves}"/></td>
                    </tr>
                    <tr>
                        <td><c:out value="${homePasses}"/></td>
                        <td><spring:message code="passes"/> </td> 
                        <td><c:out value="${awayPasses}"/></td>
                    </tr>
                    <tr>
                        <td><c:out value="${homeTackles}"/></td>
                        <td><spring:message code="tackles"/> </td> 
                        <td><c:out value="${awayTackles}"/></td>
                    </tr>
                    <tr>
                        <td><c:out value="${homeAssists}"/></td>
                        <td><spring:message code="assists"/> </td> 
                        <td><c:out value="${awayAssists}"/></td>
                    </tr>
                    <tr>
                        <td><c:out value="${homeFouls}"/></td>
                        <td><spring:message code="fouls"/> </td> 
                        <td><c:out value="${awayFouls}"/></td>
                    </tr>
                    </tbody>
                </table>
                --%>

            </div>
          </div>
        </div>
      </div>
    </div>
  </jsp:body>
</t:masterpage>
