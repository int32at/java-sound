package at.int32.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer implements IAudioListener {

	private SourceDataLine out;

	public AudioPlayer(AudioFormat format) {
		try {
			this.out = AudioSystem.getSourceDataLine(format.ToAudioFormat());
			this.out.open();
			this.out.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void callback(byte[] audio) {
		if(out.available() > audio.length) {
			out.write(audio, 0, audio.length);
		}
	}

}
