package com.beelzik.mobile.serializabletest;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int size = 3000000;

    List<LoremObject> mStoreListSerializable;
    List<LoremObject> mStoreListParcelable;

    LoremObject[] mStoreArrayParcelable;

    private static final String KEY_SERIALIZABLE = "SERIALIZABLE";
    private static final String KEY_PARCELABLE = "KEY_PARCELABLE";
    private static final String KEY_PARCELABLE_ARRAY = "PARCELABLE_ARRAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            long startCreate = System.currentTimeMillis();
            Log.d(TAG, "onCreate: startCreate: " + startCreate);
            mStoreListSerializable = ceateStoreList();
            mStoreListParcelable = ceateStoreList();
            mStoreArrayParcelable = createStoreArray();

            long endCreate = System.currentTimeMillis();

            Log.d(TAG, "onCreate: endCreate: " + endCreate);

            long deltaCreate = endCreate - startCreate;

            Log.d(TAG, "onCreate: deltaCreate: " + deltaCreate);
        } else {

            long startRestore = System.currentTimeMillis();
            Log.d(TAG, "onCreate: startRestore: " + startRestore);

            mStoreListSerializable = forSerializable(savedInstanceState);
            mStoreListParcelable = forParcelable(savedInstanceState);

            mStoreArrayParcelable = forParcelableArray(savedInstanceState);
            long endRestore = System.currentTimeMillis();

            Log.d(TAG, "onCreate: endRestore: " + endRestore);

            long deltaRestore = endRestore - startRestore;

            Log.d(TAG, "onCreate: deltaRestore: " + deltaRestore);

        }

    }


    private LoremObject[] createStoreArray() {
        LoremObject array[] = new LoremObject[size];

        for (int i = 0; i < size; i++) {
            array[i] = new LoremObject();
        }
        return array;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        long startTimeSer = System.currentTimeMillis();
        Log.d(TAG, "onSaveInstanceState: startTimeSer: " + startTimeSer);
        outState.putSerializable(KEY_SERIALIZABLE, (Serializable) mStoreListSerializable);

        long endTimeSer = System.currentTimeMillis();
        Log.d(TAG, "onSaveInstanceState: endTimeSer: " + endTimeSer);

        long deltaTimeSer = endTimeSer - startTimeSer;
        Log.d(TAG, "onSaveInstanceState: DELTA SERIALIZABLE: " + deltaTimeSer);


        long startTimePar = System.currentTimeMillis();
        Log.d(TAG, "onSaveInstanceState: startTimePar LIST: " + startTimePar);

        outState.putParcelableArrayList(KEY_PARCELABLE, (ArrayList<? extends Parcelable>) mStoreListParcelable);


        long endTimePar = System.currentTimeMillis();
        Log.d(TAG, "onSaveInstanceState: endTimePar LIST: " + endTimePar);

        long deltaTimePar = endTimePar - startTimePar;
        Log.d(TAG, "forSerializable: DELTA PARCELABLE LIST: " + deltaTimePar);


        long startTimeParArray = System.currentTimeMillis();
        Log.d(TAG, "onSaveInstanceState: startTimeParArray: " + startTimeParArray);

        outState.putParcelableArray(KEY_PARCELABLE_ARRAY, mStoreArrayParcelable);


        long endTimeParArray = System.currentTimeMillis();
        Log.d(TAG, "onSaveInstanceState: endTimeParArray: " + endTimeParArray);

        long deltaTimeParArray = endTimeParArray - startTimePar;
        Log.d(TAG, "onSaveInstanceState DELTA deltaTimeParArray: " + deltaTimeParArray);
    }

    private static final String TAG = "MainActivity";

    private List<LoremObject> forSerializable(Bundle savedInstanceState) {

        long startTime = System.currentTimeMillis();
        Log.d(TAG, "forSerializable: START: " + startTime);
        List<LoremObject> result = (List<LoremObject>) savedInstanceState.getSerializable(KEY_SERIALIZABLE);

        long endTime = System.currentTimeMillis();
        Log.d(TAG, "forSerializable: END: " + endTime);

        long deltaTime = endTime - startTime;
        Log.d(TAG, "forSerializable: DELTA: " + deltaTime);

        return result;
    }

    private List<LoremObject> forParcelable(Bundle savedInstanceState) {

        long startTime = System.currentTimeMillis();
        Log.d(TAG, "forParcelable: START: " + startTime);
        List<LoremObject> result = savedInstanceState.getParcelableArrayList(KEY_PARCELABLE);

        long endTime = System.currentTimeMillis();
        Log.d(TAG, "forParcelable: END: " + endTime);

        long deltaTime = endTime - startTime;
        Log.d(TAG, "forParcelable: DELTA: " + deltaTime);

        return result;
    }

    private LoremObject[] forParcelableArray(Bundle savedInstanceState) {

        long startTime = System.currentTimeMillis();
        Log.d(TAG, "forParcelable ARRAY : START: " + startTime);

        //Из логов невооруженным глазом видно что восстанавливать ? extends Parcelable[] мозг рака (╮°-°)╮┳━━┳ ( ╯°□°)╯ ┻━━┻
        Parcelable[] parcelables = savedInstanceState.getParcelableArray(KEY_PARCELABLE_ARRAY);
        LoremObject[] loremObjects = Arrays.copyOf(parcelables, parcelables.length, LoremObject[].class);

        long endTime = System.currentTimeMillis();
        Log.d(TAG, "forParcelable ARRAY: END: " + endTime);

        long deltaTime = endTime - startTime;
        Log.d(TAG, "forParcelable ARRAY: DELTA: " + deltaTime);

        return loremObjects;
    }


    private List<LoremObject> ceateStoreList() {

        List<LoremObject> storeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            storeList.add(new LoremObject());
        }
        return storeList;
    }
}
