import {User} from './User.js';
import {Message} from './utilities/Message.js';

$(function () {

	//Does messages exists?
    message = localStorage.getItem('error');
	if(message !== undefined || message !== null)
    {
        new Message('error', message);
    }
	
    //Verify if the user is connected
    let user = localStorage.getItem('user');

    if(user === undefined || user == null)
    {
        $("#forms").css("display", "inherit");
    } else {
        $("#navLinks").css("display", "inherit");
    }

    $("#i_submit").click(signUp);
    $("#c_submit").click(signIn);
    $("#deconnect").click(deconnect);

});
    


function signUp(e)
{
    e.preventDefault();

    let login = $("#i_login").val();
    let pass = $("#i_pass").val();
    let pass_confirm = $("#i_pass_confirm").val();

    if(pass !== pass_confirm)
    {
        let error = new Message('error', 'Le mot de passe et sa confirmation ne correspondent pas').show();
    }

    else
    {
        signUpRequest(login, pass);
    }
}

function signUpRequest(login, pass)
{
    $.ajax({
        url: "users",
        data: JSON.stringify({login:String(login),password:String(pass)}),
        contentType: "application/json",
        method: 'POST',
        success: function(result)
        {
            if(result === true) {

                let info = new Message('info', 'Inscription réussie').show();
                signInRequest(login, pass);

            } else {

                let error = new Message('error', 'Ce login est déjà pris').show();
            }
        }
    });
}


function signIn(e)
{
    e.preventDefault();

    let login = $("#c_login").val();
    let pass = $("#c_pass").val();

    signInRequest(login, pass);
}

function signInRequest(login, pass)
{
    $.ajax({
        url: "users/login",
        data: JSON.stringify({login:String(login),password:String(pass)}),
        contentType: "application/json",
        method: 'POST',
        success: function(result)
        {
            if(result !== 0) {

                console.log(result);
                let user = new User({'login': login, 'token': result});
                localStorage.setItem('user', JSON.stringify(user));
                window.location.replace('.');
            } else {

                let error = new Message('error', 'Identifiants incorrects').show();
            }
        }
    });
}

function deconnect() {

    localStorage.removeItem("user");
    window.location.replace('.');
}

function addUser (){


    var login = document.getElementById("login").value;
    var password = document.getElementById("password").value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/users', true);
    xhr.setRequestHeader("Content-Type", "application/json");

    var dataTxT = JSON.stringify({"login":login,"password":password});
    var data = {"login":String(login),"password":String(password)};
    console.log(dataTxT)
    console.log(data)
    xhr.onreadystatechange = function () {//vérifie que la requête a eu un succès
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200){
            //on attend la fin de la requête et on vérife qu'il n'y a pas d'erreurs
            console.log("Votre compte a bien été crée");
        }
    };
    xhr.send(dataTxT);


}


function seLoguer (){

    var login = document.getElementById("login").value;
    var password = document.getElementById("password").value;

    console.log(login);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/login', true);
    xhr.setRequestHeader("Content-Type", "application/json");

    console.log("Lancement Requete");

//PROBLEME: On ne rentre jamais dans le if

    //xhr.addEventListener('readystatechange',function(){
    //On utilise readyState pour connaître l'état de la requête.
    xhr.onreadystatechange = function () {
        console.log(xhr.readyState);
        console.log(xhr.status);
        if(xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200){
            console.log(xhr.response);
            console.log(xhr.responseText);
            if(xhr.responseText=="0.0"){
                alert("Le nom d'utilisateur ou le mot de passe est incorrect");
            }
        }



    };
    //});
    var dataTxT = JSON.stringify({"login":login,"password":password});
    var data = {"login":String(login),"password":String(password)};
    xhr.send(dataTxT);
}
