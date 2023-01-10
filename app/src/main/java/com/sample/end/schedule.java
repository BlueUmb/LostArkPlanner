package com.sample.end;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

public class schedule extends AppCompatActivity {

    final Bundle bundle = new Bundle();
    private Button sendbtn, findbtn, plusbtn;
    private EditText editdt, email;
    public String url, name, maxlevel, characterinfo, userid;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = firebaseDatabase.getReference();
    char[] nametextarr, endlevelarr;
    String endlevel;
    String beforelevel;
    String endlevel3;
    boolean daycheck1,daycheck2,daycheck3,weekcheck1,weekcheck2,weekcheck3,weekcheck4,weekcheck5,weekcheck6,weekcheck7,weekcheck8,weekcheck9,weekcheck10;
    int i, finallevel, finallevel2;
    boolean startbool=false;

    private ListView listView;
    private ArrayAdapter<String> adapter;
    ArrayList<String> Array = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        sendbtn = (Button) findViewById(R.id.add);
        editdt = (EditText) findViewById(R.id.nametext);
        listView = (ListView) findViewById(R.id.listviewmsg);
        findbtn = (Button) findViewById(R.id.find);
        email = (EditText) findViewById(R.id.email);
        plusbtn = (Button) findViewById(R.id.plus);
        initDatabase();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        listView.setAdapter(adapter);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://lostark.game.onstove.com/Profile/Character/" + editdt.getText().toString();

                new Thread() {
                    @Override
                    public void run() {
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(url).get();
                            Element namecontents = doc.select(".profile-character-info__name").first();//닉네임 가져오기
                            Element maxlevelcontents = doc.select(".level-info2__item span").get(1);


                            name = namecontents.text();
                            maxlevel = maxlevelcontents.text();


                            characterinfo = name + "\n" + maxlevel;

                            bundle.putString("characterinfo", characterinfo);

                            char[] nametextarr2 = maxlevel.toCharArray();
                            for (i = 0; i < nametextarr2.length; i++) {
                                if (nametextarr2[i] == '1' || nametextarr2[i] == '2' || nametextarr2[i] == '3' || nametextarr2[i] == '4' || nametextarr2[i] == '5' || nametextarr2[i] == '6' || nametextarr2[i] == '7' || nametextarr2[i] == '8' || nametextarr2[i] == '9' || nametextarr2[i] == '0') {
                                    endlevel3 += Character.toString(nametextarr2[i]);
                                }
                            }

                            //핸들러를 이용해서 Thread()에서 가져온 데이터를 메인 쓰레드에 보내준다.
                            Message msg = handler.obtainMessage();
                            msg.setData(bundle);
                            handler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });
        findbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findbtn.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                plusbtn.setVisibility(View.VISIBLE);

                userid = email.getText().toString();
                mReference = mDatabase.getReference(userid); // 변경값을 확인할 child 이름
                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        adapter.clear();
                        for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                            String namemsg = messageData.child("name").getValue(String.class);
                            String levelmsg = messageData.child("level").getValue(String.class);
                            String msg2 = namemsg + "  " + levelmsg;
                            Array.add(msg2);
                            adapter.add(msg2);

                            // child 내에 있는 데이터만큼 반복합니다.

                        }
                        adapter.notifyDataSetChanged();
                        listView.setSelection(adapter.getCount() - 1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editdt.setVisibility(View.VISIBLE);
                sendbtn.setVisibility(View.VISIBLE);
                plusbtn.setVisibility(View.GONE);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(schedule.this, schedule2.class);
                intent.putExtra("character", Array.get(position));
                endlevel = null;
                beforelevel = Array.get(position);
                nametextarr = beforelevel.toCharArray();
                for (i = 0; i < nametextarr.length; i++) {
                    if (nametextarr[i] == '1' || nametextarr[i] == '2' || nametextarr[i] == '3' || nametextarr[i] == '4' || nametextarr[i] == '5' || nametextarr[i] == '6' || nametextarr[i] == '7' || nametextarr[i] == '8' || nametextarr[i] == '9' || nametextarr[i] == '0') {
                        endlevel += Character.toString(nametextarr[i]);
                    }
                }
                String finalendlevel = endlevel.substring(4, endlevel.length() - 2);
                String name = beforelevel.substring(0, beforelevel.indexOf(" "));
                finallevel = Integer.parseInt(finalendlevel);
                intent.putExtra("level", finallevel);
                intent.putExtra("userid", userid);
                intent.putExtra("name", name);

                ///////////////////////////////////////////////////////firebase DB생성
                DatabaseReference memberRef = rootRef.child(userid);
                DatabaseReference itemRef = memberRef.child(name);
                DatabaseReference itemRef2 = itemRef.child("스케쥴");
                DatabaseReference daycontent = itemRef2.child("일일");
                DatabaseReference weekcontent = itemRef2.child("주간");
                itemRef2.child("start").setValue(true);

                //////////////////////////////////////////////////////////////캐릭터의 레벨에 따라 DB항목추가
                daycontent.child("에포나 의뢰").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            daycheck1 = Boolean.parseBoolean(snapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                daycontent.child("가디언 토벌").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            daycheck2 = Boolean.parseBoolean(snapshot.getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                daycontent.child("카오스 던전").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        daycheck3 = Boolean.parseBoolean(snapshot.getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                weekcontent.child("도전 가디언 토벌").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            weekcheck1 = Boolean.parseBoolean(snapshot.getValue().toString());

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                weekcontent.child("도전 어비스 던전").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            weekcheck2 = Boolean.parseBoolean(snapshot.getValue().toString());

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                if(finallevel>=1415) {
                    weekcontent.child("발탄").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            weekcheck3 = Boolean.parseBoolean(snapshot.getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                if(finallevel>=1430) {
                    weekcontent.child("비아키스").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            weekcheck4 = Boolean.parseBoolean(snapshot.getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }

                if(finallevel>=1370) {
                    weekcontent.child("아르고스").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            weekcheck5 = Boolean.parseBoolean(snapshot.getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                if(finallevel>=1325) {
                    weekcontent.child("오레하의 우물").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            weekcheck6 = Boolean.parseBoolean(snapshot.getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }

                if(finallevel>=1475) {
                    weekcontent.child("쿠크세이튼 노말").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            weekcheck7 = Boolean.parseBoolean(snapshot.getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                if(finallevel>=1490) {
                    weekcontent.child("아브렐슈드").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            weekcheck8 = Boolean.parseBoolean(snapshot.getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                }
                if(finallevel>=1385) {
                    weekcontent.child("쿠크세이튼 리허설").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            weekcheck9 = Boolean.parseBoolean(snapshot.getValue().toString());
                            Log.d("Main", String.valueOf(weekcheck9));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                if(finallevel>=1430) {
                    weekcontent.child("아브렐슈드 데자뷰").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            weekcheck10 = Boolean.parseBoolean(snapshot.getValue().toString());


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                intent.putExtra(name+"epona", daycheck1);
                intent.putExtra(name+"guardian", daycheck2);
                intent.putExtra(name+"chaos", daycheck3);
                intent.putExtra(name+"challengeguardian", weekcheck1);
                intent.putExtra(name+"challengedungeon", weekcheck2);
                intent.putExtra(name+"baltan", weekcheck3);
                intent.putExtra(name+"biakiss", weekcheck4);
                intent.putExtra(name+"argos", weekcheck5);
                intent.putExtra(name+"oreha", weekcheck6);
                intent.putExtra(name+"cook", weekcheck7);
                intent.putExtra(name+"abrell", weekcheck8);
                intent.putExtra(name+"cookrehasal", weekcheck9);
                intent.putExtra(name+"abrelldejavu", weekcheck10);

                startActivity(intent);
                daycheck1 = false;
                daycheck2 = false;
                daycheck3 = false;
                weekcheck1 = false;
                weekcheck2 = false;
                weekcheck3 = false;
                weekcheck4 = false;
                weekcheck5 = false;
                weekcheck6 = false;
                weekcheck7 = false;
                weekcheck8 = false;
                weekcheck9 = false;
                weekcheck10 = false;


            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            DatabaseReference memberRef = rootRef.child(userid);
            DatabaseReference itemRef = memberRef.child(name);
            DatabaseReference itemRef2 = itemRef.child("스케쥴");
            DatabaseReference daycontent = itemRef2.child("일일");
            DatabaseReference weekcontent = itemRef2.child("주간");
            itemRef.child("level").setValue(maxlevel);
            itemRef.child("name").setValue(name);
            daycontent.child("에포나 의뢰").setValue(false);
            daycontent.child("카오스 던전").setValue(false);
            daycontent.child("가디언 토벌").setValue(false);
            weekcontent.child("도전 가디언 토벌").setValue(false);
            weekcontent.child("도전 어비스 던전").setValue(false);



            String finalendlevel2 = endlevel3.substring(4, endlevel3.length() - 2);
            finallevel2 = Integer.parseInt(finalendlevel2);
            if (finallevel2 >= 1325) {
                weekcontent.child("오레하의 우물").setValue(false);
            }
            if (finallevel2 >= 1370) {
                weekcontent.child("아르고스").setValue(false);
            }
            if (finallevel2 >= 1415) {
                weekcontent.child("발탄").setValue(false);
            }
            if (finallevel2 >= 1430) {
                weekcontent.child("비아키스").setValue(false);
            }
            if (finallevel2 >= 1475) {
                weekcontent.child("쿠크세이튼 노말").setValue(false);
            }
            if (finallevel2 >= 1490) {
                weekcontent.child("아브렐슈드").setValue(false);
            }
            if (finallevel2 >= 1385) {
                weekcontent.child("쿠크세이튼 리허설").setValue(false);
            }
            if (finallevel2 >= 1430) {
                weekcontent.child("아브렐슈드 데자뷰").setValue(false);
            }
        }
    };
        private void initDatabase() {

            mDatabase = FirebaseDatabase.getInstance();

            mReference = mDatabase.getReference("log");
            mReference.child("log").setValue("check");

            mChild = new ChildEventListener() {

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mReference.addChildEventListener(mChild);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            mReference.removeEventListener(mChild);
        }
    }