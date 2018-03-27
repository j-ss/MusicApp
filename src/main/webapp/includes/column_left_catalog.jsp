<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside id="sidebarA">
    <nav>

        <ul>
            <li>
                <a href="<c:url value='/'/> ">Home</a>
            </li>
            <li>
                <a class="current" href="<c:url value='/catalog'/> ">Catalog</a>
            </li>
            <li>
                <a  href="<c:url value='/email'/> ">Email</a>
            </li>
            <li>
                <a  href="<c:url value='/customer_service'/> ">Customer service</a>
            </li>
        </ul>

    </nav>
</aside>