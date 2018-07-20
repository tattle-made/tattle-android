package studio.laboroflove.naarad;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import studio.laboroflove.naarad.utils.FirebaseAuthUtil;
import studio.laboroflove.naarad.utils.SharedPreferenceUtil;

public class OnboardingActivity extends AppCompatActivity {

    private final int READ_STORAGE_PERMISSION_CODE = 100;
    private final int READ_LOCATION_PERMISSION_CODE = 101;

    private final int PERMISSION_ALL = 102;
    private String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION};

    @BindView(R.id.privacy_policy)
    TextView privacyPolicy;

    @OnClick(R.id.privacy_policy)
    public void onClickPrivacyPolicy(View v){
        PrivacyActivity.startMe(OnboardingActivity.this);
    }

    @OnClick(R.id.start_using)
    public void onStartUsing(View v){
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }else{
            loginUserAnonymously();
            SharedPreferenceUtil.getInstance(getBaseContext()).setOnboardingDoneState(true);
            LandingActivity.startMe(OnboardingActivity.this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        ButterKnife.bind(this);

        privacyPolicy.setPaintFlags(privacyPolicy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if(SharedPreferenceUtil.getInstance(getBaseContext()).getOnboardingDoneState()){
            LandingActivity.startMe(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_ALL : {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loginUserAnonymously();
                    SharedPreferenceUtil.getInstance(getBaseContext()).setOnboardingDoneState(true);
                    LandingActivity.startMe(OnboardingActivity.this);
                } else {

                }
                return;
            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void loginUserAnonymously(){
        FirebaseAuthUtil.getInstance().signInAnonymously(new FirebaseAuthUtil.SignInCompletionHandler() {
            @Override
            public void onSuccess(String uuid) {
                SharedPreferenceUtil.getInstance(getBaseContext()).setUserUUID(uuid);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getBaseContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
