/**
 * Shy symbol: it toggles its visibility every time it is selected.
 */
public class ShySymbol extends Symbol {

    public ShySymbol(String color) {
        super(color);
    }

    @Override
    public void onSpin() { }

    @Override
    public void onSelect() {
        if (visible) makeInvisible();
        else makeVisible();
    }
}
