pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
buildscript {
    repositories {
        google() // Google Maven repository
        mavenCentral() // Maven Central repository
    }
    dependencies {
        // Thêm dependency cho Google Services Gradle plugin
        classpath ("com.google.gms:google-services:4.3.15")
    }
}

rootProject.name = "FireBase"
include(":app")
 