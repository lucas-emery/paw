<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:masterpage active="home">
  <jsp:attribute name="styles">
    <link rel="stylesheet" href="<c:url value="/assets/css/home.css"/>"/>
  </jsp:attribute>
  <jsp:attribute name="scripts">
    <script src="<c:url value="/assets/js/lib/chart-js/Chart.bundle.js"/>"></script>
    <script src="<c:url value="/assets/js/playerInfo.js"/>"></script>
  </jsp:attribute>
  <jsp:body>
    <div class="animated fadeIn p-5">
      <div class="row">
        <div class="col-lg-6">
          <div class="card">
            <div class="card-body">
              <div class="stat-widget-one">
                <div class="stat-icon dib"><i class="ti-shield text-primary border-primary"></i></div>
                <div class="stat-content dib">
                  <div class="team-name"><c:out value="${team.name}"/></div>
                </div>
              </div>
              <div class="b-b-1 pt-3"></div>
              <div class="row ml-1 mr-1">
                <div class="col-6 trust-text">
                  <spring:message code="boardTrust"/>
                </div>
                <div class="col-5 p-0 progress mt-3">
                  <div class="progress-bar bg-info" role="progressbar" style="width: ${team.boardTrust}%" aria-valuemin="0"
                       aria-valuemax="100"></div>
                </div>
              </div>
              <div class="row ml-1 mr-1">
                <div class="col-6 trust-text">
                  <spring:message code="fanTrust"/>
                </div>
                <div class="col-5 p-0 progress mt-3">
                  <div class="progress-bar bg-info" role="progressbar" style="width: ${team.fanTrust}%"
                       aria-valuemin="0" aria-valuemax="100"></div>
                </div>
              </div>
            </div>
          </div>

          <div class="card">
            <div class="card-header">
              <strong class="card-title"><spring:message code="nextMatch"/><span class="pull-right"><c:out
                  value="${date}"/></span></strong>
            </div>
            <ul class="list-group list-group-flush">
              <li class="list-group-item">
                <c:choose>
                  <c:when test="${team.name == team1}">
                    <spring:message code="homeVersus"/>
                    <span class="pull-right"><c:out value="${team2}"/></span>
                  </c:when>
                  <c:otherwise>
                    <spring:message code="awayVersus"/>
                    <span class="pull-right"><c:out value="${team1}"/></span>
                  </c:otherwise>
                </c:choose>
              </li>
              <li class="list-group-item">
                <spring:message code="stadium"/>
                <span class="pull-right"><c:out value="${stadium}"/></span>
              </li>
              <li class="list-group-item">
                <a href="<c:url value="/match"/>" class="pull-right">
                  <button class="btn btn-success "><spring:message code="playMatch"/> &nbsp; <i class="fa fa-play"></i>
                  </button>
                </a>
              </li>
            </ul>
          </div>

          <jsp:include page="playerInfo.jsp"/>

        </div>


        <div class="col-lg-6">
          <div class="card">
            <div class="card-header">
              <strong class="card-title"><spring:message code="players"/></strong>
            </div>
            <div class="card-body">
              <table class="table table-hover bg-white small table-sm">
                <thead>
                <tr>
                  <th scope="col"><spring:message code="name"/></th>
                  <th scope="col"><spring:message code="salary"/></th>
                  <th scope="col"><spring:message code="value"/></th>
                  <th scope="col"><spring:message code="fitness"/></th>
                  <th scope="col"><spring:message code="age"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${players}" var="player">
                  <tr>
                    <th scope="row"><a href="<c:url value="/${player.id}" />"><c:out
                        value="${player.name}"/></a></th>
                    <td><c:out value="${player.salary}"/></td>
                    <td><c:out value="${player.value}"/></td>
                    <td><c:out value="${player.fitness}"/></td>
                    <td><c:out value="${player.age}"/></td>
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