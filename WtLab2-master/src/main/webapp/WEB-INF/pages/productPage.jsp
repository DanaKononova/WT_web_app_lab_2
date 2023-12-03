<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="jewelry" scope="request" type="com.wt.lab2.model.entities.jewelry.Jewelry"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<tags:master pageTitle="Car Details">

    <c:choose>
        <c:when test="${not empty inputErrors}">
            <div class="container">
                <div class="panel panel-danger">
                    <div class="panel-heading"><fmt:message key="error_title" /></div>
                    <div class="panel-body">
                        <fmt:message key="error_updating_cart" />
                        <br>
                        ${inputErrors.get(jewelry.id)}
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty successMessage}">
                <div class="container">
                    <div class="panel panel-success">
                        <div class="panel-heading"><fmt:message key="success_title" /></div>
                        <div class="panel-body">${successMessage}</div>
                    </div>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
    <div class="panel"></div>
    <div class="container">
        <h2 style="color: #165014">${jewelry.mark} ${jewelry.subMark}</h2>
        <div class="row">
            <div class="col-6">
                <img class="rounded" src="${jewelry.imageUrl}">
                <div class="panel" style="background: #9da99d"></div>
                <p class="text-justify" style="font-family: 'Felix Titling',cursive; font-size: 16px; color: #165014" class="text-light">${jewelry.description}</p>
                <div class="float-right">
                    <p class="text">Price: $${jewelry.price}</p>
                    <c:choose>
                        <c:when test="${not empty sessionScope.login}">
                            <form action="/" method="post">
                                <input type="hidden" name="command" value="cart_add">
                                <input type="hidden" name="page_type" value="product_details">
                        </c:when>
                        <c:otherwise>
                            <form action="/" method="get">
                                <input type="hidden" name="command" value="authorisation">
                        </c:otherwise>
                    </c:choose>
                        <input type="hidden" name="id" value="${jewelry.id}">
                        <input type="number" name="quantity" id="quantity${jewelry.id}" min="1" required>
                        <button class="btn btn-lg btn-outline-light text-dark border-dark float-right" type="submit"><fmt:message key="button_add" /></button>
                    </form>
                </div>
            </div>
            <div class="col-1"></div>
        </div>
    </div>
</tags:master>