<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login - Facebook CRUD</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
  </head>
  <body>
    <!-- Navbar -->
    <nav class="navbar navbar-inverse">
      <div class="container">
        <div class="navbar-header">
          <span class="navbar-brand">CRUD Manager</span>
        </div>
      </div>
    </nav>

    <main class="container">
      <div class="row">
        <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
          <div class="panel panel-default">
            <div class="panel-body">
              <h2 class="text-center">Login</h2>

              <form action="/crud-manager/login" method="POST">
                <div class="form-group">
                  <label for="user_login_id" class="control-label">Login (e-mail)</label>
                  <input type="text" class="form-control" id="user_login_id" name="user_login" required />
                </div>

                <div class="form-group">
                  <label for="user_pw_id" class="control-label">Senha</label>
                  <input type="password" class="form-control" id="user_pw_id" name="user_pw" required />
                </div>

                <div class="form-group">
                  <button type="submit" class="btn btn-primary btn-lg btn-block">Logar</button>
                </div>

                <c:if test="${param.erro == 'true'}">
                  <p class="text-danger small">Usuário ou senha inválidos.</p>
                </c:if>
              </form>
            </div>
          </div>
        </div>
      </div>
    </main>

    <footer class="panel-footer navbar-fixed-bottom text-center">
      <jsp:useBean id="date" class="java.util.Date" />
      <fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
      <span class="text-muted">©<c:out value="${currentYear}" /> CRUD Manager. Todos os direitos reservados.</span>
    </footer>
  </body>
</html>
