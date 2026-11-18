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
				<!-- select (chave estrangeira) -->
				<div class="form-group col-md-4">
					<label for="employerId">Contratante</label>
					<select class="form-control" id="employerId" name="employerId" 
						    autofocus="autofocus" 
						    required 
						    oninvalid="this.setCustomValidity('Por favor, selecione o contratante.')"
						    oninput="setCustomValidity('')">
						<option value="">Selecione o contratante</option>
						<c:forEach var="employer" items="${employers}">
							<option value="${employer.getId()}" ${liveHouse.getEmployer().getId() == employer.getId() ? 'selected' : ''}>${employer}</option>
						</c:forEach>
					</select>
				</div>
				
				<!-- text -->
				<div class="form-group col-md-4">
					<label for="nomeFantasia">Nome Fantasia</label>
					<input type="text" class="form-control" id="nomeFantasia" name="nomeFantasia" 
						   placeholder="Nome da Casa de Show" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o nome fantasia.')"
						   oninput="setCustomValidity('')" 
						   value="${liveHouse.getNomeFantasia()}"/>
				</div>

				<!-- email -->
				<div class="form-group col-md-4">
					<label for="email">E-mail de Contato</label>
					<input type="email" class="form-control" id="email" name="email" 
						   placeholder="contato@casadeshow.com" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe um e-mail válido.')"
						   oninput="setCustomValidity('')" 
						   value="${liveHouse.getEmail()}"/>
				</div>
				
				<!-- number -->
				<div class="form-group col-md-4">
					<label for="capacidade">Capacidade (pessoas)</label>
					<input type="number" min="1" class="form-control" id="capacidade" name="capacidade" 
						   placeholder="Capacidade de público" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe a capacidade.')"
						   oninput="setCustomValidity('')" 
						   value="${liveHouse.getCapacidade()}"/>
				</div>
				
				<!-- date -->
				<div class="form-group col-md-4">
					<label for="dataInauguracao">Data de Inauguração</label>
					<input type="date" class="form-control" id="dataInauguracao" name="dataInauguracao" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe a data de inauguração.')"
						   oninput="setCustomValidity('')" 
						   value="${liveHouse.getDataInauguracao()}"/>
				</div>
				
				<!-- checkbox -->
				<div class="form-group col-md-4">
					<label>Estacionamento</label>
					<div class="checkbox">
						<label for="estacionamento">
							<input type="checkbox" id="estacionamento" name="estacionamento" value="true"
								   ${liveHouse.isEstacionamento() ? 'checked' : ''} />
							Possui estacionamento
						</label>
					</div>
				</div>
				
				<!-- textarea -->
				<div class="form-group col-md-12">
					<label for="endereco">Endereço</label>
					<textarea class="form-control" id="endereco" name="endereco" rows="3"
						   placeholder="Endereço da Casa de Show" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o endereço.')"
						   oninput="setCustomValidity('')">${liveHouse.getEndereco()}</textarea>
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
