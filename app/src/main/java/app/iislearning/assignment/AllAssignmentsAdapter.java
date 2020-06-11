package app.iislearning.assignment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.iislearning.R;

public class AllAssignmentsAdapter extends RecyclerView.Adapter<AllAssignmentsAdapter.AllAssignmentsAdapterViewHolder> {
    ArrayList<MyAssign> arrayListMyAssign;
    public AllAssignmentsAdapter(ArrayList<MyAssign> arrayListMyAssign) {
        this.arrayListMyAssign = arrayListMyAssign;
    }

    @NonNull
    @Override
    public AllAssignmentsAdapter.AllAssignmentsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.layout_assignment,parent,false);
        return new AllAssignmentsAdapter.AllAssignmentsAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAssignmentsAdapter.AllAssignmentsAdapterViewHolder holder, int position) {
        final MyAssign o = arrayListMyAssign.get(position);
        holder.txtTitle.setText(o.getAssignment_title());
        holder.txtSubject.setText(o.getSubject());

        if(o.getMarks_obtained().equals("NA")){
            holder.txtMarks.setText("Not Evaluated");
        }
        else{
            holder.txtMarks.setText(o.getMarks_obtained() + "/" + o.getTotal_marks());
        }


        holder.txtDate.setText(o.getAssignment_date());
        if(o.getUpload_status().equals("1"))
        {
            holder.btnUpload.setText("My Submission");
            holder.btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),ViewSubmission.class);
                    i.putExtra("imgurl",o.getStdimg_url());
                    i.putExtra("feedback",o.getFeedback());
                    v.getContext().startActivity(i);
                }
            });
        }
        else
        {
            holder.btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),UploadAssignment.class);
                    i.putExtra("id",o.getId());
                    v.getContext().startActivity(i);
                }
            });
        }

        holder.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),AssignmentDetail.class);
                intent.putExtra("title",o.getAssignment_title());
                intent.putExtra("img",o.getTeacherimg_url());
                intent.putExtra("instructions",o.getInstructions());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayListMyAssign.size();
    }

    public class AllAssignmentsAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtSubject;
        TextView txtMarks;
        TextView txtDate;
        Button btnUpload;
        Button btnShow;


        public AllAssignmentsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtSubject = itemView.findViewById(R.id.txtSubject);
            txtMarks = itemView.findViewById(R.id.txtMarks);
            txtDate = itemView.findViewById(R.id.txtDate);
            btnUpload =itemView.findViewById(R.id.btnUpload);
            btnShow = itemView.findViewById(R.id.btnShow);
        }
    }
}
