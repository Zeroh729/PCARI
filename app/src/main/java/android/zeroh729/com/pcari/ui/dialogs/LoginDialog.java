package android.zeroh729.com.pcari.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;

import org.androidannotations.annotations.EFragment;

@EFragment
public class LoginDialog extends DialogFragment {
    View.OnClickListener listener;

    private EditText et_username;
    private EditText et_password;

    public void setListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dBuilder = new AlertDialog.Builder(getContext());
        final AlertDialog dialog = dBuilder.setView(R.layout.dialog_login)
                        .setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onClick(getView());
                            }
                        })
                        .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog2) {
                et_username = (EditText)dialog.findViewById(R.id.et_username);
                et_password = (EditText)dialog.findViewById(R.id.et_password);
            }
        });
        return dialog;
    }

    public EditText getEt_username() {
        return et_username;
    }

    public EditText getEt_password() {
        return et_password;
    }
}
