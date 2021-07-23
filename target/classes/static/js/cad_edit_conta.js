function dataMinima() {
	var selData = document.querySelector("#dataMaxima");
	
	var data = new Date();
	
	var dia = String(data.getDate()).padStart(2, '0');
	var mes = String(data.getMonth() + 1).padStart(2, '0');
	var ano = data.getFullYear();
	
	var dataString = ano + '-' + mes + '-' + dia;
	console.log(dataString);
	selData.setAttribute("min",dataString);
}

dataMinima();