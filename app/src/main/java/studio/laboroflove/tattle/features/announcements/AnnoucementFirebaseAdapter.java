package studio.laboroflove.tattle.features.announcements;

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

public class AnnoucementFirebaseAdapter extends FirestoreRecyclerAdapter<Annoucement, AnnoucementFirebaseAdapter.AnnouncementViewholder> {

    public AnnoucementFirebaseAdapter(@NonNull FirestoreRecyclerOptions<Annoucement> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AnnouncementViewholder holder, int position, @NonNull Annoucement model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public AnnouncementViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.announcement_item, viewGroup,false);
        AnnouncementViewholder vh = new AnnouncementViewholder(item);
        return vh;
    }

    public class AnnouncementViewholder extends RecyclerView.ViewHolder{
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.timestamp)
        TextView timestamp;
        @BindView(R.id.content)
        TextView content;

        public AnnouncementViewholder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(Annoucement annoucement){
            title.setText(annoucement.getTitle());
            timestamp.setText(annoucement.getTimestamp().toDate().toString());
            content.setText(annoucement.getText());
        }
    }
}
