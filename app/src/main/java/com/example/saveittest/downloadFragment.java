package com.example.saveittest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class downloadFragment extends Fragment {

    private static final int MY_PERMISSION_REQUEST = 1;

    ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter<String> adapter;
//    MediaPlayer mediaPlayer; //for media player integration
    private List<File> files = new ArrayList<>();






    public void mainWork(){


        arrayList = new ArrayList<>();


        getData();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Share Video from ListView
                String fileName = (String) listView.getItemAtPosition(position);
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("video/*");
                intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse("/storage/emulated/0/"+ Environment.DIRECTORY_DOWNLOADS+"/EZ SAVE/Video/"+fileName+".mp4"));  //"file://"+
                startActivity(Intent.createChooser(intentShare,"Sharing file"));
            }
        });

    }

    public void getData(){

        Cursor videoCursor = getActivity().getContentResolver().query(MediaStore.Files.getContentUri("external"), null, MediaStore.Video.Media.DATA + " like ? ", new String[] {"%EZ SAVE/Video%"}, null);

        if(videoCursor!=null && videoCursor.moveToFirst()){
            int videoTitle = videoCursor.getColumnIndex(MediaStore.Video.Media.TITLE);
            do{
                String currentTitle = videoCursor.getString(videoTitle);
                arrayList.add(currentTitle);
            }while(videoCursor.moveToNext());

        }
    }







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        listView = (ListView)view.findViewById(R.id.data_view);
        mainWork();
        return view;
    }

}

