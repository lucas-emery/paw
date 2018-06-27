<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
  <jsp:attribute name="styles">
    <link rel="stylesheet" href="<c:url value="/assets/css/background.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/assets/css/register.css"/>"/>
  </jsp:attribute>
  <jsp:body>
    <c:url value="/login" var="loginUrl"/>

    <div class="sufee-login d-flex align-content-center flex-wrap">
      <div class="container">
        <div class="login-content">
          <div class="login-logo">
              <%--<img class="align-content" src="images/logo.png" alt="Holis">--%>
            <span class="align-content"><spring:message code="appName"/></span>
          </div>
          <div class="login-form">
            <form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
              <div class="form-group">
                <label for="username"><spring:message code="username"/></label>
                <input id="username" name="j_username" type="text" class="form-control" placeholder="<spring:message code="username"/>"/>
              </div>
              <div class="form-group">
                <label for="password"><spring:message code="password"/></label>
                <input id="password" name="j_password" type="password" class="form-control" placeholder="<spring:message code="password"/>"/>
              </div>
              <div class="checkbox">
                <label>
                  <input name="j_rememberme" type="checkbox"/> <spring:message code="remember_me"/>
                </label>
              </div>
              <button type="submit" class="btn btn-success btn-flat m-b-30 m-t-30"><spring:message code="signIn"/></button>

              <div class="register-link m-t-15 text-center">
                <p><spring:message code="dontHaveAccount"/> <a href="<c:url value="/register"/>"> <spring:message code="signUp"/></a></p>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </jsp:body>
</t:genericpage>
