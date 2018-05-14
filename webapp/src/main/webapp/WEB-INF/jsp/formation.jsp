<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title>Formation</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/css/navbar.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/background.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/formation.css"/>"/>

</head>
<body>
<c:url value="/formation" var="formationUrl" />

<jsp:include page="navBar.jsp" />

<div class="contain">
    <div class = "translucent"></div>
    <div class = "content">
        <div class="row">
            <div class="col-sm">
                <div class="row">

                    <div class="col">
                        <h5><spring:message code="formation"/> </h5>
                    </div>
                    <div class="col">
                        <select class="form-control">
                            <option>4-4-2</option>
                        </select>
                    </div>

                    <div class="col">
                        <h5><spring:message code="penaltyTaker"/> </h5>
                    </div>
                    <div class="col">
                        <select class="form-control">
                            <c:forEach items="${formation.starters.keySet()}" var="player">
                                <option><c:out value="${player.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>

                </div>

                <div class="row">

                    <div class="col">
                        <h5><spring:message code="captain"/> </h5>
                    </div>
                    <div class="col">
                        <select class="form-control">
                            <c:forEach items="${formation.starters.keySet()}" var="player">
                                <option><c:out value="${player.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col">
                        <h5><spring:message code="freekickTaker"/> </h5>
                    </div>
                    <div class="col">
                        <select class="form-control">
                            <c:forEach items="${formation.starters.keySet()}" var="player">
                                <option><c:out value="${player.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>

                </div>

                <div class="row">

                    <div class="col">
                        <h5><spring:message code="attitude"/> </h5>
                    </div>
                    <div class="col">
                        <select class="form-control">
                            <option><spring:message code="balanced"/></option>
                            <option><spring:message code="ofensive"/></option>
                            <option><spring:message code="defensive"/></option>
                        </select>
                    </div>

                    <div class="col">
                        <h5><spring:message code="pressure"/> </h5>
                    </div>
                    <div class="col">
                        <select class="form-control">
                            <option><spring:message code="low"/></option>
                            <option><spring:message code="medium"/></option>
                            <option><spring:message code="high"/></option>
                        </select>

                    </div>

                </div>

                <div class = "formation">
                </div>
            </div>

            <div class="col">

                <table class="table table-hover bg-white small table-sm">

                    <thead>
                    <tr>
                        <th scope="col"><spring:message code="position"/> </th>
                        <th scope="col"><spring:message code="name"/> </th>
                        <th scope="col"><spring:message code="fitness"/> </th>
                        <th scope="col"><spring:message code="skillLevel"/></th>
                        <th scope="col"><spring:message code="goalKeeping"/></th>
                        <th scope="col"><spring:message code="finishing"/></th>
                        <th scope="col"><spring:message code="defending"/></th>
                        <th scope="col"><spring:message code="passing"/></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${players}" var="player">
                        <tr>
                            <td><c:out value="${formation.getPlayerPosition(player.id)}"/></td>
                            <td><c:out value="${player.name}"/></td>
                            <td><c:out value="${player.fitness}"/></td>
                            <td><c:out value="${player.skillLevel}"/></td>
                            <td><c:out value="${player.goalKeeping}"/></td>
                            <td><c:out value="${player.finish}"/></td>
                            <td><c:out value="${player.defending}"/></td>
                            <td><c:out value="${player.passing}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>

            </div>

        </div>

    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</body>
</html>
