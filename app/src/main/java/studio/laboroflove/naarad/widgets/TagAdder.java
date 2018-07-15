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

    @BindView(R.id.rv_tags) RecyclerView rvTags;
    @BindView(R.id.edittext_tag) EditText editTextTag;
    private TagAdapter tagAdapter;

    @OnClick(R.id.btn_add)
    public void addClicked(View v){
        tagAdapter.getTags().add(0, editTextTag.getText().toString());
        tagAdapter.notifyDataSetChanged();
        editTextTag.setText("");
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
//        tags.add("one"); tags.add("two"); tags.add("three"); tags.add("four");
//        tags.add("twenty one"); tags.add("nine"); tags.add("who"); tags.add("93781");
        tagAdapter = new TagAdapter();
        rvTags.setAdapter(tagAdapter);
        rvTags.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //rvTags.setLayoutManager(layoutManager);
    }

    public List<String> getTags(){
        return tagAdapter.getTags();
    }

    public void clearTags(){
        tagAdapter.getTags().clear();
        tagAdapter.notifyDataSetChanged();
    }

}
