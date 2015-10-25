package com.photoviewer.data.db.migrations;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;

public abstract class Migration {
    public abstract void up(SQLiteDatabase db, ConnectionSource connectionSource);

    void addColumns(SQLiteDatabase db, String tableName, String... columnDefinitions) {
        for (String definition : columnDefinitions) {
            String sql = generateAddColumnSql(tableName, definition);
            String columnName = definition.split(" ")[0];
            if (!columnExists(db, tableName, columnName)) {
                db.execSQL(sql);
            }
        }
    }

    boolean columnExists(SQLiteDatabase db, String tableName, String columnName) {
        String sql = "PRAGMA TABLE_INFO(" + tableName + ")";
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();

        do {
            String candidateColumnName = cursor.getString(cursor.getColumnIndex("name"));

            if (candidateColumnName.equals(columnName)) {
                cursor.close();
                return true;
            }
        } while (cursor.moveToNext());

        cursor.close();

        return false;
    }

    private String generateAddColumnSql(String tableName, String columnDefinition) {
        return "ALTER TABLE " + tableName + " ADD COLUMN " + columnDefinition;
    }
}