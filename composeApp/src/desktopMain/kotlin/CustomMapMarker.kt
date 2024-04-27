import org.openstreetmap.gui.jmapviewer.Coordinate
import org.openstreetmap.gui.jmapviewer.MapMarkerDot
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import java.awt.Point
import javax.swing.ImageIcon

class CustomMapMarker(coord: Coordinate) : MapMarkerDot(coord.lat, coord.lon) {

    // you can use your image as Marker here :
    private val iconImage: Image? = ImageIcon(javaClass.getResource("/drawable/ic_map_marker.png")).image

    init {
        // Debugging: Print image loading details
        if (iconImage != null) {
            println("Image loaded successfully with size: ${iconImage.getWidth(null)}x${iconImage.getHeight(null)}")
        } else {
            println("Failed to load image...")
        }
    }

    override fun paint(g: Graphics, position: Point, radius: Int) {
        val g2d = g as Graphics2D
        if (iconImage != null) {
            val imageSize = 40  // Adjust this size based on your needs
            val x = position.x - imageSize / 2
            val y = position.y - imageSize / 2

            g2d.drawImage(iconImage, x, y, imageSize, imageSize, null)
        } else {
            // If the image is not loaded, draw a default shape
            g2d.fillOval(position.x - radius, position.y - radius, 2 * radius, 2 * radius)
        }
    }
}