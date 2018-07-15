package studio.laboroflove.naarad.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import studio.laboroflove.naarad.R;

public class TagAdder extends LinearLayout {
    private final String TAG = TagAdder.class.getSimpleName();
    private List<String> tags = new ArrayList<>();

    @BindView(R.id.rv_tags) RecyclerView rvTags;
    @BindView(R.id.edittext_tag) EditText editTextTag;

    @OnClick(R.id.btn_add)
    public void addClicked(View v){
        tags.add(editTextTag.getText().toString());
        editTextTag.setText("");
        Log.d(TAG, "tags : ");
        for(String tag:tags){
            Log.d(TAG, tag);
        }
    }

    public TagAdder(Context context) {
        super(context);
        init(context);
    }

    public TagAdder(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TagAdder(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        ButterKnife.bind(inflate(context, R.layout.tag_adder, this), this);

        rvTags.setHasFixedSize(true);
        //LessonsLayoutManager layoutManager = new LessonsLayoutManager();
        tags.add("one"); tags.add("two"); tags.add("three"); tags.add("four");
        TagAdapter adapter = new TagAdapter(tags);
        rvTags.setAdapter(adapter);
        rvTags.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
