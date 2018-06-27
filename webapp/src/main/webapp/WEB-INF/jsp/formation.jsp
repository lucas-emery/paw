<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:masterpage active="tactics">
    <jsp:attribute name="styles">
        <link rel="stylesheet" href="<c:url value="/assets/css/formation.css"/>"/>
    </jsp:attribute>
  <jsp:attribute name="scripts">
        <script src="<c:url value="/assets/js/formation.js"/>"></script>
    </jsp:attribute>

  <jsp:body>
    <c:url value="/formation" var="formationUrl"/>
    <c:url value="/processFormationForm" var="postPath"/>

    <div class="modal fade" id="staticModal" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel"
         aria-hidden="true" data-backdrop="static">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="staticModalLabel"><spring:message code="formationChange"/></h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
        </div>
      </div>
    </div>


    <div class="animated fadeIn p-5">
      <div class="row">
        <div class="col-6">
          <div class="card">
            <div class="card-body">
                <div style="position: relative; left: 0; top: 0;">
                <img src="../assets/img/soccerField.jpg"  class="formationImage"/>

                    <form:form modelAttribute="formationForm" action="${postPath}" method="post"
                               data-error="${error}" id="form">

                    <div class="formationContent">
                    <%-- Delanteros --%>
                    <div class="row forwards d-flex justify-content-center">
                      <form:select path="leftWing" id="lw" onchange="update(this.id, value)">
                        <form:option value="${lw.id}"><c:out value="${lw.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != lw && player.getPosition() == 3}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                      <form:select path="leftForward" id="lf" onchange="update(this.id, value)">
                        <form:option value="${lf.id}"><c:out value="${lf.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != lf && player.getPosition() == 3}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                      <form:select path="striker" id="st" onchange="update(this.id, value)">
                        <form:option value="${st.id}"><c:out value="${st.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != st && player.getPosition() == 3}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                      <form:select path="rightForward" id="rf" onchange="update(this.id, value)">
                        <form:option value="${rf.id}"><c:out value="${rf.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != rf && player.getPosition() == 3}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                      <form:select path="rightWing" id="rw" onchange="update(this.id, value)">
                        <form:option value="${rw.id}"><c:out value="${rw.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != rw && player.getPosition() == 3}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                    </div>

                    <%-- Volantes --%>
                    <div class="row topMid d-flex justify-content-center">
                      <form:select path="centerAttackingMid" id="cam" onchange="update(this.id, value)">
                        <form:option value="${cam.id}"><c:out value="${cam.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != cam && player.getPosition() == 2}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                    </div>
                    <div class="row mid d-flex justify-content-center">
                      <form:select path="leftMid" id="lm" onchange="update(this.id, value)">
                        <form:option value="${lm.id}"><c:out value="${lm.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != lm && player.getPosition() == 2}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                      <form:select path="leftCenterMid" id="lcm" onchange="update(this.id, value)">
                        <form:option value="${lcm.id}"><c:out value="${lcm.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != lcm && player.getPosition() == 2}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                      <form:select path="rightCenterMid" id="rcm" onchange="update(this.id, value)">
                        <form:option value="${rcm.id}"><c:out value="${rcm.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != rcm && player.getPosition() == 2}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                      <form:select path="rightMid" id="rm" onchange="update(this.id, value)">
                        <form:option value="${rm.id}"><c:out value="${rm.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != rm && player.getPosition() == 2}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                    </div>
                    <div class="row lowMid d-flex justify-content-center">
                      <form:select path="centerDefensiveMid" id="cdm" onchange="update(this.id, value)">
                        <form:option value="${cdm.id}"><c:out value="${cdm.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != cdm && player.getPosition() == 2}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                    </div>

                    <%-- Defensores --%>
                    <div class="row backs d-flex justify-content-between">
                      <form:select path="leftBack" id="lb" onchange="update(this.id, value)">
                        <form:option value="${lb.id}"><c:out value="${lb.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != lb && player.getPosition() == 1}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                        <form:select path="rightBack" id="rb" onchange="update(this.id, value)">
                            <form:option value="${rb.id}"><c:out value="${rb.name}"/></form:option>
                            <c:forEach items="${players}" var="player">
                                <c:if test="${player != rb && player.getPosition() == 1}">
                                    <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                                </c:if>
                            </c:forEach>
                        </form:select>
                    </div>
                      <div class="row backsCenter d-flex justify-content-center">
                      <form:select path="leftCenterBack" id="lcb" onchange="update(this.id, value)">
                        <form:option value="${lcb.id}"><c:out value="${lcb.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != lcb && player.getPosition() == 1}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                      <form:select path="centerBack" id="cb" onchange="update(this.id, value)">
                        <form:option value="${cb.id}"><c:out value="${cb.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != cb && player.getPosition() == 1}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                      <form:select path="rightCenterBack" id="rcb" onchange="update(this.id, value)">
                        <form:option value="${rcb.id}"><c:out value="${rcb.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != rcb && player.getPosition() == 1}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                    </div>

                    <%-- Arquero --%>
                    <div class="row goalKeeper d-flex justify-content-center">
                      <form:select path="goalkeeper" id="gk" onchange="update(this.id, value)">
                        <form:option value="${gk.id}"><c:out value="${gk.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != gk && player.getPosition() == 0}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                    </div>
                </div>

                <br>

                <div class="alert alert-danger" role="alert" id="error">
                  <spring:message code="errorFormation"/>
                </div>

                <ul class="list-group list-group-flush">

                  <li class="list-group-item">
                    <spring:message code="substitutes"/> <p></p>

                  <%-- Suplentes --%>
                    <form:select path="substitute1" id="sub1" onchange="update(this.id, value)">
                      <form:option value="${sub1.id}"><c:out value="${sub1.name}"/></form:option>
                      <c:forEach items="${players}" var="player">
                        <c:if test="${player != sub1}">
                          <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                        </c:if>
                      </c:forEach>
                    </form:select>

                    <form:select path="substitute2" id="sub2" onchange="update(this.id, value)">
                      <form:option value="${sub2.id}"><c:out value="${sub2.name}"/></form:option>
                      <c:forEach items="${players}" var="player">
                        <c:if test="${player != sub2}">
                          <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                        </c:if>
                      </c:forEach>
                    </form:select>

                    <form:select path="substitute3" id="sub3" onchange="update(this.id, value)">
                      <form:option value="${sub3.id}"><c:out value="${sub3.name}"/></form:option>
                      <c:forEach items="${players}" var="player">
                        <c:if test="${player != sub3}">
                          <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                        </c:if>
                      </c:forEach>
                    </form:select>

                    <form:select path="substitute4" id="sub4" onchange="update(this.id, value)">
                      <form:option value="${sub4.id}"><c:out value="${sub4.name}"/></form:option>
                      <c:forEach items="${players}" var="player">
                        <c:if test="${player != sub4}">
                          <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                        </c:if>
                      </c:forEach>
                    </form:select>

<<<<<<< HEAD
                    <form:select path="substitute5" id="sub5" onchange="update(this.id, value)">
                      <form:option value="${sub5.id}"><c:out value="${sub5.name}"/></form:option>
                      <c:forEach items="${players}" var="player">
                        <c:if test="${player != sub5}">
                          <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                        </c:if>
                      </c:forEach>
                    </form:select>

                    <form:select path="substitute6" id="sub6" onchange="update(this.id, value)">
                      <form:option value="${sub6.id}"><c:out value="${sub6.name}"/></form:option>
                      <c:forEach items="${players}" var="player">
                        <c:if test="${player != sub6}">
                          <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
=======
                <div class="row">
                  <div class="col">
                    <h6><spring:message code="pressure"/></h6>
                    <form:select path="pressure" id="pressure">
                      <form:option value="${pressure}"><spring:message code="pressure.${pressure}"/></form:option>
                      <c:forEach items="${pressures}" var="pres">
                        <c:if test="${pres != pressure}">
                          <form:option value="${pres}"><spring:message code="pressure.${pres}"/></form:option>
                        </c:if>
                      </c:forEach>
                    </form:select>
                  </div>
                  <div class="col">
                    <h6><spring:message code="attitude"/></h6>
                    <form:select path="attitude" id="attitude">
                      <form:option value="${attitude}"><spring:message code="attitude.${attitude}"/></form:option>
                      <c:forEach items="${attitudes}" var="att">
                        <c:if test="${att != attitude}">
                          <form:option value="${att}"><spring:message code="attitude.${att}"/></form:option>
>>>>>>> master
                        </c:if>
                      </c:forEach>
                    </form:select>

                    <form:select path="substitute7" id="sub7" onchange="update(this.id, value)">
                      <form:option value="${sub7.id}"><c:out value="${sub7.name}"/></form:option>
                      <c:forEach items="${players}" var="player">
                        <c:if test="${player != sub7}">
                          <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                        </c:if>
                      </c:forEach>
                    </form:select>
                  </li>

                  <li class="list-group-item">
                    <spring:message code="captain"/>
                    <span class="pull-right">
                      <form:select path="captain" id="cap">
                        <form:option value="${captain.id}"><c:out value="${captain.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != captain}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                    </span>
                  </li>

                  <li class="list-group-item">
                    <spring:message code="freekickTaker"/>
                    <span class="pull-right">
                      <form:select path="freeKickTaker" id="fk">
                        <form:option value="${fk.id}"><c:out value="${fk.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != fk}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                    </span>
                  </li>

                  <li class="list-group-item">
                    <spring:message code="penaltyTaker"/>
                    <span class="pull-right">
                      <form:select path="penaltyTaker" id="pen">
                        <form:option value="${pen.id}"><c:out value="${pen.name}"/></form:option>
                        <c:forEach items="${players}" var="player">
                          <c:if test="${player != pen}">
                            <form:option value="${player.id}"><c:out value="${player.name}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                    </span>
                  </li>

                <li class="list-group-item">
                  <spring:message code="pressure"/>
                  <span class="pull-right">
                      <form:select path="pressure" id="pressure">
                        <form:option value="${pressure}"><c:out value="${pressure}"/></form:option>
                        <c:forEach items="${pressures}" var="pres">
                          <c:if test="${pres != pressure}">
                            <form:option value="${pres}"><c:out value="${pres}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                  </span>
                </li>
                  <li class="list-group-item">
                    <spring:message code="attitude"/>
                    <span class="pull-right">
                      <form:select path="attitude" id="attitude">
                        <form:option value="${attitude}"><c:out value="${attitude}"/></form:option>
                        <c:forEach items="${attitudes}" var="att">
                          <c:if test="${att != attitude}">
                            <form:option value="${att}"><c:out value="${att}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                    </span>
                  </li>
                  <li class="list-group-item">
                    <spring:message code="formation"/>
                    <span class="pull-right">
                      <form:select path="formation" onchange="changeFormation(value)">
                        <form:option value="${formation}"><c:out value="${formation}"/></form:option>
                        <c:forEach items="${formations}" var="formi">
                          <c:if test="${formi != formation}">
                            <form:option value="${formi}"><c:out value="${formi}"/></form:option>
                          </c:if>
                        </c:forEach>
                      </form:select>
                    </span>
                </li>

                <li class="list-group-item">
                   <span class="pull-right">
                     <input type="submit" value="<spring:message code="save"/>" class="btn btn-light" id="submit"/>
                   </span>
                </li>
                </ul>
              </form:form>
          </div>
        </div>
        </div>
        </div>

        <div class="col-6">
          <div class="card">
            <div class="card-body">
              <table class="table table-hover bg-white small table-sm">
                <thead>
                <tr>
                  <th scope="col"><spring:message code="name"/></th>
                  <th scope="col"><spring:message code="fitness"/></th>
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
                    <td><c:out value="${player.name}"/></td>
                    <td><c:out value="${player.fitness}"/></td>
                    <td><c:out value="${player.skillLevel}"/></td>
                    <td><c:out value="${player.goalKeeping}"/></td>
                    <td><c:out value="${player.finishing}"/></td>
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
    </div>
    </div>

  </jsp:body>
</t:masterpage>
