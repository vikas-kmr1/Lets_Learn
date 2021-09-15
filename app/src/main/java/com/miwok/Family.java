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

public class Family extends AppCompatActivity {

    ListView family_listView;
    ArrayList<Words> family_list=new ArrayList<Words>();

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
        setContentView(R.layout.activity_family);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //xml reference
        family_listView=(ListView) findViewById(R.id.family_ListView);



        //append word in arrayList

        family_list.add(new Words("Father","पिता जी",R.drawable.family_father,R.raw.family_father));
        family_list.add(new Words("Mother","माँ जी",R.drawable.family_mother,R.raw.family_mother));
        family_list.add(new Words("Daughter","बेटी",R.drawable.family_daughter,R.raw.family_daughter));
        family_list.add(new Words("Son","बेटा",R.drawable.family_son,R.raw.family_son));
        family_list.add(new Words("GrandFather","दादा जी",R.drawable.family_grandfather,R.raw.family_grandfather));
        family_list.add(new Words("GrandMother","दादी जी",R.drawable.family_grandmother,R.raw.family_grandmother));
        family_list.add(new Words("Elder Brother","बड़े भाई",R.drawable.family_older_brother,R.raw.family_elder_brother));
        family_list.add(new Words("Elder Sister","बड़ी बहन",R.drawable.family_older_sister,R.raw.family_elder_sister));
        family_list.add(new Words("Younger Brother","छोटा भाई",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        family_list.add(new Words("Younger Sister","छोटी बहन",R.drawable.family_younger_sister,R.raw.family_younger_sister));



        WordsAdapter listAdapter=new WordsAdapter(this,family_list,R.color.category_family);
        //setting adapter Listview
        family_listView.setAdapter(listAdapter);



        family_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words word = family_list.get(position);

                mediaPlayer = mediaPlayer.create(Family.this, word.getMaudioResource());
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
                    mediaPlayer = mediaPlayer.create(Family.this, word.getMaudioResource());

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