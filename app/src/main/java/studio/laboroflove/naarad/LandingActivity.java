package studio.laboroflove.naarad;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import studio.laboroflove.naarad.utils.SimpleLocationUtil;
import studio.laboroflove.naarad.widgets.CompoundUploadWidget;
import studio.laboroflove.naarad.widgets.LoaderButton;
import studio.laboroflove.naarad.widgets.TagAdder;

import static android.view.View.GONE;

public class LandingActivity extends AppCompatActivity implements  LandingActivityController.ControllerInterface{
    private final String TAG = LandingActivity.class.getSimpleName();

    @BindView(R.id.form_title)
    TextInputEditText formTitle;
    @BindView(R.id.form_description)
    TextInputEditText formDescription;
    @BindView(R.id.tagadder_tags)
    TagAdder tagAdder;
    @BindView(R.id.form_location)
    CheckBox formLocation;
    @BindView(R.id.compound_submit_button)
    LoaderButton compoundSubmitButton;

    @BindView(R.id.compound_upload_component) CompoundUploadWidget compoundUploadComponent;

    LandingActivityController landingActivityController;

    @OnCheckedChanged(R.id.form_location)
    public void onSendLocationSelected(CompoundButton button, boolean checked){
        landingActivityController.locationEnabled(checked);
    }

    private final int PICK_IMAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                compoundUploadComponent.imageSelected((Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM));
            } else if (type.startsWith("video/")) {
                compoundUploadComponent.videoSelected((Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM));
            }
        }

        compoundSubmitButton.setInteractionListener(new LoaderButton.InteractionListener() {
            @Override
            public void onClicked(LoaderButton.InstructionListener instructionListener) {
            if(compoundUploadComponent.getPostState() == CompoundUploadWidget.PostState.none){
                instructionListener.shouldProceed(false);
                Toast.makeText(getBaseContext(), "You have not selected any post", Toast.LENGTH_SHORT).show();
            }else{
                instructionListener.shouldProceed(true);
                switch (compoundUploadComponent.getPostState()){
                    case text:
                        landingActivityController.uploadTextFile(compoundUploadComponent.getCurrentClipboardText());
                        break;
                    case image:
                        landingActivityController.uploadImageFile(compoundUploadComponent.getCurrentFileUri());
                        break;
                    case video:
                        landingActivityController.uploadVideoFile(compoundUploadComponent.getCurrentFileUri());
                        break;
                }
            }
            }
        });

        landingActivityController = new LandingActivityController(this);

        compoundUploadComponent.setInteractionListener(new CompoundUploadWidget.CompoundUploadWidgetInteraction() {
            @Override
            public void onPasteFromClipboardClicked() {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String clipboardText = clipboard.getPrimaryClip().getItemAt(0).coerceToText(getBaseContext()).toString();
                compoundUploadComponent.textSelected(clipboardText);
            }

            @Override
            public void onUploadClicked() {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/* video/*");
                startActivityForResult(galleryIntent, PICK_IMAGE);
            }

            @Override
            public void onClearSlate() {
                clearForm();
            }
        });
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static void startMe(OnboardingActivity onboardingActivity){
        Intent intent = new Intent(onboardingActivity, LandingActivity.class);
        onboardingActivity.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_IMAGE :
                if(resultCode==RESULT_OK){
                    if(data.getData().toString().contains("image")){
                        compoundUploadComponent.imageSelected(data.getData());
                    }else if(data.getData().toString().contains("video")){
                        compoundUploadComponent.videoSelected(data.getData());
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_about:
                AboutActivity.startMe(LandingActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    public Map<String, Object> getFormData(){
        Map<String, Object> formData = new HashMap<>();

        formData.put("title", formTitle.getText().toString());
        formData.put("description", formDescription.getText().toString());
        formData.put("tags", tagAdder.getTags());
        formData.put("type", compoundUploadComponent.getPostState().name());
        formData.put("isLocationChecked", landingActivityController.isLocationEnabled());

        return formData;
    }

    @Override
    public void onPostSubmitted() {
        clearForm();
        compoundSubmitButton.onComplete();
        compoundUploadComponent.clearSlate();
    }

    private void clearForm(){
        formTitle.setText("");
        formDescription.setText("");
        tagAdder.clearTags();
        formLocation.setChecked(false);
    }
}
