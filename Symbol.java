/**
 * Abstract class that represents a slot machine symbol.
 * Defines the general behavior of all symbols.
 * Subclasses define the behavior on spins and selections.
 */
public abstract class Symbol {
    protected String color;
    protected Circle figure;
    protected int xPosition;
    protected int yPosition;
    protected int size;
    protected boolean visible;

    /**
     * Builds a symbol with the given color.
     * @param color symbol color.
     */
    public Symbol(String color) {
        this.color = color;
        this.size = 45;
        figure = new Circle();
        figure.changeSize(size);
        figure.changeColor(color);
        figure.makeInvisible();
        xPosition = 20;
        yPosition = 15;
        visible = false;
    }

    /**
     * Behavior of the symbol when the wheel spins.
     */
    public abstract void onSpin();

    /**
     * Behavior of the symbol when it is selected on the wheel.
     */
    public abstract void onSelect();

    /**
     * Returns the color of the symbol.
     * @return symbol color.
     */
    public String getColor() { return color; }

    /**
     * Returns the size of the symbol.
     * @return symbol size.
     */
    public int getSize() { return size; }

    /**
     * Returns whether the symbol is visible.
     * @return true if visible.
     */
    public boolean isVisible() { return visible; }

    /**
     * Makes the symbol visible.
     */
    public void makeVisible() {
        figure.makeVisible();
        visible = true;
    }

    /**
     * Makes the symbol invisible.
     */
    public void makeInvisible() {
        figure.makeInvisible();
        visible = false;
    }

    /**
     * Sets the position of the symbol.
     * @param x horizontal position.
     * @param y vertical position.
     */
    public void setPosition(int x, int y) {
        figure.moveHorizontal(x - xPosition);
        figure.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
    }

    /**
     * Changes the color of the symbol.
     * @param newColor new color.
     */
    public void changeColor(String newColor) {
        this.color = newColor;
        figure.changeColor(newColor);
    }

    /**
     * Changes the size of the symbol.
     * @param newSize new size.
     */
    public void changeSize(int newSize) {
        this.size = newSize;
        figure.changeSize(newSize);
    }
}
