package com.example.antho.reportiify.Firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antho.reportiify.MainActivity;
import com.example.antho.reportiify.R;
import com.example.antho.reportiify.model.ListItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

import static android.support.constraint.Constraints.TAG;

/*
* 1.SAVE DATA TO FIREBASE
* 2.RETRIEVE
* 3.RETURN ARRAYLIST
* */

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved;
    String dbPathReference;
    EditText txtTitle, txtDesc;
    TextView txtLocation;
    ImageView img;
    Context context;
    String linkImg;

    public FirebaseHelper(DatabaseReference db, String dbPathReference, Context c) {
        this.context = c;
        this.db = db;
        this.dbPathReference = dbPathReference;
    }

    private Boolean saved(ListItem listItem) {
        if (listItem == null) {
            saved = false;
        } else {
            try {
                db.push().setValue(listItem);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    public String sendData(View v,Uri filePath, StorageReference storageReference){

        txtTitle = v.findViewById(R.id.title);
        txtDesc = v.findViewById(R.id.desc);
        txtLocation = v.findViewById(R.id.locationTxt);
        img = v.findViewById(R.id.cardImg);

        //GET DATA
        String title = txtTitle.getText().toString();
        String desc = txtDesc.getText().toString();
        String location = txtLocation.getText().toString();
        String img = linkImg;

        if(filePath != null && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc)) {

            uploadImage(filePath, storageReference);

            //SET DATA
            ListItem item = new ListItem();
            item.setHead(title);
            item.setDesc(desc);
            item.setLocation(location);
            item.setImg(img);

            if (saved(item)) {
                txtTitle.setText("");
                txtDesc.setText("");
                txtLocation.setText(R.string.loading);
            }

            return "Reporte agregado correctamente";

        }else if(filePath == null) {

            return "Debe de seleccionar una imagen!";

        } else{
            return "Ningun campo debe de estar vacio";
        }
    }
    private void uploadImage(Uri filePath, StorageReference storageReference) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Subiendo...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    linkImg = uri.toString();
                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(context, "Agregado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Error ", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage((int)progress+"% Subido");
                        }
                    });
        }

}