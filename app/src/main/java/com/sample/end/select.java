package com.sample.end;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class select extends AppCompatActivity {

        Button infosearchbtn,schedulebtn;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.select);

            infosearchbtn = (Button)findViewById(R.id.infosearch);
            schedulebtn = (Button)findViewById(R.id.schedule);

            infosearchbtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(select.this,datacrwal.class);
                    startActivity(intent);
                }
            });

            schedulebtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(select.this,schedule.class);
                    startActivity(intent);
                }
            });
        }
}
