package com.photoviewer.data.preferences.orm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreferencesDao<T> {

    public static final String EMPTY_STRING = "";
    private static final String TAG = PreferencesDao.class.getSimpleName();
    final Context mContext;
    private final SharedPreferences mSharedPreferences;
    private final Class<T> mType;

    private Map<String, Field> mFieldsPreferencesMap;

    public PreferencesDao(Class<T> type, Context context) {
        mContext = context;
        mType = type;
        mSharedPreferences = mContext.getSharedPreferences(getName(), Context.MODE_PRIVATE);
    }

    public void save(T model) {
        SharedPreferences.Editor e = mSharedPreferences.edit();
        convertModelToEditor(model, e);
        e.apply();
    }

    public T read() {
        T model = newModel();

        convertPreferencesToModel(model);

        return model;
    }

    public void delete() {
        mSharedPreferences.edit().clear();
    }

    private String getName() {
        Class<T> type = getType();
        for (Annotation annotation : type.getDeclaredAnnotations()) {
            if (annotation.annotationType().isAssignableFrom(PreferenceFile.class)) {
                PreferenceFile preferenceFileAnnotation = (PreferenceFile)annotation;
                return preferenceFileAnnotation.name();
            }
        }

        return type.getSimpleName();
    }

    private void convertModelToEditor(T model, SharedPreferences.Editor editor) {
        buildFieldsMap();

        for (Map.Entry<String, Field> entry : mFieldsPreferencesMap.entrySet()) {
            String preferenceName = entry.getKey();
            Field field = entry.getValue();
            putValueToEditor(editor, preferenceName, model, field);
        }
    }

    private void putValueToEditor(SharedPreferences.Editor editor, String preferenceName, T model, Field field) {
        field.setAccessible(true);

        try {
            if (field.getType().equals(boolean.class)) {
                boolean booleanValue = field.getBoolean(model);
                editor.putBoolean(preferenceName, booleanValue);
                return;
            }
            if (field.getType().equals(int.class)) {
                int integerValue = field.getInt(model);
                editor.putInt(preferenceName, integerValue);
                return;
            }
            if (field.getType().equals(float.class)) {
                float floatValue = field.getFloat(model);
                editor.putFloat(preferenceName, floatValue);
                return;
            }
            if (field.getType().equals(long.class)) {
                long longValue = field.getLong(model);
                editor.putLong(preferenceName, longValue);
                return;
            }
            if (field.getType().equals(String.class)) {
                String stringValue = (String) field.get(model);
                editor.putString(preferenceName, stringValue);
                return;
            }

            Gson gson = new Gson();
            editor.putString(preferenceName, gson.toJson(field.get(model)));
        } catch (IllegalAccessException e) {
            Log.wtf(TAG, "Exception during converting of Field's value to Preference", e);
        }
    }

    private synchronized void buildFieldsMap() {

        if(mFieldsPreferencesMap != null) {
            return;
        } else {
            mFieldsPreferencesMap = new HashMap<>();
        }

        Class<T> type = getType();
        List<Field> fields = Arrays.asList(type.getDeclaredFields());

        for (Field field : fields) {
            handleField(field);
        }
    }

    private void handleField(Field field) {
        Annotation[] annotations = field.getDeclaredAnnotations();

        for(Annotation annotation : annotations) {
            if(annotation.annotationType().isAssignableFrom(Preference.class)) {
                Preference preferenceAnnotation = (Preference) annotation;

                if (!preferenceAnnotation.enabled()) {
                    return;
                }

                if (!preferenceAnnotation.name().isEmpty()) {
                    mFieldsPreferencesMap.put(preferenceAnnotation.name(), field);
                    return;
                }
            }
        }

        mFieldsPreferencesMap.put(field.getName(), field);
    }

    private void convertPreferencesToModel(T model) {
        buildFieldsMap();

        for (Map.Entry<String, Field> entry : mFieldsPreferencesMap.entrySet()) {
            String preferenceName = entry.getKey();
            Field field = entry.getValue();
            setValueToModel(preferenceName, model, field);
        }
    }

    private void setValueToModel(String preferenceName, T model, Field field) {
        field.setAccessible(true);

        try {
            if (field.getType().equals(boolean.class)) {
                boolean booleanValue = mSharedPreferences.getBoolean(preferenceName, false);
                field.setBoolean(model, booleanValue);
                return;
            }
            if (field.getType().equals(int.class)) {
                int integerValue = mSharedPreferences.getInt(preferenceName, 0);
                field.setInt(model, integerValue);
                return;
            }
            if (field.getType().equals(float.class)) {
                float floatValue = mSharedPreferences.getFloat(preferenceName, 0f);
                field.setFloat(model, floatValue);
                return;
            }
            if (field.getType().equals(long.class)) {
                long longValue = mSharedPreferences.getLong(preferenceName, 0L);
                field.setLong(model, longValue);
                return;
            }
            if (field.getType().equals(String.class)) {
                String stringValue = mSharedPreferences.getString(preferenceName, EMPTY_STRING);
                field.set(model, stringValue);
                return;
            }

            Gson gson = new Gson();
            String stringValue = mSharedPreferences.getString(preferenceName, EMPTY_STRING);
            field.set(model, gson.fromJson(stringValue, field.getDeclaringClass()));
        } catch (IllegalAccessException e) {
            Log.wtf(TAG, "Exception during converting of Preference to Field's value", e);
        }
    }

    private T newModel() {
        Class<T> type = getType();
        Constructor<T> emptyConstructor = (Constructor <T>) type.getConstructors()[0];
        try {
            T model = emptyConstructor.newInstance(null);
            return model;
        } catch (InstantiationException e) {
            Log.wtf(TAG, e);
        } catch (InvocationTargetException e) {
            Log.wtf(TAG, e);
        } catch (IllegalAccessException e) {
            Log.wtf(TAG, e);
        }
        return null;
    }

    private Class<T> getType(){
        return mType;
    }
}

