import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.isaalutions.nosta.ui.custom.Fab
import kotlinx.serialization.Serializable

@Serializable
object Home

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    Fab { println("Clickeado") }
}