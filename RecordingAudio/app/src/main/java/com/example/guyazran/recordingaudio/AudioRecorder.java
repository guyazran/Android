package com.example.guyazran.recordingaudio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * Created by guyazran on 9/10/15.
 */
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by eladlavi on 9/10/15.
 */
public class AudioRecorder {
    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int SAMPLE_RATE = 8000;
    private static final int CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL, AUDIO_FORMAT);

    int counter = 0;

    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private short[] recordAudioBuffer;

    private boolean isRecording;
    private boolean isPlaying;
    private File externalStorageDir;

    public File getExternalStorageDir() {
        return externalStorageDir;
    }

    public void setExternalStorageDir(File externalStorageDir) {
        this.externalStorageDir = externalStorageDir;
    }

    public void start(){
        if(audioRecord == null || audioRecord.getState() == AudioRecord.STATE_UNINITIALIZED){
            audioRecord = new AudioRecord(AUDIO_SOURCE, SAMPLE_RATE, CHANNEL, AUDIO_FORMAT,
                    BUFFER_SIZE);

        }
        if(recordAudioBuffer == null){
            recordAudioBuffer = new short[BUFFER_SIZE / 2];
        }

        if(audioRecord.getState() == AudioRecord.STATE_INITIALIZED){
            isRecording = true;
            counter = 0;
            Thread thread = new Thread(){
                @Override
                public void run() {
                    audioRecord.startRecording();
                    int responseCode;
                    byte[] recordAudioBufferBytes = new byte[BUFFER_SIZE];


                    File file = new File(externalStorageDir, "recording.pcm");
                    if (file.exists()) {
                        file.delete();
                    }
                    Log.d("Guy", "file:" + file.getAbsolutePath());
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(file);

                        while (isRecording) {
                            responseCode = audioRecord.read(recordAudioBuffer, 0, BUFFER_SIZE / 2);
                            if (responseCode == AudioRecord.ERROR_BAD_VALUE ||
                                    responseCode == AudioRecord.ERROR_INVALID_OPERATION) {
                                Log.d("Guy", "error recording");
                            } else {
                                //handle recorded audio
                                for (int i = 0; i < recordAudioBuffer.length; i++) {
                                    recordAudioBufferBytes[i * 2] = (byte) (recordAudioBuffer[i] & 0x00FF);
                                    recordAudioBufferBytes[i * 2 + 1] = (byte) ((recordAudioBuffer[i]) >> 8);
                                }
                                fileOutputStream.write(recordAudioBufferBytes);
                                counter ++;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                        }catch (IOException e){
                            e.getStackTrace();
                        }
                    }
                    audioRecord.stop();
                    audioRecord = null;
                }
            };
            thread.start();
        }
    }

    public void stop() {
        isRecording = false;
    }

    public boolean isRecording() {
        return isRecording;
    }

   public void play(){
       if (!isPlaying) {
           isPlaying = true;
           Thread playThread = new Thread(new Runnable() {
               @Override
               public void run() {
                   if (audioTrack == null || audioTrack.getState() == AudioTrack.STATE_UNINITIALIZED){
                       audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
                               AUDIO_FORMAT, BUFFER_SIZE, AudioTrack.MODE_STREAM);
                   }

                   audioTrack.play();
                   File file = new File(externalStorageDir, "recording.pcm");
                   if (file.exists()){
                       FileInputStream fileInputStream = null;
                       try {
                           fileInputStream = new FileInputStream(file);
                           byte[] buffer = new byte[BUFFER_SIZE];
                           int actuallyRead;
                           while ((actuallyRead = fileInputStream.read(buffer))>0){
                               audioTrack.write(buffer, 0, actuallyRead);
                           }
                       } catch (FileNotFoundException e) {
                           e.printStackTrace();
                       } catch (IOException e) {
                           e.printStackTrace();
                       } finally {
                           if (fileInputStream != null){
                               try {
                                   fileInputStream.close();
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           }
                       }
                   }
                   audioTrack.stop();
                   audioTrack = null;
                   isPlaying = false;
               }
           });
           playThread.start();
       }
   }
}
