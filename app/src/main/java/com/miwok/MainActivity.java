package com.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    //TextView Objects
    TextView numbers,phrases,family,colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numbers=(TextView) findViewById(R.id.numbers);
        family=(TextView) findViewById(R.id.family);
        colors=(TextView) findViewById(R.id.colors);
        phrases=(TextView) findViewById(R.id.phrases);

        //activities intents ;


        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Numbers.class);
                startActivity(intent);
            }
        });


        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Phrases.class);
                startActivity(intent);
            }
        });

       family.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent= new Intent( getApplicationContext(),Family.class);
               startActivity(intent);
           }
       });

     colors.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent= new Intent(getApplicationContext(),Colors.class);
             startActivity(intent);
         }
     });



    }


}