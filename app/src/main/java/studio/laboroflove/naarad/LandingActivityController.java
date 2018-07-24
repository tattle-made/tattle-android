package studio.laboroflove.naarad;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

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
import java.util.List;
import java.util.Map;
import java.util.UUID;

import studio.laboroflove.naarad.utils.SimpleLocationUtil;

public class LandingActivityController {
    private final String TAG = LandingActivityController.class.getSimpleName();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storeRef = storage.getReference();
    StorageReference textFolderRef = storeRef.child("text-posts");
    StorageReference imageFolderRef = storeRef.child("image-posts");
    StorageReference videoFolderRef = storeRef.child("video-posts");

    private SimpleLocationUtil simpleLocationUtil;
    private Location lastKnownLocation;

    public interface ControllerInterface{
        public Context getContext();
        public Map<String, Object> getFormData();
        public void onPostSubmitted();
    }

    private ControllerInterface controllerInterface;

    private boolean isLocationEnabled=false;

    public LandingActivityController(ControllerInterface controllerInterface) {
        this.controllerInterface = controllerInterface;
        simpleLocationUtil = SimpleLocationUtil.getInstance(controllerInterface.getContext());
    }

    public void uploadTextFile(String clipboardText){
        try {
            final String fileName = UUID.randomUUID().toString();
            byte[] textPost = clipboardText.toString().getBytes("UTF-8");
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
                    Toast.makeText(controllerInterface.getContext(), "Error Uploading File. Try Again", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(controllerInterface.getContext(), "Error Uploading File. Try Again", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(controllerInterface.getContext(), "Error Uploading File. Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storePost(String filename){
        Map<String, Object> post = controllerInterface.getFormData();
        post.put("filename", filename);

        if((boolean)post.get("isLocationChecked")==true && simpleLocationUtil.hasLocation()){
            post.put("location", new GeoPoint(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()));
        }


        db.collection("post")
            .add(post)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "success uploading post");
                    Toast.makeText(controllerInterface.getContext(), "Post Saved. Thank you!", Toast.LENGTH_SHORT).show();
                    controllerInterface.onPostSubmitted();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "failure uploading post");
                    Toast.makeText(controllerInterface.getContext(), "Error Stroing Post in database. Try Again", Toast.LENGTH_SHORT).show();
                }
            });
    };

    public void locationEnabled(boolean flag) {
        isLocationEnabled = flag;
        if(flag==true){
            lastKnownLocation = simpleLocationUtil.getLastKnownLocation();
            Log.d(TAG, "location : " + lastKnownLocation.getLatitude() + ", " + lastKnownLocation.getLongitude());
        }
    }

    public boolean isLocationEnabled() {
        return isLocationEnabled;
    }
}
