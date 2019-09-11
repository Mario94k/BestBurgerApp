package hr.mario.kalisar.bestburger;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BurgerActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    public static final String EXTRA_IMAGE_URL = "image";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DESCRIPTION = "description";
    //Pokušao sam povući podatke iz JSON file-a koje sam upload-o na git ali nisam uspio
    //pa sam napravio if petlje kroz koje mi pokazuje za svaki item  u listi drugi popis
    //ti popisi(liste) su u RecipesActivity
    public static final String EXTRA_OPIS = "opis";

    private int CAMERA_PERMISSION_CODE = 1;
    private GoogleSignInClient mGoogleSignInClient;
    private Button sign_out;
    private TextView nameTV;
    private ImageView imageView;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Model> modelArrayList = new ArrayList<>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_out = findViewById(R.id.log_out);
        nameTV = findViewById(R.id.name);
        imageView = findViewById(R.id.camera);

        fetchJSON();

        recyclerView = findViewById(R.id.recycleviewd);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCameraPremission();


            }
        });


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(BurgerActivity.this);
        if (acct != null) {
            String personName = acct.getDisplayName();

            nameTV.setText(personName + "'s Burgers");
        }

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

    }

    private void fetchJSON() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecyclerInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RecyclerInterface api = retrofit.create(RecyclerInterface.class);

        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        writeRecycler(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    private void writeRecycler(String response) {

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if (obj.optString("status").equals("true")) {

                JSONArray dataArray = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    Model model = new Model();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    model.setImg(dataobj.getString("image"));
                    model.setTitle(dataobj.getString("title"));
                    model.setDescription(dataobj.getString("description"));
                    model.setOpis(dataobj.getString("opis"));

                    modelArrayList.add(model);

                }

                myAdapter = new MyAdapter(this, modelArrayList);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

                myAdapter.setOnItemClickListener(BurgerActivity.this);

            } else {
                Toast.makeText(this, obj.optString("message") + "", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.camera) {

            if (ContextCompat.checkSelfPermission(BurgerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(BurgerActivity.this, "You have already granted this permission", Toast.LENGTH_SHORT).show();


            } else {
                requestCameraPremission();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(BurgerActivity.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BurgerActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }


    private void requestCameraPremission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed if you want to use camera.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(BurgerActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();


        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);

            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onItemClick(int position) {
        Intent recipesIntent = new Intent(this, RecipesActivity.class);
        Model modelItem = modelArrayList.get(position);

        recipesIntent.putExtra(EXTRA_IMAGE_URL, modelItem.getImg());
        recipesIntent.putExtra(EXTRA_TITLE, modelItem.getTitle());
        recipesIntent.putExtra(EXTRA_DESCRIPTION, modelItem.getDescription());
        recipesIntent.putExtra(EXTRA_OPIS, modelItem.getOpis());

        startActivity(recipesIntent);
    }
}

