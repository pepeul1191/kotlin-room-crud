package pe.edu.ulima.dbaccess.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import pe.edu.ulima.dbaccess.navigations.AppNavigation
import pe.edu.ulima.dbaccess.ui.theme.AccesoDBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        firebaseAnalytics.logEvent("button_click", Bundle().apply {
            putString("MainActivity", "Init")
        })

        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "name" to "John Doe",
            "email" to "johndoe@example.com"
        )
        db.collection("usersxd")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "Document added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("TAG", "Error adding document", e)
            }

        super.onCreate(savedInstanceState)
        Log.d("MAIN_ACTIVITY", "++++++++++++++++++++++++++++++++++++++")
        Log.d("MAIN_ACTIVITY", savedInstanceState.toString())

        Log.d("MAIN_ACTIVITY", savedInstanceState.toString())
        setContent {
            if(savedInstanceState == null) {
                AccesoDBTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AccesoDBTheme {
    }
}