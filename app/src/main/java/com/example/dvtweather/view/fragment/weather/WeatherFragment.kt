package com.example.dvtweather.view.fragment.weather

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dvtweather.R
import com.example.dvtweather.data.entity.weather.WeatherItem
import com.example.dvtweather.util.AspectRatioImageView
import com.example.dvtweather.util.ShowToast
import com.example.dvtweather.util.callback.BaseView
import com.example.dvtweather.view.activity.MainActivity
import com.example.dvtweather.view.adapter.ForecastRecyclerAdapter
import com.example.dvtweather.view.viewmodel.FavoriteViewModel
import com.example.dvtweather.view.viewmodel.WeatherViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class WeatherFragment : Fragment(), BaseView, View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private var subscription: CompositeDisposable? = null
    private var weatherTemp: TextView? = null
    private var weatherDesc: TextView? = null
    private var weatherAvatar: AspectRatioImageView? = null
    private var minTemp: TextView? = null
    private var currentTemp: TextView? = null
    private var maxTemp: TextView? = null
    private var currentItem: LinearLayout? = null
    private var container: RecyclerView? = null

    private var toolbar: MaterialToolbar? = null

    private var lastUpdate: TextView? = null

    private var noData: RelativeLayout? = null
    private var mainLay: LinearLayout? = null
    private var manageBtn: MaterialButton? = null
    private var weatherItem: WeatherItem? = null

    private var adapter: ForecastRecyclerAdapter? = null
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
        val views = inflater.inflate(R.layout.fragment_weather, container, false)
        onFindViews(views)
        setLocation()
        setToolbar()
        setClick()
        setAdapter()
        setRecyclerView()
        Handler(Looper.getMainLooper()).postDelayed({
            onBindViewModel()
        }, 500)
        getFavorite()
        return views
    }

    private fun getFavorite() {
        subscription = CompositeDisposable()
        subscription!!.add(
            favoriteViewModel.observeFavorite()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        (requireActivity() as MainActivity).setFavorite(dataList = it.toMutableList())
                    }
                )
                { error: Throwable? ->
                    Log.d(TAG, "Error loading favorite")
                    error?.printStackTrace()
                })
    }

    private fun setClick() {
        lastUpdate?.setOnClickListener(this)
        manageBtn?.setOnClickListener(this)
    }

    private fun setLocation() {
        (requireActivity() as MainActivity).checkLocationPermission()
    }

    private fun setToolbar() {
        toolbar?.apply {
            setOnMenuItemClickListener { item ->
                when (item!!.itemId) {
                    R.id.actionFavorite -> {
                        if (weatherItem != null)
                            addFavourite()
                        true
                    }

                    else -> false
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            (requireActivity() as MainActivity).setNavigationDrawer(toolbar!!)
        }, 250)
    }

    private fun addFavourite() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                if (favoriteViewModel.checkExist(weatherItem!!.name)) {
                    ShowToast(
                        requireContext(),
                        "${weatherItem!!.name} ${getString(R.string.already_added)}"
                    )
                } else {
                    favoriteViewModel.saveFavorite(weatherItem!!)
                    ShowToast(
                        requireContext(),
                        "${weatherItem!!.name} ${getString(R.string.location_added)}"
                    )
                }
            }
        }
    }

    private fun setAdapter() {
        adapter = ForecastRecyclerAdapter(mutableListOf(), requireContext())
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        container?.layoutManager = layoutManager
        container?.adapter = adapter
    }

    companion object {
        private val TAG: String = WeatherFragment::class.java.simpleName

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onFindViews(view: View) {
        weatherTemp = view.findViewById(R.id.weatherTemp)
        weatherDesc = view.findViewById(R.id.weatherDesc)
        weatherAvatar = view.findViewById(R.id.weatherAvatar)
        minTemp = view.findViewById(R.id.minTemp)
        currentTemp = view.findViewById(R.id.currentTemp)
        maxTemp = view.findViewById(R.id.maxTemp)
        currentItem = view.findViewById(R.id.currentItem)
        container = view.findViewById(R.id.container)
        toolbar = view.findViewById(R.id.toolbar)
        lastUpdate = view.findViewById(R.id.lastUpdate)
        noData = view.findViewById(R.id.noData)
        mainLay = view.findViewById(R.id.mainLay)
        manageBtn = view.findViewById(R.id.manageBtn)

    }

    override fun onBindViewModel() {
        getCurrentWeather()
        getWeatherForecast()
    }

    private fun getWeatherForecast() {
        subscription = CompositeDisposable()
        subscription!!.add(
            weatherViewModel.getObservableForecast()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        noData?.visibility = if (it.count() > 0) View.GONE else View.VISIBLE
                        adapter?.replaceData(it.toMutableList())
                    }
                )
                { error: Throwable? ->
                    Log.d(TAG, "Error loading weather")
                    error?.printStackTrace()
                })
    }

    private fun getCurrentWeather() {
        subscription = CompositeDisposable()
        subscription!!.add(
            weatherViewModel.getObservableWeather()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {

                        if (it != null)
                            setViews(it)
                    }
                )
                { error: Throwable? ->
                    Log.d(TAG, "Error loading weather")
                    error?.printStackTrace()
                })


    }

    private fun setViews(t: WeatherItem) {
        mainLay?.visibility = View.VISIBLE
        this.weatherItem = t
        val color = ContextCompat.getColor(requireContext(), t.color)
        updateStatusBar(color)
        weatherTemp?.text = t.current
        weatherDesc?.text = getString(t.desc)
        Glide.with(requireContext())
            .load(ContextCompat.getDrawable(requireContext(), t.avatar))
            .into(weatherAvatar!!)
        minTemp?.text = t.min
        maxTemp?.text = t.max
        currentTemp?.text = t.current
        currentItem?.setBackgroundColor(color)
        container?.setBackgroundColor(color)
        lastUpdate?.text = t.updated
        (requireActivity() as MainActivity).setNavData(weatherItem = t)
    }

    private fun updateStatusBar(color: Int) {
        val window = activity!!.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    override fun onClick(v: View?) {
        when (v) {
            lastUpdate -> {
                setLocation()
            }
            manageBtn -> {
                setLocation()
            }
        }
    }

}