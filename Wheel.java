import java.util.ArrayList;

/**
 * Representa una rueda de la maquina tragamonedas.
 */
public class Wheel {
    private ArrayList<Symbol> symbols;
    private int visibleIndex;
    private Rectangle frame;
    private boolean isVisible;
    private int xPosition;
    private int yPosition;
    private boolean locked;

    /**
     * Construye una rueda en una posicion por defecto.
     */
    public Wheel() {
        this(25, 80);
    }

    /**
     * Construye una rueda en la posicion indicada.
     * @param x posicion horizontal del marco.
     * @param y posicion vertical del marco.
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
     * Adiciona un simbolo a la rueda.
     * @param color color del nuevo simbolo.
     */
    public void addSymbol(String color) {
        Symbol symbol = new Symbol(color);
        symbols.add(symbol);
        updateSymbolPosition(symbol);
        if (symbols.size() == 1) {
            visibleIndex = 0;
        }
        refresh();
    }

    /**
     * Elimina la primera presencia del simbolo indicado.
     * @param color color del simbolo.
     * @return true si se elimino un simbolo.
     */
    public boolean delSymbol(String color) {
        int index = indexOf(color);
        if (index == -1) {
            return false;
        }
        symbols.get(index).makeInvisible();
        symbols.remove(index);
        adjustVisibleIndex(index);
        refresh();
        return true;
    }

    /**
     * Selecciona el simbolo indicado como visible.
     * @param color color del simbolo.
     * @return true si el simbolo existe.
     */
    public boolean placeSymbol(String color) {
        int index = indexOf(color);
        if (index == -1) {
            return false;
        }
        visibleIndex = index;
        refresh();
        return true;
    }

    /**
     * Gira la rueda hasta el siguiente simbolo.
     */
    public void spin() {
        if (!locked && !symbols.isEmpty()) {
            visibleIndex = (visibleIndex + 1) % symbols.size();
            refresh();
        }
    }


    /**
     * Fija la rueda para impedir que gire.
     */
    public void lock() {
        locked = true;
    }

    /**
     * Suelta la rueda para permitir su giro.
     */
    public void unlock() {
        locked = false;
    }

    /**
     * Indica si la rueda esta fijada.
     * @return true si esta fijada.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Indica si la rueda contiene el simbolo indicado.
     * @param color color del simbolo.
     * @return true si existe.
     */
    public boolean hasSymbol(String color) {
        return indexOf(color) != -1;
    }

    /**
     * Retorna el color del simbolo visible.
     * @return color visible o null si no hay simbolos.
     */
    public String getVisibleColor() {
        return symbols.isEmpty() ? null : symbols.get(visibleIndex).getColor();
    }

    /**
     * Retorna los colores de todos los simbolos de la rueda.
     * @return lista de colores.
     */
    public ArrayList<String> getSymbols() {
        ArrayList<String> colors = new ArrayList<>();
        for (Symbol symbol : symbols) {
            colors.add(symbol.getColor());
        }
        return colors;
    }

    /**
     * Hace visible la rueda y su simbolo actual.
     */
    public void makeVisible() {
        isVisible = true;
        frame.makeVisible();
        refresh();
    }

    /**
     * Hace invisible la rueda y su simbolo actual.
     */
    public void makeInvisible() {
        isVisible = false;
        frame.makeInvisible();
        hideSymbols();
    }

    /**
     * Cambia la posicion del marco de la rueda.
     * @param x nueva posicion horizontal.
     * @param y nueva posicion vertical.
     */
    public void setPosition(int x, int y) {
        frame.moveHorizontal(x - xPosition);
        frame.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
        for (Symbol symbol : symbols) {
            updateSymbolPosition(symbol);
        }
    }

    private int indexOf(String color) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                return i;
            }
        }
        return -1;
    }

    private void adjustVisibleIndex(int removedIndex) {
        if (symbols.isEmpty()) {
            visibleIndex = 0;
        } else if (removedIndex < visibleIndex) {
            visibleIndex--;
        } else if (visibleIndex >= symbols.size()) {
            visibleIndex = symbols.size() - 1;
        }
    }

    private void updateSymbolPosition(Symbol symbol) {
        symbol.setPosition(xPosition + 12, yPosition + 27);
    }

    private void refresh() {
        hideSymbols();
        if (isVisible && !symbols.isEmpty()) {
            symbols.get(visibleIndex).makeVisible();
        }
    }

    private void hideSymbols() {
        for (Symbol symbol : symbols) {
            symbol.makeInvisible();
        }
    }
}
