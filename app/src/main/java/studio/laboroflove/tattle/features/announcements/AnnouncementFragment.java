package studio.laboroflove.tattle.features.announcements;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import studio.laboroflove.tattle.R;

import studio.laboroflove.tattle.fragments.BaseFragment;

public class AnnouncementFragment extends BaseFragment {
    @BindView(R.id.announcement_rv)
    RecyclerView announcements;
    private AnnoucementFirebaseAdapter announcementFirebaseAdapter;

    @Override
    public void SetupView() {
        Query query = FirebaseFirestore.getInstance()
            .collection("announcements")
            .orderBy("timestamp")
            .limit(10);
        FirestoreRecyclerOptions<Annoucement> options = new FirestoreRecyclerOptions.Builder<Annoucement>()
            .setQuery(query, Annoucement.class)
            .build();

        announcementFirebaseAdapter = new AnnoucementFirebaseAdapter(options);
        announcements.setLayoutManager(new LinearLayoutManager(getContext()));
        announcements.setAdapter(announcementFirebaseAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        announcementFirebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        announcementFirebaseAdapter.stopListening();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_announcement_layout;
    }

    @Override
    public String getTitle() {
        return "Announcements";
    }
}
