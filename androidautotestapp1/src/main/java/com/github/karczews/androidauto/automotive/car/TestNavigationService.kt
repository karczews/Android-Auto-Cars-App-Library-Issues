package com.github.karczews.androidauto.automotive.car

import android.content.Intent
import android.os.Handler
import androidx.car.app.CarAppService
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.car.app.model.*
import androidx.car.app.navigation.NavigationManager
import androidx.car.app.navigation.NavigationManagerCallback
import androidx.car.app.navigation.model.Destination
import androidx.car.app.navigation.model.Step
import androidx.car.app.navigation.model.TravelEstimate
import androidx.car.app.navigation.model.Trip
import androidx.car.app.validation.HostValidator
import java.util.*
import java.util.concurrent.TimeUnit

class TestNavigationService : CarAppService() {

    override fun createHostValidator(): HostValidator = HostValidator.ALLOW_ALL_HOSTS_VALIDATOR

    override fun onCreateSession(): Session = object : Session() {
        override fun onCreateScreen(intent: Intent): Screen = TestScreen(carContext)
    }
}

val distance1 = Distance.create(1.1, Distance.UNIT_KILOMETERS)
val time1 =  // Arrival time at the destination with the destination time zone.
    DateTimeWithZone.create(
        System.currentTimeMillis() + 1000,
        TimeZone.getTimeZone("US/Eastern"))

val distance2 = Distance.create(2.2, Distance.UNIT_KILOMETERS)
val time2 =  // Arrival time at the destination with the destination time zone.
    DateTimeWithZone.create(
        System.currentTimeMillis() + 2000,
        TimeZone.getTimeZone("US/Eastern"))

val tripUpdate1 = Trip.Builder()
    .addStep(Step.Builder("Left").build(), TravelEstimate.Builder(distance1, time1).build())
    .addDestination(Destination.Builder().setName("Destination").build(), TravelEstimate.Builder(distance1, time1).build())
    .build()

val tripUpdate2 = Trip.Builder()
    .addStep(Step.Builder("Right").build(), TravelEstimate.Builder(distance2, time2).build())
    .addDestination(Destination.Builder().setName("Destination2").build(), TravelEstimate.Builder(distance2, time2).build())
    .build()

class TestScreen(carContext: CarContext) : Screen(carContext) {
    init {
        carContext.getCarService(NavigationManager::class.java).setNavigationManagerCallback(object : NavigationManagerCallback {
            override fun onStopNavigation() {
            }

            override fun onAutoDriveEnabled() {
            }
        })
    }

    override fun onGetTemplate(): Template {
        val row = Row.Builder().setTitle("Test").build()
        return PaneTemplate.Builder(Pane.Builder().addRow(row)
            .addAction(Action.Builder()
                .setTitle("Start")
                .setOnClickListener { sendUpdates() }
                .build())
            .addAction(Action.Builder()
                .setTitle("Stop")
                .setOnClickListener { stopUpdates() }
                .build())
            .build())
            .setHeaderAction(Action.APP_ICON)
            .build()
    }

    fun sendUpdates() {
        carContext.getCarService(NavigationManager::class.java).apply {
            navigationStarted()
            updateTrip(tripUpdate1)
            Handler().postDelayed(Runnable {
                updateTrip(tripUpdate2)
            }, 200)
        }
    }

    fun stopUpdates() {
        carContext.getCarService(NavigationManager::class.java)
            .navigationEnded()
    }

}