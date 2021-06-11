package com.example.saveittest;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saveittest.FacebookAPI.FacebookExtractor;
import com.example.saveittest.FacebookAPI.FacebookFile;


public class facebookFragment extends Fragment {

    EditText url;
    Button download;

    private void downloadFacebookVideo(String url, String name){

        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request req = new DownloadManager.Request(downloadUri);
        req.setTitle(name);
        req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS , "EZ SAVE/Video/" + name + ".mp4");
        req.setMimeType("*/*");
        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(req);
        Toast.makeText(getContext(), "Download Started", Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);
        url= view.findViewById(R.id.link1);
        download= view.findViewById(R.id.download1);

        download.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Processing Request Please Wait...", Toast.LENGTH_SHORT).show();
                String videoURL = url.getText().toString();
                Boolean bool = false;


                new FacebookExtractor(getContext(),videoURL,true)
                {
                    @Override
                    protected void onExtractionComplete(FacebookFile facebookFile) {

                        String downURL = facebookFile.getHdUrl();
                        downURL = downURL.replace(".fccu19-1.fna.","-lhr8-2.xx.");
                        downURL = downURL.replace("&amp;","&");
                        String fileName = facebookFile.getFilename();
                        if(fileName.contains("Facebook Watch"))
                        {
                            fileName=fileName.replace("Facebook Watch-&x98f; ","Facebook_Shorts_");
                        }
                        Log.e("FileName","F:"+fileName);
                        downloadFacebookVideo(downURL,fileName);
                    }

                    @Override
                    protected void onExtractionFail(Exception error) {
                        Log.e("Error","Error :: "+error.getMessage());
                        Toast.makeText(getContext(), "Extraction Failed:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }

                    @Override
                    protected void onExtractionFail(String Error) {
                    }

                };

            }
        });

        return view;
    }
}