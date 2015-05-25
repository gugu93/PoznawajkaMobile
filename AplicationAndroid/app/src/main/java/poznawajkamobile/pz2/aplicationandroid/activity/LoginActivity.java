package poznawajkamobile.pz2.aplicationandroid.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kml.poznawajkamobile.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import poznawajkamobile.pz2.aplicationandroid.utils.Preferences;
import poznawajkamobile.pz2.aplicationandroid.utils.Utils;
import poznawajkamobile.pz2.aplicationandroid.utils.services.AuthorizeService;
import poznawajkamobile.pz2.aplicationandroid.utils.services.AuthorizeService.AuthorizeBinder;


public class LoginActivity extends AbstractActivity {
    private Button myZalogujButton;
    private Button exitButton;
    private TextView resetPassword;
    private TextView register;
    private EditText email;
    private EditText password;
    private ProgressBar bar;
    private Preferences mPreferences;
    private AuthorizeService authorizeService;
    private Boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            AuthorizeBinder binder = (AuthorizeBinder) service;
            authorizeService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, AuthorizeService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mPreferences = getPreferences();
        exitButton = (Button) findViewById(R.id.buttonEXIT);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        bar = (ProgressBar) findViewById(R.id.progress_bar);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        myZalogujButton = (Button) findViewById(R.id.buttonLOGIN);
        myZalogujButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeConnection();
            }
        });
        resetPassword = (TextView) findViewById(R.id.passwordReset);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
        register = (TextView) findViewById(R.id.registerText);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    public void makeConnection() {
        if (Utils.isConnectionAvailable(this)) {
            if (mBound) {
                String loginMail = email.getText().toString();
                MessageDigest mda = null;
                try {
                    mda = MessageDigest.getInstance("SHA-512", "BC");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                }
                assert mda != null;
                byte[] digestPassword = mda.digest(password.getText().toString().getBytes());
                String hash = Base64.encodeToString(digestPassword, Base64.DEFAULT);
                mPreferences.setString("login", loginMail);
                mPreferences.setString("password", hash);
                setMode(false);
                authorizeService.start(loginMail, mPreferences.getString("password"));
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                if (authorizeService.isAuthorized())
                    startActivity(intent);
                else
                    setMode(true);
                } else {
                    setMode(true);
                }
        } else {
            Toast.makeText(this, "BRAK POLACZENIA Z INTERNETEM", Toast.LENGTH_LONG).show();
        }
    }

    private void setMode(boolean configuration) {
        if (bar != null) {
            bar.setVisibility(configuration ? View.GONE : View.VISIBLE);
        }
    }

}
