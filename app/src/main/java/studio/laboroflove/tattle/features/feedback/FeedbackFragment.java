package studio.laboroflove.tattle.features.feedback;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import studio.laboroflove.tattle.R;
import studio.laboroflove.tattle.fragments.BaseFragment;

public class FeedbackFragment extends BaseFragment {
    @BindView(R.id.feedback_rv)
    RecyclerView feedbackRV;
    @BindView(R.id.feedback_text)
    EditText feedbackText;
    @OnClick(R.id.send_feedback)
    public void sendClicked(View v){
        if(feedbackText.getText().toString().isEmpty()){

        }else{
            Map<String, Object> feedback = new HashMap<>();
            feedback.put("timestamp", new Timestamp(new Date()));
            feedback.put("feedback", feedbackText.getText().toString());
            feedback.put("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
            FirebaseFirestore.getInstance().collection("feedback")
                .add(feedback)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Toast.makeText(getContext(), "Feedback Saved. Thank you!", Toast.LENGTH_SHORT).show();
                        feedbackText.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error Saving Feedback. Try Again!", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
    private FeedbackFirebaseAdapter feedbackAdapter;

    @Override
    public void SetupView() {
        Query query = FirebaseFirestore.getInstance()
            .collection("feedback")
            .whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
            .orderBy("timestamp")
            .limit(10);
        FirestoreRecyclerOptions<Feedback> options = new FirestoreRecyclerOptions.Builder<Feedback>()
            .setQuery(query, Feedback.class)
            .build();

        feedbackAdapter = new FeedbackFirebaseAdapter(options);
        feedbackRV.setLayoutManager(new LinearLayoutManager(getContext()));
        feedbackRV.setAdapter(feedbackAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        feedbackAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        feedbackAdapter.stopListening();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_feedback_layout;
    }

    @Override
    public String getTitle() {
        return "Feedback";
    }
}
