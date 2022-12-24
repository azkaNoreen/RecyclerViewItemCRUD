package azka.noreen.filemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class BrowseFilesActivity extends AppCompatActivity {
    RecyclerView recycleView;
    TextView text;
    ArrayList<StorageItems> studentArrayList=new ArrayList<>();
    Button add;
    RecyclerViewAdapter rva;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_files);
        recycleView=findViewById(R.id.rv);
        text=findViewById(R.id.text);
        add=findViewById(R.id.added);
        text.setVisibility(View.GONE);

        Intent intent=getIntent();
        String rootPath=intent.getStringExtra("RootPath");
        File file=new File(rootPath);
        File[] childFiles = file.listFiles();

        if(childFiles==null||childFiles.length==0)
        {
            text.setVisibility(View.VISIBLE);
            text.setText(rootPath);
            return;
        }
        for(int i=0; i<childFiles.length; i++){
            String fName=childFiles[i].getName();
            String fPath=childFiles[i].getPath();
            StorageItems storageItems=new StorageItems(fName,fPath);
            studentArrayList.add(storageItems);
        }
        InitRecycleView();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(BrowseFilesActivity.this);
                showDialog(BrowseFilesActivity.this,"Enter Name","Enter Path");
            }
        });
    }

    public void InitRecycleView(){
        rva=new RecyclerViewAdapter();
        recycleView.setAdapter(rva);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        rva.setData(studentArrayList);

        recycleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(BrowseFilesActivity.this);
                showDialog(BrowseFilesActivity.this,"Enter Name","Enter Path");
            }
        });


    }
    public void showDialog(Activity activity, String msg, String submessage){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //don't show dialog default title
        dialog.setCancelable(false); //don't dismiss the dialog if other area is selected
        dialog.setContentView(R.layout.dialoglayout);

        TextView text = (TextView) dialog.findViewById(R.id.name);
        TextView stext = (TextView) dialog.findViewById(R.id.sname);

        text.setText(msg);
        stext.setText(submessage);

        Button dialogButton = (Button) dialog.findViewById(R.id.clickme);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(text.getText().toString(),stext.getText().toString());

//                Toast.makeText(BrowseFilesActivity.this, "added", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                
            }
        });
        dialog.show();

    }
    void addItem(String n,String p){
        StorageItems storageItems=new StorageItems(n,p);
        studentArrayList.add(storageItems);
//        studentArrayList.notifyDataSetChanged();
        rva.notifyItemInserted(studentArrayList.size());
//        rva.notifyDataSetChanged();
                        Toast.makeText(BrowseFilesActivity.this, "Item added", Toast.LENGTH_SHORT).show();

    }



}