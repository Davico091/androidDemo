package com.example.david.sqlliteapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtCodigo,txtDescripcion,txtPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCodigo = (EditText)findViewById(R.id.txtCodigo);
        txtDescripcion = (EditText)findViewById(R.id.txtDecripcion);
        txtPrecio = (EditText)findViewById(R.id.txtPrecio);
    }

    public void alta(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String cod = txtCodigo.getText().toString();
        String desc = txtDescripcion.getText().toString();
        String prec = txtPrecio.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo",cod);
        registro.put("descripcion",desc);
        registro.put("precio",prec);
        db.insert("articulos", null, registro);
        db.close();
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        Toast.makeText(this,"Se registraron los datos del articulo",Toast.LENGTH_SHORT).show();
    }

    public void consultaCodigo(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        SQLiteDatabase db= admin.getWritableDatabase();

        String cod = txtCodigo.getText().toString();
        Cursor fila =db.rawQuery("SELECT descripcion, precio FROM articulos WHERE codigo = '" + cod + "' ", null);
        if(fila.moveToFirst()){
            txtDescripcion.setText(fila.getString(0));
            txtPrecio.setText(fila.getString(1));
        }
        else{
            Toast.makeText(this,"No existe un articulo con el codigo ingresado",Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

    public void consultaDesc(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        SQLiteDatabase db= admin.getWritableDatabase();

        String desc = txtDescripcion.getText().toString();
        Cursor fila =db.rawQuery("SELECT descripcion, precio FROM articulos WHERE descripcion = '"+desc+"' ",null);
        if(fila.moveToFirst()){
            txtDescripcion.setText(fila.getString(0));
            txtPrecio.setText(fila.getString(1));
        }
        else{
            Toast.makeText(this,"No existe un articulo con la descripcion ingresada",Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

    public void baja(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String codigo = txtCodigo.getText().toString();
        int cant = db.delete("articulos", "codigo='" + codigo + "'", null);
        db.close();
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        if(cant==1){
            Toast.makeText(this,"Se borro satisfactoriamente el articulo",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"No se pudo borrar, el codigo ingresado no existe",Toast.LENGTH_SHORT).show();
        }
    }

    public void modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"Administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String codigo = txtCodigo.getText().toString();
        String desc = txtDescripcion.getText().toString();
        String prec = txtPrecio.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo",codigo);
        registro.put("decripcion",desc);
        registro.put("precio", prec);

        int cant = db.update("articulos",registro,"codigo='"+codigo+"'",null);
        db.close();

        if(cant==1){
            Toast.makeText(this,"Se modifico el articulo satisfactoriamente",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"No se pudo modificar el articulo",Toast.LENGTH_SHORT).show();
        }

    }

}
