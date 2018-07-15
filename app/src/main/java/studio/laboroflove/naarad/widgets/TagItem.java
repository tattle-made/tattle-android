package studio.laboroflove.naarad.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.laboroflove.naarad.R;

public class TagItem extends LinearLayout {
    @BindView(R.id.tag_text)
    TextView tagName;
    @BindView(R.id.tag_remove)
    ImageButton tagRemoveButton;

    public TagItem(Context context) {
        super(context);
    }

    public TagItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TagItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
        ButterKnife.bind(inflate(context, R.layout.tag_item, this), this);
    }

    public String getTagName() {
        return tagName.getText().toString();
    }

    public ImageButton getTagRemoveButton() {
        return tagRemoveButton;
    }

    public TagItem setTagName(String tagName) {
        this.tagName.setText(tagName);
        return this;
    }
}
