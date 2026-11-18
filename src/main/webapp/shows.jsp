<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="modal.html"%>
		<%@include file="nav-menu.jsp"%>
					
		<div id="container" class="container-fluid">
			<div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}" class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
			  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			  ${message}
			</div>
		
	 	 	<div id="top" class="row">
	 			<div class="col-md-3">
			        <h3>Shows</h3>
			    </div>
			 
			    <div class="col-md-6">
			        <div class="input-group h2">
			            <input name="data[search]" class="form-control" id="search" type="text" placeholder="Pesquisar shows">
			            <span class="input-group-btn">
			                <button class="btn btn-danger" type="submit">
			                    <span class="glyphicon glyphicon-search"></span>
			                </button>
			            </span>
			        </div>
			    </div>
			 
			    <div class="col-md-3">
			        <a href="/crud-manager/shows/form" class="btn btn-danger pull-right h2"><span class="glyphicon glyphicon-plus"></span>&nbsp;Adicionar Show</a>
			    </div>
	     	</div>
	 
	     	<hr />
	     	
	     	<div id="list" class="row">
	     		<div class="table-responsive col-md-12">
			        <table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
			            <thead>
			                <tr>
			                	<th>Banda</th>
			                    <th>Contratante</th>
			                    <th>Casa de Show</th>
			                    <th>Cachê</th>
			                    <th>Data/Hora</th>
			                    <th>Tempo de Show</th>
			                    <th>Editar</th>
			                    <th>Excluir</th>
			                 </tr>
			            </thead>
			            <tbody>
			            	<c:forEach var="show" items="${shows}">
								<tr>
				                    <td>${show.getBand()}</td>
									<td>${show.getEmployer()}</td>
									<td>${show.getLiveHouse().getNomeFantasia()}</td>
				                    <td>${show.getValorCache()}</td>
				                    <td>${show.getDataHora()}</td>
				                    <td>${show.getTempoShow()}</td>
				                    		                    
				                    <td class="actions">
				                        <a class="btn btn-info btn-xs" 
				                           href="${pageContext.request.contextPath}/shows/update?showId=${show.getId()}" >
				                           <span class="glyphicon glyphicon-edit"></span>
				                        </a>
				                    </td>
				                    
				                    <td class="actions">
				                        <a class="btn btn-danger btn-xs modal-remove" 
				                           show-id="${show.getId()}" 
				                           name="${show.getBand()}"       
				                           data-toggle="modal" 
				                           data-target="#delete-modal"  href="#"><span 
				                           class="glyphicon glyphicon-trash"></span></a>
				                    </td>
				                </tr>
							</c:forEach>
			            </tbody>
			         </table>
			 
			     </div>
	     	</div>
		</div>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/table-utils.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
		    
		    $(".modal-remove").click(function () {
	            var showName = $(this).attr('name');   
	            var showId = $(this).attr('show-id');
	            
	            $(".modal-body #hiddenValue").text("O Show da banda '"+showName+"'");
	            $("#id").val(showId);
	            $("#form").attr("action", "${pageContext.request.contextPath}/shows/delete");
	        });
		});
		</script>
	</body>
</html>
