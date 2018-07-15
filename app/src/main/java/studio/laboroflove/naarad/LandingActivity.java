package studio.laboroflove.naarad;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import studio.laboroflove.naarad.widgets.LoaderButton;

import static android.view.View.GONE;

public class LandingActivity extends AppCompatActivity {
    private final String TAG = LandingActivity.class.getSimpleName();

    @BindView(R.id.clipboard_preview) TextView clipboardPreview;
    @BindView(R.id.compound_upload_widget) LinearLayout compoundWidget;

    @BindView(R.id.form_title)
    TextInputEditText formTitle;
    @BindView(R.id.form_description)
    TextInputEditText formDescription;
    @BindView(R.id.form_tags)
    TextInputEditText formTags;
    @BindView(R.id.form_location)
    CheckBox formLocation;
    @BindView(R.id.image_preview)
    ImageView imagePreview;
    @BindView(R.id.compound_submit_button)
    LoaderButton compoundSubmitButton;

    private enum PostState{
        text,
        video,
        image
    }

    private PostState postState;
    private Uri currentFileUri;

    @OnClick(R.id.upload_media)
    public void onClickUploadMedia(View v){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/* video/*");
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }
    @OnClick(R.id.paste_clipboard)
    public void onPasteClipboard(View v){
        compoundWidget.setVisibility(GONE);
        clipboardPreview.setVisibility(View.VISIBLE);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        String clipboardText = clipboard.getPrimaryClip().getItemAt(0).coerceToText(getBaseContext()).toString();
        clipboardPreview.setText(clipboardText);

        postState = PostState.text;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storeRef = storage.getReference();
    StorageReference textFolderRef = storeRef.child("text-posts");
    StorageReference imageFolderRef = storeRef.child("image-posts");
    StorageReference videoFolderRef = storeRef.child("video-posts");

    private final int PICK_IMAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type!=null){
            if(type.startsWith("image/")){
                postState = PostState.image;
                currentFileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                showOnlyImagePreview();
            }else if(type.startsWith("video/")){
                postState = PostState.video;
                currentFileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            }
        }
        compoundSubmitButton.setInteractionListener(new LoaderButton.InteractionListener() {
            @Override
            public void onClicked() {
                Log.d(TAG, formDescription.getText().toString());
                switch (postState){
                    case text:
                        uploadTextFile();
                        break;
                    case image:
                        uploadImageFile(currentFileUri);
                        break;
                    case video:
                        uploadVideoFile(currentFileUri);
                        break;
                }
            }
        });
    }

    private void showOnlyImagePreview() {
        imagePreview.setImageURI(currentFileUri);
        imagePreview.setVisibility(View.VISIBLE);
        clipboardPreview.setVisibility(GONE);
        compoundWidget.setVisibility(GONE);
    }

    public void uploadTextFile(){
        try {
            final String fileName = UUID.randomUUID().toString();
            byte[] textPost = clipboardPreview.getText().toString().getBytes("UTF-8");
            StorageReference newTextFile = textFolderRef.child(fileName);
            UploadTask uploadTask = newTextFile.putBytes(textPost);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "text uploaded");
                    storePost(fileName);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "text upload failed");
                            Toast.makeText(getBaseContext(), "Error Uploading File. Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void uploadImageFile(Uri uri){
        final String fileName = UUID.randomUUID().toString();
        StorageReference newTextFile = imageFolderRef.child(fileName);
        UploadTask uploadTask = newTextFile.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "image uploaded");
                storePost(fileName);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "image upload failed");
                        Toast.makeText(getBaseContext(), "Error Uploading File. Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void uploadVideoFile(Uri uri){
        final String fileName = UUID.randomUUID().toString();
        StorageReference newTextFile = videoFolderRef.child(fileName);
        UploadTask uploadTask = newTextFile.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "video uploaded");
                storePost(fileName);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "video upload failed");
                        Toast.makeText(getBaseContext(), "Error Uploading File. Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void storePost(String filename){
        Map<String, Object> post = new HashMap<>();
        post.put("title", formTitle.getText().toString());
        post.put("description", formDescription.getText().toString());
        List<String> tags = new ArrayList<>();
        for(String tag : formTags.getText().toString().split(",")){
            tags.add(tag);
        }
        post.put("tags", tags);
        post.put("type", "text");
        post.put("filename", filename);

        db.collection("post")
            .add(post)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "success uploading post");
                    Toast.makeText(getBaseContext(), "Post Saved. Thank you!", Toast.LENGTH_SHORT).show();
                    compoundSubmitButton.onComplete();
                    clearSlate();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "failure uploading post");
                    Toast.makeText(getBaseContext(), "Error Stroing Post in database. Try Again", Toast.LENGTH_SHORT).show();
                }
            });
    };

    private void clearSlate(){
        formTitle.setText("");
        formDescription.setText("");
        formTags.setText("");
        formLocation.setChecked(false);
        clipboardPreview.setText("");
        clipboardPreview.setVisibility(GONE);
        compoundWidget.setVisibility(View.VISIBLE);
        imagePreview.setVisibility(GONE);
        imagePreview.setImageURI(null);
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
                    currentFileUri = data.getData();
                    if(data.getData().toString().contains("image")){
                        postState = PostState.image;
                    }else if(data.getData().toString().contains("video")){
                        postState = PostState.video;
                    }
                }
                break;
        }
    }
}
