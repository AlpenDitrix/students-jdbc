<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>
            Dummy talkie web-application
        </title>
        <meta http-equiv="Content-Type"content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    </head>
     <c:if test="${not empty requestScope.error}">
           <div class="error">
               ${requestScope.error}
           </div>
   </c:if>
    <body>
            <form method="post">
            А можно ли мне
            <input type="text" name="q"
            onfocus="this.value=''"
            onblur="if (this.value=='') this.value='не программировать'"
            value="не программировать">
            <br>
            <input type="submit" value="Воспрашать!">
            </form>
            <c:if test="${not empty sessionScope.question}">
                <div>
                    Можно ли тебе ${sessionScope.question}?
                    <br>
                    ${sessionScope.answer}
                </div>
            </c:if>
</body>
</html>

