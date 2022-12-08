
const signUpA = document.getElementById('signUp');
const signInA = document.getElementById('signIn');
const container = document.getElementById('container');
const parrafo = document.getElementById("warnings")

signUpA.addEventListener('click', () =>
    container.classList.add('right-panel-active'));

signInA.addEventListener('click', () =>
    container.classList.remove('right-panel-active'));



let nomb;
let ape;
let mail;
let contrasena;
let contrasenarep;


$("input").change(function () {
    nomb = document.getElementById("nomb").value;
    ape = document.getElementById("ape").value;
    mail = document.getElementById("mail").value;
    contrasena = document.getElementById("contrasena").value;
    contrasenarep = document.getElementById("contrasenarep").value;

    console.log(nomb + ape + mail + contrasena + contrasenarep)

    desa();
});



function over() {
    if (contrasena != contrasenarep) {

        warnings = 'LAS CONTRASEÑAS NO COINCIDEN <br>'
        parrafo.innerHTML = warnings

    }
    else {
        warnings = ' '
        parrafo.innerHTML = warnings
    }
}




function desa() {



    if (nomb != "" && ape != "" && mail != "" && contrasena == contrasenarep && contrasenarep != "") {

        console.log("El boton está activo (o debería)")
        document.getElementById("btn-signup").disabled = false;

    }

    else {
        console.log("El boton está full disabled")
        document.getElementById("btn-signup").disabled = true;
    }

}


