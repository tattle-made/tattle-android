package studio.laboroflove.tattle.features.about;

import android.annotation.TargetApi;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

import butterknife.BindView;
import studio.laboroflove.tattle.R;
import studio.laboroflove.tattle.fragments.BaseFragment;

import static android.graphics.Typeface.BOLD;

public class AboutFragment extends BaseFragment {
    @BindView(R.id.about_us)
    TextView aboutUs;

    @TargetApi(21)
    @Override
    public void SetupView() {
        Spannable text = new SpannableStringBuilder()
            .append("About Us:\n", new StyleSpan(BOLD), Spannable.SPAN_INTERMEDIATE)
            .append("Fact Checked Stories for WhatsApp Users at Scale.\n\n")
            .append("Tattle leverages technology to scale existing fact checking efforts by connecting WhatsApp users to fact checked stories in real time, making users more informed about what they read and share on WhatsApp.\n\n")
            .append("Share messages you are forwarded on WhatsApp with Tattle. Be an informed WhatsApp user. Empower others to be the same.\n")
            ;

            aboutUs.setText(text);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_about_layout;
    }

    @Override
    public String getTitle() {
        return "About";
    }
}
