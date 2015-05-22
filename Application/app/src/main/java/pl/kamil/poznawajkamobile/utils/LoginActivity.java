package pl.kamil.poznawajkamobile.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;

import pl.kamil.poznawajkamobile.activity.AbstractActivity;
import pl.kamil.poznawajkamobile.activity.MainActivity;
import pl.kamil.poznawajkamobile.activity.RegisterActivity;
import pl.kamil.poznawajkamobile.activity.ResetPasswordActivity;
import pl.kamil.poznawajkamobile.utils.AuthorizeService.AuthorizeBinder;


public class LoginActivity extends AbstractActivity {
    private Button myZalogujButton;
    private Button exitButton;
    private TextView resetPassword;
    private TextView register;
    private EditText email;
    private EditText password;
    private AuthorizeService authorizeServiceE;
    private ProgressBar bar;
    private String mInfo;
    private Preferences mPreferences;
    private Boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            AuthorizeBinder binder = (AuthorizeBinder) service;
            authorizeServiceE = binder.getService();
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
        Intent intent = new Intent(getApplicationContext(), AuthorizeService.class);
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
//        if (authorizeService == null || !authorizeService.isWorking()) {
//            super.onBackPressed();
//        } else {
//            DialogUtil.getTextDialog(
//                    this, "UWAGA", "Anulujesz pobieranie danych",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    });
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mPreferences = new Preferences(this);
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
                showConnectionQuestion();
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


    public void showConnectionQuestion() {
        if (Utils.isConnectionAvailable(this)) {
            if (mBound) { //TODO tutaj wywala nulla. Nie wiem dlaczego.
                //zrobilem identycznie jak tutaj //http://developer.android.com/guide/components/bound-services.html
                //4h na tym siedzialem, moze Ty dokminisz dlaczego tutaj jest null inaczej jestesmy w dupie.
                String loginMail = email.getText().toString();
                String loginPassword = password.getText().toString();
                mPreferences.setString("login", loginMail);
                mPreferences.setString("password", loginPassword);
                setMode(false);
                if (authorizeServiceE != null) {
                    authorizeServiceE.start(loginMail, loginPassword);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    if (authorizeServiceE.isAuthorized())
                        startActivity(intent);
                    else
                        setMode(true);
                } else {
                    setMode(true);
                }
            }
        }
    }

    public void onEventMainThread(CheckFinishEvent event) {
        mInfo = event.getInfo();
        if (mInfo != null) {
        }
    }

    private void setMode(boolean configuration) {
        if (bar != null) {
            bar.setVisibility(configuration ? View.GONE : View.VISIBLE);
        }
    }

}
