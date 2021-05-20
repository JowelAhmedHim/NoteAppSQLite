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

public class AddNotesActivity extends AppCompatActivity {

    private EditText titleEd,descriptionEd;
    private Button saveBtn;

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        dbManager = new DBManager(this);
        dbManager.open();




        init();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(titleEd.getText().toString()) && !TextUtils.isEmpty(descriptionEd.getText().toString()))
                {
                    String title = titleEd.getText().toString();
                    String desc = descriptionEd.getText().toString();

                    long id = dbManager.insert(title,desc);
                    if (id<0){
                        ToastMessage.message(getApplicationContext(),"Insertion Unsuccessful");
                    }else {
                        ToastMessage.message(getApplicationContext(),"Insertion successful");
                        Intent intent = new Intent(AddNotesActivity.this, MainActivity.class);
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
        saveBtn = findViewById(R.id.saveBtn);

    }
}