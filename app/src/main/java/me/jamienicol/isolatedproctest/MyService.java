package me.jamienicol.isolatedproctest;

import android.app.Service;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.os.IBinder;
import android.util.Log;

import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public class MyService extends Service {
    private static final String LOGTAG = "MyService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOGTAG, "onStartCommand()");

        // testSurfaceTexture();

        testGL();

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

    private void testGL() {
        final EGL10 egl = (EGL10)EGLContext.getEGL();
        Log.d(LOGTAG, "Calling eglGetDisplay");
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        Log.d(LOGTAG, "eglGetDisplay returned " + display);

        int[] version = new int[2];
        Log.d(LOGTAG, "Calling eglInitialize");
        boolean ret = egl.eglInitialize(display, version);
        Log.d(LOGTAG, "eglInitialize returned " + ret);

        int numConfigs[] = new int[1];
        int[] configAttribs = new int[] {
                EGL10.EGL_RED_SIZE, 8,
                EGL10.EGL_GREEN_SIZE, 8,
                EGL10.EGL_BLUE_SIZE, 8,
                EGL10.EGL_NONE,
        };
        Log.d(LOGTAG, "Calling eglChooseConfig (first time)");
        egl.eglChooseConfig(display, configAttribs, null, 0, numConfigs);
        Log.d(LOGTAG, "eglChooseConfig (first time) returned " + numConfigs[0] + " configs");

        EGLConfig[] configs = new EGLConfig[numConfigs[0]];
        Log.d(LOGTAG, "Calling eglChooseConfig (second time)");
        egl.eglChooseConfig(display, configAttribs, configs, numConfigs[0], numConfigs);
        Log.d(LOGTAG, "eglChooseConfig (second time) returned " + numConfigs[0] + " configs");

        int[] contextAttribs = new int[] {
               EGL10.EGL_NONE,
        };
        Log.d(LOGTAG, "Calling eglCreateContext");
        EGLContext context = egl.eglCreateContext(display, configs[0], EGL10.EGL_NO_CONTEXT, contextAttribs);
        Log.d(LOGTAG, "eglCreateContext returned " + context);

        int[] surfaceAttribs = new int[] {
                EGL10.EGL_WIDTH, 1024,
                EGL10.EGL_HEIGHT, 1024,
                EGL10.EGL_NONE,
        };
        Log.d(LOGTAG, "Calling eglCreatePbufferSurface");
        EGLSurface surface = egl.eglCreatePbufferSurface(display, configs[0], surfaceAttribs);
        Log.d(LOGTAG, "eglCreatePbufferSurface returned " + surface);
    }
}
