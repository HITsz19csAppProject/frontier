package com.example.chatting;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static com.example.chatting.MyApplication.CurrentUser;

public class CenterFragment extends Fragment {

    private CenterViewModel centerViewModel;
    private Button cancel;
    private Button user_info;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        centerViewModel =
                ViewModelProviders.of(this).get(CenterViewModel.class);
        View root = inflater.inflate(R.layout.center_fragment, container, false);
        cancel = root.findViewById(R.id.cancel);
        user_info=root.findViewById(R.id.user_info);
        user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentUser.logOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        return root;
    }
}