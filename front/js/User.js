import {Message} from "./utilities/Message.js";

class User{

    constructor(login, token) {

        this.login = login;
        this.token = token;
    }
    
    static restore() {

        let data = localStorage.getItem('user');
        data = JSON.parse(data);

        if(data === undefined || data === null) {

            return null;
        } else if(data.login === undefined || data.token === undefined) {

            return null;
        }

        return new User(data.login, data.token);
    }
    
    verify(callback) {

        // $.ajax({
        //     url: "users/admin/" + this.getLogin() + "/" + this.getToken(),
        //     method: 'GET',
        //     success: callback
        // });
    }
    
    isAdmin(callback) {
        //Ajax : is the user administrator?
        $.ajax({
            url: "users/admin/" + this.getLogin() + "/" + this.getToken(),
            method: 'GET',
            success: callback
        });

        callback(true);
    }

    getLogin() {

        return this.login;
    }

    getToken() {

        return this.token;
    }
}

export {User};