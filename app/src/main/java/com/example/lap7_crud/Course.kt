package com.example.lap7_crud
import com.google.firebase.firestore.IgnoreExtraProperties
data class Course(// on below line creating variables.
    var courseName: String? = "",
    var courseDuration: String? = "",
    var courseDescription: String? = ""
)
