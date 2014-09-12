<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Adicionar Usuario</title>
<jsp:include page="../fragments/htmlHead.jsp" />
</head>
<body>
	<div id="container" style="width: 1000px; margin: 0 auto;">
		<jsp:include page="../fragments/header.jsp" />
		<div style="text-align: center" class="panel panel-default">
			<div class="panel-heading">
				<h5 class="panel-title">Adicionar usuario</h5>
			</div>
			<div class="panel-body">
		<form:form servletRelativeAction="/usuario/adicionar" method="post"
			modelAttribute="usuario" role="form" class="form-horizontal">
			
			
			
				<div class="form-group col-md-12 form-inline">
				<label for="login" class="col-sm-1 control-label">Login</label>
				<div class="col-sm-10">
					<form:input id="login" class="col-md-8 form-control" placeholder="Login"
						path="login" />
					<form:errors path="login" cssClass="error" />
				</div>
			</div>

			<div class="form-group col-md-12 form-inline">
				<label for="senha" class="col-sm-1 control-label">Senha</label>
				<div class="col-sm-10">
					<form:input name='j_password' id="senha" class="col-md-8 form-control" type='password'  placeholder="Senha"
						path="senha" />
<!-- 						<input class="col-md-8 form-control" type='password' -->
<!-- 							name='j_password' /> -->
				</div>
			</div>
			<div class="controls">
				<input id="criar" class="btn btn-primary" type="submit" value="Adicionar" /> 
				<a	href="<c:url value="/login"></c:url>" class="btn btn-default">Cancelar</a>
			</div>
		</form:form>
		</div>
		
		</div>
		<jsp:include page="../fragments/footer.jsp" />
	</div>
</body>
</html>