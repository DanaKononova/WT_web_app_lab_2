<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<jsp:useBean id="jewelries" scope="request" type="java.util.List"/>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<tags:master pageTitle="Car List">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="messages"/>

    <c:choose>
        <c:when test="${not empty inputErrors}">
            <div class="container">
                <div class="panel panel-danger">
                    <div class="panel-heading"><fmt:message key="error_title"/></div>
                    <div class="panel-body">
                        <fmt:message key="error_updating_cart"/>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty successMessage}">
                <div class="container">
                    <div class="panel panel-success">
                        <div class="panel-heading"><fmt:message key="success_title"/></div>
                        <div class="panel-body">${successMessage}</div>
                    </div>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>

    <div class="row">
        <div class="container">
            <div class="container">
                <form class="float-right">
                    <input name="query" value="${param.query}">
                    <input type="hidden" name="command" value="Product_List">
                    <button class="btn btn-light"><fmt:message key="button_search"/></button>
                </form>
            </div>
        </div>
    </div>
    <div class="row" style="background: #a5d9a5">
        <div class="col-8">
            <table class="table-bordered text-center">
                <thead>
                <tr class="bg-light">
                    <td style="background: #949894"><fmt:message key="car_image"/></td>
                    <td style="background: #949894">
                        <fmt:message key="item_brand"/>
                    </td>
                    <td style="background: #949894">
                        <fmt:message key="item_model"/>
                    </td>
                    <td style="background: #949894">
                        <fmt:message key="item_type"/>
                    </td>
                    <td style="background: #949894">
                        <fmt:message key="item_years"/>
                    </td>
                    <td style="background: #949894">
                        <fmt:message key="item_price"/>
                    </td>
                    <td style="background: #949894"><fmt:message key="item_add_to_basket"/></td>
                </tr>
                </thead>
                <c:forEach var="jewelry" items="${jewelries}">
                    <tr>
                        <td class="align-middle">
                            <img class="rounded" src="${jewelry.imageUrl}">
                        </td>
                        <td class="align-middle">
                            <form id="productFormBrand" action="/" method="GET">
                                <input type="hidden" name="command" value="product_details">
                                <input type="hidden" name="car_id" value="${jewelry.id}">
                                <span style="font-family: 'Times New Roman', sans-serif; font-size: 22px; color: #ff0000; font-weight: bold;">
                                        ${jewelry.mark}
                                </span>
                            </form>
                        </td>
                        <td class="align-middle">
                            <form id="productFormModel" action="/" method="GET">
                                <input type="hidden" name="command" value="product_details">
                                <input type="hidden" name="car_id" value="${jewelry.id}">
                                <span style="font-family: 'Times New Roman', sans-serif; font-size: 22px; color: #36227e; font-weight: bold;">
                                        ${jewelry.subMark}
                                </span>
                            </form>
                        </td>
                        <td class="align-middle">
                            <form id="productFormType" action="/" method="GET">
                                <input type="hidden" name="command" value="product_details">
                                <input type="hidden" name="car_id" value="${jewelry.id}">
                                <span style="font-family: 'Times New Roman', sans-serif; font-size: 22px; color: #177581; font-weight: bold;">
                                        ${jewelry.type}
                                </span>
                            </form>
                        </td>
                        <td class="align-middle">
                            <form id="productFormYears" action="/" method="GET">
                                <input type="hidden" name="command" value="product_details">
                                <input type="hidden" name="car_id" value="${jewelry.id}">
                                <span style="font-family: 'Times New Roman', sans-serif; font-size: 24px; color: #73398f; font-weight: bold;">
                                        ${jewelry.years}
                                </span>
                            </form>
                        </td>
                        <td class="align-middle">
                            <form id="productFormPrice" action="/" method="GET">
                                <input type="hidden" name="command" value="product_details">
                                <input type="hidden" name="car_id" value="${jewelry.id}">
                                <span style="font-family: 'Times New Roman', sans-serif; font-size: 22px; color: #943912; font-weight: bold;">
                                        ${jewelry.price}
                                </span>
                            </form>
                        </td>
                        <td class="align-middle">
                            <c:choose>
                            <c:when test="${not empty sessionScope.login}">
                            <form action="/" method="post">
                                <input type="hidden" name="command" value="cart_add">
                                </c:when>
                                <c:otherwise>
                                <form action="/" method="get">
                                    <input type="hidden" name="command" value="authorisation">
                                    </c:otherwise>
                                    </c:choose>
                                    <input type="hidden" name="id" value="${jewelry.id}">
                                    <input type="hidden" name="page_type" value="productList">
                                    <input type="number" name="quantity" id="quantity${jewelry.id}" min="1" required>
                                    <button class="btn btn-lg btn-outline-light text-dark border-dark float-right"
                                            type="subm  it" style="font-size: 14px"><fmt:message
                                            key="button_add"/></button>
                                </form>
                                <c:if test="${not empty inputErrors.get(jewelry.id)}">
                                <div class="error" style="color: red">${inputErrors[jewelry.id]}</div>
                                </c:if>
                        </td>
                        <td class="align-middle">
                            <form id="productFormMore" action="/" method="GET">
                                <input type="hidden" name="command" value="product_details">
                                <input type="hidden" name="car_id" value="${jewelry.id}">
                                <button type="submit">
                                    <fmt:message key="more_info"/>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</tags:master>