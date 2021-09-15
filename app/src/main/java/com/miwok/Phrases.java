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

public class Phrases extends AppCompatActivity {

    ListView phrases_listView;

    ArrayList<Words> phrases_list = new ArrayList<Words>();
 MediaPlayer mediaPlayer;
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
        setContentView(R.layout.activity_phrases);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //xml reference
        phrases_listView = (ListView) findViewById(R.id.phrases_ListView);


        //append word in arrayList

        phrases_list.add(new Words("How are you?", "आप कैसे हैं|", R.raw.phrases_how_r_you));
        phrases_list.add(new Words("Where are you from?", "आप कहाँ से हो|", R.raw.phrases_where_are_you_from));
        phrases_list.add(new Words("I hope you are doing well.", "मुझे उम्मीद है कि आप अच्छा कर रहे हैं।", R.raw.phrases_i_hope_you_are_doing_well));
        phrases_list.add(new Words("I'm fine.", "मै ठीक हूं।", R.raw.phrasrs_im_fine));
        phrases_list.add(new Words("Nice to meet  you.", "आप से मिलकर अच्छा लगा।", R.raw.phrases_nice_to_meet_you));
        phrases_list.add(new Words("Wait for me.", "मेरा इंतजार करना।", R.raw.phrases_wait_for_me));
        phrases_list.add(new Words("What are you studying.", "आप क्या पढ़ रहे हैं।", R.raw.phrases_what_are_you_studying));
        phrases_list.add(new Words("I know.", "मुझे पता है।", R.raw.phrases_i_know));
        phrases_list.add(new Words("See you tomorrow.", "आप से कल मिलता हूं।", R.raw.phrases_see_you_tomorrow));
        phrases_list.add(new Words("Where are you going?", "तुम कहाँ जा रहे हो?", R.raw.phrases_where_are_going));


        WordsAdapter listAdapter = new WordsAdapter(this, phrases_list, R.color.category_phrases);
        //setting adapter Listview
        phrases_listView.setAdapter(listAdapter);

        phrases_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words word = phrases_list.get(position);
                mediaPlayer = mediaPlayer.create(Phrases.this, word.getMaudioResource());
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
                    mediaPlayer = mediaPlayer.create(Phrases.this, word.getMaudioResource());

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