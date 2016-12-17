package at.int32.sound;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioRecorder implements IAudioComponent {

	private TargetDataLine in;
	private byte[] buffer;
	private Thread thread;
	private ConcurrentLinkedQueue<IAudioListener> listeners;

	public AudioRecorder(AudioFormat format) {
		try {
			this.listeners = new ConcurrentLinkedQueue<IAudioListener>();
			this.thread = new Thread(new Runnable() {

				@Override
				public void run() {
					while (!Thread.interrupted()) {
						callback();
					}
				}
			});
			this.in = AudioSystem.getTargetDataLine(format.ToAudioFormat());
			this.in.open();
			this.in.start();

			this.buffer = new byte[format.bufferSize()];
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		thread.start();
	}

	@Override
	public void stop() {
		thread.interrupt();
	}

	public void addCallback(IAudioListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	private void callback() {
		if (in.available() > buffer.length) {
			int bytesRead = in.read(buffer, 0, buffer.length);
			if (bytesRead == buffer.length) {
				notifyListeners(buffer);
			}
		}
	}

	private void notifyListeners(byte[] audio) {
		for (IAudioListener listener : listeners) {
			listener.callback(audio);
		}
	}
}
