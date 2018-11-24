package studio.laboroflove;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import studio.laboroflove.tattle.R;

public class BaseActivity extends AppCompatActivity {
    private final String TAG = BaseActivity.class.getSimpleName();

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    Fragment navigationFragment;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        setMenuItemClickHandler(navigationView);
        navigationFragment = getSupportFragmentManager().findFragmentById(R.id.navigation_fragment);
        navController = NavHostFragment.findNavController(navigationFragment);
    }

    private void setMenuItemClickHandler(final NavigationView nView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                if(!navController.getCurrentDestination().getLabel().equals("NewSubmissionFragment")){
                    navController.popBackStack();
                }
                if(navController.getCurrentDestination().getLabel().equals("SubmissionFragment")){
                    navController.popBackStack();
                }

                switch (menuItem.getItemId()){
                    case R.id.submit :
                        //NavHostFragment.findNavController(navigationFragment).navigateUp();
                        break;
                    case R.id.submissions:
                        navController.navigate(R.id.action_newSubmissionFragment_to_submissionsFragment);
                        break;
                    case R.id.settings:
                        navController.navigate(R.id.action_newSubmissionFragment_to_settingsFragment);
                        break;
                    case R.id.announcements:
                        navController.navigate(R.id.action_newSubmissionFragment_to_announcementFragment4);
                        break;
                    case R.id.feedback:
                        navController.navigate(R.id.action_newSubmissionFragment_to_feedbackFragment);
                        break;
                    case R.id.about:
                        navController.navigate(R.id.action_newSubmissionFragment_to_aboutFragment);
                        break;
                    case R.id.privacy_policy:
                        navController.navigate(R.id.action_newSubmissionFragment_to_privacyPolicyFragment);
                        break;
                    case R.id.share:
                        navController.navigate(R.id.action_newSubmissionFragment_to_shareAppFragment);
                    default:
                        break;
                }

                Log.d(TAG, "stop");
                return true;
            }
        });
    }

    public NavController getNavController(){
        return navController;
    }
}
