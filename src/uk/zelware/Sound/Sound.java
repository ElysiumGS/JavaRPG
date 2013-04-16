package uk.zelware.Sound;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import uk.zelware.Game.Level.Level;

public class Sound {
	public static void playSound(String path){

	    try {

	        Sequence sequence = MidiSystem.getSequence(Level.class.getResource(path));
	        Sequencer sequencer = MidiSystem.getSequencer();
	        sequencer.open();
	        sequencer.setSequence(sequence);
	        sequencer.start();
	    } catch (IOException e) {
	    } catch (MidiUnavailableException e) {
	    } catch (InvalidMidiDataException e) {
	    }
	}
}
