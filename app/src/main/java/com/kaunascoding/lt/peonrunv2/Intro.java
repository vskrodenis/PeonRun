package com.kaunascoding.lt.peonrunv2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.introscreen);

        Thread myThread = new Thread(){

            @Override
            public void run(){
                try {
                    //pause aka loading time
                    sleep(2000);
                    //intent leidzia is vienos klases pertiti i kita klase
                    Intent startgame = new Intent(getApplicationContext(),Game.class);
                    startActivity(startgame);
                    finish();



                }catch (InterruptedException e){e.printStackTrace();}

            }
        };//end of thread
        //kad veiktu visas processas reikia kad startintusi thread
        myThread.start();




    }
}
