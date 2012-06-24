<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<METAÂ http-equiv="Content-Type" Â content="text/html;charset=UTF-8">
<title>Create Dog Record</title>
<link rel="stylesheet" type="text/css" href="resources/main.css">
</head>
<body>

	<div id="header-container">
		<div id="header">

			<span id="headline">Create Dog Record</span>
		</div>
	</div>
	<div id="main-container">
		<div id="content">
			<form:form modelAttribute="dog" method="post">
				<fieldset>
					<legend>Dog Record Fields</legend>
					<p>
						<form:label for="name" path="name" cssErrorClass="error">Name</form:label>
						<br />
						<form:input path="name" />
						<form:errors path="name" />
					</p>
					<p>
						<form:label for="wikiPage" path="wikiPage" cssErrorClass="error">Wiki URL</form:label>
						<br />
						<form:input path="wikiPage" />
						<form:errors path="wikiPage" />
					</p>
					<p>
						<form:label for="picture" path="picture" cssErrorClass="error">Picture URL</form:label>
						<br />
						<form:input path="picture" />
						<form:errors path="picture" />
					</p>
					<p>
						<input type="submit" />
					</p>
				</fieldset>
			</form:form>
		</div>
	</div>
</body>
</html>