<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - ${not empty show ? 'Atualizar' : 'Adicionar'} Show</title>
</head>
<body>
	<%@include file="nav-menu.jsp"%>
	
	<div id="container" class="container-fluid">
		<h3 class="page-header">${not empty show ? 'Atualizar' : 'Adicionar'} Show</h3>
		
		<form action="${pageContext.request.contextPath}/shows/${action}" 
			method="POST">
			
			<input type="hidden" value="${show.getId()}" name="showId">
			
			<div class="row">
				<div class="form-group col-md-4">
					<label for="bandId">Banda</label>
					<select class="form-control" id="bandId" name="bandId" 
						    autofocus="autofocus" 
						    required 
						    oninvalid="this.setCustomValidity('Por favor, selecione a banda.')"
						    oninput="setCustomValidity('')">
						<option value="">Selecione a banda</option>
						<c:forEach var="band" items="${bands}">
							<option value="${band.getId()}" ${show.getBand().getId() == band.getId() ? 'selected' : ''}>${band}</option>
						</c:forEach>
					</select>
				</div>

				<div class="form-group col-md-4">
					<label for="liveHouseId">Casa de Show (o contratante vem da casa)</label>
					<select class="form-control" id="liveHouseId" name="liveHouseId" 
						    required 
						    oninvalid="this.setCustomValidity('Por favor, selecione a casa de show.')"
						    oninput="setCustomValidity('')">
						<option value="">Selecione a casa de show</option>
						<c:forEach var="liveHouse" items="${liveHouses}">
							<option value="${liveHouse.getId()}" ${show.getLiveHouse().getId() == liveHouse.getId() ? 'selected' : ''}>${liveHouse.getNomeFantasia()} - ${liveHouse.getEmployer().getNomeFantasia()}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="form-group col-md-4">
					<label for="valorCache">Valor do Cachê</label>
					<input type="number" step="0.01" min="0" class="form-control" id="valorCache" name="valorCache" 
						   placeholder="Valor do Cachê" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o valor do cachê.')"
						   oninput="setCustomValidity('')" 
						   value="${show.getValorCache()}"/>
				</div>
				
				<div class="form-group col-md-6">
					<label for="dataHora">Data e Hora</label>
					<input type="datetime-local" class="form-control" id="dataHora" name="dataHora" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe a data e hora.')"
						   oninput="setCustomValidity('')" 
						   value="${show.getDataHora()}"/>
				</div>
				
				<div class="form-group col-md-6">
					<label for="tempoShow">Tempo de Show: <strong><span id="tempoShowValue">${not empty show ? show.getTempoShow() : 90}</span> min</strong></label>
					<input type="range" min="30" max="300" step="15" id="tempoShow" name="tempoShow" 
						   value="${not empty show ? show.getTempoShow() : 90}"
						   oninput="document.getElementById('tempoShowValue').textContent = this.value"/>
				</div>
			</div>
			
			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/shows" 
					   class="btn btn-default">Cancelar</a>
					
					<button type="submit" 
						    class="btn btn-primary">${not empty show ? 'Atualizar' : 'Cadastrar'} Show</button>
				</div>
			</div>
		</form>
		
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
