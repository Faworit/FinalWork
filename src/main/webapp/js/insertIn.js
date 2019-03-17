function insertIn(name, field) {
    for (var i = 0; i < 5; i++){
        document.querySelector(name).onchange = function() {
            document.getElementById(field).value += document.querySelector(name).value + ", ";
        }
    }
}