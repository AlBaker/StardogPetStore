<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.example.stardog.Dog"%>
<%@page import="java.util.List"%>
<html>
<head>
<title>Stardog Pet Store - Spring Framework Example</title>
<link rel="stylesheet" type="text/css" href="resources/main.css">
</head>
<body>
	<div id="header-container">
		<div id="header">
			
			<span id="headline">Stardog Pet Store - Spring Framework Example</span>
		</div>
	</div>

	<div id="main-container">
		<div id="content">
		    
		    <span class="button"><a href="create">Add Dog</a></span>
		
			<table  width="450" class="wiki-table">
				<tr>
					<td>Name</td>
					<td>Wikipedia</td>
					<td>Photo</td>
					<td>Action</td>
				</tr>
				<%
					List<Dog> dogList = (List<Dog>) request.getAttribute("dogList");
					for (Dog dog : dogList) {
						out.println("<tr><td>" + dog.getName() + "</td>"+
										"<td> <a href=\"" + dog.getWikiPage() +	"\">About</a></td>" +
										"<td><img width=\"35\" height=\"35\" src=\"" + dog.getPicture() + "\"/></td>"+
										"<td><a href=\"delete/"+dog.getName()+"\">Delete</a></td>"+
										"</tr>");
					}
				%>
			</table>
		</div>
	</div>
</body>
</html>
