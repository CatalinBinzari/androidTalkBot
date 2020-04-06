package com.example.botver2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText userImput;
    RecyclerView recyclerView;
    List<ResponseMessage> responseMessagesList;
    MessageAdapter messageAdapter;
    PandorabotsTalkAPI talkAPI = new PandorabotsTalkAPI();
    String response;
    static Boolean hasLink=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //testPandorabots();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userImput = findViewById(R.id.userImput);
        recyclerView = findViewById(R.id.conversation);
        responseMessagesList = new ArrayList<>();
        ResponseMessage message0 = new ResponseMessage("Hello, you can use this bot to find a star.\nType \"start\" for using bot.\nIf you want to reset session, type \"resetbot\"".toString(),false);
        //static Boolean hasLink=true;
        responseMessagesList.add(message0);
        messageAdapter = new MessageAdapter(responseMessagesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(messageAdapter);
        userImput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    ResponseMessage message = new ResponseMessage(userImput.getText().toString(), true);
                    response = talkAPI.askPandorabots(userImput.getText().toString());
                    responseMessagesList.add(message);
                    ResponseMessage message2 = new ResponseMessage(response,false);
                    responseMessagesList.add(message2);
                    userImput.setText(null);
                    messageAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }




    public static void testPandorabots () {
        Log.d("Debug", "Test1");
        PandorabotsTalkAPI talkAPI = new PandorabotsTalkAPI();
        String input; String response;
        String[] inputs = {"Hello", "How are you", "What is your name?", "Call me Richard"};
        for (int i = 0; i < inputs.length; i++) {
            response=talkAPI.askPandorabots(inputs[i]);
            System.out.println("Human ("+talkAPI.sessionid+"): "+inputs[i]);
            System.out.println("Bot: "+response);
            Log.d("De",response );
        }
    }
}
