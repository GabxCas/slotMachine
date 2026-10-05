/**
 * Representa un simbolo de la maquina tragamonedas y su representacion grafica.
 */
public class Symbol {
    private String color;
    private Circle figure;
    private int xPosition;
    private int yPosition;

    /**
     * Construye un simbolo con el color indicado.
     * @param color color del simbolo.
     */
    public Symbol(String color) {
        this.color = color;
        figure = new Circle();
        figure.changeSize(45);
        figure.changeColor(color);
        figure.makeInvisible();
        xPosition = 20;
        yPosition = 15;
    }

    /**
     * Retorna el color del simbolo.
     * @return color del simbolo.
     */
    public String getColor() {
        return color;
    }

    /**
     * Hace visible el simbolo.
     */
    public void makeVisible() {
        figure.makeVisible();
    }

    /**
     * Hace invisible el simbolo.
     */
    public void makeInvisible() {
        figure.makeInvisible();
    }

    /**
     * Ubica el simbolo en la posicion indicada.
     * @param x posicion horizontal.
     * @param y posicion vertical.
     */
    public void setPosition(int x, int y) {
        figure.moveHorizontal(x - xPosition);
        figure.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
    }
}
