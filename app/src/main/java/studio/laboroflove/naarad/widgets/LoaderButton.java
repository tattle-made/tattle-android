package studio.laboroflove.naarad.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import studio.laboroflove.naarad.R;

public class LoaderButton extends LinearLayout {
    private final String TAG = LoaderButton.class.getSimpleName();
    @BindView(R.id.button) LinearLayout button;
    @BindView(R.id.button_label) TextView buttonLabel;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    public void onComplete() {
        progressBar.setVisibility(GONE);
        buttonLabel.setVisibility(VISIBLE);
        button.setClickable(true);
    }

    public interface InteractionListener{
        public void onClicked(InstructionListener instructionListener);
    }

    public interface InstructionListener{
        public void shouldProceed(boolean flag);
    }

    private InstructionListener instructionListener = new InstructionListener() {
        @Override
        public void shouldProceed(boolean flag) {
            if(flag){
                progressBar.setVisibility(VISIBLE);
                buttonLabel.setVisibility(GONE);
                button.setClickable(false);
            }
        }
    };

    private InteractionListener interactionListener;

    @OnClick(R.id.button)
    public void buttonClicked(View v){
        Log.d(TAG, "clicked");
        interactionListener.onClicked(instructionListener);
    }

    public LoaderButton(Context context) {
        super(context);
        init(context);
    }

    public LoaderButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoaderButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        ButterKnife.bind(inflate(context, R.layout.loader_button, this), this);
    }

    public LoaderButton setInteractionListener(InteractionListener interactionListener) {
        this.interactionListener = interactionListener;
        return this;
    }
}
