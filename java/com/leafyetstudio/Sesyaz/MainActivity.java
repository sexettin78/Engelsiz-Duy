package com.leafyetstudio.Sesyaz;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    TextToSpeech t1;
    EditText ed1;
    Button b1;
    Button mic;
    TextView yazi;
    private  static final int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button);
        mic = findViewById(R.id.mic);
        yazi = findViewById(R.id.yazi);


        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(new Locale("tr", "TR"));
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ed1.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });



mic.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        try
        {
            Intent sesIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);//sesi yazıya çevirme servisi
            sesIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getClass().getPackage().getName());//istekte bulnuyoruz
            sesIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);//dil modeli
            startActivityForResult(sesIntent, id);//ses olayı için verdiğimiz id değişmeyecek static bir id intenti başlatıyoruz
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this,"Telefonunuz Bu Servisi Desteklemiyor",Toast.LENGTH_SHORT).show();
            //cihaz servisi desteklemiyorsa mesaj veriyor
        }

    }
});



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)//eğer bir veri varsa
        {
            ArrayList yazıyaDonusen = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);//sonucu arraylist olarak dönderdi
            yazi.setText(yazıyaDonusen.get(0).toString());//elde ettiğimiz sonucu label'da gösterdik
        }

    }
}
