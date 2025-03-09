package com.example.lap7_crud

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lap7_crud.ui.theme.Lap7_crudTheme
import com.example.lap7_crud.ui.theme.GreenColor
import com.google.firebase.firestore.FirebaseFirestore

class UpdateCourse : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lap7_crudTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Update Course",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = GreenColor, // Màu xanh lá cây
                                    titleContentColor = Color.White
                                )
                            )
                        }
                    ) { padding ->
                        val context = LocalContext.current
                        val courseName = intent.getStringExtra("courseName") ?: ""
                        val courseDuration = intent.getStringExtra("courseDuration") ?: ""
                        val courseDescription = intent.getStringExtra("courseDescription") ?: ""
                        val courseID = intent.getStringExtra("courseID") ?: ""

                        firebaseUI(
                            context,
                            courseName,
                            courseDuration,
                            courseDescription,
                            courseID,
                            Modifier.padding(padding)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun firebaseUI(
        context: Context,
        name: String,
        duration: String,
        description: String,
        courseID: String,
        modifier: Modifier = Modifier
    ) {
        var courseName by remember { mutableStateOf(name) }
        var courseDuration by remember { mutableStateOf(duration) }
        var courseDescription by remember { mutableStateOf(description) }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = courseName,
                onValueChange = { courseName = it },
                label = { Text("Course Name") },
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = courseDuration,
                onValueChange = { courseDuration = it },
                label = { Text("Course Duration") },
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = courseDescription,
                onValueChange = { courseDescription = it },
                label = { Text("Course Description") },
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (courseName.isNotEmpty() && courseDuration.isNotEmpty() && courseDescription.isNotEmpty()) {
                        updateDataToFirebase(courseID, courseName, courseDuration, courseDescription, context)
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Update Data", modifier = Modifier.padding(8.dp))
            }
        }
    }

    private fun updateDataToFirebase(
        courseID: String,
        name: String,
        duration: String,
        description: String,
        context: Context
    ) {
        val updatedCourse = hashMapOf(
            "courseName" to name,
            "courseDuration" to duration,
            "courseDescription" to description
        )

        val db = FirebaseFirestore.getInstance()
        db.collection("Courses").document(courseID)
            .set(updatedCourse)
            .addOnSuccessListener {
                Toast.makeText(context, "Course Updated Successfully!", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, CourseDetailsActivity::class.java))
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to update course: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
