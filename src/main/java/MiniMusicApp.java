import javax.sound.midi.*;

public class MiniMusicApp {
    public static void main(String[] args) {
        MiniMusicApp mini = new MiniMusicApp();
        mini.play();
    }

    public void play() {
        try {
            // 1. Get a sequencer and open it
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            // 2. Make a new sequence
            Sequence seq = new Sequence(Sequence.PPQ, 4);

            // 3. Get a new track (track lives in the sequence, and the MIDI data lives in the track)
            Track track = seq.createTrack();

            // 4. Fill the track with MIDI events and give the sequence to the sequencer (copy-paste from book)
            // A MIDIEvent is an instruction for a part of a song. A series of events is akin to sheet music.
            // Every instruction must include timing for the instruction.
            // The MIDI instruction goes into a Message object; the event is a combo of the Message plus the moment in time when the message should 'fire'

            // Make the message
            ShortMessage a = new ShortMessage();
            // Put the Instruction in the message
            // 144 is the message type (144 = NOTE ON)
            // 1 is the channel (is it a drum, piano, etc.)
            // 44 is the note to play
            // 100 is the velocity
            a.setMessage(144, 1, 44, 100);
            // Make a new event using the message - you have to specify when to start playing a note: trigger note 'a' ot the first (1) beat
            MidiEvent noteOn = new MidiEvent(a, 1);
            // Add the note to the track - the track holds all the MIDIEvent  objects. The sequence organizes them according to when the event is supposed to happen.
            // The Sequencer plays them back in that order. You can have multiple events that happen at the same moment in time.
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            // 128 - NOTE OFF
            b.setMessage(128, 1, 44, 100);
            // You have to specify when to stop playing the note
            MidiEvent noteOff = new MidiEvent(b, 16);
            track.add(noteOff);

            // Give the sequence to sequencer
            player.setSequence(seq);

            // Start the sequencer
            player.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } // play
} // class
