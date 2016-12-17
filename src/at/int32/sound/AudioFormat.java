package at.int32.sound;

public class AudioFormat {

	public enum Channel {
		MONO, STEREO
	}

	private int frameSize, sampleRate, sampleSizeInBits;
	private boolean signed, bigEndian;
	private Channel channel;

	public AudioFormat(int frameSize, int sampleRate, int sampleSizeInBits,
			Channel channel, boolean signed, boolean bigEndian) {
		this.frameSize = frameSize;
		this.sampleRate = sampleRate;
		this.sampleSizeInBits = sampleSizeInBits;
		this.channel = channel;
		this.signed = signed;
		this.bigEndian = bigEndian;
	}

	public int frameSize() {
		return frameSize;
	}

	public int sampleRate() {
		return sampleRate;
	}

	public int sampleSizeInBits() {
		return sampleSizeInBits;
	}

	public Channel channel() {
		return channel;
	}

	public int channelAsInt() {
		return channel == Channel.MONO ? 1 : 2;
	}

	public boolean signed() {
		return signed;
	}

	public boolean bigEndioan() {
		return bigEndian;
	}

	public int bufferSize() {
		return (channelAsInt() * (sampleSizeInBits / 8) * (sampleRate / 1000))
				* frameSize;
	}

	public javax.sound.sampled.AudioFormat ToAudioFormat() {
		return new javax.sound.sampled.AudioFormat(sampleRate,
				sampleSizeInBits, channelAsInt(), signed, bigEndian);
	}

}
