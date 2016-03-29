package com.linhphan.androidboilerplate.api;

import android.content.Context;

import com.linhphan.androidboilerplate.util.AppUtil;
import com.linhphan.androidboilerplate.util.FileUtil;
import com.linhphan.androidboilerplate.util.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by linhphan on 11/26/15.
 */
public class FileDownloadWorker extends BaseDownloadWorker {

    public FileDownloadWorker(Context mContext, boolean isShowDialog, DownloadCallback mCallback) {
        super(mContext, isShowDialog, mCallback);
    }

    @Override
    protected Object doInBackground(String... params) {
        if (mException != null)
            return null;
        String path;
        String fileName = "default";
        path = params[0];
        if (params.length >= 2) {
            fileName = params[1];
        }
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            int responseCode = httpURLConnection.getResponseCode();
            Logger.i(getClass().getName(), "sending POST  request to URL: " + path);
            Logger.i(getClass().getName(), "response code: " + responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) {
                Logger.e(getClass().getName(), "connection is failed");
                return null;
            }

            int contentLength = httpURLConnection.getContentLength();

            InputStream inputStream = httpURLConnection.getInputStream();
            File publicMusicDir = FileUtil.getPublicDownloadDirectory();
            File newFile = new File(publicMusicDir, fileName);
            OutputStream outputStream = new FileOutputStream(newFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

            byte[] buffer = new byte[1024];
            long total = 0;
            int count;
            while ((count = inputStream.read(buffer)) != -1) {
                total += count;

                int percent = (int) (total * 100 / contentLength);
                if (mProgressbar.isShowing()) {// publishing the progress....
                    publishProgress(percent);
                } else {//show the progress in notification bar.
                    if (percent < 100) {
                        showNotificationProgress(mContext.get(), "Downloading...", percent);
                    } else {
                        showNotificationProgress(mContext.get(), "Completed!", percent);
                    }
                }
                bufferedOutputStream.write(buffer, 0, count);
            }

            inputStream.close();
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            outputStream.close();
            //rescan media files
            AppUtil.getInstance().reScanSystemFileAt(mContext.get(), newFile);
            return newFile.getAbsolutePath();//locate the new downloaded file

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
