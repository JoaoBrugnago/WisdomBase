const formulario = document.querySelector(".redefinirSenha-form");

formulario.addEventListener("submit", function(event) {
    const senha = document.getElementById("senha").value;
    const confirmarSenha = document.getElementById("confirmarSenha").value;

    if (senha !== confirmarSenha) {
        event.preventDefault();
        alert("As senhas não coincidem!");
    }
});