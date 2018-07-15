package studio.laboroflove.naarad.widgets;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.laboroflove.naarad.R;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private List<String> tags = new ArrayList<>();

    public TagAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tag_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if(viewHolder.getTagText()!=null){
            viewHolder.getTagText().setText(tags.get(i));
        }
        viewHolder.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tags.remove(tags.get(i));
                notifyItemChanged(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tag_text) TextView tagText;
        @BindView(R.id.tag_root) LinearLayout root;

        public ViewHolder(@NonNull LinearLayout itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTagText() {
            return tagText;
        }

        public LinearLayout getRoot() {
            return root;
        }
    }

    public List<String> getTags() {
        return tags;
    }
}