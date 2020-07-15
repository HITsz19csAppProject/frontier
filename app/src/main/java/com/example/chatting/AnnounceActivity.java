package com.example.chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AnnounceActivity extends AppCompatActivity {

    private Button publish,addLabel;
    private EditText my_headline;
    private EditText my_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);
        publish=findViewById(R.id.publish);
        addLabel = findViewById(R.id.btn_addlabel);
        my_headline=findViewById(R.id.my_headline);
        my_context=findViewById(R.id.my_context);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                String myheadline=my_headline.getText().toString();
                String mycontext=my_context.getText().toString();
                if(!myheadline.isEmpty()&&!mycontext.isEmpty())
                {
                    intent.putExtra("headline_return",myheadline);
                    intent.putExtra("context_return",mycontext);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else{
                    my_headline.setText("error");
                }
            }
        });
        addLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnounceActivity.this,LabelActivity.class);
                startActivity(intent);
            }
        });

    }
}