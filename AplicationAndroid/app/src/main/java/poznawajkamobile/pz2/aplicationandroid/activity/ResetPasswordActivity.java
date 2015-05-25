package poznawajkamobile.pz2.aplicationandroid.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kml.poznawajkamobile.R;


public class ResetPasswordActivity extends ActionBarActivity {
    private Button buttonExitReset;
    private Button buttonReset;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        buttonExitReset = (Button)findViewById(R.id.buttonExitReset);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassword2 = (EditText) findViewById(R.id.editTextPassword2);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        setTitle("Przypomnij hasło");

        buttonReset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(validateData()) {
                    openAlert(v);
                }
                else {

                }
            }
        });

        buttonExitReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void openAlert(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ResetPasswordActivity.this);

        alertDialogBuilder.setTitle("Informacja!");
        alertDialogBuilder.setMessage("Zmiana hasła została zakończona pomyślnie. Potwierdź zmianę hasła klikając w link w treści e-maila.");
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("Powrót do menu logowania", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // go to a new activity of the app
                Intent loginActivity = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(loginActivity);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void openAlert2(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ResetPasswordActivity.this);

        alertDialogBuilder.setTitle("Odmowa!");
        alertDialogBuilder.setMessage("Zmiana hasła zmiana hasła nie powiodła się. Podany e-mail jest nieprawidłowy.");
        // set positive button: Yes message
        alertDialogBuilder.setNegativeButton("Powrót do menu logowania", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // go to a new activity of the app
                Intent loginActivity = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(loginActivity);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean validateData(){
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reset_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
