package studio.laboroflove.tattle;

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
                .append("About Tattle:\n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("Tattle is an app for anonymously crowdsourcing, and annotating messages forwarded on WhatsApp from WhatsApp users.\n\n")
                .append("What Tattle Collects About You?\n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("Besides the content you share with Tattle, the app stores the time at which the post was submitted to the app. While submitting, the location from where the message is being shared is selected by default, but a user can opt out of this prior to submission of a post. This location is that given by your Internet service provider.\n\n")
                .append("Why Tattle Needs this Information?\n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("The three data points- content, time and location enable for deeper understanding of trends on information dissemination on WhatsApp such as:\n")
                .append("1. How quickly and how far does information travel on WhatsApp?\n")
                .append("2. Where do posts originate from?\n")
                .append("3. Do WhatsApp videos have a shelf life or are they recycled and forwarded in newer forms?\n")
                .append("4. What kind of content is circulated on WhatsApp and how is it received?\n")
                .append("The crowdsourced data will be open-access and can be used by researchers studying spread of information and misinformation on encrypted platforms such as WhatsApp. The data will also be valuable for online information verification groups. \n\n")
                .append("Control over Information:\n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("Tattle does not collect any personal information on the user. Any user can request for access to the data being collected by the app through the contact email id.\n\n")
                .append("Reach us:\n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("You can reach out with further questions at feedback@gettattle.app\n")
                ;

        privacyPolicy.setText(text);
    }
}
