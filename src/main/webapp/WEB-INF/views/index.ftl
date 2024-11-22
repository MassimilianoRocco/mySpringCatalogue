<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- BOOTSTRAP -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

<!-- CSS -->
<link href="/resources/css/index.css" rel="stylesheet" type="text/css">

<title>Catalogo Prodotti</title>
</head>
<body>

	<div class="container">
		
	
		<h1>Catalogo Prodotti</h1>

		<#if prodottoDaModificare??>
			<h2 style="margin-top:1rem">Modifica del Prodotto - ${prodottoDaModificare.nome}</h2>
			<div style="margin-top:1rem">
				<form id="datiProdotto" action="update" method="POST">
				<input type="hidden" name="id" value="${prodottoDaModificare.id}" />
					<div>
						<label for="nome">Nome:</label><br>
						<input type="text" id="nome" name="nome" value="${prodottoDaModificare.nome}">
					</div>
					<div>
						<label for="descrizione">Descrizione:</label><br>
						<input type="text" id="descrizione" name="descrizione" value="${prodottoDaModificare.descrizione}">
					</div>
					<div>
						<label for="prezzo">Prezzo:</label><br>
						<input type="number" id="prezzo" name="prezzo" value="${prodottoDaModificare.prezzo}">
					</div>
					<div style="margin-top:1rem">
						<button type="submit" form="datiProdotto" value="aggiungi">Modifica</button>
					</div>
				</form>
			</div>

		<#else>	
		
			<h2 style="margin-top:1rem">Nuovo Prodotto</h2>
			<div style="margin-top:1rem">
				<form id="datiProdotto" action="add" method="POST">
					<div>
						<label for="nome">Nome:</label><br>
						<input type="text" id="nome" name="nome" required>
					</div>
					<div>
						<label for="descrizione">Descrizione:</label><br>
						<input type="text" id="descrizione" name="descrizione" required>
					</div>
					<div>
						<label for="prezzo">Prezzo:</label><br>
						<input type="number" id="prezzo" name="prezzo" required>
					</div>
					<div style="margin-top:1rem">
						<button type="submit" form="datiProdotto" value="aggiungi">Aggiungi</button>
					</div>
				</form>
			</div>

		</#if>
		<hr>

		<h2>Lista Prodotti</h2>
		<div>
			<table class="table">
				<thead>
					<tr>
					<th scope="col">Nome</th>
					<th scope="col">Descrizione</th>
					<th scope="col">Prezzo</th>
					<th scope="col">Azioni</th>
					</tr>
				</thead>
				<tbody>
					<#list listaProdotti as prodotto>
					<tr>
						<td>${prodotto.nome}</td>
						<td>${prodotto.descrizione}</td>
						<td>${prodotto.prezzo}</td>
						<td>
						<a href="delete?id=${prodotto.id}">Elimina</a>
						<a href="?id=${prodotto.id}">Modifica</a>
						</td>
					</tr>
					</#list>
				</tbody>
			</table>
		</div>
	</div>
	
</body>
</html>