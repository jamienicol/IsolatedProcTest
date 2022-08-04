package me.jamienicol.isolatedproctest;

import android.app.Service;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String LOGTAG = "MyService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOGTAG, "onStartCommand()");

        testSurfaceTexture();

        Log.d(LOGTAG, "Finished starting service");
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void testSurfaceTexture() {
        Log.d(LOGTAG, "Creating SurfaceTexture");
        // This indefinitely hangs on an Android 7 AVD, but works fine on Android 10
        final SurfaceTexture st = new SurfaceTexture(0, false);
        Log.d(LOGTAG, "Finished creating SurfaceTexture");
    }
