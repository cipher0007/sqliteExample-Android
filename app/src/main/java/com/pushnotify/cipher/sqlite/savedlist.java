package com.pushnotify.cipher.sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class savedlist extends AppCompatActivity {

    GridView gridView;
    ArrayList<pushsave> list;
    PushSaveListAdapter adapter = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_push_list_activity);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new PushSaveListAdapter(this, R.layout.save_items, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = deleteKrniH.sqLiteHelper.getData("SELECT * FROM pushsave");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String message = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new pushsave(title, message, image, id));
        }
        adapter.notifyDataSetChanged();
gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor c = deleteKrniH.sqLiteHelper.getData("SELECT * FROM pushsave");
        String title="";
        String message="";
        byte[] image =null;
        while (c.moveToNext()){
            title=c.getString(1);
            message=c.getString(2);
            image=c.getBlob(3);

        }
         Toast.makeText(getApplicationContext(),title,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

        //showDialogDelete(arrID.get(position));

    }
});




        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(savedlist.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                       // String title="";
                            // delete
                            Cursor c = deleteKrniH.sqLiteHelper.getData("SELECT id FROM pushsave");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));

                            }
                           // Toast.makeText(getApplicationContext(),title,Toast.LENGTH_LONG).show();
                            showDialogDelete(arrID.get(position));

                        }

                });
                dialog.show();
                return true;
            }
        });
    }

    ImageView imageViewFood;

    private void showDialogDelete(final int idFood){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(savedlist.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    deleteKrniH.sqLiteHelper.deleteData(idFood);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateFoodList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateFoodList(){
        // get all data from sqlite
        Cursor cursor = deleteKrniH.sqLiteHelper.getData("SELECT * FROM pushsave");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String message = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new pushsave(title, message, image, id));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewFood.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}