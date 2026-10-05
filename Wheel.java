import java.util.ArrayList;

/**
 * Abstract class that represents a slot machine wheel.
 * Defines the general behavior of all wheels.
 * Subclasses define the specific spin behavior.
 */
public abstract class Wheel {
    protected ArrayList<Symbol> symbols;
    protected int visibleIndex;
    protected Rectangle frame;
    protected boolean isVisible;
    protected int xPosition;
    protected int yPosition;
    protected boolean locked;

    /**
     * Builds a wheel at the given position.
     * @param x horizontal position of the frame.
     * @param y vertical position of the frame.
     */
    public Wheel(int x, int y) {
        symbols = new ArrayList<>();
        visibleIndex = 0;
        isVisible = false;
        xPosition = x;
        yPosition = y;
        locked = false;
        frame = new Rectangle();
        frame.changeSize(70, 100);
        frame.moveHorizontal(x - 70);
        frame.moveVertical(y - 15);
    }

    /**
     * Spins the wheel. Each subclass defines its own behavior.
     */
    public abstract void spin();

    /**
     * Adds a symbol to the wheel.
     * @param symbol symbol to add.
     */
    public void addSymbol(Symbol symbol) {
        symbols.add(symbol);
        updateSymbolPosition(symbol);
        if (symbols.size() == 1) visibleIndex = 0;
        refresh();
    }

    /**
     * Removes the first occurrence of the given symbol.
     * @param color symbol color.
     * @return true if a symbol was removed.
     */
    public boolean delSymbol(String color) {
        int index = indexOf(color);
        if (index == -1) return false;
        symbols.get(index).makeInvisible();
        symbols.remove(index);
        adjustVisibleIndex(index);
        refresh();
        return true;
    }

    /**
     * Selects the given symbol as visible.
     * @param color symbol color.
     * @return true if the symbol exists.
     */
    public boolean placeSymbol(String color) {
        int index = indexOf(color);
        if (index == -1) return false;
        visibleIndex = index;
        symbols.get(visibleIndex).onSelect();
        refresh();
        return true;
    }

    /**
     * Locks the wheel to prevent spinning.
     */
    public void lock() { locked = true; }

    /**
     * Unlocks the wheel to allow spinning again.
     */
    public void unlock() { locked = false; }

    /**
     * Returns whether the wheel is locked.
     * @return true if locked.
     */
    public boolean isLocked() { return locked; }

    /**
     * Returns whether the wheel contains the given symbol.
     * @param color symbol color.
     * @return true if it exists.
     */
    public boolean hasSymbol(String color) { return indexOf(color) != -1; }

    /**
     * Returns the color of the visible symbol.
     * @return visible color or null if there are no symbols.
     */
    public String getVisibleColor() {
        return symbols.isEmpty() ? null : symbols.get(visibleIndex).getColor();
    }

    /**
     * Returns the symbols of the wheel.
     * @return list of symbols.
     */
    public ArrayList<Symbol> getSymbols() { return symbols; }

    /**
     * Makes the wheel and its current symbol visible.
     */
    public void makeVisible() {
        isVisible = true;
        frame.makeVisible();
        refresh();
    }

    /**
     * Makes the wheel and its current symbol invisible.
     */
    public void makeInvisible() {
        isVisible = false;
        frame.makeInvisible();
        hideSymbols();
    }

    /**
     * Changes the position of the wheel frame.
     * @param x new horizontal position.
     * @param y new vertical position.
     */
    public void setPosition(int x, int y) {
        frame.moveHorizontal(x - xPosition);
        frame.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
        for (Symbol symbol : symbols) updateSymbolPosition(symbol);
    }

    /**
     * Returns the index of the first symbol with the given color.
     */
    protected int indexOf(String color) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) return i;
        }
        return -1;
    }

    /**
     * Adjusts the visible index after a symbol is removed.
     */
    protected void adjustVisibleIndex(int removedIndex) {
        if (symbols.isEmpty()) visibleIndex = 0;
        else if (removedIndex < visibleIndex) visibleIndex--;
        else if (visibleIndex >= symbols.size()) visibleIndex = symbols.size() - 1;
    }

    /**
     * Updates the position of the given symbol.
     */
    protected void updateSymbolPosition(Symbol symbol) {
        symbol.setPosition(xPosition + 12, yPosition + 27);
    }

    /**
     * Refreshes the visible symbol.
     */
    protected void refresh() {
        hideSymbols();
        if (isVisible && !symbols.isEmpty()) symbols.get(visibleIndex).makeVisible();
    }

    /**
     * Hides all the symbols.
     */
    protected void hideSymbols() {
        for (Symbol symbol : symbols) symbol.makeInvisible();
    }

    /**
     * Returns the index of the currently visible symbol.
     * @return visible index.
     */
    public int getVisibleIndex() {
        return visibleIndex;
    }
}
