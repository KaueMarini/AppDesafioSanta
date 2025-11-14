package com.example.appdesafiosanta

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appdesafiosanta.databinding.ItemTarefaBinding

class TarefasAdapter(
    private val tarefas: List<Tarefa>
) : RecyclerView.Adapter<TarefasAdapter.TarefaViewHolder>() {


    inner class TarefaViewHolder(val binding: ItemTarefaBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {

        val binding = ItemTarefaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TarefaViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = tarefas[position]

        // Define os textos nas TextViews
        holder.binding.tvNomeTarefa.text = tarefa.nome
        holder.binding.tvCategoriaTarefa.text = tarefa.categoria
        holder.binding.tvStatusTarefa.text = tarefa.status
    }


    override fun getItemCount(): Int {
        return tarefas.size
    }
}