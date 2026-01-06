import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import kotlinx.serialization.Serializable

@Serializable
object Home

data class HomePin(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val icon: ImageVector,
    val category: String,
    val heightDp: Int
)

private fun dummyPins(): List<HomePin> {
    val cats = listOf("All", "Coffee", "Places", "Gear", "Tips", "Recipes", "Aesthetic")

    val templates = listOf(
        Triple("Latte art ideas", "Try these at home", Icons.Outlined.LocalCafe),
        Triple("Hidden cafés", "Cozy spots", Icons.Outlined.Place),
        Triple("Brew guide", "V60 basics", Icons.Outlined.MenuBook),
        Triple("Beans of the week", "Roast notes", Icons.Outlined.Inventory2),
        Triple("Cozy corners", "Study vibes", Icons.Outlined.Chair),
        Triple("New grinder", "Worth it?", Icons.Outlined.Build),
        Triple("Morning routine", "Tiny habits", Icons.Outlined.WbSunny),
        Triple("Iced coffee", "Quick recipe", Icons.Outlined.Icecream)
    )

    val urls = listOf(
        "https://source.unsplash.com/800x1100/?coffee",
        "https://source.unsplash.com/800x1100/?cafe",
        "https://source.unsplash.com/800x1100/?latte",
        "https://source.unsplash.com/800x1100/?espresso",
        "https://source.unsplash.com/800x1100/?dessert",
        "https://source.unsplash.com/800x1100/?breakfast",
        "https://source.unsplash.com/800x1100/?travel",
        "https://source.unsplash.com/800x1100/?aesthetic"
    )

    return List(32) { i ->
        val (t, s, icon) = templates[i % templates.size]
        HomePin(
            id = "pin_$i",
            title = t,
            subtitle = s,
            imageUrl = urls[i % urls.size],
            icon = icon,
            category = cats[(i % (cats.size - 1)) + 1],
            heightDp = listOf(190, 210, 230, 250, 270, 300).random()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val allPins = remember { dummyPins() }

    var query by rememberSaveable { mutableStateOf("") }
    val categories = remember { listOf("All", "Coffee", "Places", "Gear", "Tips", "Recipes", "Aesthetic") }
    var selectedCategory by rememberSaveable { mutableStateOf("All") }

    val filteredPins by remember(query, selectedCategory) {
        derivedStateOf {
            allPins
                .asSequence()
                .filter { selectedCategory == "All" || it.category == selectedCategory }
                .filter { query.isBlank() || it.title.contains(query, ignoreCase = true) || it.subtitle.contains(query, ignoreCase = true) }
                .toList()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Nosta", fontWeight = FontWeight.ExtraBold)
                        Text(
                            "Topics",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("Search cafés, recipes, tips…") },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                trailingIcon = {
                    if (query.isNotBlank()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Outlined.Close, contentDescription = "Clear")
                        }
                    }
                }
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat) }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalItemSpacing = 12.dp,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredPins, key = { it.id }) { pin ->
                    PinCard(
                        pin = pin,
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
private fun PinCard(
    pin: HomePin,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(pin.heightDp.dp)
                    .clip(RoundedCornerShape(18.dp))
            ) {
                SubcomposeAsyncImage(
                    model = pin.imageUrl,
                    contentDescription = pin.title,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                strokeWidth = 2.dp
                            )
                        }
                    },
                    error = {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Outlined.ImageNotSupported, contentDescription = null)
                        }
                    }
                )

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(999.dp),
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(pin.icon, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text(pin.category, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    pin.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    pin.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}