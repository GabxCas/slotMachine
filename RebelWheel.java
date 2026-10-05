/**
 * Rebel wheel: it cannot be locked, swapped, or deleted.
 */
public class RebelWheel extends Wheel {

    public RebelWheel(int x, int y) {
        super(x, y);
    }

    @Override
    public void spin() {
        if (!symbols.isEmpty()) {
            visibleIndex = (visibleIndex + 1) % symbols.size();
            symbols.get(visibleIndex).onSpin();
            refresh();
        }
    }

    @Override
    public void lock() {
        // It cannot be locked
    }

    @Override
    public boolean delSymbol(String color) {
        // It does not allow symbol deletion
        return false;
    }
}
