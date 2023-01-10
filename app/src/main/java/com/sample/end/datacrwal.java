package com.sample.end;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class datacrwal extends AppCompatActivity {
    TextView textVi,textVi2,textVi3,basicabilitytitle,settingtitle,basictitle,textVi4,jeweltitle;
    EditText edit;
    Button btn,jewelbtn,basicbtn,statbtn;
    String characterinfo,characterinfo2,characterinfo3,characterinfo4,level,name,url,maxlevel,title,guild,pvp,landlevel,landname,attackability,heartability,jewel;
    String setting,endlevel;
    char[] settingarr,jewelarr,levelarr;
    int i,jewelcount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datacrawl);

        statbtn = (Button)findViewById(R.id.statbtn);
        btn=(Button)findViewById(R.id.btn1);
        jewelbtn=(Button)findViewById(R.id.jewelbtn);
        basicbtn=(Button)findViewById(R.id.basicbtn);
        edit=(EditText)findViewById(R.id.edit);
        textVi=(TextView)findViewById(R.id.crawltext);
        textVi2=(TextView)findViewById(R.id.crawltext2);
        textVi3=(TextView)findViewById(R.id.crawltext3);
        basicabilitytitle=(TextView)findViewById(R.id.basicabilitytitle);
        basictitle=(TextView)findViewById(R.id.basictitle);
        jeweltitle=(TextView)findViewById(R.id.jeweltitle);
        textVi4=(TextView)findViewById(R.id.jewel);
        settingtitle=(TextView)findViewById(R.id.settingtitle);

        textVi4.setMovementMethod(new ScrollingMovementMethod());
        final Bundle bundle = new Bundle();

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                url = "https://lostark.game.onstove.com/Profile/Character/" + edit.getText().toString();
                characterinfo3 = " ";
                characterinfo4 = " ";
                basicbtn.setVisibility(View.VISIBLE);
                jewelbtn.setVisibility(View.VISIBLE);

                new Thread(){
                    @Override
                    public void run() {
                        Document doc = null;
                        try {
                            ///////////////////////////////////////////////////////////// 유저정보 크롤링
                            doc = Jsoup.connect(url).get();
                            Element namecontents = doc.select(".profile-character-info__name").first();//닉네임 가져오기
                            Element levelcontents = doc.select(".level-info2__expedition span").get(1); //장착아이템레벨 가져오기
                            Element maxlevelcontents = doc.select(".level-info2__item span").get(1); //달성아이템레벨 가져오기
                            Elements settingcontents = doc.select(".profile-ability-engrave span"); //각인 가져오기
                            Element titlecontents = doc.select(".game-info__title span").get(1); //칭호 가져오기
                            Element guildcontents = doc.select(".game-info__guild span").get(1); //길드 가져오기
                            Element pvpcontents = doc.select(".level-info__pvp span").get(1); //pvp 가져오기
                            Element landlevelcontents = doc.select(".game-info__wisdom span").get(1); //영지레벨 가져오기
                            Element landnamecontents = doc.select(".game-info__wisdom span").get(2); //영지이름 가져오기
                            Element attackabilitycontents = doc.select(".profile-ability-basic span").get(1); //공격력 가져오기
                            Element heartabliltycontents = doc.select(".profile-ability-basic span").get(3); //체력 가져오기
                            Elements jewelcontents = doc.select(".jewel-effect__list p"); //보석 가져오기
                            ////////////////////////////////////////유저 정보 파싱
                            name = "닉네임 : "+ namecontents.text();
                            level = "장착아이템레벨 : "+levelcontents.text();
                            maxlevel = "달성아이템레벨 : "+maxlevelcontents.text();
                            title= "칭호 : "+titlecontents.text();
                            guild = "길드 : "+guildcontents.text();
                            pvp = "PVP : "+pvpcontents.text();
                            landlevel = "영지 레벨 : "+landlevelcontents.text();
                            landname = "영지 이름 : "+landnamecontents.text();
                            attackability = " 공격력 : "+attackabilitycontents.text();
                            heartability = " 최대 생명력 : "+heartabliltycontents.text();
                            ////////////////////////////////////////////////////////////////////////보석띄어쓰기 만들기
                            jewel = jewelcontents.text();
                            jewelarr = jewel.toCharArray();

                            for(i=0; i<jewelarr.length; i++){
                                if(jewelarr[i]=='%')
                                {
                                    jewelcount = 1;
                                    characterinfo4 += Character.toString(jewelarr[i]);
                                }
                                else if(jewelcount==1&&jewelarr[i]=='소') {
                                    characterinfo4 += Character.toString(jewelarr[i]) + "\n";
                                    jewelcount = 0;
                                }
                                else if(jewelcount==1&&jewelarr[i]=='가') {
                                    characterinfo4 += Character.toString(jewelarr[i]) + "\n";
                                    jewelcount = 0;
                                }
                                else
                                    characterinfo4 += Character.toString(jewelarr[i]);
                            }


                            characterinfo = name+"\n"+level+"\n"+maxlevel+"\n"+title+"\n"+guild+"\n"+pvp+"\n"+landname+"\n"+landlevel;

                            /////////////////////////////////////////////////////////////////////////각인띄어쓰기만들기
                            setting = settingcontents.text();
                            settingarr = setting.toCharArray();
                            for(i=0; i<settingarr.length; i++){
                                if(settingarr[i]=='1'||settingarr[i]=='2'||settingarr[i]=='3')
                                {
                                    characterinfo3 += Character.toString(settingarr[i])+"\n";
                                }
                                else
                                    characterinfo3 += Character.toString(settingarr[i]);
                            }
                            /////////////////////////////////////////////////////////////////////////////////////
                            characterinfo2 =attackability+"\n"+heartability;

                            bundle.putString("characterinfo", characterinfo);
                            bundle.putString("characterinfo2", characterinfo2);
                            bundle.putString("characterinfo3", characterinfo3);
                            bundle.putString("characterinfo4", characterinfo4);
                            //핸들러를 이용해서 Thread()에서 가져온 데이터를 메인 쓰레드에 보내준다.
                            Message msg = handler.obtainMessage();
                            msg.setData(bundle);
                            handler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                settingtitle.setVisibility(View.GONE);
                basicabilitytitle.setVisibility(View.GONE);
                basictitle.setVisibility(View.VISIBLE);
                jeweltitle.setVisibility(View.GONE);
                textVi.setVisibility(View.VISIBLE);
                textVi2.setVisibility(View.GONE);
                textVi3.setVisibility(View.GONE);
                statbtn.setVisibility(View.VISIBLE);
                textVi4.setVisibility(View.GONE);
            }
        });
        basicbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                settingtitle.setVisibility(View.GONE);
                basicabilitytitle.setVisibility(View.GONE);
                basictitle.setVisibility(View.VISIBLE);
                jeweltitle.setVisibility(View.GONE);
                textVi.setVisibility(View.VISIBLE);
                textVi2.setVisibility(View.GONE);
                textVi3.setVisibility(View.GONE);
                textVi4.setVisibility(View.GONE);
            }
        });
        jewelbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                settingtitle.setVisibility(View.GONE);
                basicabilitytitle.setVisibility(View.GONE);
                basictitle.setVisibility(View.GONE);
                jeweltitle.setVisibility(View.VISIBLE);
                textVi.setVisibility(View.GONE);
                textVi2.setVisibility(View.GONE);
                textVi3.setVisibility(View.GONE);
                textVi4.setVisibility(View.VISIBLE);
            }
        });
        statbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                settingtitle.setVisibility(View.VISIBLE);
                basicabilitytitle.setVisibility(View.VISIBLE);
                basictitle.setVisibility(View.GONE);
                jeweltitle.setVisibility(View.GONE);
                textVi.setVisibility(View.GONE);
                textVi2.setVisibility(View.VISIBLE);
                textVi3.setVisibility(View.VISIBLE);
                textVi4.setVisibility(View.GONE);
            }
        });


    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String content = bundle.getString("characterinfo");
            String content2 = bundle.getString("characterinfo4");

            SpannableString spannableString = new SpannableString(content);
            String word = name.substring(5, name.length());
            int startword = content.indexOf(word);
            int end = startword + word.length();
            endlevel = null;
            levelarr = level.toCharArray();
            for (i = 0; i < levelarr.length; i++) {
                if (levelarr[i] == '1' || levelarr[i] == '2' || levelarr[i] == '3' ||levelarr[i] == '4' || levelarr[i] == '5' || levelarr[i] == '6' || levelarr[i] == '7' ||levelarr[i] == '8' || levelarr[i] == '9' || levelarr[i] == '0') {
                    endlevel += Character.toString(levelarr[i]);
                }
            }
            String finalendlevel = endlevel.substring(4, endlevel.length() - 2);
            Log.d("level",finalendlevel);
            int Colorlevel = Integer.parseInt(finalendlevel);


            if(1300>Colorlevel){
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), startword, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            else if(Colorlevel>=1500){
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), startword, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            else if(Colorlevel>=1400){
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4682B4")), startword, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            else if(Colorlevel>=1300){
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#7CFC00")), startword, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            SpannableString spannableString2 = new SpannableString(content2);

            int jewellevel;
            String jeweltest;
            int jewelmiddle;
            String jeweltestindex;
            String[] str= characterinfo4.split("\n");
            int length=0;
            if(characterinfo4!=" ") {
                for (int i = 0; i < str.length; i++) {
                    jeweltest = str[i].substring(str[i].length() - 10);
                    jewellevel = Integer.parseInt(jeweltest.substring(1, 3));
                    String jewellevel2 = str[i].substring(str[i].length() - 2);

                    if (jewellevel2.equals("감소")) {
                        if (jewellevel >= 20) {
                            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), length, length + str[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (jewellevel >= 14) {
                            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFA500")), length, length + str[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (jewellevel >= 10) {
                            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#9932CC")), length, length + str[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (jewellevel >= 6) {
                            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#1E90FF")), length, length + str[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#7CFC00")), length, length + str[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    } else if (jewellevel2.equals("증가")) {
                        if (jewellevel >= 40) {
                            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), length, length + str[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (jewellevel >= 21) {
                            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#FFA500")), length, length + str[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (jewellevel >= 15) {
                            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#9932CC")), length, length + str[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (jewellevel >= 9) {
                            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#1E90FF")), length, length + str[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#7CFC00")), length, length + str[i].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                    length += str[i].length() + 1;
                }
            }



            textVi.setText(spannableString);//이런식으로 View를 메인 쓰레드에서 뿌려줘야한다.
            textVi2.setText(bundle.getString("characterinfo2"));
            textVi3.setText(bundle.getString("characterinfo3"));
            textVi4.setText(spannableString2);
        }
    };
}

