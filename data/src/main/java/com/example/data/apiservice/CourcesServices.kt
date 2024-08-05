package com.example.data.apiservice

import android.util.Log
import com.example.domain.datamodel.CourcesModel
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder


class CourcesServices {

    private lateinit var hubConnection: HubConnection

    fun startConnection() {
        fun startConnection() {
            hubConnection =
                HubConnectionBuilder.create("http://dopamineapi.runasp.net/courseHub") // Replace with your actual SignalR Hub URL
                    .build()

            hubConnection.on("ReceiveCourseUpdate", { course: CourcesModel ->
                // Handle the incoming course update here
                println("Course update received: $course")
                // You can update your UI or data model here
            }, CourcesModel::class.java)

            hubConnection.start().doOnComplete {
                Log.d("TAG", "SignalR connection established.")
            }.doOnError { error ->
                Log.d("TAG", "Error establishing SignalR connection: $error")
            }
        }

        fun stopConnection() {
            hubConnection.stop()
        }
    }

}