<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="pageTitle" required="true" %>


<html>
<head>
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lobster&family=Lobster+Two:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
</head>
<body style="background: #9da99d">
<header>
    <fmt:setLocale value="${sessionScope.lang}" />
    <fmt:setBundle basename="messages" />
    <div class="container">
        <div class="row d-flex align-items-center justify-content-center">
            <div class="col-6 text-center">
                <h1 class="text-center">
                    <a style="font-family: 'Cursive'; color: #FFD700" class="text-light" href="<c:url value="/?command=Product_List"/>">
                        AutoDeals
                    </a>
                    <br>
                    <a style="font-family: 'Ubuntu'; color: #00FF00" class="text-light">
                        <c:if test="${not empty sessionScope.login}">
                            <fmt:message key="master_username" />: ${sessionScope.login}
                        </c:if>
                    </a>
                </h1>
                <form action="<c:url value="/"/>" method="get" class="mb-2 text-center">
                    <input type="hidden" name="command" value="cart">
                    <c:choose>
                        <c:when test="${not empty sessionScope.login}">
                            <button class="btn btn-warning">
                                <fmt:message key="master_cart" />:
                                <span id="cartTotalCost"><c:out value="${basket.totalCost}"/></span>$
                            </button>
                        </c:when>
                        <c:otherwise>
                            <form action="<c:url value="/"/>" method="get">
                                <input type="hidden" name="command" value="authorisation">
                                <button class="btn btn-warning"> <fmt:message key="master_cart" />:</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${not empty sessionScope.login}">
                        <a href="<c:url value="/?command=user_orders"/>" class="btn btn-warning mt-2"><fmt:message key="master_orders_page" /></a>
                    </c:if>
                </form>
                <div class="d-flex flex-column align-items-center text-center">
                    <li style="color: white"><a class="btn btn-info" href="?sessionLocale=en"><fmt:message key="label.lang.en" /></a></li>
                    <li style="color: white"><a class="btn btn-info" href="?sessionLocale=ru"><fmt:message key="label.lang.ru" /></a></li>
                </div>
            </div>
            <div class="col-6 text-center">
                <div class="float-right" style="display: flex; flex-direction: column; align-items: end">
                    <c:if test="${role.equals('Admin')}">
                        <form action="<c:url value="/"/>">
                            <input type="hidden" name="command" value="admin_orders">
                            <button class="btn btn-warning"> <fmt:message key="master_orders_page" /> </button>
                        </form>
                        <form action="<c:url value="/"/>">
                            <input type="hidden" name="command" value="admin_users">
                            <button class="btn btn-warning"> <fmt:message key="master_users_page" /> </button>
                        </form>
                    </c:if>
                    <c:if test="${role.equals('User')}">
                        <!-- Removed the form for user orders -->
                    </c:if>
                </div>
                <div class="d-flex flex-column align-items-center text-center">
                    <a href="<c:url value="/?command=registration"/>" class="btn btn-danger mb-2 d-flex align-items-center justify-content-center"><fmt:message key="master_registration" /></a>

                    <c:choose>
                        <c:when test="${not empty sessionScope.login}">
                            <a href="<c:url value="/?command=logout"/>" class="btn btn-success d-flex align-items-center justify-content-center"><fmt:message key="master_logout" /></a>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/?command=authorisation"/>" class="btn btn-success d-flex align-items-center justify-content-center"><fmt:message key="master_login" /></a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</header>

<main>
    <p></p>
    <jsp:doBody/>
</main>
</body>
</html>