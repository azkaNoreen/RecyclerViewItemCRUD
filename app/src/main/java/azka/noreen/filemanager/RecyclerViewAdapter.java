package azka.noreen.filemanager;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<StorageItems> StorageItemsArrayList=new ArrayList<StorageItems>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.storageitems_item,parent,false);
        return new StorageItemsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        StorageItems st=StorageItemsArrayList.get(position);
        StorageItemsViewHolder StorageItemsViewHolder= (StorageItemsViewHolder) holder;
        StorageItemsViewHolder.StorageItemsPhone.setSelected(true);

        StorageItemsViewHolder.StorageItemsTextName.setText(st.getFileName());
        StorageItemsViewHolder.StorageItemsPhone.setText(st.getFilePath());
        File file=new File(st.getFilePath());

        if(file.isDirectory()){
            StorageItemsViewHolder.img.setImageResource(R.drawable.ic_folder);
        }
        else if(st.getFileName().endsWith(".mp3")||st.getFileName().endsWith(".wav")||st.getFileName().endsWith(".m4a"))
        {
            StorageItemsViewHolder.img.setImageResource(R.drawable.ic_music);

        }else if(st.getFileName().endsWith(".jpg")||st.getFileName().endsWith(".jpeg")||st.getFileName().endsWith(".webp"))
        {
            StorageItemsViewHolder.img.setImageResource(R.drawable.ic_baseline_image_24);
        }
        else if(st.getFileName().endsWith(".mp4")||st.getFileName().endsWith(".mov")||st.getFileName().endsWith(".avi")||st.getFileName().endsWith(".mkv"))
        {
            StorageItemsViewHolder.img.setImageResource(R.drawable.ic_baseline_videocam_24);
        }
        else if(st.getFileName().endsWith(".txt"))
        {
            StorageItemsViewHolder.img.setImageResource(R.drawable.ic_baseline_text_snippet_24);
        }
        else{
            StorageItemsViewHolder.img.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);

        }

//        String filepath=st.getFilePath();
//        String extension = filepath.substring(filepath.lastIndexOf("."));
//        StorageItemsViewHolder.StorageItemsPhone.setText(extension);
//
//        StorageItemsViewHolder.img.setImageResource(R.drawable.ic_folder);
        StorageItemsViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageItemsArrayList.remove( position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getItemCount());

                Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT).show();

            }
        });

        StorageItemsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    Intent intent=new Intent(view.getContext(),BrowseFilesActivity.class);
//                    intent.putExtra("RootPath",st.getFilePath());
//                    view.getContext().startActivity(intent);
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
              BrowseFilesActivity activity =  ((BrowseFilesActivity) StorageItemsViewHolder.itemView.getContext());
                showDialog(activity,"Enter Name","Enter Path",position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return StorageItemsArrayList.size();
    }
    public void setData(ArrayList<StorageItems> StorageItemsArrayList){
        this.StorageItemsArrayList=StorageItemsArrayList;
        notifyDataSetChanged();
    }

    //to find views of single list xml file
    public static class StorageItemsViewHolder extends RecyclerView.ViewHolder{

        TextView StorageItemsTextName;
        TextView StorageItemsPhone;
    ImageView img;
    Button delete;


    public StorageItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            StorageItemsTextName=itemView.findViewById(R.id.fileName);
            StorageItemsPhone=itemView.findViewById(R.id.filePath);
        img=itemView.findViewById(R.id.img);
        delete=itemView.findViewById(R.id.del);
    }
    }
    public void showDialog(Activity activity, String msg, String submessage,int pos){
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
                StorageItems item=new StorageItems(text.getText().toString(),stext.getText().toString());
                StorageItemsArrayList.set( pos,item);
                notifyItemChanged(pos);

//                Toast.makeText(BrowseFilesActivity.this, "added", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        dialog.show();

    }


}
