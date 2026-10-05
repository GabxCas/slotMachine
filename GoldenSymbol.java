/**
 * Golden symbol (new proposed type): it increases its size on every spin
 * up to a maximum of 70 pixels. If selected, it changes its color
 * to a golden tone (represented as magenta due to Canvas limitations).
 */
public class GoldenSymbol extends Symbol {
    private static final int MAX_SIZE = 70;

    public GoldenSymbol() {
        super("yellow");
    }

    @Override
    public void onSpin() {
        if (size < MAX_SIZE) {
            changeSize(Math.min(size + 5, MAX_SIZE));
        }
    }

    @Override
    public void onSelect() {
        changeColor("magenta");
    }
}
