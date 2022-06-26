package com.example.paradox.ui.profiles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.paradox.R
import com.example.paradox.controller.activities.EditProfilePostulant
import com.example.paradox.controller.activities.SharedPreferences
import com.example.paradox.databinding.FragmentSeeProfProfileBBinding
import com.example.paradox.databinding.FragmentSeeProfilePostulantBBinding
import com.example.paradox.models.PostulantBri
import com.example.paradox.network.PostulantService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SeeProfilePostulantB : Fragment() {
    private lateinit var binding: FragmentSeeProfilePostulantBBinding
    private var postulantBri = PostulantBri()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_see_profile_postulant_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSeeProfilePostulantBBinding.bind(view)
        loadPostulant(view)
        binding.btGoToEditProfile.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("Postulant", this.postulantBri)
            val fragment = EditPostulantProfileB()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_postulant, fragment)?.commit()
        }
        binding.btGoToProfProfile.setOnClickListener{
            val fragment = SeeProfProfileB()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_postulant, fragment)?.commit()
        }
    }

    companion object {

    }

    fun loadPostulant(view: View) {
        val tvNameShow = view.findViewById<TextView>(R.id.tvNameShow)
        val tvLastNameShow = view.findViewById<TextView>(R.id.tvLastNameShow)
        val tvIdDocShow = view.findViewById<TextView>(R.id.tvIdDocShow)
        val tvCivilStatusShow = view.findViewById<TextView>(R.id.tvCivilStatusShow)
        val tvPhoneShow = view.findViewById<TextView>(R.id.tvPhoneShow)
        val tvEmailShow = view.findViewById<TextView>(R.id.tvEmailShow)
        val tvPasswordShow = view.findViewById<TextView>(R.id.tvPasswordShow)
        val ivProfilePhoto = view.findViewById<ImageView>(R.id.ivProfilePhoto)
        val sharedPreferences = context?.let { SharedPreferences(it) }
        var idd: Int = 0
        if (sharedPreferences != null) {
            if(sharedPreferences.getValues("id") != null) {
                idd = sharedPreferences.getValues("id")!!.toInt()
                Log.d("Brig", idd.toString());
                val request = PostulantService.postulantInstance.getPostulantById(idd)
                request.enqueue(object : Callback<PostulantBri> {
                    override fun onResponse(
                        call: Call<PostulantBri?>,
                        response: Response<PostulantBri?>
                    ) {
                        if (response.isSuccessful) {
                            val postulantRetrieved = response.body()
                            tvNameShow.text = postulantRetrieved!!.firstName
                            if (postulantRetrieved.link != null || postulantRetrieved.link != "string") {
                                val result = postulantRetrieved.link.startsWith("https:")
                                if (result) {
                                    Glide.with(this@SeeProfilePostulantB).load(postulantRetrieved.link)
                                        .apply(RequestOptions.bitmapTransform( RoundedCorners(40)))
                                        .into(ivProfilePhoto)
                                } else {
                                    Glide.with(this@SeeProfilePostulantB).load("https://i.stack.imgur.com/34AD2.jpg")
                                        .into(ivProfilePhoto)
                                }
                            }
                            tvLastNameShow.text = postulantRetrieved.lastName
                            tvIdDocShow.text = postulantRetrieved.document
                            tvCivilStatusShow.text = postulantRetrieved.civilStatus
                            tvPhoneShow.text = postulantRetrieved.number.toString()
                            tvEmailShow.text = postulantRetrieved.email
                            tvPasswordShow.text = postulantRetrieved.password
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