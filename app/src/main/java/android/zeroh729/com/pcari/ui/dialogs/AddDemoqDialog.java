package android.zeroh729.com.pcari.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.ui.activities.CreateSurveyActivity;

import org.androidannotations.annotations.EFragment;

@EFragment
public class AddDemoqDialog extends DialogFragment {
    CreateSurveyActivity.ClickListener listener;

    public EditText et_question;
    public RadioButton rb_number;
    public RadioButton rb_choices;
    public TextView tv_label_choices;
    public EditText et_choices;

    public void setListener(CreateSurveyActivity.ClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dBuilder = new AlertDialog.Builder(getContext());
        final AlertDialog dialog = dBuilder.setView(R.layout.dialog_adddemoq)
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
                rb_number = (RadioButton) dialog.findViewById(R.id.rb_number);
                rb_choices = (RadioButton) dialog.findViewById(R.id.rb_multiplechoice);
                tv_label_choices = (TextView) dialog.findViewById(R.id.tv_label_choices);
                et_choices = (EditText) dialog.findViewById(R.id.et_choices);
                rb_choices.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(!isChecked){
                            tv_label_choices.setVisibility(View.GONE);
                            et_choices.setVisibility(View.GONE);
                        }else{
                            tv_label_choices.setVisibility(View.VISIBLE);
                            et_choices.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
        return dialog;
    }

    public EditText getEt_question() {
        return et_question;
    }

    public RadioButton getRb_number() {
        return rb_number;
    }

    public RadioButton getRb_choices() {
        return rb_choices;
    }

    public TextView getTv_label_choices() {
        return tv_label_choices;
    }

    public EditText getEt_choices() {
        return et_choices;
    }
}
