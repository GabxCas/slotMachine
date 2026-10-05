/**
 * Normal wheel: when spun, it advances to the next symbol in circular order.
 */
public class NormalWheel extends Wheel {

    public NormalWheel(int x, int y) {
        super(x, y);
    }

    @Override
    public void spin() {
        if (!locked && !symbols.isEmpty()) {
            visibleIndex = (visibleIndex + 1) % symbols.size();
            symbols.get(visibleIndex).onSpin();
            refresh();
        }
    }
}
