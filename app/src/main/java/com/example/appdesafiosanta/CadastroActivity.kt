package com.example.appdesafiosanta

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = Firebase.firestore


        binding.btnCadastrar.setOnClickListener {
            val nome = binding.edtNome.text.toString().trim()
            val preco = binding.edtPreco.text.toString().trim()
            val quantidade = binding.edtQuantidade.text.toString().trim()

            if (nome.isEmpty() || preco.isEmpty() || quantidade.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val produto = hashMapOf(
                "nome" to nome,
                "preco" to preco.toDouble(),
                "quantidade" to quantidade.toInt()
            )

            db.collection("produtos")
                .add(produto)
                .addOnSuccessListener {
                    Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    binding.edtNome.text?.clear()
                    binding.edtPreco.text?.clear()
                    binding.edtQuantidade.text?.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao cadastrar produto.", Toast.LENGTH_SHORT).show()
                }
        }


        binding.fabLista.setOnClickListener {
            Toast.makeText(this, "Abrir lista de produtos (a implementar)", Toast.LENGTH_SHORT).show()
        }
    }
}