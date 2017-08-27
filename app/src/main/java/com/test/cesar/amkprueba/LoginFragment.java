package com.test.cesar.amkprueba;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.test.cesar.amkprueba.model.Constants;
import com.test.cesar.amkprueba.model.SharedPreferencesManager;
import com.test.cesar.amkprueba.utils.ICommunication;

import java.util.regex.Pattern;

/**
 * Created by Cesar on 26/08/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextInputLayout tilLoginUser;
    private TextInputLayout tilLoginPassword;
    private TextInputEditText tvLoginUser;
    private TextInputEditText tvLoginPassword;
    private Button btnLogin;
    private ProgressBar pbLogin;

    ICommunication mCallback;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (ICommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ICommunication");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).updateToolbarTitle("Login");

        tilLoginUser = view.findViewById(R.id.til_login_user);
        tilLoginPassword = view.findViewById(R.id.til_login_password);
        tvLoginUser = view.findViewById(R.id.tv_login_user);
        tvLoginPassword = view.findViewById(R.id.tv_login_password);
        btnLogin = view.findViewById(R.id.btn_login);
        pbLogin = view.findViewById(R.id.pb_login);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onClick(View view) {
        if (view.getId() == btnLogin.getId()) {
            if (validations()) {
                pbLogin.setVisibility(View.VISIBLE);

                int LOGIN_DURATION = 3000;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pbLogin.setVisibility(View.GONE);
                        SharedPreferencesManager sharedPreferences = new SharedPreferencesManager(getContext());
                        sharedPreferences.setValueString(SharedPreferencesManager.ACCESS_TOKEN, tvLoginUser.getText().toString());
                        mCallback.onResponse(Constants.LOGIN_SUCCESS, null);
                    }
                }, LOGIN_DURATION);

            }
        }
    }


    private boolean validations() {
        boolean isValid = true;
        Pattern patron = Pattern.compile("^(?=.*[A-Z])[A-Za-z\\d]{8,}$");
        tilLoginUser.setError(null);
        tilLoginPassword.setError(null);

        if (tvLoginUser.getText().toString().isEmpty()) {
            tilLoginUser.setError(getString(R.string.error_user_empty));
            isValid = false;

        } else if (!patron.matcher(tvLoginUser.getText().toString()).matches()) {
            tilLoginUser.setError(getString(R.string.error_validations));
            isValid = false;

        }
        if (tvLoginPassword.getText().toString().isEmpty()) {
            tilLoginPassword.setError(getString(R.string.error_password_empty));
            isValid = false;
        } else if (!patron.matcher(tvLoginPassword.getText().toString()).matches()) {
            tilLoginPassword.setError(getString(R.string.error_validations));
            isValid = false;

        }

        return isValid;
    }


}
