int function (int a, int b) {

}

int function (int a) {
    function(a, b);
}

int hola () {

}

int main () {
    int a, b, c, d;
    a = 10;
    b = a + 15;
    c = 50;
    d = 6;

    function(a);
    int h = 10;

    for (int i = 0; i < h; i = i + 1) {
        d = d + 1;
    }

    function(a);
    function(a, b + 10);

    int e = 18;
    a = 32;
    b = e + 15;

}