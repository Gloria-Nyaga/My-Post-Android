package com.nyagagloria.postsapp

import ApiClient
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nyagagloria.postsapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this,1)
        getPosts()


    }

    fun getPosts (){

        val apiInterface = ApiClient.buildApiInterface(PostsApiInterface::class.java)
        val request = apiInterface.getPosts()
        request.enqueue(object :Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful){
                    val posts= response.body()
                    posts?.let {
                        val adapter= PostAdapter(it)
                        binding.recyclerView.adapter = adapter

                    }
                    Toast.makeText(baseContext, "Fetched 100 posts",
                        Toast.LENGTH_LONG).show()
                }   else {
                    Toast.makeText(baseContext, "Failed to fetch posts", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(baseContext, t.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }

        } )

    }
}