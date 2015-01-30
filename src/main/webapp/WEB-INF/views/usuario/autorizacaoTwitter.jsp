<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Autorização do Twitter</title>
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
		<div class="center col-md-5">
			<div style="text-align: center" class="panel panel-default">
				<div class="panel-heading">
					<h5 class="panel-title">Autorização do Twitter</h5>
				</div>
				<div class="panel-body">
					<form:form servletRelativeAction="/twitter/${id}/autorizacaoTwitter"
						method="post" modelAttribute="pin" role="form"
						class="form-horizontal">
						<input name="requestToken" type="hidden" value="${requestToken}"/>
						<input name="twitter" type="hidden" value="${twitter}"/>
						<label>Click no Link abaixo e copie o pin de autorização
							Twitter no campo Pin</label>
						<br />
						<a href="<c:url value="${url}"/>" target="_blank">Link de
							Autorização do Twitter</a>
						<div class="form-group col-md-12 form-inline">
							<h4 class="col-md-2">Pin</h4>
							<form:input id="pin" class="col-md-8 form-control"
								placeholder="Pin" path="pin" />
							<form:errors path="pin" cssClass="error" />
							
						</div>
						<div class="controls">
							<input id="criar" class="btn btn-primary" type="submit" value="Autorizar" /> 
							<a href="<c:url value="/login"></c:url>" class="btn btn-default">Cancelar</a>
						</div>
					</form:form>
				</div>

			</div>
		</div>
		<jsp:include page="../fragments/footer.jsp" />
	</div>
</body>
</html>