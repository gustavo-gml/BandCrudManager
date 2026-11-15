<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<%@include file="base-head.jsp"%>
<title>CRUD Manager - Empregadores</title>
</head>
<body>
	<%@include file="modal.html"%>
	<%@include file="nav-menu.jsp"%>

	<div id="container" class="container-fluid">
		<div id="alert"
			style="${not empty message ? 'display: block;' : 'display: none;'}"
			class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			${message}
		</div>

		<div id="top" class="row">
			<div class="col-md-3">
				<h3>Empregadores</h3>
			</div>

			<div class="col-md-6">
				<div class="input-group h2">
					<input name="data[search]" class="form-control" id="search"
						type="text" placeholder="Pesquisar empregadores"> <span
						class="input-group-btn">
						<button class="btn btn-danger" type="button">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>
			</div>

			<div class="col-md-3">
				<a href="${pageContext.request.contextPath}/employer/form"
					class="btn btn-danger pull-right h2"><span
					class="glyphicon glyphicon-plus"></span>&nbsp;Adicionar Empregador</a>
			</div>
		</div>

		<hr />

		<div id="list" class="row">
			<div class="table-responsive col-md-12">
				<table class="table table-striped table-hover" cellspacing="0"
					cellpadding="0">
					<thead>
						<tr>
							<th>Razão Social</th>
							<th>Nome Fantasia</th>
							<th>CNPJ</th>
							<th>Editar</th>
							<th>Excluir</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="emp" items="${employers}">
							<tr>
								<td>${emp.getRazaoSocial()}</td>
								<td>${emp.getNomeFantasia()}</td>
								<td>${emp.getCNPJ()}</td>

								<td class="actions"><a class="btn btn-info btn-xs"
									href="${pageContext.request.contextPath}/employer/update?employerId=${emp.getId()}">
										<span class="glyphicon glyphicon-edit"></span>
								</a></td>

								<td class="actions">
									<a class="btn btn-danger btn-xs modal-remove"
										employer-id="${emp.getId()}" 
										employer-name="<c:out value='${emp.getNomeFantasia()}' />"
										data-toggle="modal" 
										data-target="#delete-modal" href="#"> 
										<span class="glyphicon glyphicon-trash"></span>
									</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div id="bottom" class="row">
			<div class="col-md-12">
				<ul class="pagination">
					<li class="disabled"><a>&lt; Anterior</a></li>
					<li class="disabled"><a>1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li class="next"><a href="#" rel="next">Próximo &gt;</a></li>
				</ul>
			</div>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/table-utils.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".modal-remove").click(function() {
				var employerName = $(this).attr('employer-name');
				var employerId = $(this).attr('employer-id');
				$(".modal-body #hiddenValue").text("o empregador '" + employerName + "'");
				$("#id").val(employerId);
				$("#form").attr("action", "${pageContext.request.contextPath}/employer/delete");
			});
		});
	</script>
</body>
</html>