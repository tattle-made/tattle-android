package studio.laboroflove.tattle.features.settings;

import studio.laboroflove.tattle.R;
import studio.laboroflove.tattle.fragments.BaseFragment;

public class SettingsFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings_layout;
    }

    @Override
    public String getTitle() {
        return "Settings";
    }
}
