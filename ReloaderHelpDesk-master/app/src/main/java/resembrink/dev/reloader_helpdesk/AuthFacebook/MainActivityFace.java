package resembrink.dev.reloader_helpdesk.AuthFacebook;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import resembrink.dev.reloader_helpdesk.AuthGoogle.firebaseAuth;
import resembrink.dev.reloader_helpdesk.MainActivity;
import resembrink.dev.reloader_helpdesk.R;

public class MainActivityFace extends AppCompatActivity {
    Button btlogout;
    Button btinicio;

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView uidTextview;
    private CircleImageView circlephoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueo);

        nameTextView= findViewById(R.id.nameTextViewF);
        emailTextView= findViewById(R.id.emailTextViewF);
        uidTextview= findViewById(R.id.uidTextViewF);
        circlephoto= findViewById(R.id.id_photoResume);
        btinicio= findViewById(R.id.btnIniciar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if( user != null)
        {

            String name= user.getDisplayName();
            String email= user.getEmail();
            Uri photoUrl= user.getPhotoUrl();
            String uid= user.getUid();

            nameTextView.setText(name);
            emailTextView.setText(email);
            uidTextview.setText(uid);
            Glide.with(getApplicationContext())
                    .load(photoUrl)
                    .into(circlephoto);
        }
        else
        {
            goLoginScreen();
        }

        btlogout= findViewById(R.id.btnlogout);

        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                goLoginScreen();
            }
        });

        btinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1= new Intent(MainActivityFace.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();

            }
        });
    }


        private void goLoginScreen() {
            Intent intent= new Intent(MainActivityFace.this, firebaseAuth.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


}
