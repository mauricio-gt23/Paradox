package com.example.paradox.ui.profiles

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.paradox.R
import com.example.paradox.controller.activities.EditProfilePostulant
import com.example.paradox.controller.activities.SharedPreferences
import com.example.paradox.models.PostulantBri
import com.example.paradox.network.PostulantService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SeeProfilePostulantB : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var postulantBri = PostulantBri()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vista: View = inflater.inflate(R.layout.fragment_see_profile_postulant_b, container, false)
        val buttonEdit = vista.findViewById<FloatingActionButton>(R.id.fabEditProfile)
        loadPostulant(vista)
        buttonEdit.setOnClickListener {
            val intent = Intent(context, EditProfilePostulant::class.java)
            intent.putExtra("Postulant", this.postulantBri)
            startActivity(intent)
        }
        return vista
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SeeProfilePostulantB.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SeeProfilePostulantB().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun loadPostulant(view: View) {
        val tvNameShow = view.findViewById<TextView>(R.id.tvNameShow)
        val tvLastNameShow = view.findViewById<TextView>(R.id.tvLastNameShow)
        val tvIdDocShow = view.findViewById<TextView>(R.id.tvIdDocShow)
        val tvCivilStatusShow = view.findViewById<TextView>(R.id.tvCivilStatusShow)
        val tvPhoneShow = view.findViewById<TextView>(R.id.tvPhoneShow)
        val tvEmailShow = view.findViewById<TextView>(R.id.tvEmailShow)
        val ivProfilePhoto = view.findViewById<ImageView>(R.id.ivProfilePhoto)
        val sharedPreferences = context?.let { SharedPreferences(it) }
        var idd: Int = 0
        if (sharedPreferences != null) {
            if(sharedPreferences.getValues("id") != null) {
                idd = sharedPreferences.getValues("id")!!.toInt()
                val request = PostulantService.postulantInstance.getPostulantById(4)
                request.enqueue(object : Callback<PostulantBri> {
                    override fun onResponse(
                        call: Call<PostulantBri>,
                        response: Response<PostulantBri>
                    ) {
                        if (response.isSuccessful) {
                            val postulantRetrieved = response.body()
                            tvNameShow.text = postulantRetrieved!!.firstName
/*                            if (postulantRetrieved.link != null || postulantRetrieved.link != "null") {
                                Glide.with(this@SeeProfilePostulantB).load(postulantRetrieved.link)
                                    .into(ivProfilePhoto)
                            }*/
                            tvLastNameShow.text = postulantRetrieved.lastName
                            tvIdDocShow.text = postulantRetrieved.document
                            tvCivilStatusShow.text = postulantRetrieved.civilStatus
                            tvPhoneShow.text = postulantRetrieved.number.toString()
                            tvEmailShow.text = postulantRetrieved.email
                            postulantBri = PostulantBri(
                                postulantRetrieved.id,
                                postulantRetrieved.firstName,
                                postulantRetrieved.lastName,
                                postulantRetrieved.email,
                                postulantRetrieved.number,
                                postulantRetrieved.password,
                                postulantRetrieved.document,
                                postulantRetrieved.civilStatus,
                                postulantRetrieved.link,
                                postulantRetrieved.other
                            )
                        }
                    }

                    override fun onFailure(call: Call<PostulantBri>, t: Throwable) {
                        Toast.makeText(
                            activity,
                            "We couldn't retrieve postulant, try again please ",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
            }
        }
    }
}