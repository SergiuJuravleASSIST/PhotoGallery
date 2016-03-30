package ro.sergiu.photogallery;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Context mContext = this;
    private String userName;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUserName = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        TextView tvRegister = (TextView) findViewById(R.id.tvRegister);
        Button loginButton = (Button) findViewById(R.id.loginButton);


        if (loginButton != null) {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isOnline(mContext)) {
                        //userName = etUserName.getText().toString();
                        //password = etPassword.getText().toString();

                        Intent intent = new Intent(LoginActivity.this, PhotoGalleryActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(mContext, R.string.no_internet,Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        if (tvRegister != null) {
            tvRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
