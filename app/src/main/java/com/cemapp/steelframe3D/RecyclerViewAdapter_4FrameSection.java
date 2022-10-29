package com.cemapp.steelframe3D;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerViewAdapter_4FrameSection extends RecyclerView.Adapter<RecyclerViewAdapter_4FrameSection.ViewHolder> {
    private Context context;
    private List<SteelSection> data;

    public RecyclerViewAdapter_4FrameSection(Context context, List<SteelSection> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.z_card_4framesection, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        if(D_MainActivity.isRecyclerViewForDefine){
            //coding define frame section
            viewHolder.imgDeleteSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = viewHolder.getAdapterPosition();

                    if(D_MainActivity.isDefineFrameSectionNow){
                        boolean isSectionUsed = false;
                        for(int i : D_MainActivity.pointerFrameSection){
                            if(i == x){
                                isSectionUsed = true;
                            }
                        }

                        if(!isSectionUsed){
                            D_MainActivity.listFrameSection.remove(x);
                            notifyItemRemoved(x);

                            int counter = 0;
                            for(int i : D_MainActivity.pointerFrameSection){
                                if(i > x){
                                    D_MainActivity.pointerFrameSection[counter] = i - 1;
                                }
                                counter++;
                            }
                        }else{
                            Toast.makeText(D_MainActivity.appContext, "Section is used!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            viewHolder.cardDefineSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                int x = viewHolder.getAdapterPosition();
                D_MainActivity d = new D_MainActivity();
                d.showFrameSectionEdit(v, x, viewHolder.txtSectionCode, viewHolder.txtSectionName);
                }
            });

        }else{
            //coding assign frame section
            viewHolder.cardDefineSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = viewHolder.getAdapterPosition();
                    int counter = 0;
                    D_MainActivity objek = new D_MainActivity();

                    if(D_MainActivity.isAssignFrameSectionNow){
                        for(boolean i : D_MainActivity.isPointerElementSelected){
                            if(i){
                                D_MainActivity.pointerFrameSection[counter] = x;
                            }
                            counter++;
                        }
                        //close dialog
                        Toast.makeText(D_MainActivity.appContext, "Section is updated.", Toast.LENGTH_SHORT).show();
                        D_MainActivity.dialogFrameSectionList.dismiss();
                        D_MainActivity.fabMenu.close(true);
                        objek.setDeselectAll();
                    }
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtSectionCode.setText(data.get(position).getSectionCode());
        holder.txtSectionName.setText(data.get(position).getSectionName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton imgDeleteSection;
        private CardView cardDefineSection;
        private TextView txtSectionName;
        private TextView txtSectionCode;

        public ViewHolder(View itemView) {
            super(itemView);

            imgDeleteSection = itemView.findViewById(R.id.imgDeleteSection);
            txtSectionName = itemView.findViewById(R.id.txtSectionName);
            txtSectionCode = itemView.findViewById(R.id.txtSectionCode);
            cardDefineSection = itemView.findViewById(R.id.cardDefineSection);

            if(D_MainActivity.isRecyclerViewForDefine) {
                imgDeleteSection.setVisibility(View.VISIBLE);
                imgDeleteSection.setEnabled(true);
            }else{
                imgDeleteSection.setVisibility(View.INVISIBLE);
                imgDeleteSection.setEnabled(false);
            }
        }
    }
}
