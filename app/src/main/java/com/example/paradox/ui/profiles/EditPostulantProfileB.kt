package com.example.paradox.ui.profiles

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.paradox.NavigationPostulantActivity
import com.example.paradox.R
import com.example.paradox.databinding.FragmentEditProfProfileBinding
import com.example.paradox.databinding.FragmentFragmentaEditPostulantProfileBinding
import com.example.paradox.models.PostulantBri
import com.example.paradox.network.PostulantService
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPostulantProfileB : Fragment() {

    private lateinit var binding: FragmentFragmentaEditPostulantProfileBinding
    var postulantBri = PostulantBri()
    private val File = 1
    private val database = Firebase.database
    val myRef = database.getReference("user")
    private var prueba = "prueba"

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
        binding.btBack2.setOnClickListener {
            val fragment = SeeProfilePostulantB()
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_content_navigation_postulant, fragment)?.commit()
        }
        binding.etChangePhotoPostulant.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            Log.d("intentbri", intent.toString())
            startActivityForResult(intent, File)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == File) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val FileUri = data!!.data
                Log.d("firebase", FileUri.toString())
                val Folder: StorageReference =
                    FirebaseStorage.getInstance().getReference().child("User")
                val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                    file_name.getDownloadUrl().addOnSuccessListener { uri ->
                        val hashMap =
                            HashMap<String, String>()
                        hashMap["link"] = java.lang.String.valueOf(uri)
                        myRef.setValue(hashMap)
                        prueba = uri.toString()
                        if(uri.toString().isNotEmpty()){
                            Toast.makeText(context, "Se subio el archivo correctamente!", Toast.LENGTH_LONG).show()
                        }
                        Log.d("Mensaje", uri.toString())
                    }
                }
            }
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
        val etProfPassword = view.findViewById<EditText>(R.id.etProfPassword)
        val ivProfilePhotoEdir = view.findViewById<ImageView>(R.id.ivProfilePhotoEdir)

        etNameProfProfile.setText(postulantBri.firstName)
        etLastNameProfProfile.setText(postulantBri.lastName)
        etPhoneProfProfile.setText(postulantBri.document)
        etCivilStatusProfile.setText(postulantBri.civilStatus)
        etPhoneProfProfileEdit.setText(postulantBri.number.toString())
        etProfEmail.setText(postulantBri.email)
        etProfPassword.setText(postulantBri.password)
        context?.let { Glide.with(it).load(postulantBri.link)
            .transform(CenterCrop(), RoundedCorners(20))
            .into(ivProfilePhotoEdir) }

    }
}