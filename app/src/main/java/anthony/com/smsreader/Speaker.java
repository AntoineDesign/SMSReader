package anthony.com.smsreader;

import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by anthony on 11/12/2014.
 */
public  class Speaker implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;

    private boolean ready = false;

    private boolean allowed = false;

    public Speaker(Context context) {
        tts = new TextToSpeech(context, this);
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void allowed(boolean  allowed){
        this.allowed = allowed;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            //CHANGE TO MATCH LOCALE
            //SET LANGUAGE TO US ENGLISH in your case
            tts.setLanguage(Locale.US);
            ready = true;

        }else {
            ready = false;
        }
    }

    public void speak(String text){
        //Speak only if is ready
        //and user let them speak by hitting button
        if(ready && allowed){
            HashMap<String, String> hash = new HashMap<String,String>();
            hash.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_NOTIFICATION));
            tts.speak(text, TextToSpeech.QUEUE_ADD, hash);
        }
    }

    public void pause(int duration){
        tts.playSilence(duration, TextToSpeech.QUEUE_ADD, null);

    }

    //Freee up resources
    public void destroy(){
        tts.shutdown();
    }
}
