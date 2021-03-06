# Prerequisites

First, consult the prerequisites section of [Python binding](/binding/python). Additionally, demo application uses
[PyAudio](https://people.csail.mit.edu/hubert/pyaudio/) for recording input audio (i.e. microphone).
Consult the installation guide at [PyAudio](https://people.csail.mit.edu/hubert/pyaudio/).

# Demo Application

Usage information can be found via

```bash
python demo/python/rhino_demo.py --help
```

On a Raspberry Pi 3 the demo can be run via

```bash
python demo/python/rhino_demo.py \
--rhino_library_path ./lib/raspberry-pi/cortex-a53/libpv_rhino.so \
--rhino_model_file_path ./lib/common/rhino_params.pv \
--rhino_context_file_path ./resources/contexts/raspberrypi/smart_lighting_raspberrypi.rhn \
--porcupine_library_path ./resources/porcupine/lib/raspberry-pi/cortex-a53/libpv_porcupine.so \
--porcupine_model_file_path ./resources/porcupine/lib/common/porcupine_params.pv \
--porcupine_keyword_file_path ./resources/porcupine/resources/keyword_files/raspberrypi/hey\ pico_raspberrypi.ppn
```

It starts recording audio from the **default** input audio device, initializes instances of Porcupine and Rhino
engines, and monitors the incoming audio for the wake phrase **Hey Pico**. Upon detection of the wake word the followup
 command is processed by Rhino to infer user's intent and the inferred result is writen into the console.

For running on a different platform you to use the corresponding platform-specific library paths, Porcupine keyword file,
and Rhino context file. For example to run the same demo on a Linux box

```bash
python demo/python/rhino_demo.py \
--rhino_library_path ./lib/linux/x86_64/libpv_rhino.so \
--rhino_model_file_path ./lib/common/rhino_params.pv \
--rhino_context_file_path ./resources/contexts/linux/music_player_linux.rhn \
--porcupine_library_path ./resources/porcupine/lib/linux/x86_64/libpv_porcupine.so \
--porcupine_model_file_path ./resources/porcupine/lib/common/porcupine_params.pv \
--porcupine_keyword_file_path ./resources/porcupine/resources/keyword_files/linux/hey\ pico_linux.ppn
```

Below is an example console output

```
****************************** context ******************************
<expressions>

turnLight : {turnCommand} the lights
turnLight : {turnCommand} the {location} lights
turnLight : {turnCommand} the lights in the {location}
changeIntensity : make the lights {intensityChange}
changeIntensity : make the {location} lights {intensityChange}
changeIntensity : make the lights in the {location} {intensityChange}
changeColor : set the lights to {color}
changeColor : set the {location} lights to {color}
changeColor : set the lights in the {location} to {color}

<slots>

turnCommand : [turn off, turn on]
location : [attic, balcony, basement, bathroom, bedroom, corridor, den, entrance, kitchen, living room]
intensityChange : [brighter, darker]
color : [blue, green, lavender, olive, pink, purple, red, silver, violet, white, yellow]
*********************************************************************

ALSA lib pcm.c:2266:(snd_pcm_open_noupdate) Unknown PCM cards.pcm.rear
ALSA lib pcm.c:2266:(snd_pcm_open_noupdate) Unknown PCM cards.pcm.center_lfe
ALSA lib pcm.c:2266:(snd_pcm_open_noupdate) Unknown PCM cards.pcm.side
ALSA lib pcm_route.c:867:(find_matching_chmap) Found no matching channel map
Cannot connect to server socket err = No such file or directory
Cannot connect to server request channel
jack server is not running or cannot be started
JackShmReadWritePtr::~JackShmReadWritePtr - Init not done for 4294967295, skipping unlock
JackShmReadWritePtr::~JackShmReadWritePtr - Init not done for 4294967295, skipping unlock
````

First, the demo outputs the information regarding the context of Rhino including supported expressions, slots, and their
possible values. The information is retrieved by calling `rhino.context_expressions`

There are few lines of output generated by [ALSA](https://en.wikipedia.org/wiki/Advanced_Linux_Sound_Architecture). Don't
be alarmed this is normal! In order to detect a different wake word change the keyword file. In order to infer commands
within a different context change the context file.

## FAQ

#### The demo application does not detect/infer anything. Why?

The most probable cause of this is that the default audio input device recognized by PyAudio is not the one being used.
There are a couple of debugging facilities baked into the demo application to solve this. First, type the following into
the console

```bash
python ./demo/python/rhino_demo.py --show_audio_devices_info
```

It provides information about various audio input devices on the box. On a Linux box, this is the console output

```
'index': '0', 'name': 'HDA Intel PCH: ALC892 Analog (hw:0,0)', 'defaultSampleRate': '44100.0', 'maxInputChannels': '2'
'index': '1', 'name': 'HDA Intel PCH: ALC892 Alt Analog (hw:0,2)', 'defaultSampleRate': '44100.0', 'maxInputChannels': '2'
'index': '2', 'name': 'HDA NVidia: HDMI 0 (hw:1,3)', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '3', 'name': 'HDA NVidia: HDMI 1 (hw:1,7)', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '4', 'name': 'HDA NVidia: HDMI 2 (hw:1,8)', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '5', 'name': 'HDA NVidia: HDMI 3 (hw:1,9)', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '6', 'name': 'HDA NVidia: HDMI 0 (hw:2,3)', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '7', 'name': 'HDA NVidia: HDMI 1 (hw:2,7)', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '8', 'name': 'HDA NVidia: HDMI 2 (hw:2,8)', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '9', 'name': 'HDA NVidia: HDMI 3 (hw:2,9)', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '10', 'name': 'Logitech USB Headset: Audio (hw:3,0)', 'defaultSampleRate': '44100.0', 'maxInputChannels': '1'
'index': '11', 'name': 'sysdefault', 'defaultSampleRate': '48000.0', 'maxInputChannels': '128'
'index': '12', 'name': 'front', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '13', 'name': 'surround21', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '14', 'name': 'surround40', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '15', 'name': 'surround41', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '16', 'name': 'surround50', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '17', 'name': 'surround51', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '18', 'name': 'surround71', 'defaultSampleRate': '44100.0', 'maxInputChannels': '0'
'index': '19', 'name': 'pulse', 'defaultSampleRate': '44100.0', 'maxInputChannels': '32'
'index': '20', 'name': 'dmix', 'defaultSampleRate': '48000.0', 'maxInputChannels': '0'
'index': '21', 'name': 'default', 'defaultSampleRate': '44100.0', 'maxInputChannels': '32'
```

It can be seen that the last device (index 21) is considered default. But on this machine, a headset is being used as
the input device which has an index of 10. After finding the correct index the demo application can be invoked as below

```bash
python demo/python/rhino_demo.py \
--rhino_library_path ./lib/linux/x86_64/libpv_rhino.so \
--rhino_model_file_path ./lib/common/rhino_params.pv \
--rhino_context_file_path ./resources/contexts/linux/smart_lighting_linux.rhn \
--porcupine_library_path ./resources/porcupine/lib/linux/x86_64/libpv_porcupine.so \
--porcupine_model_file_path ./resources/porcupine/lib/common/porcupine_params.pv \
--porcupine_keyword_file_path ./resources/porcupine/resources/keyword_files/linux/hey\ pico_linux.ppn
--input_audio_device_index 10
```

If the problem persists we suggest storing the recorded audio into a file for inspection. This can be achieved by

```bash
python demo/python/rhino_demo.py \
--rhino_library_path ./lib/linux/x86_64/libpv_rhino.so \
--rhino_model_file_path ./lib/common/rhino_params.pv \
--rhino_context_file_path ./resources/contexts/linux/smart_lighting_linux.rhn \
--porcupine_library_path ./resources/porcupine/lib/linux/x86_64/libpv_porcupine.so \
--porcupine_model_file_path ./resources/porcupine/lib/common/porcupine_params.pv \
--porcupine_keyword_file_path ./resources/porcupine/resources/keyword_files/linux/hey\ pico_linux.ppn
--input_audio_device_index 10 \
--output-path ~/test.wav
```

If after listening to stored file there is no apparent problem detected please open an issue.
