package com.example.wheelscatalogapp

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), WheelAdapter.OnWheelItemLongClick {

    private val PROFILE_DEBUG = "PROFILE_DEBUG"
    private val REQUEST_CAPTURE_IMAGE = 123
    private val profileVm by viewModels<ProfileViewModel>()
    private val adapter = WheelAdapter(this)
    private fun bindUserData(user:User) {
        Log.d(PROFILE_DEBUG, user.toString())
        imieUser.setText(user.name)
        nazwiskoUser.setText(user.surname)
        emailUser.setText(user.email)
    }
    private fun updateDataClick(){
        updateUser.setOnClickListener {
            val name = imieUser.text.trim().toString()
            val surname = nazwiskoUser.text.trim().toString()
            val map = mapOf("name" to name, "surname" to surname)
            profileVm.editProfileDane(map)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateDataClick()
        recyclerViewFavWheel.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewFavWheel.adapter = adapter
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        profileVm.user.observe(viewLifecycleOwner, { user ->
            bindUserData(user)
        })
        profileVm.favWheel.observe(viewLifecycleOwner, {list ->
            list?.let{
                adapter.setWheel(it)
            }
        })
    }
    override fun onWheelLongClick(wheel: Wheel, position: Int) {
        profileVm.removeFavWheel(wheel)
        adapter.removeWheel(wheel, position)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.homeback, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}

