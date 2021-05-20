package com.example.noteappbysqldatabase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.noteappbysqldatabase.Adapter.NoteAdapter;
import com.example.noteappbysqldatabase.SqlDatabase.DBManager;
import com.example.noteappbysqldatabase.Model.NotesModel;
import com.example.noteappbysqldatabase.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   private EditText searchNote;
   private TextView firstShow;
   private RecyclerView recyclerView;
   private FloatingActionButton fab;
   private NoteAdapter adapter;
   private List<NotesModel> notesList;

   private CoordinatorLayout coordinatorLayout;

   private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SqlDatabase initialization
        dbManager = new DBManager(this);
        dbManager.open();


        init();

        notesList = new ArrayList<>();
        fetchAllData();

        //set adapter in RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new NoteAdapter(this,MainActivity.this,notesList);
        recyclerView.setAdapter(adapter);


        //add itemTouchHelper on recyclerView
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);


        //add listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNotesActivity.class);
                startActivity(intent);
            }
        });
        searchNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapter.getFilter().filter(s);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    private void init() {
        recyclerView = findViewById(R.id.noteRecyclerView);
        fab = findViewById(R.id.fabButton);
        firstShow = findViewById(R.id.emptyDesign);
        searchNote = findViewById(R.id.searchNote);
        coordinatorLayout = findViewById(R.id.layout_main);
    }

    //read data from sqlDatabase
    private void fetchAllData() {
        Cursor cursor = dbManager.readAllData();
        if (cursor.getCount() == 0)
        {
            firstShow.setVisibility(View.VISIBLE);

        }else {
            firstShow.setVisibility(View.GONE);
            while (cursor.moveToNext()){
                notesList.add(new NotesModel(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }
        }
    }


    //optionMenu Create
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }


    //optionMenu item listener
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAll){
            //delete all note by one click
            dbManager.deleteAllNote();
            recreate();

        }
        return super.onOptionsItemSelected(item);
    }




    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            NotesModel item = adapter.getList().get(position);

            adapter.removeItem(viewHolder.getAdapterPosition());
            Snackbar snackbar = Snackbar.make(coordinatorLayout,"Item Deleted",Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.restoreItem(item,position);
                            recyclerView.scrollToPosition(position);
                        }
                    }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                            if (!(event == DISMISS_EVENT_ACTION))
                            {
                                DBManager dbManager = new DBManager(MainActivity.this);
                                dbManager.open();
                                dbManager.delete(item.getId());
                            }
                        }
                    });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    };
}