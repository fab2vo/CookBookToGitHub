package com.fdx.cookbook;

import android.content.Context;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestConnection {
    private static String PHP204="return204.php";
    private static final String TAG = "DebugTestConnection";
    private SessionInfo mSession;

    public void TestConnection() {
        //
    }
    public void setTestConnexion(Context context) {
        mSession = SessionInfo.get(context);
    }

    public void testGo(){

        class testCon extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    URL url = new URL(mSession.getURLPath()+PHP204);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(mSession.getConnectTimeout());
                    conn.setReadTimeout(mSession.getReadTimeout());
                    conn.setRequestMethod("HEAD");
                    InputStream in = conn.getInputStream();
                    int status = conn.getResponseCode();
                    in.close();
                    conn.disconnect();
                    if (status == HttpURLConnection.HTTP_NO_CONTENT) {
                        //Log.d(TAG, "Test 204 : true ");
                        return true;
                    } else {return false;}
                } catch (Exception e) {return false;}
            }

            @Override
            protected void onPostExecute(Boolean s) {
                super.onPostExecute(s);
                mSession.setConnection(s);
            }
        }

        testCon test = new testCon();
        test.execute();
    }
}
