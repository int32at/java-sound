# java-sound
A neat little wrapper to make the horrors of Java Sound more enjoyable.

```java

import at.int32.sound.AudioFormat;
import at.int32.sound.AudioFormat.Channel;
import at.int32.sound.AudioPlayer;
import at.int32.sound.AudioRecorder;


// the format in which you would like to record/play
AudioFormat format = new AudioFormat(20, 48000, 16, Channel.STEREO, true, false);

// create a new recorder instance
AudioRecorder recorder = new AudioRecorder(format);

// adding the player as callback will simply output the audio through
// your default output device
recorder.addCallback(new AudioPlayer(format));

// start recording!
recorder.start();
```

### Audio Format
- Framesize: the size of the frame which should be recorded. Will be used for buffer calculations.
- Sample Rate: the sample rate (in Hz) which is supported by the soundcard
- Sample Size: the sample size (in bits)
- Channel: MONO or STEREO
- Signed: whether the audio should be PCM signed/unsigned
- Big Endian: whether to use big or little endian

### Audio Recorder
The audio recorder is used to record raw PCM data directly from the system based on the given AudioFormat.
It runs in a seperate thread and will supply *one* or *multiple* IAudioListener(s) with data whenever possible. 
There is no need to buffer the data yourself, the recorder takes care of that already!

### Audio Player
The audio player is used to play raw PCM data directly into the system. 
It can play any kind of audio no matter the format.

### IAudioListener
You can provide your own listener by implementing the `IAudioListener` interface or using an anonymous class instead.

```java
recorder.addCallback(new IAudioListener() {			

		@Override
		public void callback(byte[] audio) {
			//proccess pcm data here
		}
    
});
```



