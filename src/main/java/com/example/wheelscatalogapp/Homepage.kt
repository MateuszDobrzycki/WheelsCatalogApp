package com.example.wheelscatalogapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_homepage.*

class Homepage : Fragment(), WheelAdapter.OnWheelItemLongClick {
    private val homeVm by viewModels<HomepageViewModel>()
    private val adapter = WheelAdapter(this)
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_app, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logoutHome ->{
                auth.signOut()
                requireActivity().finish()
            }
        }
        return false
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rview_home.layoutManager = LinearLayoutManager(requireContext())
        rview_home.adapter = adapter
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeVm.wheel.observe(viewLifecycleOwner, {list ->
            adapter.setWheel(list)
        })
    }
    override fun onWheelLongClick(wheel: Wheel, position: Int) {
        homeVm.addFavWheel(wheel)
    }
}

