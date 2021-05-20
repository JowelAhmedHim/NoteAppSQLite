package com.example.noteappbysqldatabase.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.noteappbysqldatabase.SqlDatabase.DBManager;
import com.example.noteappbysqldatabase.R;
import com.example.noteappbysqldatabase.widget.ToastMessage;

public class UpdateNoteActivity extends AppCompatActivity {
    private EditText titleEd,descriptionEd;
    private Button updateBtn;
    String id;

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        dbManager = new DBManager(this);
        dbManager.open();

        init();

        Intent intent = getIntent();
        titleEd.setText(intent.getStringExtra("title"));
        descriptionEd.setText(intent.getStringExtra("description"));
        id = intent.getStringExtra("id");

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(titleEd.getText().toString()) && !TextUtils.isEmpty(descriptionEd.getText().toString()))
                {
                    String title = titleEd.getText().toString();
                    String desc = descriptionEd.getText().toString();

                    long result = dbManager.update(id,title,desc);
                    if (result == -1)
                    {
                        ToastMessage.message(getApplicationContext(),"Failed to update note");
                    }else {
                        ToastMessage.message(getApplicationContext(),"Updated successfully");
                        Intent intent = new Intent(UpdateNoteActivity
                                .this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }


                }else {
                    ToastMessage.message(getApplicationContext(),"Both field required..");
                }
            }
        });


    }
    private void init() {

        titleEd = findViewById(R.id.noteTitle);
        descriptionEd = findViewById(R.id.description);
        updateBtn = findViewById(R.id.updateBtn);

    }
}