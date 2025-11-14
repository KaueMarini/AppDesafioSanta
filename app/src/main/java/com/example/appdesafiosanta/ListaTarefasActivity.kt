package com.example.appdesafiosanta

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdesafiosanta.databinding.ActivityListaTarefasBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ListaTarefasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaTarefasBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var tarefasAdapter: TarefasAdapter
    private val listaDeTarefas = mutableListOf<Tarefa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListaTarefasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = Firebase.firestore


        setupRecyclerView()
        fetchTarefas()
    }

    private fun setupRecyclerView() {

        tarefasAdapter = TarefasAdapter(listaDeTarefas)


        binding.recyclerViewTarefas.apply {
            adapter = tarefasAdapter
            layoutManager = LinearLayoutManager(this@ListaTarefasActivity)
        }
    }

    private fun fetchTarefas() {

        db.collection("tarefas")
            .orderBy("nome", Query.Direction.ASCENDING) // Opcional: ordenar por nome
            .addSnapshotListener { snapshot, e ->


                if (e != null) {
                    Log.w("ListaTarefas", "Listen failed.", e)
                    Toast.makeText(this, "Erro ao carregar tarefas.", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }


                if (snapshot != null) {

                    listaDeTarefas.clear()


                    for (document in snapshot.documents) {

                        val tarefa = Tarefa(
                            id = document.id,
                            nome = document.getString("nome"),
                            categoria = document.getString("categoria"),
                            status = document.getString("status")
                        )

                        listaDeTarefas.add(tarefa)
                    }


                    tarefasAdapter.notifyDataSetChanged()
                }
            }
    }
}