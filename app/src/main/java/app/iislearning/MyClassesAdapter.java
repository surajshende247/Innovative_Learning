package app.iislearning;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.iislearning.classplayer.LecturePlayer;

public class MyClassesAdapter extends RecyclerView.Adapter<MyClassesAdapter.MyClassViewHolder>{

    ArrayList<LectureData> arrayListLectures;
    public MyClassesAdapter(ArrayList<LectureData> arrayListLectures) {
        this.arrayListLectures = arrayListLectures;
    }

    @NonNull
    @Override
    public MyClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.layout_lecture,parent,false);
        return new MyClassesAdapter.MyClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClassViewHolder holder, int position) {
        final LectureData o = arrayListLectures.get(position);
        holder.title.setText(o.getClass_title());
        holder.txtSubject.setText(o.getClass_subject());
        holder.txtClass.setText(o.getClass_grade());
        holder.txtUpload.setText("Uploaded On: "+o.getUploaded_date());
        holder.txtDue.setText("Due on: "+o.getDue_date());
        holder.txtTeacher.setText("Uploaded by "+o.getSubject_teacher());

        holder.cardLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), LecturePlayer.class);
                i.putExtra("videoid",o.getVideo_id());
                i.putExtra("title",o.getClass_title());
                i.putExtra("txtSubject",o.getClass_subject());
                i.putExtra("txtClass",o.getClass_grade());
                i.putExtra("txtDescription",o.getClass_description());
                i.putExtra("txtInstructions",o.getClass_instructions());
                i.putExtra("txtUpload",o.getUploaded_date());
                i.putExtra("txtDue",o.getDue_date());
                i.putExtra("txtTeacher",o.getSubject_teacher());
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListLectures.size();
    }

    public class MyClassViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView txtSubject;
        TextView txtClass;
        TextView txtUpload;
        TextView txtDue;
        TextView txtTeacher;
        CardView cardLecture;
        public MyClassViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            txtSubject = itemView.findViewById(R.id.txtSubject);
            txtClass = itemView.findViewById(R.id.txtClass);
            txtUpload = itemView.findViewById(R.id.txtUpload);
            txtDue = itemView.findViewById(R.id.txtDue);
            txtTeacher = itemView.findViewById(R.id.txtTeacher);
            cardLecture = itemView.findViewById(R.id.cardLecture);
        }
    }
}
