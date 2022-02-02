package com.example.dvtweather.view.activity

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import com.example.dvtweather.R
import com.example.dvtweather.data.entity.favourite.FavoriteEntity
import com.example.dvtweather.util.ShowToast
import com.example.dvtweather.util.callback.BaseView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapsFragment : BottomSheetDialogFragment(), OnMapReadyCallback, BaseView {
    private var googleMap: GoogleMap? = null
    private val options = MarkerOptions()
    private var data: FavoriteEntity? = null
    private var toolbar: MaterialToolbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable("data")
        }


    }

    companion object {
        private val TAG: String = MapsFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() =
            MapsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views = inflater.inflate(R.layout.fragment_maps, container, false)
        onFindViews(views)
        setToolbar()
        return views
    }

    private fun setToolbar() {
        toolbar?.apply {
            setNavigationOnClickListener {
                (requireActivity() as MainActivity).provideNavigationGraph().navigateUp()
            }
            if (data != null) title = "${data!!.name} ${data!!.temp} ${getString(data!!.weather)}"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { data ->
                val behaviour = BottomSheetBehavior.from(data)
                setupFullHeight(data)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.isDraggable = false
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun moveCamera(latLng: LatLng, zoom: Float, title: String) {
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
        val options = MarkerOptions()
            .position(latLng)
            .title(title)
        googleMap!!.addMarker(options)
    }

    override fun onMapReady(gMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            googleMap = gMap
            if (data != null) {
                moveCamera(
                    LatLng(data!!.coordinate.latitude, data!!.coordinate.longitude),
                    15f,
                    data!!.name
                )
            }


            // googleMap!!.isMyLocationEnabled = true
            googleMap!!.uiSettings.isMyLocationButtonEnabled = false
        } else {
            ShowToast(requireContext(), getString(R.string.enable_location))
        }

    }

    override fun onFindViews(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
    }

    override fun onBindViewModel() {

    }


}