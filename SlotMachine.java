import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Simulador de maquina tragamonedas.
 */
public class SlotMachine {
    private ArrayList<Wheel> wheels;
    private Rectangle machine;
    private boolean isVisible;
    private boolean ok;

    /**
     * Construye una maquina tragamonedas vacia.
     */
    public SlotMachine() {
        wheels = new ArrayList<>();
        machine = new Rectangle();
        machine.changeSize(280, 220);
        machine.moveHorizontal(-60);
        machine.moveVertical(15);
        machine.changeColor("black");
        isVisible = false;
        ok = true;
    }

    /**
     * Adiciona una rueda en la posicion solicitada.
     * @param pos posicion de insercion, comenzando en uno.
     */
    public void addWheel(int pos) {
        int index = adjustPos(pos);
        wheels.add(index, new Wheel(25, 80));
        layoutWheels();
        if (isVisible) {
            wheels.get(index).makeVisible();
        }
        refreshJackpot();
        ok = true;
    }

    /**
     * Elimina una rueda.
     * @param pos posicion de la rueda, comenzando en uno.
     */
    public void delWheel(int pos) {
        if (!validWheel(pos)) {
            fail("No es posible eliminar esa rueda");
            return;
        }
        wheels.get(pos - 1).makeInvisible();
        wheels.remove(pos - 1);
        layoutWheels();
        refreshJackpot();
        ok = true;
    }

    /**
     * Adiciona un simbolo a una rueda.
     * @param pos posicion de la rueda.
     * @param color color del simbolo.
     */
    public void addSymbol(int pos, String color) {
        if (!validWheel(pos)) {
            fail("Rueda no existente");
            return;
        }
        if (!validColor(color)) {
            fail("Color no soportado");
            return;
        }
        wheels.get(pos - 1).addSymbol(color);
        refreshJackpot();
        ok = true;
    }

    /**
     * Elimina un simbolo de todas las ruedas que lo contengan.
     * @param symbol color del simbolo.
     */
    public void delSymbol(String symbol) {
        ok = false;
        for (Wheel wheel : wheels) {
            if (wheel.delSymbol(symbol)) {
                ok = true;
            }
        }
        if (!ok) {
            fail("Simbolo no encontrado");
        }
        refreshJackpot();
    }

    /**
     * Coloca un simbolo especifico en una rueda.
     * @param wheel posicion de la rueda.
     * @param symbol color del simbolo.
     */
    public void placeSymbol(int wheel, String symbol) {
        if (!validWheel(wheel)) {
            fail("Rueda no existe");
            return;
        }
        ok = wheels.get(wheel - 1).placeSymbol(symbol);
        if (!ok) {
            fail("Simbolo no encontrado en la rueda");
        }
        refreshJackpot();
    }

    /**
     * Gira una rueda especifica.
     * @param wheel posicion de la rueda.
     */
    public void spin(int wheel) {
        if (!validWheel(wheel)) {
            fail("Rueda no existe");
            return;
        }
        wheels.get(wheel - 1).spin();
        refreshJackpot();
        ok = true;
    }

    /**
     * Gira todas las ruedas.
     */
    public void spin() {
        if (wheels.isEmpty()) {
            fail("No hay ruedas para girar");
            return;
        }
        for (Wheel wheel : wheels) {
            wheel.spin();
        }
        refreshJackpot();
        ok = true;
    }

    /**
     * Retorna todos los simbolos de la maquina.
     * @return arreglo con los colores de los simbolos.
     */
    public String[] symbols() {
        ArrayList<String> allSymbols = new ArrayList<>();
        for (Wheel wheel : wheels) {
            allSymbols.addAll(wheel.getSymbols());
        }
        ok = true;
        return allSymbols.toArray(new String[0]);
    }

    /**
     * Retorna la cantidad de colores diferentes.
     * @return numero de simbolos distintos.
     */
    public int distinctSymbols() {
        return (int) java.util.Arrays.stream(symbols()).distinct().count();
    }

    /**
     * Retorna la configuracion visible actual.
     * @return un color por cada rueda.
     */
    public String[] configuration() {
        String[] config = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            config[i] = wheels.get(i).getVisibleColor();
        }
        ok = true;
        return config;
    }

    /**
     * Determina si todas las ruedas muestran el mismo simbolo.
     * @return true si la configuracion es ganadora.
     */
    public boolean isjackpot() {
        if (wheels.isEmpty()) {
            return false;
        }
        String first = wheels.get(0).getVisibleColor();
        if (first == null) {
            return false;
        }
        for (Wheel wheel : wheels) {
            if (!first.equalsIgnoreCase(wheel.getVisibleColor())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Hace visible la maquina y sus ruedas.
     */
    public void makeVisible() {
        isVisible = true;
        machine.makeVisible();
        for (Wheel wheel : wheels) {
            wheel.makeVisible();
        }
        refreshJackpot();
        ok = true;
    }

    /**
     * Hace invisible la maquina y sus ruedas.
     */
    public void makeInvisible() {
        for (Wheel wheel : wheels) {
            wheel.makeInvisible();
        }
        machine.makeInvisible();
        isVisible = false;
        ok = true;
    }

    /**
     * Termina el simulador.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Retorna si la ultima operacion fue correcta.
     * @return true si fue correcta.
     */
    public boolean ok() {
        return ok;
    }

    private int adjustPos(int pos) {
        if (pos < 1) return 0;
        if (pos > wheels.size()) return wheels.size();
        return pos - 1;
    }

    private boolean validWheel(int pos) {
        return pos >= 1 && pos <= wheels.size();
    }

    private boolean validColor(String color) {
        if (color == null) return false;
        return color.equalsIgnoreCase("red") || color.equalsIgnoreCase("yellow")
            || color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("green")
            || color.equalsIgnoreCase("magenta") || color.equalsIgnoreCase("black");
    }

    private void layoutWheels() {
        int x = 25;
        for (Wheel wheel : wheels) {
            wheel.setPosition(x, 80);
            x += 85;
        }
    }

    private void refreshJackpot() {
        machine.changeColor(isjackpot() ? "green" : "black");
        if (!isVisible) {
            return;
        }
        machine.makeVisible();
    }

    private void fail(String message) {
        ok = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
