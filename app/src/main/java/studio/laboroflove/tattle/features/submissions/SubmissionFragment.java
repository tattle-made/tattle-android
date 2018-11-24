package studio.laboroflove.tattle.features.submissions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import studio.laboroflove.tattle.R;
import studio.laboroflove.tattle.fragments.BaseFragment;

public class SubmissionFragment extends BaseFragment {

    @Override
    public void SetupView() {
        super.SetupView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_submission_layout;
    }

    @Override
    public String getTitle() {
        return "Submission";
    }
}
