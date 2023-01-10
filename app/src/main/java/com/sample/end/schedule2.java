package com.sample.end;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class schedule2  extends AppCompatActivity {

    TextView text;
    CheckBox daycheck1,daycheck2, daycheck3,weekcheck1,weekcheck2,weekcheck3,weekcheck4,weekcheck5,weekcheck6,weekcheck7,weekcheck8,weekcheck9,weekcheck10;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference rootRef = firebaseDatabase.getReference();
    boolean daystate1,daystate2,daystate3,weekstate1,weekstate2,weekstate3,weekstate4,weekstate5,weekstate6,weekstate7,weekstate8,weekstate9,weekstate10;
    String checkstate;
    ListView listview ;
    ListView listview2 ;
    private ArrayList<String> mList;
    private ArrayList<String> mList2;
    boolean state,state2;
    boolean endcheck[];
    int i=0;
    Button schedulebtn;
    boolean schedulebtnstate=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule2);

        schedulebtn=(Button)findViewById(R.id.scheduleswap);
        text=(TextView)findViewById(R.id.characterid);
        daycheck1=(CheckBox)findViewById(R.id.daycheckbox1);
        daycheck2=(CheckBox)findViewById(R.id.daycheckbox2);
        daycheck3=(CheckBox)findViewById(R.id.daycheckbox3);
        weekcheck1=(CheckBox)findViewById(R.id.weekcheckbox1);
        weekcheck2=(CheckBox)findViewById(R.id.weekcheckbox2);
        weekcheck3=(CheckBox)findViewById(R.id.weekcheckbox3);
        weekcheck4=(CheckBox)findViewById(R.id.weekcheckbox4);
        weekcheck5=(CheckBox)findViewById(R.id.weekcheckbox5);
        weekcheck6=(CheckBox)findViewById(R.id.weekcheckbox6);
        weekcheck7=(CheckBox)findViewById(R.id.weekcheckbox7);
        weekcheck8=(CheckBox)findViewById(R.id.weekcheckbox8);
        weekcheck9=(CheckBox)findViewById(R.id.weekcheckbox9);
        weekcheck10=(CheckBox)findViewById(R.id.weekcheckbox10);


        Intent intent = getIntent();
        String nametext2 = intent.getStringExtra("character");
        String userid = intent.getStringExtra("userid");
        String name = intent.getStringExtra("name");
        daystate1 = intent.getBooleanExtra(name+"epona",true);
        daystate2 = intent.getBooleanExtra(name+"guardian",false);
        daystate3 = intent.getBooleanExtra(name+"chaos",false);
        weekstate1 = intent.getBooleanExtra(name+"challengeguardian", false);
        weekstate2 = intent.getBooleanExtra(name+"challengedungeon", false);
        weekstate3 = intent.getBooleanExtra(name+"oreha", false);
        weekstate4 = intent.getBooleanExtra(name+"argos", false);
        weekstate5 = intent.getBooleanExtra(name+"cookrehasal",true);
        weekstate6 = intent.getBooleanExtra(name+"baltan", false);
        weekstate7 = intent.getBooleanExtra(name+"abrelldejavu", false);
        weekstate8 = intent.getBooleanExtra(name+"biakiss",false);
        weekstate9 = intent.getBooleanExtra(name+"cook", false);
        weekstate10 = intent.getBooleanExtra(name+"abrell", false);

        Log.d("schedule",String.valueOf(weekstate9));
        int finallevel = intent.getIntExtra("level",0);
        DatabaseReference memberRef = rootRef.child(userid);
        DatabaseReference itemRef = memberRef.child(name);
        DatabaseReference itemRef2 = itemRef.child("스케쥴");
        DatabaseReference daycontent = itemRef2.child("일일");
        DatabaseReference weekcontent = itemRef2.child("주간");
        itemRef2.child("start").setValue(false);

        text.setText(nametext2);

        weekcheck1.setVisibility(View.GONE);
        weekcheck2.setVisibility(View.GONE);
        weekcheck3.setVisibility(View.GONE);
        weekcheck4.setVisibility(View.GONE);
        weekcheck5.setVisibility(View.GONE);
        weekcheck6.setVisibility(View.GONE);
        weekcheck7.setVisibility(View.GONE);
        weekcheck8.setVisibility(View.GONE);
        weekcheck9.setVisibility(View.GONE);
        weekcheck10.setVisibility(View.GONE);



        mList = new ArrayList<String>();
        mList2 = new ArrayList<String>();


        daycheck1.setChecked(daystate1);
        daycheck2.setChecked(daystate2);
        daycheck3.setChecked(daystate3);
        weekcheck1.setChecked(weekstate1);
        weekcheck2.setChecked(weekstate2);
        weekcheck3.setChecked(weekstate3);
        weekcheck4.setChecked(weekstate4);
        weekcheck5.setChecked(weekstate5);
        weekcheck6.setChecked(weekstate6);
        weekcheck7.setChecked(weekstate7);
        weekcheck8.setChecked(weekstate8);
        weekcheck9.setChecked(weekstate9);
        weekcheck10.setChecked(weekstate10);

        mList.add("에포나 의뢰") ;


        // 첫 번째 아이템 추가.
        mList.add("카오스 던전") ;


        // 두 번째 아이템 추가.
        mList.add("가디언 토벌") ;


        // 세 번째 아이템 추가.
        mList2.add("도전 가디언 토벌") ;

        // 네 번째 아이템 추가.
        mList2.add("도전 어비스 던전") ;

        // 다섯 번째 아이템 추가.
        if(finallevel>=1325){
            mList2.add("오레하의 우물") ;
        }
        if(finallevel>=1370){
            mList2.add("아르고스") ;

        }
        if(finallevel>=1385){

            mList2.add("쿠크세이튼\n리허설") ;

        }
        if(finallevel>=1415){
            if(finallevel>=1445){
                mList2.add("발탄 하드") ;
            }
            else{
                mList2.add("발탄 노말") ;
            }
        }
        if(finallevel>=1430){

            mList2.add("아브렐슈드\n데자뷰") ;

        }
        if(finallevel>=1430){
            if(finallevel>=1460){
                mList2.add("비아키스 하드") ;
            }
            else{
                mList2.add("비아키스 노말") ;
            }
        }
        if(finallevel>=1475){
            mList2.add("쿠크세이튼 노말") ;

        }
        if(finallevel>=1490){
            mList2.add("아브렐슈드") ;

        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mList);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mList2);


        listview = (ListView) findViewById(R.id.schedulelist2);
        listview.setAdapter(adapter);


        listview2 = (ListView) findViewById(R.id.schedulelist3);
        listview2.setAdapter(adapter2);

        schedulebtn.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(schedulebtnstate==true){
                  daycheck1.setVisibility(View.VISIBLE);
                  daycheck2.setVisibility(View.VISIBLE);
                  daycheck3.setVisibility(View.VISIBLE);
                  listview.setVisibility(View.VISIBLE);
                  weekcheck1.setVisibility(View.GONE);
                  weekcheck2.setVisibility(View.GONE);
                  weekcheck3.setVisibility(View.GONE);
                  weekcheck4.setVisibility(View.GONE);
                  weekcheck5.setVisibility(View.GONE);
                  weekcheck6.setVisibility(View.GONE);
                  weekcheck7.setVisibility(View.GONE);
                  weekcheck8.setVisibility(View.GONE);
                  weekcheck9.setVisibility(View.GONE);
                  weekcheck10.setVisibility(View.GONE);
                  listview2.setVisibility(View.GONE);
                  schedulebtn.setText("일 간");
                  schedulebtnstate=false;
              }
              else{
                  daycheck1.setVisibility(View.GONE);
                  daycheck2.setVisibility(View.GONE);
                  daycheck3.setVisibility(View.GONE);
                  listview.setVisibility(View.GONE);
                  listview2.setVisibility(View.VISIBLE);
                  schedulebtn.setText("주 간");
                  schedulebtnstate=true;

                  if(finallevel>=1490){
                    weekcheck1.setVisibility(View.VISIBLE);
                    weekcheck2.setVisibility(View.VISIBLE);
                    weekcheck3.setVisibility(View.VISIBLE);
                    weekcheck4.setVisibility(View.VISIBLE);
                    weekcheck5.setVisibility(View.VISIBLE);
                    weekcheck6.setVisibility(View.VISIBLE);
                    weekcheck7.setVisibility(View.VISIBLE);
                    weekcheck8.setVisibility(View.VISIBLE);
                    weekcheck9.setVisibility(View.VISIBLE);
                    weekcheck10.setVisibility(View.VISIBLE);

                  }
                  else if(finallevel>=1475){
                      weekcheck1.setVisibility(View.VISIBLE);
                      weekcheck2.setVisibility(View.VISIBLE);
                      weekcheck3.setVisibility(View.VISIBLE);
                      weekcheck4.setVisibility(View.VISIBLE);
                      weekcheck5.setVisibility(View.VISIBLE);
                      weekcheck6.setVisibility(View.VISIBLE);
                      weekcheck7.setVisibility(View.VISIBLE);
                      weekcheck8.setVisibility(View.VISIBLE);
                      weekcheck9.setVisibility(View.VISIBLE);

                  }
                  else if(finallevel>=1430){
                      weekcheck1.setVisibility(View.VISIBLE);
                      weekcheck2.setVisibility(View.VISIBLE);
                      weekcheck3.setVisibility(View.VISIBLE);
                      weekcheck4.setVisibility(View.VISIBLE);
                      weekcheck5.setVisibility(View.VISIBLE);
                      weekcheck6.setVisibility(View.VISIBLE);
                      weekcheck7.setVisibility(View.VISIBLE);
                      weekcheck8.setVisibility(View.VISIBLE);

                  }
                  else if(finallevel>=1415) {
                      weekcheck1.setVisibility(View.VISIBLE);
                      weekcheck2.setVisibility(View.VISIBLE);
                      weekcheck3.setVisibility(View.VISIBLE);
                      weekcheck4.setVisibility(View.VISIBLE);
                      weekcheck5.setVisibility(View.VISIBLE);
                      weekcheck6.setVisibility(View.VISIBLE);
                  }
                  else if(finallevel>=1385){
                      weekcheck1.setVisibility(View.VISIBLE);
                      weekcheck2.setVisibility(View.VISIBLE);
                      weekcheck3.setVisibility(View.VISIBLE);
                      weekcheck4.setVisibility(View.VISIBLE);
                      weekcheck5.setVisibility(View.VISIBLE);

                  }
                  else if(finallevel>=1370){
                      weekcheck1.setVisibility(View.VISIBLE);
                      weekcheck2.setVisibility(View.VISIBLE);
                      weekcheck3.setVisibility(View.VISIBLE);
                      weekcheck4.setVisibility(View.VISIBLE);

                  }
                  else if(finallevel>=1325){
                      weekcheck1.setVisibility(View.VISIBLE);
                      weekcheck2.setVisibility(View.VISIBLE);
                      weekcheck3.setVisibility(View.VISIBLE);

                  }

              }
            }
        }) ;

        // 리스트뷰 참조 및 Adapter달기
        daycheck1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(daycheck1.isChecked()) {
                    daycontent.child("에포나 의뢰").setValue(true);
                }
                else
                {
                    daycontent.child("에포나 의뢰").setValue(false);
                }
            }
        }) ;
        daycheck2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(daycheck2.isChecked()) {
                    daycontent.child("카오스 던전").setValue(true);
                }
                else
                {
                    daycontent.child("카오스 던전").setValue(false);
                }
            }
        }) ;
        daycheck3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(daycheck3.isChecked()) {
                    daycontent.child("가디언 토벌").setValue(true);
                }
                else
                {
                    daycontent.child("가디언 토벌").setValue(false);
                }
            }
        }) ;
        weekcheck1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekcheck1.isChecked()) {
                    weekcontent.child("도전 가디언 토벌").setValue(true);
                }
                else
                {
                    weekcontent.child("도전 가디언 토벌").setValue(false);
                }
            }
        }) ;
        weekcheck2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekcheck2.isChecked()) {
                    weekcontent.child("도전 어비스 던전").setValue(true);
                }
                else
                {
                    weekcontent.child("도전 어비스 던전").setValue(false);
                }
            }
        }) ;
        weekcheck3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekcheck3.isChecked()) {
                    weekcontent.child("오레하의 우물").setValue(true);
                }
                else
                {
                    weekcontent.child("오레하의 우물").setValue(false);
                }
            }
        }) ;
        weekcheck4.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekcheck4.isChecked()) {
                    weekcontent.child("아르고스").setValue(true);
                }
                else
                {
                    weekcontent.child("아르고스").setValue(false);
                }
            }
        }) ;
        weekcheck5.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekcheck5.isChecked()) {
                    weekcontent.child("쿠크세이튼 리허설").setValue(true);
                }
                else
                {
                    weekcontent.child("쿠크세이튼 리허설").setValue(false);
                }
            }
        }) ;
        weekcheck6.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(weekcheck6.isChecked()) {
                        weekcontent.child("발탄").setValue(true);
                    }
                    else
                    {
                        weekcontent.child("발탄").setValue(false);
                    }



            }
        }) ;
        weekcheck7.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekcheck7.isChecked()) {
                    weekcontent.child("아브렐슈드 데자뷰").setValue(true);
                }
                else
                {
                    weekcontent.child("아브렐슈드 데자뷰").setValue(false);
                }
            }
        }) ;
        weekcheck8.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (weekcheck8.isChecked()) {
                        weekcontent.child("비아키스").setValue(true);
                    } else {
                        weekcontent.child("비아키스").setValue(false);
                    }

            }
        }) ;
        weekcheck9.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekcheck9.isChecked()) {
                    weekcontent.child("쿠크세이튼 노말").setValue(true);
                }
                else
                {
                    weekcontent.child("쿠크세이튼 노말").setValue(false);
                }
            }
        }) ;
        weekcheck10.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekcheck10.isChecked()) {
                    weekcontent.child("아브렐슈드").setValue(true);
                }
                else
                {
                    weekcontent.child("아브렐슈드").setValue(false);
                }
            }
        }) ;

    }
    public void onBackPressed(){
        finish();
        super.onBackPressed();
    }

}
