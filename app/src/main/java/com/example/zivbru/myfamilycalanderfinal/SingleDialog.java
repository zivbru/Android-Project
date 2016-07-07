package com.example.zivbru.myfamilycalanderfinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class SingleDialog extends DialogFragment {

    int selected = 0;
    String[] data ;

    public SingleDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick your choice");
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(), "CANCEL", Toast.LENGTH_LONG).show();
            }
        });

        builder.setSingleChoiceItems(data, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setSelected(which);
                        Toast.makeText(getActivity(), "OK" + which,
                                Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });
        return builder.create();
    }


    public void setData(ArrayList<String> arrayList){
        data= arrayList.toArray(new String[arrayList.size()]);
    }

    public String getSelected() {
        return String.valueOf(selected);
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}