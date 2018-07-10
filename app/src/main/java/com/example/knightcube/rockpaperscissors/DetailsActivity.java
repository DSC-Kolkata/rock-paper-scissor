package com.example.knightcube.rockpaperscissors;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    private EditText userNameEdtTxt;
    private TextView userScoreTxt;
    private Button saveBtn;
    private UserModel userModel;
    private String TAG = "DetailsActivity";
    private TextView leaderBoardTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userNameEdtTxt = findViewById(R.id.user_name_edt_txt);
        userScoreTxt = findViewById(R.id.user_score_txt);
        saveBtn = findViewById(R.id.save_btn);
        leaderBoardTxt = findViewById(R.id.leaderboard_txt);
        Bundle bundle = getIntent().getExtras();
        final int score = bundle.getInt("user_score", -1);
        Log.i(TAG, "onCreate: " + score);
        if (score == -1) {
            Toast.makeText(this, "Score is -1. Error", Toast.LENGTH_SHORT).show();
            userScoreTxt.setText("Could not calculate your score");
        } else {
            userScoreTxt.setText("Your Score:" + score);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(userNameEdtTxt.getText().toString())) {
                    userModel = new UserModel(userNameEdtTxt.getText().toString(), score);
                    pushUserModelToFirebase();
                } else {
                    Toast.makeText(DetailsActivity.this, "Please fill your name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void readFromFirebase() {

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("user_details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                leaderBoardTxt.setText("");
                for(DataSnapshot userDetailsDataSnapshot:dataSnapshot.getChildren()){
                    UserModel userFirebaseModel = userDetailsDataSnapshot.getValue(UserModel.class);
                    if(userFirebaseModel!=null)
                        leaderBoardTxt.append(userFirebaseModel.getName()+"-->"+userFirebaseModel.getScore()+"\n");
                    Log.i(TAG, "onDataChange: "+userFirebaseModel.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i(TAG, "Failed to read value."+ error.toException());
            }
        });

    }

    private void pushUserModelToFirebase() {
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("user_details").push().setValue(userModel);
        readFromFirebase();
    }

}
