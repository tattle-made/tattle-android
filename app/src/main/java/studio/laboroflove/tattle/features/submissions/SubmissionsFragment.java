package studio.laboroflove.tattle.features.submissions;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import studio.laboroflove.tattle.R;
import studio.laboroflove.tattle.features.feedback.Feedback;
import studio.laboroflove.tattle.fragments.BaseFragment;

public class SubmissionsFragment extends BaseFragment {
    @BindView(R.id.submission_rv)
    RecyclerView submissionRV;
    private SubmissionFirebaseAdapter submissionAdapter;

    @Override
    public void SetupView() {
        Query query = FirebaseFirestore.getInstance()
                .collection("post")
                .whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderBy("timestamp");
        FirestoreRecyclerOptions<Submission> options = new FirestoreRecyclerOptions.Builder<Submission>()
                .setQuery(query, Submission.class)
                .build();
        submissionAdapter = new SubmissionFirebaseAdapter(options);
        submissionAdapter.setParentFragment(this);
        submissionRV.setLayoutManager(new LinearLayoutManager(getContext()));
        submissionRV.setAdapter(submissionAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        submissionAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        submissionAdapter.startListening();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_submissions_layout;
    }

    @Override
    public String getTitle() {
        return "Submissions";
    }
}
