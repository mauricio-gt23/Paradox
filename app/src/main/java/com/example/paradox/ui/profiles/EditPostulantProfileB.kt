package com.example.paradox.ui.profiles

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.paradox.NavigationPostulantActivity
import com.example.paradox.R
import com.example.paradox.databinding.FragmentEditProfProfileBinding
import com.example.paradox.databinding.FragmentFragmentaEditPostulantProfileBinding
import com.example.paradox.models.PostulantBri
import com.example.paradox.network.PostulantService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPostulantProfileB : Fragment() {

    private lateinit var binding: FragmentFragmentaEditPostulantProfileBinding
    var postulantBri = PostulantBri()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postulantBri = it.getParcelable("Postulant")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_fragmenta_edit_postulant_profile,
            container,
            false
        )
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditPostulantProfileB().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFragmentaEditPostulantProfileBinding.bind(view)
        loadData(view)
        binding.btSaveEdit.setOnClickListener{
            saveEditedPostulant(view)
        }
    }

    private fun saveEditedPostulant(view: View) {
        val etNameProfProfile = view.findViewById<EditText>(R.id.etNameProfProfile)
        val etLastNameProfProfile = view.findViewById<EditText>(R.id.etLastNameProfProfile)
        val etPhoneProfProfile = view.findViewById<EditText>(R.id.etPhoneProfProfile)
        val etCivilStatusProfile = view.findViewById<EditText>(R.id.etCivilStatusProfile)
        val etPhoneProfProfileEdit = view.findViewById<EditText>(R.id.etPhoneProfProfileEdit)
        val etProfEmail = view.findViewById<EditText>(R.id.etProfEmail)

        val postulantBriEdited = PostulantBri(postulantBri.id, etNameProfProfile.text.toString(),
            etLastNameProfProfile.text.toString(), etProfEmail.text.toString(),
            etPhoneProfProfileEdit.text.toString().toInt(), postulantBri.password,etPhoneProfProfile.text.toString(),
            etCivilStatusProfile.text.toString(), postulantBri.link, postulantBri.other)
        val request = PostulantService.postulantInstance.editPostulant(postulantBri.id, postulantBriEdited)
        request.enqueue(object : Callback<PostulantBri> {
            override fun onResponse(call: Call<PostulantBri>, response: Response<PostulantBri>) {
                if (response.isSuccessful){
                    val editedPostulant = response.body()
                    if (editedPostulant != null) {
                        Toast.makeText(context, "Successfully updated", Toast.LENGTH_LONG).show()
                        Handler().postDelayed({
                            val intent = Intent(context, NavigationPostulantActivity::class.java)
                            startActivity(intent)
                        }, 1700)
                    }

                }
            }

            override fun onFailure(call: Call<PostulantBri>, t: Throwable) {
                Toast.makeText(context, "There was a problem updating the info ", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun loadData(view: View) {

        val etNameProfProfile = view.findViewById<EditText>(R.id.etNameProfProfile)
        val etLastNameProfProfile = view.findViewById<EditText>(R.id.etLastNameProfProfile)
        val etPhoneProfProfile = view.findViewById<EditText>(R.id.etPhoneProfProfile)
        val etCivilStatusProfile = view.findViewById<EditText>(R.id.etCivilStatusProfile)
        val etPhoneProfProfileEdit = view.findViewById<EditText>(R.id.etPhoneProfProfileEdit)
        val etProfEmail = view.findViewById<EditText>(R.id.etProfEmail)
        val ivProfilePhotoEdir = view.findViewById<ImageView>(R.id.ivProfilePhotoEdir)

        etNameProfProfile.setText(postulantBri.firstName)
        etLastNameProfProfile.setText(postulantBri.lastName)
        etPhoneProfProfile.setText(postulantBri.document)
        etCivilStatusProfile.setText(postulantBri.civilStatus)
        etPhoneProfProfileEdit.setText(postulantBri.number.toString())
        etProfEmail.setText(postulantBri.email)
        context?.let { Glide.with(it).load(postulantBri.link).apply(RequestOptions.bitmapTransform( RoundedCorners(18))).into(ivProfilePhotoEdir) }

    }
}