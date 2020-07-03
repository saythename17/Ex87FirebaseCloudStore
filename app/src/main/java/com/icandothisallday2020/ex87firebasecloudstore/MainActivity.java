package com.icandothisallday2020.ex87firebasecloudstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveBtn(View view) {
        //FireStore DB에 저장 - Map Collection 을 통으로 저장
        //저장할 데이터를 Map 으로 생성
        Map<String,Object> user=new HashMap<>();
        user.put("name","ice bear");
        user.put("age",20);
        user.put("address","Alaska");
        
        //FireStore DB 객체 소환
       // FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        
        //"users"라는 이름의 Collection(≒자식노드) 참조
        CollectionReference userRef=firestore.collection("users");// users: 
        userRef.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    public void loadBtn(View view) {
        //FireStore DB 에서 get()메소드를 이용->DB값 읽기
        CollectionReference users=firestore.collection("users");
        Task<QuerySnapshot> task=users.get();
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    //user 안에 여러개의 문서가 있으므로
                    for(QueryDocumentSnapshot snapshot: querySnapshot){
                        Map<String, Object> user = snapshot.getData();
                        String name= (String) user.get("name");
                        long age= (long) user.get("age");
                        String address=user.get("address").toString();
                        TextView tv=findViewById(R.id.tv);
                        tv.append(name+","+age+","+address+"\n");
                    }
                }
            }
        });

    }
}
