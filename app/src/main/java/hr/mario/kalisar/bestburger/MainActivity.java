package hr.mario.kalisar.bestburger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    int RC_SIGN_IN = 0;
    SignInButton signInButton;
    GoogleSignInClient googleSignInClient;
    private static final String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInButton = findViewById(R.id.sign_in_button);

        //Request-a korisnikov ID, email i basic profil
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        //Kreira GoogleSignInClient sa svim svim opcijama navedenim u googleSignInOptions objektu
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        //OnClickListener za signInButton koji poziva signIn metodu
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }

    // Eksplicitni intent kojim se pokreće activity koji omogućuje korisniku da se prijavi
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(GoogleSignInAccount account) {
        //odvedi korisnika ako je vec napravio sign in
        openBUrgerActivity();
    }

    public void openBUrgerActivity() {
        Intent intent = new Intent(this, BurgerActivity.class);
        startActivity(intent);
    }

    //Eksplicitni intent SignInIntent pokreće ovaj activity koji pak vraća rezultat
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Ukoliko je request code jednak konstanti, tada kreiraj task koji prikuplja
        // sve detalje o korisniku i prosljeđuje ih.
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            // task malo prije spomenut prosljđuje se metodi handleSignInResult koja provjerava
            // da li je korisnik logiran
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }


    @Override
    protected void onStart() {
        // Provjeri da li je korisnik već logiran ako je tada GoogleSignInAccount neće biti non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            startActivity(new Intent(MainActivity.this, BurgerActivity.class));
        }
        super.onStart();
    }
}
