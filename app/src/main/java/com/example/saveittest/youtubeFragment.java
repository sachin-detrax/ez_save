package com.example.saveittest;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;

//yt
import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;


public class youtubeFragment extends Fragment {

    Button download;
    EditText editText;
    String newLink;
    RadioGroup radioGroup;
    RadioButton rad_720, rad_480, rad_360, rad_audio;
    int tag;
    String values;
    String ext = null;

    private File storage;
    private String[] storagePaths;




    public youtubeFragment() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }
    public void DownloadMyVideo(String values, int t, String extens) {

        @SuppressLint("StaticFieldLeak") YouTubeExtractor youTubeExtractor = new YouTubeExtractor(getContext()) {
            @Override
            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {
                if (ytFiles != null) {

                    newLink = ytFiles.get(t).getUrl();
                    String title = videoMeta.getTitle();
                    String title1 = title.replace(".", ""); //to be able to download videos which have "." in title name but gives an error
                    //but video thumbnail will not be visible
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(newLink));      //Constructor call
                    title1 = title1.replace("#","");
                    request.setTitle(title1);        //set name for video file
                    if (extens == ".mp4") {
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "SaveIT Downloads/Video/" + title1 + extens);//Destination of video file
                    } else if (extens == ".mp3") {
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "SaveIT Downloads/Audio/" + title1 + extens);//Destination of audio file
                    }
                    DownloadManager downloadManager; //Return the handle to a system-level service by name
                    downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

                    request.allowScanningByMediaScanner();                 //the file to be downloaded is to be scanned by MediaScanner


                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);       //Restrict the types of networks over which this download may proceed.


                    downloadManager.enqueue(request);   //Enqueues Download in device

                    Toast.makeText(getContext(), "Downloading Started", Toast.LENGTH_SHORT).show(); //show download started popup
                } else {
                    Toast.makeText(getContext(), ytFiles + "yTfiles : Null , API error!!", Toast.LENGTH_SHORT).show();
                }
            }

        };
        youTubeExtractor.execute(values);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youtube, container, false);
        editText =   view.findViewById(R.id.link);
        radioGroup = view.findViewById(R.id.radioGroup);
        rad_720 = view.findViewById(R.id.rad_720);
        rad_480 = view.findViewById(R.id.rad_480);
        rad_360 = view.findViewById(R.id.rad_360);
        rad_audio = view.findViewById(R.id.rad_audio);
        download = view.findViewById(R.id.download);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rad_720.isChecked()) {
                    tag = 22;
                    ext = ".mp4";
                } else if (rad_480.isChecked()) {
                    tag = 135;
                    ext = ".mp4";
                } else if (rad_360.isChecked()) {
                    tag = 18;
                    ext = ".mp4";
                } else if (rad_audio.isChecked()) {
                    tag = 251;
                    ext = ".mp3";
                }
                values = editText.getText().toString();

                DownloadMyVideo(values, tag, ext);
            }
        });
//        mgr= (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

        return view;


    }
}