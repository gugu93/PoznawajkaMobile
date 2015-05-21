package pl.kamil.poznawajkamobile.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;


public class RegisterActivity extends ActionBarActivity {
    private TextView imie;
    private TextView nazwisko;
    private TextView plec;
    private TextView wiek;
    private TextView stanCywilny;
    private TextView preferencje;
    private TextView osobowosc;
    private TextView email;
    private Button exitButton;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        imie = (TextView) findViewById(R.id.timie);
        nazwisko = (TextView) findViewById(R.id.tnazwisko);
        plec = (TextView) findViewById(R.id.tplec);
        wiek = (TextView) findViewById(R.id.twiek);
        stanCywilny = (TextView) findViewById(R.id.tstan);
        preferencje = (TextView) findViewById(R.id.tpreferencje);
        osobowosc = (TextView) findViewById(R.id.tcechy);
        email = (TextView) findViewById(R.id.tmail);
        exitButton = (Button) findViewById(R.id.button_powrot);
        registerButton = (Button) findViewById(R.id.button_register);
    }

}
