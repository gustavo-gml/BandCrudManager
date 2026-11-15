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
			<h3 class="page-header">Adicionar Usuário</h3>

			<form action="${pageContext.request.contextPath}/admins/${action}" method="POST">
				<input type="hidden" value="${admin.getId()}" name="adminId">
				<div class="row">
					<div class="form-group col-md-4">
					<label for="name">Nome</label>
						<input type="text" class="form-control" id="name" name="name" 
							   autofocus="autofocus" placeholder="Nome do usuário" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o nome do usuário.')"
							   oninput="setCustomValidity('')"
							   value="${admin.getName()}">
					</div>

					<div class="form-group col-md-4">
						<label for="gender">Sexo</label>
						<select id="gender" class="form-control selectpicker" name="gender" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o sexo')"
							    oninput="setCustomValidity('')">
					      <option value="" disabled ${not empty admin ? "" : "selected"}>Selecione o sexo</option>
						  <option value="F" ${admin.getGender() == 'F' ? "selected" : ""}>Feminino</option>
						  <option value="M" ${admin.getGender() == 'M' ? "selected" : ""}>Masculino</option>
						</select>
					</div>
					
					<div class="form-group col-md-4">
					<label for="mail">E-mail</label>
						<input type="email" class="form-control" id="mail" name="mail" 
							   autofocus="autofocus" placeholder="E-mail do usuário" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o email do usuário.')"
							   oninput="setCustomValidity('')"
							   value="${admin.getEmail()}">
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/admins" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty admin ? "Alterar Usuário" : "Cadastrar Usuário"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
