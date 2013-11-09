package com.example.sound;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private MediaRecorder mRecorder =null;
	private MediaPlayer   mPlayer = null;
	
	private static final String LOG_TAG = "AudioRecordTest";
	private String RAW_RECORD_FILE = "";
	
	static final int frequency = 44100;
	static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	int recBufSize, playBufSize;
	
	private AudioRecord audioRecord = null;
	private AudioTrack audioTrack = null;
	
	private boolean isRecording = false;
	private File audioFile;
	private File audioChangeFile;
	
	private Thread recordThread = null;
	private Thread playThread = null;
	
	private LinkedList<byte[]> wavDatas = new LinkedList<byte[]>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		recBufSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
		playBufSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
		
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency, channelConfiguration,
				audioEncoding, recBufSize);
		
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequency, channelConfiguration,
				audioEncoding, playBufSize + 2048, AudioTrack.MODE_STREAM);
		
		File fpath = new File(this.getApplicationContext().getFilesDir() + "/");
		try {
			audioFile = new File(fpath, "recording.pcm");
			audioChangeFile = new File(fpath, "recording2.wav");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDestroy() {
		if (audioFile.exists()) {
			//audioFile.delete();
		}
		if (audioChangeFile.exists()) {
			audioChangeFile.delete();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	public void onRecord(View v) {
		// 开始记录临时音频文件
		// 使用AudioRecorder记录音频文件，并存为WAV格式
		isRecording = true;
		//开通输出流到指定的文件  
        
		try{
			// 启动监听进程
			recordThread = new Thread(new Runnable(){
				
				public void run(){
					Log.d("MyAndroid", "This is run Android");
					short[] buffer = new short[recBufSize];
					try {
						DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(audioFile)));
						audioRecord.startRecording();
						while(isRecording) {
							int bufferReadResult = audioRecord.read(buffer, 0, recBufSize);
							//循环将buffer中的音频数据写入到OutputStream中  
		                    for(int i=0; i<bufferReadResult; i++){  
		                        dos.writeShort(buffer[i]);
		                    }
						}
						audioRecord.stop();
						dos.flush();
						dos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			});
			recordThread.start();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void onStop(View v) {
		isRecording = false;
		//recordThread.stop();
	}
	
	public void onChange(View v) {
		// 执行音频修改
		// 使用SoundTouch做
		JNISoundTouch soundtouch = new JNISoundTouch();
		soundtouch.setSampleRate(16000);
		soundtouch.setChannels(1);
		soundtouch.setPitchSemiTones(10);
		soundtouch.setRateChange(-0.7f);
		soundtouch.setTempoChange(0.5f);
		
		FileInputStream inStream;
		wavDatas.clear();
		try {
			inStream = new FileInputStream(audioFile);
			
			long size = audioFile.length();
			byte[] data_pack = new byte[(int) size];
			inStream.read(data_pack);
			
			short[] shorts = Utils.byteToShortSmall(data_pack);
			soundtouch.putSamples(shorts, shorts.length);
			
			short[] buffer;
			do {
				buffer = soundtouch.receiveSamples();
				byte[] bytes = Utils.shortToByteSmall(buffer);
				wavDatas.add(bytes);
			} while (buffer.length > 0);
			
			// 存储数据
			int fileLength = 0;
			for(byte[] bytes: wavDatas){
				fileLength += bytes.length;
			}
			
			WaveHeader header = new WaveHeader(fileLength);
			byte[] headers = header.getHeader();	
			
			// 保存为wav文件
			FileOutputStream out = new FileOutputStream(audioChangeFile);
			out.write(headers);
			
			for(byte[] bytes: wavDatas){
				out.write(bytes);
			}
			
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onPlay(View v) {
		// 播放原本的音频文件
		// 使用AudioTrack
		try{
			// 启动监听进程
			playThread = new Thread(new Runnable(){
						
				public void run(){
							
					try {
						FileInputStream inStream;
						inStream = new FileInputStream(audioChangeFile);
								
						long size = audioFile.length();
						byte[] data_pack = new byte[(int) size];
						inStream.read(data_pack);
						
						audioTrack.play();
						int offset = 0;
						while (offset < size) {
							audioTrack.write(data_pack, offset, playBufSize);
							offset += playBufSize;
						}
						audioTrack.stop();
								
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
						
			});
			playThread.start();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void onPlayRaw(View v) {
		// 播放原本的音频文件
		// 使用AudioTrack
		try {
			FileInputStream inStream;
			inStream = new FileInputStream(audioFile);
			
			int bufferSize = 512;
			int i = 0;
			
			byte[] buffer = new byte[bufferSize];
			audioTrack.play();
			while((i = inStream.read(buffer)) != -1) {
				audioTrack.write(buffer, 0, i);
			}
			inStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
