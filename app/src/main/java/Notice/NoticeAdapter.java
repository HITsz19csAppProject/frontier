package Notice;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.chatting.R;

import static com.example.chatting.R.layout.activity_announce;
import static com.example.chatting.R.layout.chatting_fragment;

@SuppressLint("ValidFragment")
public class NoticeAdapter extends Fragment{
    private String content;
    @SuppressLint("ValidFragment")
    public NoticeAdapter(String content){
        this.content = content;
    }
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(chatting_fragment,container,false);
        return view;
    }

    //@Override
//    public void onActivityCreated(Bundle savedInstanceState){
//        super.onActivityCreated(savedInstanceState);
//        //listView =(ListView)getActivity().findViewById(R.id.listView);
////        Button send=(Button)getActivity().findViewById(R.id.publish)
////        .setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent1=new Intent(getActivity(),NoticeAddActivity.class);
////                startActivity(intent1);
////            }
//
//    });

            //Button get=(button)getActivity().findViewById(R.id.)
  //      get.setOnClickListener(new View.OnClickListener() {

//            @Override

            public void onClick(View v) {
//                get();
            }
   //     });




}
