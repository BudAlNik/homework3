package com.example.nik.homework3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Nik on 10.03.2017.
 */

public class LoadService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Load service", "here");
        File destFile = new File(getFilesDir(), MainActivity.destFile);
        if (!destFile.exists()) {
            new Thread(new ImageLoader(MainActivity.url, destFile)).start();
        }
        sendBroadcast(new Intent(MainActivity.INTENT_FILTER));
        return START_STICKY;
    }

    public class ImageLoader implements Runnable {
        private String urlAddress;
        private File destFile;

        public ImageLoader(String url, File destFile) {
            this.urlAddress = url;
            this.destFile = destFile;
        }

        private static final String TAG = "Image loader";

        @Override
        public void run() {
            InputStream in = null;
            FileOutputStream out = null;
            try {
                Log.d(TAG, "start downloading");
                URL url = new URL(urlAddress);
                in = new BufferedInputStream(url.openStream());
                out = new FileOutputStream(destFile);
                int tmp;
                byte[] buffer = new byte[1024];
                while ((tmp = in.read(buffer)) != -1) {
                    out.write(buffer, 0, tmp);
                }
                Log.d(TAG, "downloaded");
                sendBroadcast(new Intent(MainActivity.INTENT_FILTER));
            } catch (IOException e) {
                e.printStackTrace();
                destFile.delete();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Cannot close file: ", e);
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Cannot close connection: ", e);
                    }
                }
            }
        }
    }
}
