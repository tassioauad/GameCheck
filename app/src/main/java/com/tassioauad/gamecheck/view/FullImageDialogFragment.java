package com.tassioauad.gamecheck.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tassioauad.gamecheck.R;

public class FullImageDialogFragment extends DialogFragment {

    private static final String BUNDLE_ARG_PHOTOURL = "bundle_arg_photourl";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialogfragment_fullimage, null);
        ImageView imageViewPhoto = (ImageView) view.findViewById(R.id.imageview_photo);
        Picasso.with(getActivity()).load(getArguments().getString(BUNDLE_ARG_PHOTOURL))
                .placeholder(R.drawable.nophoto).into(imageViewPhoto);
        alertDialogBuilder.setView(view);

        return alertDialogBuilder.create();
    }

    public static FullImageDialogFragment newInstance(String photoUrl) {
        FullImageDialogFragment fullImageDialogFragment = new FullImageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ARG_PHOTOURL, photoUrl);
        fullImageDialogFragment.setArguments(bundle);

        return fullImageDialogFragment;
    }
}
