package android.zeroh729.com.pcari.ui.dialogs;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.ui.activities.CreateSurveyActivity;

import org.androidannotations.annotations.EFragment;

@EFragment
public class AddQualqDialog extends DialogFragment {
    CreateSurveyActivity.ClickListener listener;

    private EditText et_question;
    private EditText et_questionForRating;
    private TextView tv_label_ratingRange;
    private SeekBar sb_ratingRange;

    public void setListener(CreateSurveyActivity.ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dBuilder = new AlertDialog.Builder(getContext());

        final AlertDialog dialog = dBuilder.setView(R.layout.dialog_addqualq)
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
                et_questionForRating = (EditText) dialog.findViewById(R.id.et_questionForRating);
                tv_label_ratingRange = (TextView)dialog.findViewById(R.id.tv_label_ratingRange);
                sb_ratingRange = (SeekBar)dialog.findViewById(R.id.sb_ratingRange);
                sb_ratingRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        String label = getResources().getString(R.string.add_qualq_label_ratingrange) + (progress+1);
                        tv_label_ratingRange.setText(label);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        });
        return dialog;
    }

    public EditText getEt_question() {
        return et_question;
    }

    public EditText getEt_questionForRating() {
        return et_questionForRating;
    }

    public SeekBar getSb_ratingRange() {
        return sb_ratingRange;
    }
}
