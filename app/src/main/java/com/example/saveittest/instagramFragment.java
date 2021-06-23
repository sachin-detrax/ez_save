package com.example.saveittest;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saveittest.FacebookAPI.*;


public class instagramFragment extends Fragment {
    EditText url;
    Button download;







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void downloadInstagramVideo(String url, String name){
        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request req = new DownloadManager.Request(downloadUri);
        req.setTitle(name);
        req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS , "EZ SAVE/Video/" + name + ".mp4");
        req.setMimeType("*/*");
        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(req);
        Toast.makeText(getContext(), "Download Started", Toast.LENGTH_SHORT).show();
    }


    private String genName(int n){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_instagram, container, false);
        url=view.findViewById(R.id.link2);

        download = view.findViewById(R.id.download2);
        download.setOnClickListener(new View.OnClickListener() {




            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Processing Request Please Wait...", Toast.LENGTH_SHORT).show();
                String videoURL = url.getText().toString();
                Boolean bool = false;


                new com.example.saveittest.FacebookAPI.FacebookExtractor(getContext(),videoURL,true)
                {
                    @Override
                    protected void onExtractionComplete(com.example.saveittest.FacebookAPI.FacebookFile facebookFile) {


                        String downURL = facebookFile.getHdUrl();
                        downURL = downURL.replace("&amp;","&");

                        downURL = downURL.replace("/instagram.fccu19-1.fna.fbcdn.net/","/scontent.cdninstagram.com/");
                        Log.e("DownloadURL","URL:"+downURL);
                        String fileName = genName(20);

                        downloadInstagramVideo(downURL,fileName);

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
        return  view;
    }
}