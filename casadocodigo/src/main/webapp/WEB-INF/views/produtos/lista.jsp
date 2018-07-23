<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html >
<html>
<head>
<meta charset="UTF-8">
<title>Livros de Java, Android, Iphone, Ruby, PHP e muito mais - Casa do código</title>
</head>
<body>
	<h1>Lista de Produtos</h1>
	
	<div>${sucesso}</div>
	
		<table>
			<tr>
				<td>Titulo</td>
				<td>Descrição</td>
				<td>Paginas</td>
			</tr>
			
			<c:forEach items="${produtos}" var="produto" varStatus="status" >
				<tr>
					<td><a href="${s:mvcUrl('PC#detalhe').arg(0,produto.id).build()}">${produto.titulo}</a></td>
					<td>${produto.descricao}</td>
					<td>${produto.paginas}</td>
				</tr>	
			</c:forEach>
		</table>

		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status" >
			<div>
				<label>${tipoPreco}</label>
				<input type="text" name="precos[${status.index }].valor" >
				<input type="hidden" name="precos[${status.index }].tipo" value="${tipoPreco}" >
			</div>			
		</c:forEach>
		
		</br>
		
		
		<a href="${s:mvcUrl('PC#form').build()}">Novo</a>

</body>
</html>