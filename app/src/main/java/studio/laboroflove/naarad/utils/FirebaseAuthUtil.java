package studio.laboroflove.naarad.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.signin.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthUtil {
    private final String TAG = FirebaseAuthUtil.class.getSimpleName();
    private static FirebaseAuthUtil _instance;

    FirebaseAuth firebaseAuth;

    public interface SignInCompletionHandler{
        public void onSuccess(String uuid);
        public void onFailure();
    }

    public static FirebaseAuthUtil getInstance(){
        if(_instance==null){
            _instance = new FirebaseAuthUtil();
            _instance.init();
        }
        return _instance;
    }

    private void init(){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signInAnonymously(final SignInCompletionHandler completionHandler){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user==null){
            firebaseAuth.signInAnonymously()
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                completionHandler.onSuccess(user.getUid());
                            }else{
                                completionHandler.onFailure();
                            }
                        }
                    });
        }else{
            completionHandler.onSuccess(user.getUid());
        }
    }

    public void signOut(){
        firebaseAuth.signOut();
    }
}
