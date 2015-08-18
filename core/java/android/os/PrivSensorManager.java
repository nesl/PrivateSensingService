package android.os;

import android.os.IBinder;
import android.os.IPrivSensorService;
import android.util.Log;

public class PrivSensorManager {
    private static final String TAG = "PrivSensorManager";
    private final IPrivSensorService mPrivSensorService;
    private static PrivSensorManager privSensorManager;

    public static synchronized PrivSensorManager getPrivSensorManager() {
        if (privSensorManager == null) {
            IBinder binder = android.os.ServiceManager.getService("PrivSensor");
            if (binder != null) {
                IPrivSensorService privSensorService = IPrivSensorService.Stub.asInterface(binder);
                privSensorManager = new PrivSensorManager(privSensorService);

            } else {
                Log.e(TAG, "PrivSensorService binder is null");
            }

        }
        return privSensorManager;
    }


    public PrivSensorManager(IPrivSensorService privSensorService) {
        if (privSensorService == null) {
            throw new IllegalArgumentException("PrivSensorService is null");
        }
        mPrivSensorService = privSensorService;
    }

    public String helloWorld() {
        try {
            Log.d(TAG, "Going to call service from framework manager");
            String result = mPrivSensorService.helloWorld();
            Log.d(TAG, "Service called successfully from framework manager");
            return result;
        } catch (Exception e) {
        }
        return "";
    }

    public IPrivSensorService getPrivSensorService() {
        return mPrivSensorService;
    }
}
