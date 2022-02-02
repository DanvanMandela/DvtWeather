package com.example.dvtweather.view.fragment.favourite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dvtweather.R
import com.example.dvtweather.util.callback.BaseView
import com.example.dvtweather.view.activity.MainActivity
import com.example.dvtweather.view.adapter.FavoriteRecyclerViewMain
import com.example.dvtweather.view.fragment.weather.WeatherFragment
import com.example.dvtweather.view.viewmodel.FavoriteViewModel
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FavouriteFragment : Fragment(), BaseView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var toolbar: MaterialToolbar? = null
    private var container: RecyclerView? = null
    private var adapter: FavoriteRecyclerViewMain? = null
    private var noFavLocation: RelativeLayout? = null

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private var subscription: CompositeDisposable? = null

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
    ): View? {
        // Inflate the layout for this fragment
        val views = inflater.inflate(R.layout.fragment_favourite, container, false)
        onFindViews(views)
        setToolbar()
        setAdapter()
        setRecyclerView()
        onBindViewModel()
        return views
    }

    private fun setAdapter() {
        adapter = FavoriteRecyclerViewMain(mutableListOf(), requireActivity())
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        container?.layoutManager = layoutManager
        container?.adapter = adapter
    }

    private fun setToolbar() {
        toolbar?.setNavigationOnClickListener {
            (requireActivity() as MainActivity).provideNavigationGraph().navigateUp()
        }
    }

    companion object {
        private val TAG: String = WeatherFragment::class.java.simpleName

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavouriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onFindViews(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        container = view.findViewById(R.id.container)
        noFavLocation = view.findViewById(R.id.noFavLocation)
    }

    override fun onBindViewModel() {
        subscription = CompositeDisposable()
        subscription!!.add(
            favoriteViewModel.getFavoriteItem()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        noFavLocation?.visibility =
                            if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
                        it.isNotEmpty()
                        adapter?.replaceData(it.toMutableList())

                    }
                )
                { error: Throwable? ->
                    Log.d(TAG, "Error loading favorite")
                    error?.printStackTrace()
                })
    }
}