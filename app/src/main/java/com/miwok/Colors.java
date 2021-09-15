package com.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Colors extends AppCompatActivity {

    ListView colors_listView;
    ArrayList<Words> colors_list = new ArrayList<Words>();

    private MediaPlayer mediaPlayer;

    /** Handles audio focus when playing a sound file */
    private AudioManager mAudioManager;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releasemediaPlayer();
        }
    };


    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releasemediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //xml reference
        colors_listView = (ListView) findViewById(R.id.colors_ListView);


        //append word in arrayList

        colors_list.add(new Words("Black", "काला", R.drawable.color_black, R.raw.colors_black));
        colors_list.add(new Words("Brown", "भूरा", R.drawable.color_brown, R.raw.colors_brown));
        colors_list.add(new Words("Gray", "धुमैला (ग्रे)", R.drawable.color_gray, R.raw.colors_gray));
        colors_list.add(new Words("Red", "लाल", R.drawable.color_red, R.raw.colors_red));
        colors_list.add(new Words("White", "सफेद", R.drawable.color_white, R.raw.colors_white));
        colors_list.add(new Words("Green", "हरा", R.drawable.color_green, R.raw.colors_green));
        colors_list.add(new Words("Mustard Yellow", "सरसों पीला", R.drawable.color_mustard_yellow, R.raw.colors_mustard_yellow));
        colors_list.add(new Words("Dusty Yellow", "धूल भरा पीला", R.drawable.color_dusty_yellow, R.raw.colors_dusty_yellow));


        WordsAdapter listAdapter = new WordsAdapter(this, colors_list, R.color.category_colors);
        //setting adapter Listview
        colors_listView.setAdapter(listAdapter);
        colors_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words word = colors_list.get(position);

                mediaPlayer = mediaPlayer.create(Colors.this, word.getMaudioResource());
                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                // Start the audio file
                mediaPlayer.start();
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                    // Create and setup the {@link mediaPlayer} for the audio resource associated
                    // with the current word
                    mediaPlayer = mediaPlayer.create(Colors.this, word.getMaudioResource());

                    // Start the audio file
                    mediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });




    }

    @Override
    protected void onStop() {
        super.onStop();
        releasemediaPlayer();
    }


    /**
     * Clean up the media player by releasing its resources.
     */
    private void releasemediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}