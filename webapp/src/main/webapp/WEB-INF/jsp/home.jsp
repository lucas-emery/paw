
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title>Main</title>
    <link rel="stylesheet" href="<c:url value="/css/home.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/navbar.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/background.css"/>"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
    <c:url value="/home" var="homeUrl" />

    <jsp:include page="navBar.jsp" />

    <div class="contain">

        <div class = "translucent"></div>

            <div class = "content">

                <div class="row">

                    <div class="col-sm">

                        <div class="trust">
                            <h5><spring:message code="boardTrust"/> </h5>
                            <br>
                            <h5><spring:message code="fanTrust"/> </h5>
                        </div>

                        <div class = "player">
                            <h5>Perez (Age: 28)</h5>

                            <hr>

                            <h6><spring:message code="attributes"/></h6>

                            <div class="container">
                                <div class="row">
                                    <div class="col-sm">
                                        <h7><spring:message code="finishing"/></h7>
                                    </div>
                                    <div class="col-sm">
                                        <h7><spring:message code="defending"/></h7>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm">
                                        <h7><spring:message code="passing"/></h7>
                                    </div>
                                    <div class="col-sm">
                                        <h7><spring:message code = "potential"/></h7>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm">
                                        <h7><spring:message code = "goalKeeping"/></h7>
                                    </div>
                                    <div class="col-sm">
                                        <h7><spring:message code="fitness"/> </h7>
                                    </div>
                                </div>
                            </div>

                            <hr>

                            <h6><spring:message code="finance"/> </h6>

                            <div class="container">
                                <div class="row">
                                    <div class="col-sm">
                                        <h7><spring:message code="salary"/> </h7>
                                    </div>
                                    <div class="col-sm">
                                        <h7><spring:message code="value"/> </h7>
                                    </div>
                                </div>
                            </div>

                            <hr>

                            <div class="btn-toolbar justify-content-between" role="toolbar">
                                <div class="btn-group mr-2" role="group">
                                    <a href="<c:url value="/"/>"><button class="btn btn-light"><spring:message code="sell"/> </button></a>
                                </div>
                                <div class="btn-group mr-2" role="group">
                                        <a href="<c:url value="/"/>"><button class="btn btn-light"><spring:message code="contract"/> </button></a>
                                </div>
                                <div class="btn-group mr-2" role="group">
                                        <a href="<c:url value="/"/>"><button class="btn btn-light"><spring:message code="retire"/> </button></a>
                                </div>
                            </div>

                        </div>

                        <a href="<c:url value="/"/>"><button class="button formation"><spring:message code="formation"/> </button></a>

                    </div>

                    <div class="col">

                        <table class="table table-hover bg-white small table-sm">

                            <thead>
                            <tr>
                                <th scope="col"><spring:message code="name"/> </th>
                                <th scope="col"><spring:message code="salary"/> </th>
                                <th scope="col"><spring:message code="value"/> </th>
                                <th scope="col"><spring:message code="fitness"/> </th>
                                <th scope="col"><spring:message code="age"/></th>
                            </tr>
                            </thead>

                            <tbody>

                                <c:forEach var = "i" begin = "1" end = "23">
                                    <tr>
                                        <th scope="row"><a href="<c:url value="/"/>">Perez</a></th>
                                        <td>3,000</td> 
                                        <td>400k</td> 
                                        <td>93</td> 
                                        <td>35</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <%--<c:forEach items="${playerName}" var="player">--%>
                                <%--<tr>--%>
                                    <%--<td>${player.name}</td>--%>
                                    <%--<td>${player.salary}</td>--%>
                                    <%--<td>${player.value}</td>--%>
                                    <%--<td>${player.fitness}</td>--%>
                                    <%--<td>${player.age}</td>--%>
                                <%--</tr>--%>
                            <%--</c:forEach>--%>

                        </table>

                    </div>

                </div>

            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</body>
</html>
