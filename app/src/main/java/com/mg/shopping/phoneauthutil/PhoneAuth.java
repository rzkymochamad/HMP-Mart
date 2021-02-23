package com.mg.shopping.phoneauthutil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

public class PhoneAuth {
    private String tag = this.getClass().getSimpleName();
    private final FirebaseAuth mAuth;
    private Context context;
    private Activity activity;
    private PhoneAuthCallback callback;


    public PhoneAuth(Context context, Activity activity , PhoneAuthCallback callback) {

        this.context = context;
        this.activity = activity;
        this.callback = callback;
        mAuth = FirebaseAuth.getInstance();

    }


    /**
     * <p>It is used to show Language Selector</p>
     *
     */
    public void showPhoneAuthenticationSheet(String phoneNo) {
        final View view = activity.getLayoutInflater().inflate(R.layout.phone_authencitation_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        final EditText editPhoneNo = view.findViewById(R.id.edit_phone_no);
        LinearLayout layoutDone = view.findViewById(R.id.layout_done);
        final TextView txtDone = view.findViewById(R.id.txt_done);
        final GeometricProgressView progressBar = view.findViewById(R.id.progress_bar);

        editPhoneNo.setText(phoneNo);

        final boolean[] isCodeSent = new boolean[1];

        isCodeSent[0] = false;
        final String[] firebaseVerificationId = {null};

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = null;
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     userObject action.

                Utility.Logger(tag, "onVerificationCompleted:" + credential);

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

                if (callback!=null){
                    callback.onPhoneAuthSuccess();
                }

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Utility.Logger(tag, "onVerificationFailed " + e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Utility.Logger(tag, "Invalid request " + e);
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Utility.Logger(tag, "SMS quota for the project has been exceeded " + e);
                    // ...
                }

                if (callback!=null){
                    callback.onPhoneAuthError();
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the userObject to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Utility.Logger(tag, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later

                firebaseVerificationId[0] = verificationId;

                txtDone.setVisibility(View.VISIBLE);
                txtDone.setText(Utility.getStringFromRes(context, R.string.done));

                editPhoneNo.setText(null);
                editPhoneNo.setHint(Utility.getStringFromRes(context, R.string.verification_code));

                isCodeSent[0] = true;

                progressBar.setVisibility(View.GONE);

                // ...
            }
        };

        final PhoneAuthProvider.OnVerificationStateChangedCallbacks finalMCallbacks = mCallbacks;
        layoutDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtDone.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                if (isCodeSent[0]) {

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(firebaseVerificationId[0], editPhoneNo.getText().toString());
                    signInWithPhoneAuthCredential(credential, bottomSheetDialog);

                    return;

                }


                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        editPhoneNo.getText().toString(),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        activity,               // Activity (for callback binding)
                        finalMCallbacks);        // OnVerificationStateChangedCallbacks


            }
        });


    }


    /**
     * <p>It is used to login with Phone Authentication</p>
     *
     * @param credential
     */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, final BottomSheetDialog bottomSheetDialog) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in userObject's information
                            Utility.Logger(tag, "signInWithCredential:success");

                            if (bottomSheetDialog.isShowing())
                                bottomSheetDialog.dismiss();

                            if (callback!=null){
                                callback.onPhoneAuthSuccess();
                            }


                        } else {
                            // Sign in failed, display a message and update the UI
                            Utility.Logger(tag, "signInWithCredential:failure " + task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Utility.Logger(tag, "FirebaseAuthInvalidCredentialsException:failure " + task.getException());
                            }

                            if (callback!=null){
                                callback.onPhoneAuthError();
                            }
                        }
                    }
                });
    }


    /**
     * Interface for handling the Phone Authentication Success/failure callback
     */
    public interface PhoneAuthCallback {

        void onPhoneAuthSuccess();
        void onPhoneAuthError();

    }

}
