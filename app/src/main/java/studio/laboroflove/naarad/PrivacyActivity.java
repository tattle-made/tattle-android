package studio.laboroflove.naarad;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.graphics.Typeface.BOLD;

public class PrivacyActivity extends AppCompatActivity {
    @BindView(R.id.privacy_policy)
    TextView privacyPolicy;

    public static void startMe(Activity hostActivity){
        Intent intent = new Intent(hostActivity, PrivacyActivity.class);
        hostActivity.startActivity(intent);
    }

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Privacy Policy");
        setContentView(R.layout.activity_privacy);

        ButterKnife.bind(this);

        Spannable text = new SpannableStringBuilder()
//                .append("PrivacyActivity Policy for Beta Version\n\n")
                .append("What Information Naarad Collects About You:\n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("When you use Naarad or visit the site, no personally identifiable information about you is collected. While submitting entries, you have the option of providing location data which will be used for research purposes. This location is that provided by your Internet network provider.\n\n")
                .append("How We Use this Information?\n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("The crowdsourced data will be shared with online information verification groups. The dataset will also be shared with researchers interested in studying spread of information and misinformation on encrypted platforms such as WhatsApp.  This location information will be used for research on trends in misinformation and disinformation such as:\n" +
                        "1. Are certain messages specific to a geographical region?\n" +
                        "2. How is misinformation\\disinformation propagated?\n\n")
                .append("Control over Information:\n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("Naarad does not store any personal details of the user and hence any data that a user posts through Naarad is associated with a unique ID that is assigned to them by the app. In case the App is deleted, this history will be lost. \n" +
                        "On your app you can look at the history of messages you have shared in the past. This information is stored locally on your phone.Since Naarad does not store information about a user, Naarad will not be able to provide the posts shared by a specific person once they have uninstalled or reinstalled an app.\n\n")
                .append("Reach us:\n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("You can reach out with further questions at contact@naarad.com\n")
                ;

        privacyPolicy.setText(text);
    }
}
