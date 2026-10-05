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
        wheels.add(index, new NormalWheel(25, 80));
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
            Wheel w = wheels.get(pos - 1);
            if (w instanceof RebelWheel) {
                fail("A rebel wheel cannot be deleted");
                return;
            }
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
        wheels.get(pos - 1).addSymbol(new NormalSymbol(color));
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
        spin(wheel, 1);
    }

    /**
     * Gira una rueda un numero de pasos.
     * @param wheel posicion de la rueda.
     * @param steps numero de pasos a girar.
     */
    public void spin(int wheel, int steps) {
        if (!validWheel(wheel)) {
            fail("Rueda no existe");
            return;
        }
        if (steps < 0) {
            fail("El numero de pasos no puede ser negativo");
            return;
        }
        if (wheels.get(wheel - 1).isLocked()) {
            fail("La rueda esta fijada");
            return;
        }
        for (int i = 0; i < steps; i++) {
            wheels.get(wheel - 1).spin();
            refreshJackpot();
            pauseIfVisible();
        }
        ok = true;
    }

    /**
     * Intercambia dos ruedas.
     * @param wheel1 posicion de la primera rueda.
     * @param wheel2 posicion de la segunda rueda.
     */
    public void swap(int wheel1, int wheel2) {
        if (!validWheel(wheel1) || !validWheel(wheel2)) {
            fail("Rueda no existe");
            return;
        }
        if (wheel1 == wheel2) {
            Wheel first = wheels.get(wheel1 - 1);
            Wheel second = wheels.get(wheel2 - 1);
            if (first instanceof RebelWheel || second instanceof RebelWheel) {
                fail("A rebel wheel cannot be swapped");
                return;
                }
        }
        Wheel first = wheels.get(wheel1 - 1);
        wheels.set(wheel1 - 1, wheels.get(wheel2 - 1));
        wheels.set(wheel2 - 1, first);
        layoutWheels();
        refreshJackpot();
        ok = true;
    }

    /**
     * Fija una rueda para impedir que gire.
     * @param wheel posicion de la rueda.
     */
    public void lock(int wheel) {
        if (!validWheel(wheel)) {
            fail("Rueda no existe");
            return;
        }
        wheels.get(wheel - 1).lock();
        ok = true;
    }

    /**
     * Suelta una rueda para permitir nuevamente su giro.
     * @param wheel posicion de la rueda.
     */
    public void unlock(int wheel) {
        if (!validWheel(wheel)) {
            fail("Rueda no existe");
            return;
        }
        wheels.get(wheel - 1).unlock();
        ok = true;
    }

    /**
     * Deja la maquina en la configuracion indicada.
     * @param symbols colores que debe mostrar cada rueda.
     */
    public void setSymbols(String[] symbols) {
        if (symbols == null || symbols.length != wheels.size()) {
            fail("Configuracion invalida");
            return;
        }
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] == null || !wheels.get(i).hasSymbol(symbols[i])) {
                fail("Configuracion invalida");
                return;
            }
        }
        for (int i = 0; i < symbols.length; i++) {
            wheels.get(i).placeSymbol(symbols[i]);
        }
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
            if (!wheel.isLocked()) {
                wheel.spin();
            }
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
        linkLeftyWheels();
    }

private void refreshJackpot() {
    machine.changeColor(isjackpot() ? "green" : "black");
    if (isVisible) {
        for (Wheel wheel : wheels) {
            wheel.makeVisible();
        }
    }
}

    private void pauseIfVisible() {
        if (!isVisible) {
            return;
        }
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void fail(String message) {
        ok = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Returns the number of wheels in the machine.
     * @return number of wheels.
     */
    public int getNumberOfWheels() {
        return wheels.size();
    }

    /**
     * Returns the number of distinct symbols currently in the machine.
     * @return number of distinct symbols.
     */
    public int getNumberOfDistinctSymbols() {
        return distinctSymbols();
    }

    /**
     * Returns a list with the distinct symbols currently in the machine.
     * @return list of distinct symbol colors.
     */
    public ArrayList<String> getDistinctSymbolList() {
        ArrayList<String> distinct = new ArrayList<>();
        for (String s : symbols()) {
            if (!distinct.contains(s)) {
                distinct.add(s);
            }
        }
        return distinct;
    }

    /**
     * Returns the number of symbols in a given wheel.
     * @param wheel wheel position, starting at one.
     * @return number of symbols in that wheel, or 0 if the wheel does not exist.
     */
    public int getWheelSymbolCount(int wheel) {
        if (!validWheel(wheel)) {
            return 0;
        }
        return wheels.get(wheel - 1).getSymbols().size();
    }  

    /**
     * Checks whether a wheel would show the target symbol after a given number
     * of spins, without modifying the machine.
     * @param wheel wheel position, starting at one.
     * @param steps number of spins to simulate.
     * @param target target symbol color.
     * @return true if after "steps" spins the wheel would show the target.
     */
    public boolean wouldShow(int wheel, int steps, String target) {
        if (!validWheel(wheel) || target == null) {
            return false;
        }
        ArrayList<Symbol> symbols = wheels.get(wheel - 1).getSymbols();
        if (symbols.isEmpty()) {
            return false;
        }
        int size = symbols.size();
        int current = wheels.get(wheel - 1).getVisibleIndex();
        int index = (current + steps) % size;
        return symbols.get(index).getColor().equalsIgnoreCase(target);
    }
}
