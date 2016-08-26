package android.zeroh729.com.pcari.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.ui.activities.CreateSurveyActivity;

import org.androidannotations.annotations.EFragment;

@EFragment
public class AddQuanqDialog extends DialogFragment {
    CreateSurveyActivity.ClickListener listener;

    private EditText et_question;
    private EditText et_ratingLabels;

    public void setListener(CreateSurveyActivity.ClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dBuilder = new AlertDialog.Builder(getContext());

        final AlertDialog dialog = dBuilder.setView(R.layout.dialog_addquanq)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onClick();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog2) {
                et_question = (EditText) dialog.findViewById(R.id.et_question);
                et_ratingLabels = (EditText) dialog.findViewById(R.id.et_ratings);
            }
        });
        return dialog;
    }

    public EditText getEt_question() {
        return et_question;
    }

    public EditText getEt_ratingLabels() {
        return et_ratingLabels;
    }
}
