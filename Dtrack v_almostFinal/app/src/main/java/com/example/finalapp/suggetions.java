package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class suggetions extends AppCompatActivity {
private Button ke;
private EditText id;
private TextView suggest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggetions);
        Intent intent=getIntent();
        String ID=intent.getStringExtra("KEY");
        suggest=findViewById(R.id.suggest);
        switch (Integer.parseInt(ID)){
            case 1:
                suggest.setText("Boost your intake of vegetables and fresh fruit.\n" +
                        "\nEat more low-fat dairy products and beans.\n" +
                        "\nChoose whole grains more often.\n" +                                                         //suggestions if A1C is below expected range
                        "\nChoose fish, poultry, and lean meat instead of fatty red meat or processed meat.\n" +
                        "\nReduce the amount of sweets you have.");
                break;
            case 2:
                suggest.setText("Choose healthier carbohydrates. All carbs affect blood glucose levels so it's important to know which foods contain carbohydrates.\n" +
                        "\nEat less salt.\n" +
                        "\nEat less red and processed meat.\n" +
                        "\nEat more fruit and veg.\n" +
                        "\nChoose healthier fats.\n" +                                      //suggestions if A1C is above expected range
                        "\nCut down on added sugar.\n" +
                        "\nBe smart with snacks.\n" +
                        "\nDrink alcohol sensibly.\n"+ "Consult your Physician.");
                break;
            case 3:
                suggest.setText("Eat small meals more frequently. \n" +
                        "\nDrink more water and limit alcohol. \n" +
                        "\nIf you exercise outdoors in extreme heat, take frequent breaks and be sure to increase hydration efforts.\n" +           //suggestions if bp is below expected range
                        "\nAvoid prolonged bed rest.\n" +
                        "\nWear compression stockings. ");
                break;
            case 4:
                suggest.setText("Lose extra pounds.\n" +
                        "\nExercise regularly.\n" +
                        "\nEat a healthy diet.\n" +
                        "\nReduce sodium in your diet.\n" +
                        "\nLimit the amount of alcohol you drink.\n" +          //suggestions if bp is above expected range
                        "\nQuit smoking.\n" +
                        "\nCut back on caffeine.\n" +
                        "\nReduce your stress.");
                break;
            case 5:
                suggest.setText("Add healthy calories by adding nut or seed toppings, cheese, almonds, sunflower seeds, fruit, wheat toast.\n" +
                        "\nGo nutrient dense.Consider high-protein meats, which can help you to build muscle, choose nutritious carbohydrates, such as brown rice and other whole grains. \n" +                     //suggestions if bmi is below expected range
                        "\nSnack away. Consider options like trail mix, protein bars or drinks, and crackers with hummus or peanut butter.");
                break;
            case 6:
                suggest.setText("Eat a high protein breakfast.\n" +
                        "\nAvoid sugary drinks and fruit juice.\n" +
                        "\nDrink water before meals.\n" +
                        "\nChoose weight-loss-friendly foods.\n" +
                        "\nEat soluble fiber.\n" +                                  //suggestions if bmi is above expected range
                        "\nDrink coffee or tea.\n" +
                        "\nBase your diet on whole foods.\n" +
                        "\nEat slowly.\n" +
                        "\nConsult your Physician.");
        }



        /*ke=findViewById(R.id.ke);
        id=findViewById(R.id.idkey);
        ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setText(ID);
            }
        });*/

    }
}
