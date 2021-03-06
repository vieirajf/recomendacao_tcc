<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Adicionar Usuario</title>
<jsp:include page="../fragments/htmlHead.jsp" />

<style>
.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}

.center {
	float: none;
	margin-left: auto;
	margin-right: auto;
	padding-top: 100px;
}
</style>
</head>
<body>

	<div id="container" style="width: 1000px; margin: 0 auto;">
		<jsp:include page="../fragments/header.jsp" />
		<div class="center col-md-6">
			<div style="text-align: center" class="panel panel-default">
				<div class="panel-heading">
					<h5 class="panel-title">Adicionar usuario</h5>
				</div>
				<div class="panel-body">
					<form:form servletRelativeAction="/usuario/adicionar" method="post"
						modelAttribute="usuario" role="form" class="form-horizontal">
						<div class="form-group col-md-12 form-inline">
							<h4 class="col-md-4">Login</h4>
							<form:input id="login" class="col-md-8 form-control"
								placeholder="Login" path="login" />
							<form:errors path="login" cssClass="error" />

						</div>

						<div class="form-group col-md-12 form-inline">

							<h4 class="col-md-4">Senha</h4>
							<form:input name='j_password' id="senha"
								class="col-md-8 form-control" type='password'
								placeholder="Senha" path="senha" />
						</div>
						<div class="controls">
							<input id="criar" class="btn btn-primary" type="submit"
								value="Adicionar" /> <a href="<c:url value="/login"></c:url>"
								class="btn btn-default">Cancelar</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
		<jsp:include page="../fragments/footer.jsp" />

	</div>
</body>
</html>