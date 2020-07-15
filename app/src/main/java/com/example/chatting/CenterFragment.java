package com.example.chatting;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.chatting.R;

public class CenterFragment extends Fragment {

    private CenterViewModel centerViewModel;
    private Button cancel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        centerViewModel =
                ViewModelProviders.of(this).get(CenterViewModel.class);
        View root = inflater.inflate(R.layout.center_fragment, container, false);
        cancel = root.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        return root;
    }
}