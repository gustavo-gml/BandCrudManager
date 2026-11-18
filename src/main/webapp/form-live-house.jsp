<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - ${not empty liveHouse ? 'Atualizar' : 'Adicionar'} Casa de Show</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>
	
	<div id="container" class="container-fluid">
		<h3 class="page-header">${not empty liveHouse ? 'Atualizar' : 'Adicionar'} Casa de Show</h3>
		
		<form action="${pageContext.request.contextPath}/liveHouses/${action}" 
			method="POST">
			
			<input type="hidden" value="${liveHouse.getId()}" name="liveHouseId">
			
			<div class="row">
				<div class="form-group col-md-4">
					<label for="razaoSocial">Razão Social</label>
					<input type="text" class="form-control" id="razaoSocial" name="razaoSocial" 
						   autofocus="autofocus" placeholder="Razão Social da Casa de Show" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe a razão social.')"
						   oninput="setCustomValidity('')"
						   value="${liveHouse.getRazaoSocial()}" />
				</div>
				
				<div class="form-group col-md-4">
					<label for="nomeFantasia">Nome Fantasia</label>
					<input type="text" class="form-control" id="nomeFantasia" name="nomeFantasia" 
						   placeholder="Nome Fantasia" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o nome fantasia.')"
						   oninput="setCustomValidity('')" 
						   value="${liveHouse.getNomeFantasia()}"/>
				</div>

				<div class="form-group col-md-4">
					<label for="cnpj">CNPJ</label>
					<input type="text" class="form-control" id="cnpj" name="cnpj" 
						   placeholder="00.000.000/0000-00" 
						   maxlength="14"
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o CNPJ.')"
						   oninput="setCustomValidity('')" 
						   value="${liveHouse.getCnpj()}"/>
				</div>
				
				
				<div class="form-group col-md-4">
					<label for="endereco">Endereço</label>
					<input type="text" class="form-control" id="endereco" name="endereco" 
						   autofocus="autofocus" placeholder="Endereço da Casa de Show" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o endereço.')"
						   oninput="setCustomValidity('')"
						   value="${liveHouse.getEndereco()}" />
				</div>
			</div>
			
			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/liveHouses" 
					   class="btn btn-default">Cancelar</a>
					
					<button type="submit" 
						    class="btn btn-primary">${not empty liveHouse ? 'Atualizar' : 'Cadastrar'} Casa de Show</button>
				</div>
			</div>
		</form>
		
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>