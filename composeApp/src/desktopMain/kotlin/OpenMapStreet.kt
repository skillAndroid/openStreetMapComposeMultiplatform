import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import org.openstreetmap.gui.jmapviewer.Coordinate
import org.openstreetmap.gui.jmapviewer.JMapViewer
import org.openstreetmap.gui.jmapviewer.MapMarkerDot
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter

@Composable
fun MapView(onLocationSelected: (Coordinate) -> Unit) {

    val mapViewer = remember { JMapViewer() }
    var currentMarker by remember { mutableStateOf<MapMarkerDot?>(null) }
    var dragged by remember { mutableStateOf<Point?>(null) }

    LaunchedEffect(mapViewer) {
        mapViewer.apply {
            isScrollWrapEnabled = true
            mapViewer.setTileSource(OsmTileSource.Mapnik()) // Stylish map tiles

            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    if (e.button == MouseEvent.BUTTON1 && e.clickCount == 1) {
                        val iCoord = mapViewer.getPosition(e.point)
                        if (iCoord != null) {
                            // to get locaction
                            val coord = Coordinate(iCoord.lat, iCoord.lon)
                            onLocationSelected(coord)
                            // if there are more then 1 marked locations it removes all of them saving only last position
                            currentMarker?.let { removeMapMarker(it) }
                            // Usage
                            val marker = CustomMapMarker(coord)
                            mapViewer.addMapMarker(marker)
                            currentMarker = marker
                        }
                    }
                }
                override fun mousePressed(e: MouseEvent) {
                    dragged = e.point
                }
                override fun mouseReleased(e: MouseEvent) {
                    dragged = null
                }
            })

            // this is for dragging the card to left right and so on...
            addMouseMotionListener(object : MouseMotionAdapter() {
                override fun mouseDragged(e: MouseEvent) {
                    dragged?.let { startPoint ->
                        val currentPoint = e.point
                        val diffX = startPoint.x - currentPoint.x
                        val diffY = startPoint.y - currentPoint.y
                        moveMap(diffX, diffY)
                        dragged = currentPoint
                    }
                }
            })
        }
    }

    // this makes your map inside the compose view (screen)
    SwingPanel(
        modifier = Modifier.fillMaxSize(),
        factory = { mapViewer }
    )
}

