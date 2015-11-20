<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>NBME MVC File Downloads</title>
	<link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet" type="text/css">
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet" type="text/css">
</head>
<body>
<h2>Spring MVC download files:<spring:message code="app.title" text="NBME document service" /></h2>

<a href="files/nbme">Click and download file here</a>
<form:form action="file" method="get" modelAttribute="allFiles">
	<form:select path="" items="${allFiles}" itemLabel="name" itemValue="name" name="fileName">
		<%--<form:options items="${allfiles}"></form:options>--%>
	</form:select>
	<input type="submit" value="download"/>
</form:form>
</body>
</html>
