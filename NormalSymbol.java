/**
 * Normal symbol: its behavior does not change on spins or selections.
 */
public class NormalSymbol extends Symbol {

    public NormalSymbol(String color) {
        super(color);
    }

    @Override
    public void onSpin() { }

    @Override
    public void onSelect() { }
}
