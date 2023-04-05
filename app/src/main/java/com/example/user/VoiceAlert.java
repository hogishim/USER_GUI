package com.example.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import static android.speech.tts.TextToSpeech.ERROR;


public class VoiceAlert extends Fragment {
    MainActivity mainActivity;
    private TextToSpeech tts;

    boolean tts_init = false;

    Intent speech_intent;
    RecognitionListener listener;
    SpeechRecognizer speechRecognizer;
    final int PERMISSION = 1;

    boolean recording= false;

    Button buttonNext;
    Button buttonPrev;
    Button buttonStart;
    TextView recipeText;
    TextView pageText;
    ProgressBar bar;
    String result = null;
    // 메인 액티비티 위에 올린다.

    recipe_data data = null;
    String recipeNowString;

    TextToSpeech current_tts;

    int current_index = 0;
    int maxIndex = 0;

    int barCurrentValue = 0;
    int barMaxValue = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    // 메인 액티비티에서 내려온다.
    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_voice_alert, container, false);

        CheckPermission();//한국어

        this.listener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                //사용자가 말하기 시작
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                //사용자가 말을 멈추면 호출
                //인식 결과에 따라 onError나 onResults가 호출됨
            }

            @Override
            public void onError(int error) {    //토스트 메세지로 에러 출력
                String message;
                switch (error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        message = "오디오 에러";
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        //message = "클라이언트 에러";
                        //speechRecognizer.stopListening()을 호출하면 발생하는 에러
                        return; //토스트 메세지 출력 X
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        message = "퍼미션 없음";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        message = "네트워크 에러";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        message = "네트웍 타임아웃";
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        //message = "찾을 수 없음";
                        //녹음을 오래하거나 speechRecognizer.stopListening()을 호출하면 발생하는 에러
                        //speechRecognizer를 다시 생성하여 녹음 재개
                        if (recording)
                            StartRecord();
                        return; //토스트 메세지 출력 X
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        message = "RECOGNIZER가 바쁨";
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        message = "서버가 이상함";
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        message = "말하는 시간초과";
                        break;
                    default:
                        message = "알 수 없는 오류임";
                        break;
                }
                Toast.makeText(mainActivity.getApplicationContext(), "에러가 발생하였습니다. : " + message, Toast.LENGTH_SHORT).show();
            }

            //인식 결과가 준비되면 호출
            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);	//인식 결과를 담은 ArrayList
                //인식 결과
                String newText="";
                for (int i = 0; i < matches.size() ; i++) {
                    newText += matches.get(i);
                }
                // Chat_API 주소지 박아줄것.
                request_Chat(newText,"http://9059-35-237-67-214.ngrok.io/chat_request");

                StopRecord();
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        };

        this.speech_intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speech_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,mainActivity.getApplicationContext().getPackageName());
        speech_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        bar = rootView.findViewById(R.id.progressBar);
        recipeText = rootView.findViewById(R.id.tmpTextView);
        pageText = rootView.findViewById(R.id.test);

        recipeText.setText(recipeNowString);
        pageText.setText(Integer.toString(current_index));

        current_tts = speak_set();

        barCurrentValue = bar.getProgress();
        barMaxValue = bar.getMax();

        ImageButton button6 = rootView.findViewById(R.id.toL);

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(2);
            }

        });

        ImageButton button7 = rootView.findViewById(R.id.toM);

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(4);
            }

        });

        ImageButton button8 = rootView.findViewById(R.id.toH);

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        ImageButton button9 = rootView.findViewById(R.id.toT);

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(3);
            }

        });


        ImageButton button10 = rootView.findViewById(R.id.toE);

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(5);

            }

        });



        Button button = rootView.findViewById(R.id.bcbc);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentChange(1);
            }

        });

        buttonNext = rootView.findViewById(R.id.nex);

        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                control(true);
                ResetText();

            }
        });

        buttonPrev = rootView.findViewById(R.id.baku);

        buttonPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                control(false);
                ResetText();

            }
        });

        buttonStart = rootView.findViewById(R.id.st);



        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!recording) {   //녹음 시작
                    StartRecord();
                    Toast.makeText(mainActivity.getApplicationContext(), "음성 안내를 시작합니다. 음성으로 기록합니다.", Toast.LENGTH_SHORT).show();
                    current_tts.speak("음성 안내 시작 " + recipeNowString, TextToSpeech.QUEUE_FLUSH, null, null);

                    while(current_tts.isSpeaking()){}

                    StartRecord();
                    buttonStart.setText("음성인식중");
                }
                else {  //이미 녹음 중이면 녹음 중지
                    buttonStart.setText("음성인식");
                    StopRecord();
                }
            }
        });

        return rootView;
    }

    void StartRecord() {
        recording = true;

        //이미지 및 텍스트 변경 예정.
        /*recordBtn.setImageResource(R.drawable.stop_record);
        recordTextView.setText("음성 녹음 중지");*/

        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(mainActivity.getApplicationContext());
        speechRecognizer.setRecognitionListener(listener);
        speechRecognizer.startListening(speech_intent);
    }

    //녹음 중지
    void StopRecord() {
        recording = false;

        //이미지 및 텍스트 변경 예정.
        /*recordBtn.setImageResource(R.drawable.start_record);
        recordTextView.setText("음성 녹음 시작");*/
        speechRecognizer.stopListening();
        speechRecognizer.destroy();   //녹음 중지
        Toast.makeText(mainActivity.getApplicationContext(), "음성 기록을 중지합니다.", Toast.LENGTH_SHORT).show();
    }

    public void control(boolean tmp){
        if(barMaxValue == barCurrentValue) {
            barCurrentValue = 0;
            current_index = 0;
        } else {
            bar.setVisibility(View.VISIBLE);
            if(tmp){
                barCurrentValue += (barMaxValue / maxIndex);
                current_index += 1;
                if(current_index == maxIndex){
                    Toast.makeText(mainActivity.getApplicationContext(),"마지막 페이지 입니다.",Toast.LENGTH_SHORT).show();
                    current_index = maxIndex;
                }

                ResetText();
            }else{
                barCurrentValue -= (barMaxValue / maxIndex);
                current_index -= 1;
                if(current_index < 0){
                    Toast.makeText(mainActivity.getApplicationContext(),"첫 페이지 입니다.",Toast.LENGTH_SHORT).show();
                    current_index = 0;
                }

                ResetText();
            }
        }
        bar.setProgress(barCurrentValue);
    }

    public void ResetText(){
        recipeNowString = data.getRecipeData(current_index);
    }

    public void CheckPermission() {
        //안드로이드 버전이 6.0 이상
        if ( Build.VERSION.SDK_INT >= 23 ){
            //인터넷이나 녹음 권한이 없으면 권한 요청
            if (ContextCompat.checkSelfPermission(mainActivity.getApplicationContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED
                    || ContextCompat.checkSelfPermission(mainActivity.getApplicationContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED ) {
                ActivityCompat.requestPermissions(mainActivity,
                        new String[]{Manifest.permission.INTERNET,
                                Manifest.permission.RECORD_AUDIO},PERMISSION);
            }
        }
    }

    public void request_Chat(String user_chat, String Url){

        String JSON = "{\"chatString\":\""+user_chat+"\"}";
        mainActivity.sendHttpApi(JSON,Url,106,-1);
    }

    public void Chat_result(int control){
        /*
        control : Chat_result 에 따른 제어 처리.
            0: timer
            1: next
            2: before
            3: repeat
        */
        StopRecord();

        if (control == 0) {
            //this.setTimer();
            Toast.makeText(mainActivity.getApplicationContext(), "타이머 설정", Toast.LENGTH_SHORT).show();
            current_tts.speak("타이머를 설정합니다.", TextToSpeech.QUEUE_FLUSH, null, null);
        } else if(control == 2){
            this.control(true);
            recipeText.setText(recipeNowString);
            pageText.setText(Integer.toString(current_index));
            current_tts.speak(recipeNowString, TextToSpeech.QUEUE_FLUSH, null, null);
        } else if(control == 1){
            this.control(false);
            recipeText.setText(recipeNowString);
            pageText.setText(Integer.toString(current_index));
            current_tts.speak(recipeNowString, TextToSpeech.QUEUE_FLUSH, null, null);
        } else if(control == 3){
            //this.repeat();
            Toast.makeText(mainActivity.getApplicationContext(), "다시듣기 실행", Toast.LENGTH_SHORT).show();
            current_tts.speak("다시듣기 " + recipeNowString, TextToSpeech.QUEUE_FLUSH, null, null);
        }

        while(current_tts.isSpeaking()){}

        StartRecord();

    }

    public void send_result (String result){
        this.result = result;
    }

    public void setData (recipe_data input) {
        this.data = input;
        current_index = 0;
        maxIndex = this.data.getMaxPage();
        recipeNowString = this.data.getRecipeData(current_index);
    }

    public TextToSpeech speak_set(){
        tts = new TextToSpeech(mainActivity.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                tts_init = true;

                if (status != ERROR){
                    int result = tts.setLanguage(Locale.KOREA); // 언어 선택
                    if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                        Log.e("TTS", "This Language is not supported");
                    }
                }else{
                    Log.e("TTS", "Initialization Failed!");
                }
            }
        });

        return tts;

    }
}