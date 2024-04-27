import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun App() {
    MaterialTheme {
        var latitude by remember { mutableStateOf("") }
        var longitude by remember { mutableStateOf("") }

        Column {
            Row(
                modifier = Modifier// Align to the top center of the Box
                    .fillMaxWidth()
                    .background(Color.White) // Semi-transparent background // Padding inside the Row for the text
            ) {
                Text(text = "Latitude: $latitude", color = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Longitude: $longitude", color = Color.Black)
            }
            MapView { coord ->
                latitude = coord.lat.toString()
                longitude = coord.lon.toString()
            }
        }
    }
}
