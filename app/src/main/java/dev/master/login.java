package dev.master;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword, editTextName, editTextSchool;

    SQLiteDatabase db;
    DbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);



        findViewById(R.id.buttonLogin).setOnClickListener(this);


        mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();
    }




    public  void makeNotification(String title,String content) {



        NotificationCompat.Builder builder =
                new NotificationCompat.Builder( getApplicationContext())
                        .setSmallIcon(R.drawable.ic_add_location_black_24dp)
                        .setContentTitle(title)
                        .setContentText(content).setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" +  getApplicationContext().getPackageName() + "/raw/notify"));

        // Add as notification
        NotificationManager manager = (NotificationManager)  getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, locations_list.class);
        startActivity(setIntent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private void signin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }



        if (mDbHelper.Userlogin(editTextEmail.getText().toString().trim()
                , editTextPassword.getText().toString().trim())) {

//
            Intent accountsIntent = new Intent(login.this, HomeActivity.class);
            startActivity(accountsIntent);


            SharedPrefManager.getInstance(login.this)
                    .savelogin(email);


            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();


            Cursor cursor = db.rawQuery("select * from places ", null);



            if (cursor != null) {
                if (cursor.moveToLast()) {





                      makeNotification("Dalelk App  ","You Have Saved  "+cursor.getCount()+" Places");






                }
            }

            cursor.close();
            db.close();


        } else {
            // Snack Bar to show success message that record is wrong

            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();


        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                signin();
                break;


        }
    }

    public void openreg(View view) {


        Intent Intent = new Intent(login.this, register.class);
        startActivity(Intent);
    }
}