package studio.laboroflove.naarad.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    private final String TAG = SharedPreferenceUtil.class.getSimpleName();
    private final String PREFERENCE_FILE_NAME = "NaaradPreference";
    private static SharedPreferenceUtil _instance;
    private SharedPreferences sharedPreferences;

    private enum PreferenceKeys{
        onboardingDone
    }

    public static SharedPreferenceUtil getInstance(Context context){
        if(_instance==null){
            _instance = new SharedPreferenceUtil();
            _instance.init(context);
        }
        return _instance;
    }

    private void init(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setOnboardingDoneState(boolean flag){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PreferenceKeys.onboardingDone.name(), flag);
        editor.commit();
    }

    public boolean getOnboardingDoneState(){
        return sharedPreferences.getBoolean(PreferenceKeys.onboardingDone.name(), false);
    }
}
