package studio.laboroflove.naarad.widgets;

import android.content.ClipboardManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import studio.laboroflove.naarad.LandingActivity;
import studio.laboroflove.naarad.R;

import static android.content.Context.CLIPBOARD_SERVICE;

public class CompoundUploadWidget extends FrameLayout {
    @BindView(R.id.compound_upload_widget) LinearLayout compoundWidget;
    @BindView(R.id.clipboard_preview) TextView clipboardPreview;
    @BindView(R.id.image_preview) ImageView imagePreview;
    @BindView(R.id.video_preview) VideoView videoPreview;
    @BindView(R.id.button_undo) ImageButton undoButton;
    @OnClick(R.id.button_undo)
    public void onUndoClicked(View v){
        clearSlate();
    }
    @OnClick(R.id.paste_clipboard)
    public void onPasteClipboardClicked(View v){
        undoButton.setVisibility(VISIBLE);
        compoundWidget.setVisibility(GONE);
        clipboardPreview.setVisibility(View.VISIBLE);
        interactionListener.onPasteFromClipboardClicked();
    }
    @OnClick(R.id.upload_media)
    public void onUploadClicked(View view){
        interactionListener.onUploadClicked();
    }

    public interface CompoundUploadWidgetInteraction{
        public void onPasteFromClipboardClicked();
        public void onUploadClicked();
        public void onClearSlate();
    }

    private  CompoundUploadWidgetInteraction interactionListener;

    public enum PostState {
        text,
        video,
        image,
        none
    }

    private PostState postState = PostState.none;
    private Uri currentFileUri;
    private String currentClipboardText;

    public CompoundUploadWidget(Context context) {
        super(context);
        init(context);
    }

    public CompoundUploadWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CompoundUploadWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        ButterKnife.bind(inflate(context, R.layout.compound_widget, this), this);

        videoPreview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setVolume(0,0);
            }
        });
    }

    public CompoundUploadWidget setInteractionListener(CompoundUploadWidgetInteraction interactionListener) {
        this.interactionListener = interactionListener;
        return this;
    }

    public CompoundUploadWidget setCurrentFileUri(Uri currentFileUri) {
        this.currentFileUri = currentFileUri;
        return this;
    }

    public void videoSelected(Uri uri){
        currentFileUri = uri;
        postState = PostState.video;
        showOnlyVideoPreview();
    }

    public void imageSelected(Uri uri){
        currentFileUri = uri;
        postState = PostState.image;
        showOnlyImagePreview();
    }

    public void textSelected(String clipboardText){
        currentClipboardText = clipboardText;
        clipboardPreview.setText(clipboardText);
        postState = PostState.text;
    }

    private void showOnlyImagePreview() {
        imagePreview.setImageURI(currentFileUri);
        imagePreview.setVisibility(View.VISIBLE);
        clipboardPreview.setVisibility(GONE);
        compoundWidget.setVisibility(GONE);
        videoPreview.setVisibility(GONE);

        undoButton.setVisibility(VISIBLE);
    }

    private void showOnlyVideoPreview() {
        videoPreview.setVideoURI(currentFileUri);
        videoPreview.setVisibility(View.VISIBLE);
        videoPreview.start();

        clipboardPreview.setVisibility(GONE);
        compoundWidget.setVisibility(GONE);
        imagePreview.setVisibility(GONE);

        undoButton.setVisibility(VISIBLE);
    }

    public void clearSlate(){
//        formTitle.setText("");
//        formDescription.setText("");
//        tagAdder.clearTags();
//        formLocation.setChecked(false);
        clipboardPreview.setText("");
        clipboardPreview.setVisibility(GONE);
        compoundWidget.setVisibility(View.VISIBLE);
        imagePreview.setVisibility(GONE);
        imagePreview.setImageURI(null);
        videoPreview.setVideoURI(null);
        videoPreview.setVisibility(GONE);
        undoButton.setVisibility(GONE);

        postState = PostState.none;
    }

    public Uri getCurrentFileUri() {
        return currentFileUri;
    }

    public String getCurrentClipboardText() {
        return currentClipboardText;
    }

    public PostState getPostState() {
        return postState;
    }
}
