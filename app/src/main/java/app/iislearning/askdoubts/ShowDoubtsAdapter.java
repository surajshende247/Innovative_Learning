package app.iislearning.askdoubts;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.iislearning.R;

public class ShowDoubtsAdapter  extends RecyclerView.Adapter<ShowDoubtsAdapter.ShowDoubtsViewHolder> {
    ArrayList<MyDoubt> arrayListDoubt;
    public ShowDoubtsAdapter(ArrayList<MyDoubt> arrayListDoubt) {
        this.arrayListDoubt = arrayListDoubt;
    }
    @NonNull
    @Override
    public ShowDoubtsAdapter.ShowDoubtsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.layout_doubt,parent,false);
        return new ShowDoubtsAdapter.ShowDoubtsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowDoubtsAdapter.ShowDoubtsViewHolder holder, int position) {
        final MyDoubt o = arrayListDoubt.get(position);
        holder.txtTitle.setText(o.getSubject());



        if(o.getStatus().equals("0"))
            holder.txtStatus.setText("Pending");
        else
            holder.txtStatus.setText("Solved");

        holder.cardDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(o.getStatus().equals("1"))
                {
                    Intent intent = new Intent(v.getContext(), ShowResponse.class);
                    intent.putExtra("answer", o.getAnswer());
                    v.getContext().startActivity(intent);
                }
                else
                {
                    Toast.makeText(holder.txtTitle.getContext(),"Query is pending. Please check back soon",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListDoubt.size();
    }

    public class ShowDoubtsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtStatus;
        CardView cardDoubt;
        public ShowDoubtsViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            cardDoubt = itemView.findViewById(R.id.cardDoubt);
        }
    }
}
