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

public class Numbers extends AppCompatActivity {

    ListView number_listView;


    ArrayList<Words> number_List = new ArrayList<Words>();
    /** Handles playback of all the sound files */
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

                mediaPlayer.seekTo(0);
                mediaPlayer.pause();
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
        setContentView(R.layout.activity_numbers);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        //xml reference
        number_listView = (ListView) findViewById(R.id.number_ListView);


        //append word in arrayList

        number_List.add(new Words("One", "एक", R.drawable.number_one, R.raw.number_one));
        number_List.add(new Words("Two", "दो", R.drawable.number_two, R.raw.number_two));
        number_List.add(new Words("Three", "तीन", R.drawable.number_three, R.raw.number_three));
        number_List.add(new Words("Four", "चार", R.drawable.number_four, R.raw.number_four));
        number_List.add(new Words("Five", "पाँच", R.drawable.number_five, R.raw.number_five));
        number_List.add(new Words("Six", "छह", R.drawable.number_six, R.raw.number_six));
        number_List.add(new Words("Seven", "सात", R.drawable.number_seven, R.raw.number_seven));
        number_List.add(new Words("Eight", "आठ", R.drawable.number_eight, R.raw.number_eight));
        number_List.add(new Words("Nine", "नौ", R.drawable.number_nine, R.raw.number_nine));
        number_List.add(new Words("Ten", "दस", R.drawable.number_ten, R.raw.number_ten));


        WordsAdapter listAdapter = new WordsAdapter(this, number_List, R.color.category_numbers);
        //setting adapter Listview
        number_listView.setAdapter(listAdapter);


        number_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words word = number_List.get(position);
                mediaPlayer = mediaPlayer.create(Numbers.this, word.getMaudioResource());
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
                    mediaPlayer = mediaPlayer.create(Numbers.this, word.getMaudioResource());

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

            mediaPlayer.release();

            mediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }
}