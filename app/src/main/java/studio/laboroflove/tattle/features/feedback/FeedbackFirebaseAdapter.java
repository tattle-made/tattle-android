package studio.laboroflove.tattle.features.feedback;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.laboroflove.tattle.R;
import studio.laboroflove.tattle.features.announcements.AnnoucementFirebaseAdapter;

public class FeedbackFirebaseAdapter extends FirestoreRecyclerAdapter<Feedback, FeedbackFirebaseAdapter.FeedbackViewHolder> {

    public FeedbackFirebaseAdapter(@NonNull FirestoreRecyclerOptions<Feedback> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position, @NonNull Feedback model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedback_item, viewGroup,false);
        FeedbackViewHolder vh = new FeedbackViewHolder(item);
        return vh;
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.feedback)
        TextView feedback;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Feedback feedbackData){
            feedback.setText(feedbackData.getFeedback());
        }
    }
}
