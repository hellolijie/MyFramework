package cn.lijie.activity;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import cn.lijie.R;
import cn.lijie.utils.FileUtils;

public class SoundRecoderActivity extends Activity{
	private MediaRecorder mRecorder;
	private MediaPlayer mPlayer;
	private FileUtils fileUtils;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sound_recoder_layout);
		fileUtils=new FileUtils();
	}

	private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        try {
        	File audioFile = File.createTempFile("record_", ".3gp");
//        mRecorder.setOutputFile(fileUtils.getSDPath()+"audiorecordtest.3gp");
        	mRecorder.setOutputFile(audioFile.getAbsolutePath());
        	mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("tag", "prepare() failed");
        }

        mRecorder.start();
    }
	
	 private void stopRecording() {
	        mRecorder.stop();
	        mRecorder.release();
	        mRecorder = null;
	    }
	 
	 private void startPlaying(String mFileName) {
	        mPlayer = new MediaPlayer();
	        try {
	            mPlayer.setDataSource(mFileName);
	            mPlayer.prepare();
	            mPlayer.start();
	        } catch (IOException e) {
	            Log.e("tag", "prepare() failed");
	        }
	    }
}
