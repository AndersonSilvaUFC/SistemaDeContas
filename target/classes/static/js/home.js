function fechaAlerta() {
    let alerta = document.querySelector("#alerta")
    let navbar = document.querySelector("#nav-main")
	alerta.setAttribute("class" , "alert alert-danger alert-dismissible fade");
	navbar.setAttribute("class", "navbar navbar-expand-lg fixed-top")
}