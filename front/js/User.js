class User{
    
    constructor() {
        this.token = null;
        this.id = null;
    }
    
    verify(callback) {
        
        //Ajax : is the user valid?
        callback(true);
    }
    
    isAdmin(callback) {
        //Ajax : is the user administrator?
        callback(true);
    }
    
    persist(callback) {
        //Ajax : save the user in the database if it doesn't exists
        callback(true);
    }
}

export {User};