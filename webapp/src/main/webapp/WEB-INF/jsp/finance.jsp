<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:masterpage active="finance">
<<<<<<< HEAD
=======
  <jsp:attribute name="styles">
    <link rel="stylesheet" href="<c:url value="/assets/css/finance.css"/>"/>
  </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="<c:url value="/assets/js/finance.js"/>"></script>
    </jsp:attribute>
>>>>>>> master
  <jsp:body>
    <div class="animated fadeIn p-5">
      <div class="row">
        <div class="col-lg-4 col-md-6">
          <div class="card">
            <div class="card-body">
              <div class="stat-widget-one">
                <div class="stat-icon dib"><i class="ti-money text-success border-success"></i></div>
                <div class="stat-content dib">
                  <div class="stat-text"><spring:message code="money"/></div>
                  <div class="stat-digit"><spring:message code="currency" arguments="${money}"/></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-6 col-lg-4">
          <div class="card">
            <div class="card-body">
              <div class="clearfix">
                <i class="ti-stats-up bg-flat-color-5 p-3 font-2xl mr-3 float-left text-light"></i>
                <div class="h5 text-success mb-0 mt-1"><spring:message code="currency" arguments="${income}"/></div>
                <div class="text-muted text-uppercase font-weight-bold font-xs small"><spring:message
                    code="income"/></div>
              </div>
              <div class="b-b-1 pt-3"></div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">
                  <spring:message code="playersSold"/>
                  <span class="pull-right"><spring:message code="currency" arguments="${playersSold}"/></span>
                </li>
                <li class="list-group-item">
                  <spring:message code="ticketsSold"/>
                  <span class="pull-right"><spring:message code="currency" arguments="${ticketsSold}"/></span>
                </li>
              </ul>
            </div>
          </div>

          <div class="card">
            <div class="card-body">
              <div class="clearfix">
                <i class="ti-stats-down bg-danger p-3 font-2xl mr-3 float-left text-light"></i>
                <div class="h5 text-danger mb-0 mt-1"><spring:message code="currency" arguments="${-expenses}"/></div>
                <div class="text-muted text-uppercase font-weight-bold font-xs small"><spring:message
                    code="expenses"/></div>
              </div>
              <div class="b-b-1 pt-3"></div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">
                  <spring:message code="playersBought"/>
                  <span class="pull-right"><spring:message code="currency" arguments="${-playersBought}"/></span>
                </li>
                <li class="list-group-item">
                  <spring:message code="salaries"/>
                  <span class="pull-right"><spring:message code="currency" arguments="${-salaries}"/></span>
                </li>
                <li class="list-group-item">
                  <spring:message code="stadiumExpansion"/>
                  <span class="pull-right"><spring:message code="currency" arguments="${-stadiumExpansion}"/></span>
                </li>
            </div>
          </div>
        </div>
        <div class="col-lg-4">
          <div class="card">
            <div class="card-header">
              <strong class="card-title"><spring:message code="lastReceipts"/></strong>
            </div>
            <ul class="list-group list-group-flush">
              <c:forEach items="${lastReceipts}" var="receipt">
                <li class="list-group-item">
                  <spring:message code="receipt.${receipt.type}"/>
                  <span class="pull-right"><spring:message code="currency" arguments="${receipt.amount}"/></span>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
        <div class="col-lg-4">
          <div class="card">
            <div class="card-header">
              <strong class="card-title"><spring:message code="stadium"/></strong>
            </div>
<<<<<<< HEAD
            <div class="card-body">
=======
            <div class="card-body pt-0">
              <form:form id="sForm" modelAttribute="stadiumForm" action="${upgradeStadium}" method="post">
                <ul class="list-group list-group-flush">
                  <li class="list-group-item">
                    <spring:message code="lowSeats"/>
                    <form:input id="lowInput" cssClass="input-field pull-right" path="lowClass" type="number" min="${stadium.lowClass}" max="100000" value="${stadium.lowClass}"/>
                  </li>
                  <li class="list-group-item">
                    <spring:message code="mediumSeats"/>
                    <form:input id="mediumInput" cssClass="input-field pull-right" path="mediumClass" type="number" min="${stadium.mediumClass}" max="100000" value="${stadium.mediumClass}"/>
                  </li>
                  <li class="list-group-item">
                    <spring:message code="highSeats"/>
                    <form:input id="highInput" cssClass="input-field pull-right" path="highClass" type="number" min="${stadium.highClass}" max="100000" value="${stadium.highClass}"/>
                  </li>
                  <li class="list-group-item">
                    <spring:message code="total"/>
                    <span class="pull-right" id="total">$ 0</span>
                  </li>
                </ul>
                <input type="submit" class="btn btn-info pull-right mt-3" value="<spring:message code="upgradeStadium"/>">
              </form:form>
>>>>>>> master
            </div>
          </div>
        </div>
      </div>
    </div>
  </jsp:body>
</t:masterpage>
