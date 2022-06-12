package com.example.paradox.ui.postulantprofilem

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.paradox.R
import com.example.paradox.ui.publishedworksm.PublishedWorksFragment
import com.example.paradox.ui.watchannouncementm.WatchAnnouncementFragment
import com.google.gson.Gson

class PostulantProfileFragment : Fragment() {

    private var firstname: String = ""
    private var lastname: String = ""
    private var document: String = ""
    private var number: Int = 0
    private var email: String = ""
    private var publishedWorkId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //
            firstname = it.getString("firstname").toString()
            lastname = it.getString("lastname").toString()
            document = it.getString("document").toString()
            number = it.getInt("number")
            email = it.getString("email").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_postulant_profile, container, false)

        loadPostulantProfile(vista)

        val btBack3: ImageButton = vista.findViewById<ImageButton>(R.id.btBack3)

        btBack3.setOnClickListener {
            val bundle = Bundle()
            val fragment = WatchAnnouncementFragment()
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment_content_navigation_employeer, fragment)?.commit()
        }
        return vista
    }

    private fun loadPostulantProfile(view: View) {
        Log.d("AAA", publishedWorkId.toString())
        view.findViewById<TextView>(R.id.tvNamePos).text = firstname
        view.findViewById<TextView>(R.id.tvLastPos).text = lastname
        view.findViewById<TextView>(R.id.tvDocument).text = document
        view.findViewById<TextView>(R.id.tvNum).text = number.toString()
        view.findViewById<TextView>(R.id.tvGmail).text = email
    }
}