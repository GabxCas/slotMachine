PROJECT TITLE: slotMachine
PURPOSE OF PROJECT: Simular una maquina tragamonedas para el Ciclo No. 1 de DOPO-POOB.
VERSION or DATE: 2026-10-03
AUTHORS: Completar con los nombres de los integrantes.

DESCRIPCION
Este proyecto implementa el simulador del primer ciclo. No resuelve el problema de la maraton.
La maquina administra ruedas, simbolos, giro, configuracion, jackpot, visibilidad y mensajes de error.

DECISIONES DE DISENO
- Wheel utiliza Symbol en lugar de almacenar directamente Strings.
- Symbol utiliza Circle de shapes para su representacion grafica.
- SlotMachine utiliza Rectangle como cuerpo de la maquina.
- El jackpot se considera verdadero cuando todas las ruedas tienen el mismo simbolo visible.
- La maquina cambia a verde cuando esta en jackpot y permanece negra en otro caso.
- Los colores admitidos son los soportados por shapes: red, yellow, blue, green, magenta y black.

COMO INICIAR
1. Abra este proyecto en BlueJ.
2. Compile todas las clases.
3. Cree un objeto SlotMachine.
4. Agregue ruedas con addWheel(pos).
5. Agregue simbolos con addSymbol(pos, color).
6. Use makeVisible() para mostrar la maquina.
7. Use spin(), configuration(), symbols() e isjackpot() para probarla.

NOTA
Canvas, Circle, Rectangle y Triangle son reutilizados del proyecto shapes proporcionado para la asignatura.
