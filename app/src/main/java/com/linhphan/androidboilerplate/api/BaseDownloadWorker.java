package com.linhphan.androidboilerplate.api;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import com.linhphan.androidboilerplate.api.Parser.IParser;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.androidboilerplate.util.NetworkUtil;
import com.linhphan.smssample.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by linhphan on 11/17/15.
 */
public class BaseDownloadWorker extends AsyncTask<String, Integer, Object> {
    protected final WeakReference<Context> mContext;

    protected Method mType = Method.GET;//the method of request whether POST or GET, default value is GET
    protected Map<String, String> mParams;
    protected IParser mParser;

    private DownloadCallback mCallback;
    private int mRequestCode = DownloadCallback.UNKNOWN_CODE;
    protected ResponseCodeHolder mResponseCode = new ResponseCodeHolder();

    //progress dialog
    protected ProgressDialog mProgressbar;

    //exception
    protected Exception mException;

    //================== constructors ==============================================================

    /**
     * constructs an AsyncTask download worker. this will initialize a progress bar dialog with a STYLE_SPINNER if isShowDialog is set true
     *
     * @param isShowDialog if this argument is set true, then a dialog will be showed when this download worker is working.
     * @param mCallback    a callback which do something after the download worker is finish or error.
     */
    public BaseDownloadWorker(Context context, boolean isShowDialog, DownloadCallback mCallback) {
        this.mContext = new WeakReference<>(context);
        this.mCallback = mCallback;

        if (isShowDialog && context != null) {
            this.mProgressbar = new ProgressDialog(this.mContext.get());
            this.mProgressbar.setMessage("Please! wait a minute");
            mProgressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressbar.setCancelable(false);
        }
    }

    //================= setters and getters ========================================================
    public BaseDownloadWorker setRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public BaseDownloadWorker setType(Method type) {
        this.mType = type;
        return this;
    }

    public BaseDownloadWorker setParams(Map<String, String> params) {
        this.mParams = params;
        return this;
    }

    public BaseDownloadWorker setParser(IParser jsonParser) {
        mParser = jsonParser;
        return this;
    }

    //================== overridden methods ========================================================
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (!NetworkUtil.isNetworkConnected(mContext.get())) {//determine whether internet connection is available
            this.mException = new NoInternetConnectionException();
            return;
        }

        if (mContext.get() != null && mProgressbar != null) {
            mProgressbar.show();
        }
    }

    /**
     * this method should be overridden in it's sub classes
     */
    @Override
    protected Object doInBackground(String... params) {
        return null;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (mException == null)
            mCallback.onSuccessfully(o, mRequestCode, mResponseCode.getValue());
        else {
            mCallback.onFailed(mException, mRequestCode, mResponseCode.getValue());
        }
        if (mProgressbar != null && mProgressbar.isShowing())
            mProgressbar.dismiss();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (mProgressbar != null && mProgressbar.isShowing())
            mProgressbar.setProgress(values[0]);
    }

    @Override
    protected void onCancelled(Object o) {
        if (mProgressbar != null && mProgressbar.isShowing())
            mProgressbar.dismiss();
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        if (mProgressbar != null && mProgressbar.isShowing())
            mProgressbar.dismiss();
        super.onCancelled();
    }

    //================== other methods =============================================================

    /**
     * the progressbar will be cancelable when user touches anywhere outside the dialog if this method is called.
     * default is false.
     *
     * @return the current instance.
     */
    public BaseDownloadWorker setDialogCancelable() {
        if (mProgressbar != null) {
            mProgressbar.setCancelable(true);
        }
        return this;
    }

    public BaseDownloadWorker setDialogCancelCallback(String buttonName, DialogInterface.OnClickListener callback) {
        if (mProgressbar != null) {
            mProgressbar.setButton(DialogInterface.BUTTON_NEGATIVE, buttonName, callback);
        }
        return this;
    }

    public BaseDownloadWorker setDialogTitle(String title) {
        if (mProgressbar != null) {
            if (title != null && !title.isEmpty()) {
                mProgressbar.setTitle(title);
            }
        }
        return this;
    }

    /**
     * set a message to the dialog, if
     */
    public BaseDownloadWorker setDialogMessage(String message) {
        if (mProgressbar != null) {
            if (message != null && !message.isEmpty()) {
                mProgressbar.setMessage(message);
            }
        }
        return this;
    }

    /**
     * setup the horizontal progressbar which will be showed on screen
     *
     * @return JsonDownloadWorker object
     */
    public BaseDownloadWorker setHorizontalProgressbar() {
        mProgressbar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressbar.setMax(100);
        mProgressbar.setProgress(0);

        return this;
    }

    /**
     * try to retrieve data from remote server
     *
     * @return data from server which is presented by a string
     * @throws IOException
     */
    protected String sendGet(String path) throws IOException {
        String query = null;
        if (mParams != null)
            query = encodeQueryString(mParams);
        if (query != null && !query.isEmpty())
            query = "?" + query;
        URL url = new URL(path + query);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        int responseCode = httpURLConnection.getResponseCode();
        Logger.i(getClass().getName(), "sending POST  request to URL: " + path);
        Logger.i(getClass().getName(), "post parameters: " + query);
        Logger.i(getClass().getName(), "response code: " + responseCode);

        if (responseCode != HttpURLConnection.HTTP_OK) {
            Logger.e(getClass().getName(), "connection is failed");
            return null;
        }

        InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
        InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String result = readBuffer(bufferedReader);

        bufferedReader.close();
        inputStreamReader.close();
        in.close();
        httpURLConnection.disconnect();
        return result;
    }

    /**
     * try to retrieve data from remote server
     * dump server: http://www.posttestserver.com/
     *
     * @return data from server which is presented by a string
     * @throws IOException
     */
    protected String sendPost(String path, Map<String, String> params) throws IOException {
        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        String query = encodeQueryString(params);


        //== add header
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Length", "" + Integer.toString(query.getBytes().length));

        //== set post request
        httpURLConnection.setDoOutput(true);
        DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        dataOutputStream.writeBytes(query);
        dataOutputStream.flush();
        dataOutputStream.close();

        int responseCode = httpURLConnection.getResponseCode();
        Logger.i(getClass().getName(), "sending POST  request to URL: " + path);
        Logger.i(getClass().getName(), "post parameters: " + query);
        Logger.i(getClass().getName(), "response code: " + responseCode);

        if (responseCode != HttpURLConnection.HTTP_OK) {
            Logger.e(getClass().getName(), "connection is failed");
            return null;
        }

        InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
        InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String result = readBuffer(bufferedReader);

        //== close streams
        bufferedReader.close();
        inputStreamReader.close();
        in.close();
        httpURLConnection.disconnect();
        return result;
    }

    /**
     * used to upload files to a remote server
     * @param path the url point to the remote server
     * @param fileList a list of files will be uploaded
     * @param params parameters will be request to the remote server.
     * @return the response received from the remote server
     * @throws IOException
     */
    protected String sendPostMultipart(String path, Map<String, String> fileList, Map<String, String> params) throws IOException {
        final int CONNECT_TIMEOUT = 15000;
        final int READ_TIMEOUT = 10000;
        final String CHARSET = "UTF-8";
        final String CRLF = "\r\n";
        final String TWO_HYPHENS = "--";
        final String boundary = "*****";

        HttpURLConnection httpURLConnection;
        DataOutputStream dos;
        String response = "";

        URL url = new URL(path);
        httpURLConnection = (HttpURLConnection) url.openConnection();

        //== add header
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);

        //== set post request
        httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
        httpURLConnection.setReadTimeout(READ_TIMEOUT);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);

        //Start content wrapper
        dos = new DataOutputStream(httpURLConnection.getOutputStream());

        //==== add form field
        for (String s : params.keySet()) {
            String key = String.valueOf(s);
            String value = params.get(key);
            try {
                dos.writeBytes(TWO_HYPHENS + boundary + CRLF);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + CRLF);
                dos.writeBytes("Content-Type: text/plain; charset=" + CHARSET + CRLF);
                dos.writeBytes(CRLF);
                dos.writeBytes(value + CRLF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dos.writeBytes(CRLF);
        dos.flush();

        //== add files field
        for (String s : fileList.keySet()) {
            String key = String.valueOf(s);
            String filePath = fileList.get(key);
            File uploadFile = new File(filePath);
            if (uploadFile.exists()) {
                dos.writeBytes(TWO_HYPHENS + boundary + CRLF);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\";filename=\"" + uploadFile.getName() + "\"" + CRLF);
                dos.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(uploadFile.getName()) + CRLF);
                dos.writeBytes("Content-Transfer-Encoding: binary" + CRLF);
                dos.writeBytes(CRLF);

                FileInputStream fStream = new FileInputStream(uploadFile);
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int length;

                while ((length = fStream.read(buffer)) != -1) {
                    dos.write(buffer, 0, length);
                }
                dos.writeBytes(CRLF);
                dos.writeBytes(TWO_HYPHENS + boundary + TWO_HYPHENS + CRLF);
                    /* close streams */
                fStream.close();
            }
        }
        dos.writeBytes(CRLF);
        dos.flush();
        dos.close();

        httpURLConnection.connect();
        int statusCode;
        httpURLConnection.connect();
        statusCode = httpURLConnection.getResponseCode();
        Logger.d(getClass().getName(), "sending POST  request to URL: " + path);
        Logger.d(getClass().getName(), "post parameters: " + params);
        // 200 represents HTTP OK
        if (statusCode == HttpURLConnection.HTTP_OK) {
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            response = readBuffer(bufferedReader);

            //disconnecting
            bufferedReader.close();
            inputStreamReader.close();
            in.close();
            httpURLConnection.disconnect();

        } else {
            Logger.d(getClass().getName(), "connection is failed");
//            System.out.println(urlConnection.getResponseMessage());
//            inputStream = new BufferedInputStream(urlConnection.getInputStream());
//            strResponse = readStream(inputStream);
        }

        return response;
    }

    /**
     * read data from buffer
     *
     * @return data which is presented by a string
     * @throws IOException
     */
    private String readBuffer(BufferedReader reader) throws IOException {
        if (reader == null) return null;
        StringBuilder builder = new StringBuilder();
        int tamp;
        while ((tamp = reader.read()) != -1) {
            builder.append((char) tamp);
        }
        return builder.toString();
    }

    private String encodeQueryString(Map<String, String> params) throws UnsupportedEncodingException {
        final char PARAMETER_DELIMITER = '&';
        final char PARAMETER_EQUALS_CHAR = '=';

        StringBuilder builder = new StringBuilder();
        if (params != null) {
            boolean firstParameter = true;
            for (String key : params.keySet()) {
                if (!firstParameter) {
                    builder.append(PARAMETER_DELIMITER);
                }
                builder.append(key)
                        .append(PARAMETER_EQUALS_CHAR)
                        .append(URLEncoder.encode(params.get(key), "UTF-8"));

                if (firstParameter)
                    firstParameter = false;
            }
        }

        return builder.toString();
    }

    /**
     * show notification progress on notification bar. this will show the progress of downloading.
     *
     * @param contentText the message will be showed in the notification
     * @param percent     the percent of downloading progress
     */
    protected void showNotificationProgress(Context context, String contentText, int percent) {
        int notId = 898989;
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)// TODO: 19/12/2015  must replace the icon for the notification.
                .setContentText(contentText)
                .setProgress(100, percent, false)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notId, notification);
    }

    protected String getTag() {
        return getClass().getName();
    }

    //=========== inner classes ====================================================================
    public enum Method {
        GET, POST
    }

    /**
     * this class will be used to instance an object which is passed to an parser as a parameter to get the response code from server.
     */
    public class ResponseCodeHolder {
        private int value = DownloadCallback.UNKNOWN_CODE;

        public ResponseCodeHolder() {
        }

        public ResponseCodeHolder(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public interface DownloadCallback {
        public static final int UNKNOWN_CODE = 1000;
        public static final int RESPONSE_CODE_SUCCESSFULLY = 10000;
        public static final int RESPONSE_Code_UNKNOWN_REQUEST = 11000;
        public static final int RESPONSE_CODE_MISSING_PARAMS = 12000;

        void onSuccessfully(Object data, int requestCode, int responseCode);

        void onFailed(Exception e, int requestCode, int responseCode);
    }

    public class NoInternetConnectionException extends Exception {
        @Override
        public String getMessage() {
            return "No internet connection available";
        }
    }
}