package smp.components.staff;

import smp.ImageIndex;
import smp.ImageLoader;
import smp.components.staff.sequences.StaffNoteLine;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * This takes care of the volume bars on the staff.
 * @author RehdBlob
 * @since 2013.12.01
 *
 */
public class StaffVolumeEventHandler implements EventHandler<Event> {

    /** The line number of this volume bar, on the screen. */
    private int line;

    /** The StackPane that this event handler is linked to. */
    private StackPane stp;

    /** The ImageView object that is this volume bar. */
    private ImageView theVolBar;

    /** The StaffNoteLine that this event handler is associated with. */
    private StaffNoteLine theLine;

    /** Makes a new StaffVolumeEventHandler. */
    public StaffVolumeEventHandler(StackPane st) {
        stp = st;
        theVolBar = (ImageView) st.getChildren().get(0);
        theVolBar.setImage(ImageLoader.getSpriteFX(ImageIndex.VOL_BAR));
        theVolBar.setVisible(false);
    }

    @Override
    public void handle(Event event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            mousePressed((MouseEvent) event);
        } else if (event.getEventType() == MouseEvent.DRAG_DETECTED) {
            mouseDragStart();
        } else if (event.getEventType() == DragEvent.DRAG_DONE) {
            mouseDragEnd();
        }
    }

    /** Called whenever the mouse is pressed. */
    private void mousePressed(MouseEvent event) {
        setVolume(stp.getHeight() - event.getY());
    }

    /** Called whenever the mouse is dragged. */
    private void mouseDragStart() {

    }

    /** Called whenever we finish dragging the mouse. */
    private void mouseDragEnd() {

    }

    /**
     * Sets the volume of this note based on the y location of the click.
     * @param y The y-location of the click.
     */
    private void setVolume(double y) {
        theVolBar.setVisible(true);
        theVolBar.setFitHeight(y);
    }

    @Override
    public String toString() {
        return "Line: " + line;
    }

}