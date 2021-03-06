package Devoir_3;

import java.awt.*;

/**
 * Luka Virtual Machine (LVM) -- An interpreter for the Luka programming
 * language.
 *
 * @author Marcel Turcotte
 */
public class Interpreter {

    /**
     * Class variable. Newline symbol on this machine at run-time.
     */
    private static final String NL = System.getProperty("line.separator");

    /**
     * Instance variable. The operands stack.
     */
    private Stack<Token> operands;

    /**
     * Instance variable. A reference to a lexical analyzer (Reader).
     */
    private Reader r;

    /**
     * Instance variable. Coordinate x of the graphics state.
     */
    private int gsX;

    /**
     * Instance variable. Coordinate y of the graphics state.
     */
    private int gsY;

    /**
     * Instance variable. Color of the pen.
     */
    private Color gsColor;

    /**
     * Initializes this newly created interpreter so that the operand stack is
     * empty, the accumulator is set 0, the cursor is at (0,0), and the default
     * color is blue.
     */
    public Interpreter() {
        reset();
    }

    /**
     * Auxiliary method that resets the graphics state of this interpreter.
     */
    private void reset() {

        operands = new LinkedStack<>();

        gsX = 0;
        gsY = 0;
        gsColor = Color.BLUE;
    }

    /**
     * Executes the input program and displays the result onto the Graphics
     * object received as an argument.
     *
     * @param program contains the source to be executed.
     * @param g the graphics context.
     */
    
    public void execute(String program, Graphics g) {

        reset();

        r = new Reader(program);

        g.setColor(gsColor);

        while (r.hasMoreTokens()) {

            Token t = r.nextToken();

            if (t.isNumber()) {

                operands.push(t);

            } else if (t.getSymbol().equals("add")) {

                execute_add();

            } else if (t.getSymbol().equals("sub")) {

                execute_sub();

            } else if (t.getSymbol().equals("mul")) {

                execute_mul();

            } else if (t.getSymbol().equals("div")) {

                execute_div();

            } else if (t.getSymbol().equals("pop")) {

                execute_pop();

            } else if (t.getSymbol().equals("clear")) {

                execute_clear();

            } else if (t.getSymbol().equals("pstack")) {

                execute_pstack();

            } else if (t.getSymbol().equals("moveto")) {

                execute_moveto();

            } else if (t.getSymbol().equals("lineto")) {

                execute_lineto(g);

            } else if (t.getSymbol().equals("arc")) {

                execute_arc(g);

            } else if (t.getSymbol().equals("quit")) {

                execute_quit();

            } else {

                System.err.println("ILLEGAL SYMBOL: " + t);

            }
        }

    }

    private void execute_add() {
        Token op1 = operands.pop();
        Token op2 = operands.pop();
        Token res = new Token(op1.getNumber() + op2.getNumber());
        operands.push(res);
    }

    private void execute_sub() {
        Token op1 = operands.pop();
        Token op2 = operands.pop();
        Token res = new Token(op2.getNumber() - op1.getNumber());
        operands.push(res);
    }

    private void execute_mul() {
        Token op1 = operands.pop();
        Token op2 = operands.pop();
        Token res = new Token(op1.getNumber() * op2.getNumber());
        operands.push(res);
    }

    private void execute_div() {
        Token op1 = operands.pop();
        Token op2 = operands.pop();
        Token res = new Token(op2.getNumber() / op1.getNumber());
        operands.push(res);
    }

    private void execute_pop() {
        operands.pop();
    }

    private void execute_moveto() {
        Token y = operands.pop();
        Token x = operands.pop();
        gsX = x.getNumber();
        gsY = y.getNumber();
    }

    private void execute_lineto(Graphics g) {
        Token y = operands.pop();
        Token x = operands.pop();
        g.drawLine(gsX, gsY, x.getNumber(), y.getNumber());
        gsX = x.getNumber();
        gsY = y.getNumber();
    }

    private void execute_arc(Graphics g) {
        Token a2 = operands.pop();
        Token a1 = operands.pop();
        Token r = operands.pop();
        g.drawArc(gsX, gsY, r.getNumber(), r.getNumber(), a1.getNumber(),
                a2.getNumber());
    }

    private void execute_pstack() {

        System.out.println(operands);

    }

    private void execute_clear() {
        while (!operands.isEmpty()) {
            operands.pop();
        }
    }

    private void execute_quit() {
        System.out.println("Bye!");
        System.exit(0);
    }

}
