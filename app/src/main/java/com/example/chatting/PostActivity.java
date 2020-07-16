package com.example.chatting;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
=======
import android.os.Bundle;
import android.view.View;
>>>>>>> 57a7597d622aeacff8e3aff3b8bf31bc4cc297a6
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
public class PostActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button mBtnAddLabel;
=======
import com.example.chatting.R;

public class PostActivity extends AppCompatActivity {

    private ImageView imageView;
>>>>>>> 57a7597d622aeacff8e3aff3b8bf31bc4cc297a6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        imageView = (ImageView)findViewById(R.id.im_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
<<<<<<< HEAD
        mBtnAddLabel = (Button)findViewById(R.id.btn_label);
        mBtnAddLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, LabelActivity.class);
                startActivity(intent);
            }
        });
=======
>>>>>>> 57a7597d622aeacff8e3aff3b8bf31bc4cc297a6
    }
}