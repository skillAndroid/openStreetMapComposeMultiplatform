import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)

    // add this dependency
    id("org.openjfx.javafxplugin") version "0.0.10"
}

// add this also at the beginning could show you error but continue to use!
javafx {
    version = "17.0.2"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
        "javafx.web",
        "javafx.swing"
    )
}


kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // add this to image loading it is actually is not such needed )
            api("io.github.qdsfdhvh:image-loader:1.7.8")

        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)

            // library for open street map
            implementation("org.openstreetmap.jmapviewer:jmapviewer:2.16")

        }
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        // add this also
        // this is as i remember for default toolbar for desktop app but also add this )
        jvmArgs("--add-opens", "java.desktop/sun.awt=ALL-UNNAMED")
        jvmArgs("--add-opens", "java.desktop/java.awt.peer=ALL-UNNAMED")
        if (System.getProperty("os.name").contains("Mac")) {
            jvmArgs("--add-opens", "java.desktop/sun.lwawt=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.desktop/sun.lwawt.macosx=ALL-UNNAMED")
        }




        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.websale2.compose"
            packageVersion = "1.0.0"
            // add
            modules("jdk.unsupported")
        }
        // also proguard rules
        buildTypes.release.proguard {
            configurationFiles.from("compose-desktop.pro")
        }
    }
}
