package studio.laboroflove.naarad;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.graphics.Typeface.BOLD;

public class AboutActivity extends AppCompatActivity {
    @BindView(R.id.about_content)
    TextView aboutContent;

    public static void startMe(Activity hostActivity){
        Intent intent = new Intent(hostActivity, AboutActivity.class);
        hostActivity.startActivity(intent);
    }

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("About Naarad");
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);

        Spannable text = new SpannableStringBuilder()
//                .append("PrivacyActivity Policy for Beta Version\n\n")
                .append("Thank you for Using Naarad? \n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("A breif paragraph explaining what the intention behind the project is and how they are helping in that. A breif reminder of our grand vision and how their involvement will be rewarded in the future. \n\n")
                .append("Get Involved \n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("We are looking for all kind of support. Help in translating the app to various indian languages. People who would be interested in promoting our work. People who will like to use this data for research and public outreach.\n")
                .append("Control over Information:\n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
                .append("You can reach out with further questions at contact@naarad.com\n")
                ;

        aboutContent.setText(text);
    }
}
