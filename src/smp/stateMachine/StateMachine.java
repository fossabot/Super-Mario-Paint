package smp.stateMachine;

/**
 * This is the state machine that keeps track of what state the
 * main window is in.
 * @author RehdBlob
 * @since 2012.08.07
 */
public class StateMachine {

    /** Whether we've pressed down the shift key. */
    private static boolean shiftPressed = false;

    /** Whether we've pressed down the alt key. */
    private static boolean altPressed = false;

    /** Whether we've pressed down the ctrl key. */
    private static boolean ctrlPressed = false;

    /**
     * The default state that the program is in is the
     * EDITING state, in which notes are being placed on the staff.
     */
    private static State currentState = State.EDITING;

    /**
     * The default time signature that we start out with is 4/4 time.
     */
    private static TimeSignature currentTimeSignature = TimeSignature.FOUR_FOUR;

    /**
     * The current measure line number that the program is on. Typically
     * a number between 0 and 383. This is zero by default.
     */
    private static int currentLine = 0;

    /**
     * This is the current tempo that the program is running at.
     */
    private static double tempo = 240;


    /**
     * Do not make an instance of this class! The implementation is such
     * that several classes may check the overall state of the program,
     * so there should only ever be just the class and its static variables
     * and methods around.
     * @deprecated
     */
    private StateMachine(){
    }

    /**
     * Get the current <code>State</code> of the <code>StateMachine</code>
     * @return The current <code>State</code>.
     */
    public static synchronized State getState() {
        return currentState;
    }

    /**
     * @param s Set the <code>StateMachine</code> to a certain State.
     */
    public static synchronized void setState(State s) {
        currentState = s;
    }

    /**
     * Sets the state back to "Editing" by default.
     */
    public static synchronized void resetState() {
        currentState = State.EDITING;
    }

    /**
     * @return The current time signature that we are running at.
     */
    public static synchronized TimeSignature getTimeSignature() {
        return currentTimeSignature;
    }

    /**
     * Sets the time signature to whatever that we give this method.
     * @param t The new time signature.
     */
    public static synchronized void setTimeSignature(TimeSignature t) {
        currentTimeSignature = t;
    }

    /** Sets the time signature back to "4/4" by default. */
    public static synchronized void resetTimeSignature() {
        currentTimeSignature = TimeSignature.FOUR_FOUR;
    }



    /**
     * @return The tempo that this program is running at.
     */
    public static synchronized double getTempo() {
        return tempo;
    }

    /**
     * Sets the tempo to what we give it here.
     * @param num The tempo we want to set the program to run at.
     * @return
     */
    public static synchronized void setTempo(double num) {
        tempo = num;
    }

    /**
     * Gets the current line number that we're on. Typically a value
     * between 0 and 383 for most files unless you've done fun stuff
     * and removed the 96-measure limit.
     * @return The current line number (left justify)
     */
    public static synchronized int getMeasureLineNum() {
        return currentLine;
    }

    /**
     * Sets the current line number to whatever is given to this method.
     * @param num The number that we're trying to set our current
     * line number to.
     */
    public static synchronized void setMeasureLineNum(int num) {
        currentLine = num;
    }

    /** @return Whatever key is pressed at the moment on the keyboard. */
    public static synchronized int getKeyPressed() {
        return 0;
    }

    /** Sets that we've pressed down the shift key. */
    public static void setShiftPressed() {
        shiftPressed = true;
    }

    /** @return Is shift pressed down? */
    public static boolean isShiftPressed() {
        return shiftPressed;
    }

    /** Sets that we've pressed down the alt key. */
    public static void setAltPressed() {
        altPressed = true;
    }

    /** @return Is alt pressed down? */
    public static boolean isAltPressed() {
        return altPressed;
    }

    /** Sets that we've pressed down the ctrl key. */
    public static void setCtrlPressed() {
        ctrlPressed = true;
    }

    /** @return Is ctrl pressed down? */
    public static boolean isCtrlPressed() {
        return ctrlPressed;
    }

    /** Sets that we've released the shift key. */
    public static void resetShiftPressed() {
        shiftPressed = true;
    }

    /** Sets that we've released the alt key. */
    public static void resetAltPressed() {
        altPressed = true;
    }

    /** Sets that we've released the ctrl key. */
    public static void resetCtrlPressed() {
        ctrlPressed = true;
    }


}
