class Message {

    constructor(type, text) {

        this.text = text;
    }

    show() {
        $('<p class="message">').html(this.text).appendTo("body");
    }
}

export {Message};