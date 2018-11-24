package studio.laboroflove.tattle.features.new_submission;

import studio.laboroflove.tattle.R;
import studio.laboroflove.tattle.fragments.BaseFragment;

public class NewSubmissionFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_submit_layout;
    }

    @Override
    public String getTitle() {
        return "Submit Post";
    }
}
