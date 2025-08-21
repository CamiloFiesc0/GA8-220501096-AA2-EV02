package com.example.helpinghands_v02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 2); // versión 2 para que se ejecute onUpgrade
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla original de usuarios
        db.execSQL("CREATE TABLE userdetails(" +
                "id TEXT PRIMARY KEY, " +
                "nombre TEXT, " +
                "telefono TEXT, " +
                "direccion TEXT, " +
                "correo TEXT, " +
                "contraseña TEXT, " +
                "edad TEXT)");

        // Nueva tabla para Buscar Empleo
        db.execSQL("CREATE TABLE empleos(" +
                "idasignado TEXT PRIMARY KEY, " +
                "estadoservicio TEXT, " +
                "distancia TEXT, " +
                "idsolicitud TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS userdetails");
        db.execSQL("DROP TABLE IF EXISTS empleos");
        onCreate(db);
    }

    // ---------------------- Métodos para tabla userdetails ----------------------

    public Boolean insertuserdata(String id, String nombre, String telefono, String direccion, String correo, String contraseña, String edad) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("nombre", nombre);
        cv.put("telefono", telefono);
        cv.put("direccion", direccion);
        cv.put("correo", correo);
        cv.put("contraseña", contraseña);
        cv.put("edad", edad);

        long result = DB.insert("userdetails", null, cv);
        return result != -1;
    }

    public Boolean updateuserdata(String id, String nombre, String telefono, String direccion, String correo, String contraseña, String edad) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("telefono", telefono);
        cv.put("direccion", direccion);
        cv.put("correo", correo);
        cv.put("contraseña", contraseña);
        cv.put("edad", edad);

        // Verificar si existe el usuario antes de actualizar
        Cursor cursor = DB.rawQuery("SELECT * FROM userdetails WHERE id=?", new String[]{id});
        boolean exists = cursor.getCount() > 0;
        cursor.close(); // 🔴 Muy importante cerrar cursor

        if (exists) {
            int result = DB.update("userdetails", cv, "id=?", new String[]{id});
            return result > 0; // retorna true si actualizó al menos 1 fila
        } else {
            return false; // no existe el usuario con ese id
        }
    }


    public Boolean deletedata(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM userdetails WHERE id=?", new String[]{id});
        if (cursor.getCount() > 0) {
            cursor.close(); // 🔴 Cerrar cursor antes de borrar
            long result = DB.delete("userdetails", "id=?", new String[]{id});
            return result != -1;
        } else {
            cursor.close(); // 🔴 Importante
            return false;
        }
    }


    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT * FROM userdetails", null);
    }



    // Método para validar login
    public Boolean checkUser(String correo, String contraseña) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM userdetails WHERE correo=? AND contraseña=?", new String[]{correo, contraseña});
        boolean exists = cursor.getCount() > 0;
        cursor.close(); // cerrar cursor
        return exists;
    }






    // ---------------------- Métodos para tabla empleos ----------------------

    public Boolean insertData(String idasignado, String estado, String distancia, String idsolicitud) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("idasignado", idasignado);
        cv.put("estadoservicio", estado);
        cv.put("distancia", distancia);
        cv.put("idsolicitud", idsolicitud);

        long result = DB.insert("empleos", null, cv);
        return result != -1;
    }

    public Boolean updateData(String idasignado, String estado, String distancia, String idsolicitud) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("estadoservicio", estado);
        cv.put("distancia", distancia);
        cv.put("idsolicitud", idsolicitud);

        Cursor cursor = DB.rawQuery("SELECT * FROM empleos WHERE idasignado=?", new String[]{idasignado});
        if (cursor.getCount() > 0) {
            long result = DB.update("empleos", cv, "idasignado=?", new String[]{idasignado});
            return result != -1;
        } else {
            return false;
        }
    }

    public Boolean deleteData(String idasignado) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM empleos WHERE idasignado=?", new String[]{idasignado});
        if (cursor.getCount() > 0) {
            long result = DB.delete("empleos", "idasignado=?", new String[]{idasignado});
            return result != -1;
        } else {
            return false;
        }
    }

    public String getAllData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM empleos", null);
        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()) {
            sb.append("ID Asignado: ").append(cursor.getString(0)).append("\n");
            sb.append("Estado: ").append(cursor.getString(1)).append("\n");
            sb.append("Distancia: ").append(cursor.getString(2)).append("\n");
            sb.append("ID Solicitud: ").append(cursor.getString(3)).append("\n\n");
        }
        cursor.close();
        return sb.toString();
    }


    public Cursor getDataById(String idasignado) {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT * FROM empleos WHERE idasignado=?", new String[]{idasignado});
    }

}
