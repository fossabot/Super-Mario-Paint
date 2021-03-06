package smp;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import smp.components.Values;
import smp.components.InstrumentIndex;
import smp.components.staff.sequences.Note;
import smp.components.staff.sounds.SMPSynthesizer;
import smp.stateMachine.Settings;

/**
 * Loads the soundfonts that will be used to play sounds.
 * Also holds a Synthesizer and Soundbank that will be used
 * to play more sounds.
 * @author RehdBlob
 * @since 2012.08.14
 */
public class SoundfontLoader implements Loader {

    /**
     * A number between 0 and 1 that indicates the
     * completion of the loading Thread's tasks.
     */
    private double loadStatus = 0.0;

    /**
     * The sound synthesizer used to hold as many instruments as needed.
     */
    private static SMPSynthesizer theSynthesizer;

    /**
     * The MIDI channels associated with the MultiSynthsizer.
     */
    private static MidiChannel [] chan;

    /**
     * The soundbank that will hold the sounds that we're trying to play.
     */
    private static Soundbank bank;

    /**
     * The default soundset name.
     */
    private String soundset = "soundset3.sf2";

    /**
     * Initializes a MultiSynthesizer with the soundfont.
     */
    @Override
    public void run() {
        try {
            File f = new File("./" + soundset);
            bank = MidiSystem.getSoundbank(f);
            theSynthesizer = new SMPSynthesizer();
            theSynthesizer.open();
            setLoadStatus(0.1);
            /* if (advanced mode on)
             *     theSynthesizer.ensureCapacity(50);
             * else
             */

            theSynthesizer.ensureCapacity(19);
            for (Instrument i : theSynthesizer.getLoadedInstruments()) {
                theSynthesizer.unloadInstrument(i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            setLoadStatus(0.2);
            theSynthesizer.loadAllInstruments(bank);
            setLoadStatus(0.3);

            if (Settings.debug > 0){
                System.out.println("Loaded Instruments: ");
                for (Instrument j : theSynthesizer.getLoadedInstruments())
                    System.out.println(j.getName());
            }

            int ordinal = 0;
            chan = theSynthesizer.getChannels();
            for (InstrumentIndex i : InstrumentIndex.values()) {
                setLoadStatus(0.3 + 0.7
                        * ordinal / InstrumentIndex.values().length);
                chan[ordinal].programChange(ordinal);
                chan[ordinal].controlChange(Values.REVERB, 0);
                ordinal++;
                System.out.println("Initialized Instrument: "
                        + i.toString());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (Settings.debug > 0)
                System.out.println(
                        "Synth Latency: " + theSynthesizer.getLatency());
            setLoadStatus(1);
        } catch (MidiUnavailableException e) {
            // Can't recover.
            e.printStackTrace();
            System.exit(0);
        } catch (InvalidMidiDataException e) {
            // Can't recover.
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            // Can't recover.
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * @return The MultiSynthesizer that holds a list of Synthesizers.
     */
    public static Synthesizer getSynth() {
        return theSynthesizer;
    }

    /**
     * @return An Array of references for MidiChannel objects needed to
     * play sounds.
     */
    public static MidiChannel[] getChannels() {
        return chan;
    }

    /**
     * Closes the synthesizers.
     */
    public void close() {
        theSynthesizer.close();
    }

    /**
     * @return A double value between 0 and 1, representing the
     * load status of this class.
     */
    @Override
    public double getLoadStatus() {
        return loadStatus;
    }

    /**
     * Set the load status of the SoundfontLoader.
     * @param d A double value between 0 and 1 that represents the
     * load state of this class.
     */
    @Override
    public void setLoadStatus(double d) {
        if (d >= 0 && d <= 1)
            loadStatus = d;
    }

    /**
     * Plays a certain sound given a Note and some instrument.
     * @param n The Note to play
     * @param i The Instrument to play it with.
     */
    public static void playSound(Note n, InstrumentIndex i) {
        playSound(n.getKeyNum(), i, 0);
    }

    /**
     * Plays a certain sound given a Note and some instrument, along with the
     * accidental we are supposed to play it with.
     * @param i The note index we are supposed to play this note at.
     * @param theInd The InstrumentIndex.
     * @param acc The accidental that we are given.
     */
    public static void playSound(int i, InstrumentIndex theInd, int acc) {
        playSound(i, theInd, acc, Values.MAX_VELOCITY);
    }

    /**
     * Plays a certain sound given a Note and some instrument, along with the
     * accidental we are supposed to play it with and the volume with which we are
     * trying to play at.
     * @param i The note index we are supposed to play this note at.
     * @param theInd The InstrumentIndex.
     * @param acc The accidental that we are given.
     * @param vel The velocity of the note that we are given.
     */
    public static void playSound(int i, InstrumentIndex theInd, int acc, int vel) {
        int ind = theInd.getChannel() - 1;
        chan[ind].noteOn(i + acc, vel);
    }

    /**
     * Stops a certain sound given a Note and some instrument, along with the
     * accidental we are supposed to play it with and the volume with which we are
     * trying to play at.
     * @param i The note index we are supposed to play this note at.
     * @param theInd The InstrumentIndex.
     * @param acc The accidental that we are given.
     */
    public static void stopSound(int i, InstrumentIndex theInd, int acc) {
        int ind = theInd.getChannel() - 1;
        chan[ind].noteOff(i + acc);
    }

    /**
     * Stops a certain sound given a Note and some instrument, along with the
     * accidental we are supposed to play it with and the volume with which we are
     * trying to play at.
     * @param i The note index we are supposed to play this note at.
     * @param theInd The InstrumentIndex.
     * @param acc The accidental that we are given.
     * @param vel The note-off velocity.
     */
    public static void stopSound(int i, InstrumentIndex theInd, int acc, int vel) {
        int ind = theInd.getChannel() - 1;
        chan[ind].noteOff(i + acc, vel);
    }

}
