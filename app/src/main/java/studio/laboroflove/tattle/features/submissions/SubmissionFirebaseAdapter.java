package studio.laboroflove.tattle.features.submissions;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.laboroflove.BaseActivity;
import studio.laboroflove.tattle.R;

public class SubmissionFirebaseAdapter extends FirestoreRecyclerAdapter<Submission, SubmissionFirebaseAdapter.SubmissionViewHolder> {
    private SubmissionsFragment submissionsFragment;

    public SubmissionFirebaseAdapter(@NonNull FirestoreRecyclerOptions<Submission> options) {
        super(options);
    }

    public void setParentFragment(SubmissionsFragment submissionsFragment){
        this.submissionsFragment = submissionsFragment;
    }

    @Override
    protected void onBindViewHolder(@NonNull SubmissionViewHolder holder, int position, @NonNull Submission model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public SubmissionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.submission_item, viewGroup, false);
        SubmissionViewHolder vh = new SubmissionViewHolder(item);
        return vh;
    }

    public class SubmissionViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.submission_title) TextView submissionTitle;
        @BindView(R.id.submission_description) TextView submissionDescription;
        @BindView(R.id.submission_type) TextView submissionType;
        @BindView(R.id.root_view) ConstraintLayout rootView;

        public SubmissionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Submission submissionData){
            submissionTitle.setText(submissionData.getTitle());
            submissionDescription.setText(submissionData.getDescription());
            String type = submissionData.getType();
            switch (type){
                case "text":
                    submissionType.setText("T");
                    break;
                case "video":
                    submissionType.setText("V");
                    break;
                case "image":
                    submissionType.setText("I");
                    break;
            }

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((BaseActivity)(submissionsFragment.getActivity())).getNavController().navigate(R.id.action_submissionsFragment_to_submissionFragment);
                }
            });
            //action_submissionsFragment_to_submissionFragment
        }
    }
}
