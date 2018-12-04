package com.yourcompany.bro.hi.wap


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log


import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.yourcompany.bro.hi.wap.common.Common

class MainActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var coordinatorLayout: CoordinatorLayout? = null

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coordinatorLayout = findViewById(R.id.coordinator)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                        if (report.areAllPermissionsGranted()) {

                            buildLocationRequest()
                            buildLocationCallBack()

                            if (ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                return
                            }
                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
                            fusedLocationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback!!, Looper.myLooper())

                        }

                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        Snackbar.make(coordinatorLayout!!, "Permission denied", Snackbar.LENGTH_LONG).show()
                    }
                }).check()


    }


    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {

            @SuppressLint("WrongViewCast")
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                Common.current_Location = locationResult!!.lastLocation

                viewPager = findViewById(R.id.viewPager)
                setViewPager(viewPager!!)

                tabLayout = findViewById(R.id.tabLayout)
                tabLayout!!.setupWithViewPager(viewPager)

                Log.d("Location", locationResult.lastLocation.latitude.toString() + "/" + locationResult.lastLocation.longitude)
            }
        }


    }

    private fun setViewPager(viewPager: ViewPager) {
        val viewPagerAdatper = ViewPagerAdatper(supportFragmentManager)
        viewPagerAdatper.addFragment(TodayFragment.getInstanse(), "Today")
        viewPagerAdatper.addFragment(ForecastFragment.getInstanse(), "5 DAYS")
        viewPager.adapter = viewPagerAdatper
    }


    private fun buildLocationRequest() {

        locationRequest = LocationRequest()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest!!.interval = 5000
        locationRequest!!.fastestInterval = 3000
        locationRequest!!.smallestDisplacement = 10.0f


    }
}
