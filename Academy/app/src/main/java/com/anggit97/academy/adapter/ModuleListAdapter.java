package com.anggit97.academy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anggit97.academy.R;
import com.anggit97.academy.data.source.local.entity.ModuleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
public class ModuleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ModuleListAdapterListener listener;
    private List<ModuleEntity> modules = new ArrayList<>();

    public ModuleListAdapter(ModuleListAdapterListener listener) {
        this.listener = listener;
    }

    public void setModules(List<ModuleEntity> modules) {
        if (modules == null) return;
        this.modules.clear();
        this.modules.addAll(modules);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ModuleViewHolderHide(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_module_list_custom_disable, parent, false));
        } else {
            return new ModuleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_module_list_custom, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {
        ModuleEntity module = modules.get(position);
        if (viewHolder.getItemViewType() == 0) {
            ModuleViewHolderHide moduleViewHolderHide = (ModuleViewHolderHide) viewHolder;
            moduleViewHolderHide.bind(module.getTitle());
        } else {
            ModuleViewHolder moduleViewHolder = (ModuleViewHolder) viewHolder;
            moduleViewHolder.bind(module.getTitle());
            moduleViewHolder.itemView.setOnClickListener(v -> {
                listener.onItemClicked(viewHolder.getAdapterPosition(), modules.get(moduleViewHolder.getAdapterPosition()).getModuleId());
            });
        }
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }


    @Override
    public int getItemViewType(int position) {

        int modulePosition = modules.get(position).getPosition();
        if (modulePosition == 0) return 1;
        else if (modules.get(modulePosition - 1).getRead()) return 1;
        else return 0;

    }

    class ModuleViewHolder extends RecyclerView.ViewHolder {
        final TextView textTitle;
        final TextView textLastSeen;

        ModuleViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_module_title);
            textLastSeen = itemView.findViewById(R.id.text_last_seen);
        }

        void bind(String title) {
            textTitle.setText(title);
        }
    }

    class ModuleViewHolderHide extends RecyclerView.ViewHolder {
        final TextView textTitle;

        ModuleViewHolderHide(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_module_title);
        }

        void bind(String title) {
            textTitle.setText(title);
        }
    }
}

interface MyAdapterClickListener {
    void onItemClicked(int position, String moduleId);
}
