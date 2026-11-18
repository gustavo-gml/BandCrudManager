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

		<main class="container">

			<div class="page-header">
				<h1>Controle de Bandas <small>Bandas, contratantes, casas de show e os shows que ligam os três</small></h1>
			</div>

			<div class="row">
				<div class="col-md-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title"><span class="glyphicon glyphicon-music"></span>&nbsp;Bandas</h3>
						</div>
						<div class="panel-body">
							<p>Os grupos que se apresentam nos shows.</p>
							<p class="text-muted">Nome, gênero, integrantes e fundação.</p>
						</div>
						<div class="panel-footer">
							<a href="${pageContext.request.contextPath}/bands" class="btn btn-danger btn-sm">Ver bandas</a>
						</div>
					</div>
				</div>

				<div class="col-md-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title"><span class="glyphicon glyphicon-briefcase"></span>&nbsp;Contratantes</h3>
						</div>
						<div class="panel-body">
							<p>Empresas que contratam as bandas.</p>
							<p class="text-muted">Razão social, nome fantasia e CNPJ.</p>
						</div>
						<div class="panel-footer">
							<a href="${pageContext.request.contextPath}/employers" class="btn btn-danger btn-sm">Ver contratantes</a>
						</div>
					</div>
				</div>

				<div class="col-md-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title"><span class="glyphicon glyphicon-map-marker"></span>&nbsp;Casas de Show</h3>
						</div>
						<div class="panel-body">
							<p>Os locais onde os shows acontecem.</p>
							<p class="text-muted">Razão social, nome fantasia, CNPJ e endereço.</p>
						</div>
						<div class="panel-footer">
							<a href="${pageContext.request.contextPath}/liveHouses" class="btn btn-danger btn-sm">Ver casas de show</a>
						</div>
					</div>
				</div>
			</div>

			<div class="well">
				<h3><span class="glyphicon glyphicon-calendar"></span>&nbsp;Shows</h3>
				<p>
					Um show liga uma banda, um contratante e uma casa de show, com valor do cachê,
					data e hora e tempo de show. Cadastre os três primeiro, depois escolha cada um ao agendar.
				</p>
				<a href="${pageContext.request.contextPath}/shows" class="btn btn-danger">Ver shows</a>
				<a href="${pageContext.request.contextPath}/shows/form" class="btn btn-default">Adicionar show</a>
			</div>

			<p class="text-muted">
				Administradores (nome, e-mail e senha) têm acesso ao sistema.
				<a href="${pageContext.request.contextPath}/admins">Gerenciar administradores</a>
			</p>

		</main>

		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>

