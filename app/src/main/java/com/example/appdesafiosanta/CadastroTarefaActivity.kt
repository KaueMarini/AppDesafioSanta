package com.example.appdesafiosanta

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appdesafiosanta.databinding.ActivityCadastroTarefaBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CadastroTarefaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroTarefaBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroTarefaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = Firebase.firestore


        setupStatusDropdown()


        binding.btnCadastrarTarefa.setOnClickListener {
            cadastrarTarefa()
        }
    }

    private fun setupStatusDropdown() {

        val statusOpcoes = resources.getStringArray(R.array.status_tarefas)


        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, statusOpcoes)


        binding.autoCompleteStatus.setAdapter(adapter)
    }

    private fun cadastrarTarefa() {
        val nome = binding.edtNomeTarefa.text.toString().trim()
        val categoria = binding.edtCategoriaTarefa.text.toString().trim()
        val status = binding.autoCompleteStatus.text.toString() // Pega o valor do dropdown

        if (nome.isEmpty() || categoria.isEmpty()) {
            Toast.makeText(this, "Preencha o nome e a categoria!", Toast.LENGTH_SHORT).show()
            return
        }


        val tarefa = hashMapOf(
            "nome" to nome,
            "categoria" to categoria,
            "status" to status
        )


        db.collection("tarefas")
            .add(tarefa)
            .addOnSuccessListener {
                Toast.makeText(this, "Tarefa cadastrada com sucesso!", Toast.LENGTH_SHORT).show()

                binding.edtNomeTarefa.text?.clear()
                binding.edtCategoriaTarefa.text?.clear()
                binding.autoCompleteStatus.setText("Pendente", false)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao cadastrar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}