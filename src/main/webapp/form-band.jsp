<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="nav-menu.jsp"%>
			
		<div id="container" class="container-fluid">
			<h3 class="page-header">Adicionar Banda</h3>

			<form action="${pageContext.request.contextPath}/bands/${action}" method="POST">
			
				<input type="hidden" value="${band.getId()}" name="bandId">
				
				<div class="row">
				
					<div class="form-group col-md-6">
					
					<label for="name">Nome</label>
					<input type="text" class="form-control" id="name" name="name" 
							   autofocus="autofocus" placeholder="Nome da banda" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o nome da banda.')"
							   oninput="setCustomValidity('')"
							   value="${band.getName()}">
					
					</div>
					
					<div class="form-group col-md-6">
					
					<label for="musicGenre">Estilo Musical</label>
					<input type="text" class="form-control" id="musicGenre" name="musicGenre" 
							   autofocus="autofocus" placeholder="Nome da banda" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o estilo musical da banda.')"
							   oninput="setCustomValidity('')"
							   value="${band.getMusicGenre()}">
					
					</div>
					
					
					<div class="form-group col-md-6">
					
					<label for="members">Quantidade de Integrantes</label>
					<input type="number" class="form-control" id="members" name="members" 
							   autofocus="autofocus" placeholder="Número total de integrantes" 
							   required value="${band.getMembers()}">
					
					</div>
					
					
					<div class="form-group col-md-6">
					
					<label for="fundationDate">Data de Fundação</label>
					<input type="date" class="form-control" id="fundationDate" name="fundationDate" 
							   autofocus="autofocus" placeholder="Data de criação da banda" 
							   required value="${band.getFundationDate()}">
					
					</div>

					
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/bands" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty band ? "Alterar Banda" : "Criar Banda"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
