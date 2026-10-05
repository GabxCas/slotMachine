/**
 * Lefty wheel: if there is a wheel to its left, when spun it copies the state
 * of that wheel instead of advancing normally.
 */
public class LeftyWheel extends Wheel {
    private Wheel leftWheel;

    public LeftyWheel(int x, int y) {
        super(x, y);
    }

    /**
     * Sets the wheel located to the left of this one.
     */
    public void setLeftWheel(Wheel w) {
        this.leftWheel = w;
    }

    @Override
    public void spin() {
        if (locked) return;
        if (leftWheel != null && !leftWheel.getSymbols().isEmpty()) {
            String leftColor = leftWheel.getVisibleColor();
            if (leftColor != null) placeSymbol(leftColor);
        } else if (!symbols.isEmpty()) {
            visibleIndex = (visibleIndex + 1) % symbols.size();
            symbols.get(visibleIndex).onSpin();
            refresh();
        }
    }
}
