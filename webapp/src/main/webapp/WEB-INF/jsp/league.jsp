<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:masterpage active="league">
  <jsp:attribute name="styles">
    <link rel="stylesheet" href="<c:url value="/assets/css/home.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/assets/css/match.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/assets/css/league.css"/>"/>
  </jsp:attribute>
  <jsp:body>
    <div class="animated fadeIn p-5">
      <div class="row">
        <div class="col-lg-6">
          <div class="card">
            <div class="card-header">
              <%--<strong class="card-title"><c:out value="${league.name}"/></strong>--%>
              <div class="stat-widget-one">
                <div class="stat-icon dib"><i class="ti-cup text-warning border-warning"></i></div>
                <div class="stat-content dib">
                  <div class="team-name"><c:out value="${league.name}"/></div>
                </div>
              </div>
            </div>
            <div class="card-body">
              <div class="weather-category twt-category m-0 p-0">
                <ul class="m-0">
                  <li>
                    <h5><c:out value="${matchesPlayed}"/></h5>
                    <spring:message code="matchesPlayed"/>
                  </li>
                  <li>
                    <h5><c:out value="${matchesToPlay}"/></h5>
                    <spring:message code="matchesToPlay"/>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="card">
            <div class="card-header">
              <strong class="card-title"><spring:message code="upcomingMatches"/></strong>
            </div>
            <div class="card-body">
              <table class="table bg-white small center header-fixed">
                <thead>
                <tr>
                  <th scope="col" class="match-stadium"><spring:message code="stadium"/></th>
                  <th scope="col" class="match-home"><spring:message code="home"/></th>
                  <th scope="col" class="match-away"><spring:message code="away"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${upcomingMatches}" var="match">
                  <tr>
                    <td class="match-stadium"><c:out value="${match.home.stadium.name}"/></td>
                    <td class="match-home"><c:out value="${match.home.name}"/></td>
                    <td class="match-away"><c:out value="${match.away.name}"/></td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>

        <div class="col-lg-6">
          <div class="card">
            <div class="card-header">
              <strong class="card-title"><spring:message code="positionTable"/></strong>
            </div>
            <div class="card-body">
              <table class="table bg-white small center header-fixed">
                <thead>
                <tr>
                  <th scope="col" class="match-position"><spring:message code="position"/></th>
                  <th scope="col" class="match-team"><spring:message code="team"/></th>
                  <th scope="col" class="match-points"><spring:message code="points"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${teams}" var="team" varStatus="loop">
                  <tr>
                    <th scope="row" class="match-position"><c:out value="${loop.index + 1}"/></th>
                    <td class="match-team"><c:out value="${team.key}"/></td>
                    <td class="match-points"><c:out value="${team.value}"/></td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </jsp:body>
</t:masterpage>
