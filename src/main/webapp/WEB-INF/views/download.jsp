<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>NBME MVC File Downloads</title>
	<link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet" type="text/css"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet" type="text/css"></link>
</head>
<body>
<h2>Spring MVC download files: ${message}</h2>

<a href="files/nbme">Click and download file here</a>
<%--<form:form action="file" method="get" modelAttribute="fileNames">
	<form:select  path="fileNames">
		<form:options items="${fileNames}"></form:options>
	</form:select>
	<input type="submit" value="download"/>
</form:form>--%>
</body>
</html>
