<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<meta charset="UTF-8">
		<title>Controle de Bandas</title>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="nav-menu.jsp"%>

		<main class="container text-center">
			<h1 class="my-4">Bem-vindo ao Sistema de Controle de Bandas !</h1>
			<p>
				<a href="${pageContext.request.contextPath}/admins" class="btn btn-default btn-lg">Administradores</a>
				<a href="${pageContext.request.contextPath}/bands" class="btn btn-default btn-lg">Bandas</a>
				<a href="${pageContext.request.contextPath}/employers" class="btn btn-default btn-lg">Contratantes</a>
				<a href="${pageContext.request.contextPath}/liveHouses" class="btn btn-default btn-lg">Casas de Show</a>
				<a href="${pageContext.request.contextPath}/shows" class="btn btn-default btn-lg">Shows</a>
			</p>
		</main>
	</body>
</html>
