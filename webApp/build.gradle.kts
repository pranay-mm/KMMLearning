
plugins {
    kotlin("multiplatform")
    application
}
fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

kotlin {
    js(IR) {
        browser{
            binaries.executable()
            runTask {
                devServer = devServer?.copy(port = 8080)
            }
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(kotlin("stdlib-js"))
                //implementation("rg.jetbrains.kotlin-wrappers:kotlin-styled:5.3.5-pre.332-kotlin-1.6.21")
                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.5")
//                implementation(npm("copy-webpack-plugin","9.0.0"))
            }
        }
    }
    dependencies {
        commonMainImplementation(enforcedPlatform(kotlinw("wrappers-bom:1.0.0-pre.340")))
        commonMainImplementation(kotlinw("react"))
        commonMainImplementation(kotlinw("react-dom"))
        commonMainImplementation(kotlinw("react-router-dom"))
        commonMainImplementation(kotlinw("mui"))
        commonMainImplementation(kotlinw("emotion"))

    }
}
/*rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin::class.java) {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().versions.webpackDevServer.version = "4.10.0"
}*/
