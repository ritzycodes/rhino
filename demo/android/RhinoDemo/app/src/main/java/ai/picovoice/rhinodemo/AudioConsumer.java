/*
 * Copyright 2018 Picovoice Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.picovoice.rhinodemo;

/**
 * Consumes (processes) audio in consecutive chunks (frames).
 */
interface AudioConsumer {
    /**
     * Consumes (processes) a frame of audio.
     *
     * @param pcm Audio PCM.
     * @throws Exception On failure.
     */
    void consume(short[] pcm) throws Exception;

    /**
     * Getter for valid number of samples per frame.
     *
     * @return Valid number of samples per frame.
     */
    int getFrameLength();

    /**
     * Getter for valid sample rate.
     *
     * @return Valid sample rate.
     */
    int getSampleRate();
}
