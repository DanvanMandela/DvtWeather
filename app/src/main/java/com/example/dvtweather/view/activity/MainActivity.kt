package com.example.dvtweather.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dvtweather.R
import com.example.dvtweather.data.entity.favourite.FavoriteEntity
import com.example.dvtweather.data.entity.weather.WeatherBody
import com.example.dvtweather.data.entity.weather.WeatherItem
import com.example.dvtweather.data.source.constants.Constants.MapsConstants
import com.example.dvtweather.util.AspectRatioImageView
import com.example.dvtweather.util.callback.RefreshData
import com.example.dvtweather.view.adapter.FavoriteRecyclerAdapter
import com.example.dvtweather.view.viewmodel.FavoriteViewModel
import com.example.dvtweather.view.viewmodel.WorkViewModel
import com.google.android.gms.location.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener,
    View.OnClickListener {
    private var navController: NavController? = null
    lateinit var drawerLayout: DrawerLayout
    private val workViewModel: WorkViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private var fusedLocationProvider: FusedLocationProviderClient? = null
    private var locationTemp: TextView? = null
    private var locationIcon: AspectRatioImageView? = null
    private var currentLocation: TextView? = null
    private var navigationLayout: LinearLayout? = null
    private var containerFav: RecyclerView? = null
    private var adapter: FavoriteRecyclerAdapter? = null
    private var manageBtn: MaterialButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        setNavigation()
        setAdapter()
        setRecyclerView()
        setClicks()
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun setClicks() {
        manageBtn?.setOnClickListener(this)
    }

    private fun setAdapter() {
        adapter = FavoriteRecyclerAdapter(mutableListOf(), this)
    }

    private fun findViews() {
        drawerLayout = findViewById(R.id.drawer_layout)
        locationTemp = findViewById(R.id.locationTemp)
        locationIcon = findViewById(R.id.locationIcon)
        currentLocation = findViewById(R.id.currentLocation)
        navigationLayout = findViewById(R.id.navigation_layout)
        containerFav = findViewById(R.id.containerFav)
        manageBtn = findViewById(R.id.manageBtn)
    }

    fun setNavData(weatherItem: WeatherItem) {
        navigationLayout!!.setBackgroundColor(ContextCompat.getColor(this, weatherItem.color))
        currentLocation?.text = weatherItem.name
        locationTemp?.text = weatherItem.current
        Glide.with(this).load(ContextCompat.getDrawable(this, weatherItem.icon))
            .into(locationIcon!!)
    }

    fun setFavorite(dataList: MutableList<FavoriteEntity>) {
        adapter?.replaceData(dataList)
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        containerFav?.layoutManager = layoutManager
        containerFav?.adapter = adapter
    }

    private fun setNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        if (navHostFragment != null) {
            navController = navHostFragment.navController
            navController?.addOnDestinationChangedListener(this)
        }
    }

    fun provideNavigationGraph(): NavController {
        return navController!!
    }


    fun setNavigationDrawer(toolbar: MaterialToolbar) {
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.fragment_weather) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
                ) {
                    override fun onDrawerClosed(drawerView: View) {
                        super.onDrawerClosed(drawerView)

                        try {
                            val inputMethodManager =
                                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                        } catch (e: Exception) {
                            e.stackTrace
                        }
                    }

                    override fun onDrawerOpened(drawerView: View) {
                        super.onDrawerOpened(drawerView)
                        super.onDrawerOpened(drawerView)

                        try {
                            val inputMethodManager =
                                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(
                                currentFocus!!.windowToken,
                                0
                            )
                        } catch (e: Exception) {
                            e.stackTrace
                        }
                    }
                }
                toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)
                drawerLayout.addDrawerListener(toggle)

                toggle.syncState()
            }
        }
    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }

    companion object {
        private const val REQUEST_LOCATION = 100
        private const val REQUEST_LOCATION_BACKGROUND = 101
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Location Permission Needed")
        builder.setMessage("This app needs  Location permission, please accept to use location functionality")
        builder.setPositiveButton(
            "Ok"
        ) { dialog, _ ->
            requestLocationPermission()
            dialog.cancel()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }


    @SuppressLint("ServiceCast")
    fun checkLocationPermission() {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showGpsAlert(this)
            return
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                showSettingsDialog()
            } else {
                requestLocationPermission()
            }
        } else {
            getCurrentLocation()
        }
    }


    private fun checkBackgroundLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestBackgroundLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            REQUEST_LOCATION
        )
    }

    private fun requestBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                REQUEST_LOCATION_BACKGROUND
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        getCurrentLocation()
                    }

                } else {

                    Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show()

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this.packageName, null),
                            ),
                        )
                    }
                }
                return
            }
            REQUEST_LOCATION_BACKGROUND -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(this, "back ground location granted", Toast.LENGTH_LONG)
                            .show()

                    }
                } else {
                    Toast.makeText(this, "location permission denied", Toast.LENGTH_LONG).show()
                }
                return

            }
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationRequest: LocationRequest = LocationRequest.create().apply {
                interval = MapsConstants.interval
                fastestInterval = MapsConstants.fastestInterval
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                maxWaitTime = MapsConstants.maxWaitTime
            }
            val locationCallback: LocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val locationList = locationResult.locations
                    if (locationList.isNotEmpty()) {
                        val location = locationList.last()
                        val body =
                            WeatherBody(lat = location.latitude, lon = location.longitude)
                        workViewModel.getWeather(body)
                    }
                }
            }
            fusedLocationProvider?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun showGpsAlert(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Device location is disabled. Do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    override fun onClick(v: View?) {
        when (v) {
            manageBtn -> {
                navigateToFavorite()
            }
        }

    }

    private fun navigateToFavorite() {
        drawerLayout.closeDrawers()
        Handler(Looper.getMainLooper()).postDelayed({
            provideNavigationGraph().navigate(favoriteViewModel.getNavigateToFavorite())
        }, 200)
    }

}