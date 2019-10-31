package com.khoa.project2_movethepeg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

class WarningDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.win_title);
        builder.setMessage(R.string.win_message);
        builder.setPositiveButton(R.string.ok, null);
        return builder.create();
    }
}