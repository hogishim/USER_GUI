package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Main frameLayout1;
    FoodList frameLayout2;
    TestAgain frameLayout3;
    UserPage frameLayout4;
    EnrollRecipe frameLayout5;
    VoiceAlert  frameLayout6;
    RecipeView frameLayout7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout1 = new Main();
        frameLayout2 = new FoodList();
        frameLayout3 = new TestAgain();
        frameLayout4 = new UserPage();
        frameLayout5 = new EnrollRecipe();
        frameLayout6 = new VoiceAlert();
        frameLayout7 = new RecipeView();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lgn, frameLayout1).commit();

    }

    public void fragmentChange(int index){
        if(index == 1){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout1).commit();
        }
        else if(index == 2){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout2).commit();
        }
        else if(index == 3){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout3).commit();
        }
        else if(index == 4){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout4).commit();
        }
        else if(index == 5){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout5).commit();
        }
        else if(index == 6){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout6).commit();
        }
        else if(index == 7){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lgn, frameLayout7).commit();
        }
    }

}