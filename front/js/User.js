class User{

    constructor(login, token) {

        this.login = login;
        this.token = token;
    }
    
    static restore() {

        let data = localStorage.getItem('user');

        if(data === undefined || data === null) {

            return null;
        } else if(data.login === undefined || data.token === undefined) {

            return false;
        }

        return new User(data.login, data.token);
    }
    
    verify(callback) {
        
        //Ajax : is the user valid?
        callback(true);
    }
    
    isAdmin(callback) {
        //Ajax : is the user administrator?
        callback(true);
    }

    getLogin() {

        return this.getLogin();
    }

    getToken() {

        return this.getToken();
    }
}

export {User};