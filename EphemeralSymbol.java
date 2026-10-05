/**
 * Ephemeral symbol: on every spin it decreases its size until it becomes a dot.
 */
public class EphemeralSymbol extends Symbol {

    public EphemeralSymbol(String color) {
        super(color);
    }

    @Override
    public void onSpin() {
        if (size > 1) {
            changeSize(size - 5);
            if (size < 1) changeSize(1);
        }
    }

    @Override
    public void onSelect() { }
}
