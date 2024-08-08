# What is it?

This is a framework to create scalable modular web front-end projects
with type-safe resources and out-of-the-box bundling.

It's mostly written in **Kotlin** and makes heavy use of
**React**, [Kotlin Wrappers](https://github.com/JetBrains/kotlin-wrappers)
and **Gradle** with [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) structure.
These technologies are assumed to be known for those who want to try this framework.

Jump to [All features](#all-features) to get acquainted with all functionality **kjs-box** provides.

# Prerequisites

1. **JDK 17**. Everything else should be downloaded by Gradle.
2. **GitHub account** with an access token having the **read:packages** scope.
   Here is the instruction on
   [creating a personal access token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic).
   Without it there is no way to fetch the framework's Gradle plugins and libraries,
   as they are published at GitHub Packages.

# Quick start

**Note** - if you want to check out some out-of-the-box demos without writing any code yourself,
see the [Demos](#demos) section.

Create a new Kotlin project with Gradle used as a build system and Kotlin preferred as Gradle DSL.

<details>
<summary>Write the following code inside <b>settings.gradle.kts</b>:</summary>

```kotlin
pluginManagement {
    repositories {
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/andrew-k-21-12/kjs-box")
            credentials {
                username = "your-github-username"
                password = "your-github-access-token"
            }
        }
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/andrew-k-21-12/kjs-box")
            credentials {
                username = "your-github-username"
                password = "your-github-access-token"
            }
        }
    }
}

rootProject.name = "example"
include("entry", "lazy")
```

Don't forget to replace `"your-github-username"` and `"your-github-access-token"` with your actual GitHub credentials -
see the [Prerequisites](#prerequisites) section for details.
Keep in mind that `rootProject.name` affects the way how names for generated sources are constructed.

</details>

<details>
<summary>
    Apply the <b>frontend-main</b> plugin and set up basic project descriptions 
    in the root <b>build.gradle.kts</b> file (which is on the same level with <b>settings.gradle.kts</b>):
</summary>

```kotlin
plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-main") version "1.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"
```

The **frontend-main** plugin is applied
to set up webpack, add JavaScript dependencies, include basic resources and perform other configurations.

Setting `group` is required to construct names for some generated sources,
`version` - to configure directories for production bundles.

Earlier we declared two Gradle subprojects inside **settings.gradle.kts**: **entry** and **lazy**.
Make sure folders with the corresponding names (**entry** and **lazy**)
are created on the same level with **settings.gradle.kts**
and each of them has its own **build.gradle.kts** file inside.

</details>

<details>
<summary>Write the following code inside <b>lazy/build.gradle.kts</b>:</summary>

```kotlin
plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-lazy-module") version "1.0.0"
    id("io.github.andrew-k-21-12.kjs-box.frontend-resource-wrappers") version "1.0.0"
}

lazyModule.exportedComponent = "org.example.lazy.LazyPage"
```

We have applied **frontend-lazy-module** to turn the corresponding Gradle subproject into an on-demand React module
which will be loaded only when becomes needed,
**frontend-resource-wrappers** - to enable type-safe resources wrappers generation.

To configure which React component is going to be exported (becomes a kind of entry point for the on-demand module),
it's needed to set its full name to `lazyModule.exportedComponent`.

</details>

<details>
<summary>
    Create <b>lazy/src/jsMain/kotlin/org/example/lazy/LazyPage.kt</b> referenced in the previous step 
    with the following code:
</summary>

```kotlin
package org.example.lazy

import io.github.andrewk2112.kjsbox.frontend.image.components.Image
import org.example.example.resourcewrappers.images.lazy.SampleImage
import react.FC

val LazyPage = FC {
    +"This is a lazy page."
    Image(SampleImage, "Sample image")
}
```

In the code above it is possible to check how resources wrappers generation works.
You need to put an image of some common format (JPEG, PNG or WebP, for example)
into **lazy/src/jsMain/resources/images/**.
If you use an image named as **sample.png**, the generated wrapper becomes named as `SampleImage`.
Other images will produce wrappers named in a similar manner.
It's strictly required to **put all raster images into** the **images** folder.

**All resources wrappers will be generated only after building the project!**

</details>

<details>
<summary>Write the following code inside <b>entry/build.gradle.kts</b>:</summary>

```kotlin
plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-entry-point") version "1.0.0"
    id("io.github.andrew-k-21-12.kjs-box.frontend-lazy-module-accessors") version "1.0.0"
}

kotlin.sourceSets {
    val jsMain by getting {
        kotlin.srcDirs(
            lazyModuleAccessors.generateOrGetFor(project(":lazy"))
        )
        dependencies {
            implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom")
        }
    }
}

entryPoint.rootComponent = "org.example.entry.App"
```

By applying **frontend-entry-point** and setting `entryPoint.rootComponent`
it's possible to configure a React component which is going to be loaded and rendered in the first order.

Having **frontend-lazy-module-accessors** applied
and sources generated by `lazyModuleAccessors.generateOrGetFor(project(":lazy"))` included,
we get the code of a lazy React component to load and open `project(":lazy")` on demand.
Such step is desirable only when configuring modules from which there is navigation to required lazy modules
(listed inside `lazyModuleAccessors.generateOrGetFor(...)`).

Also including **kotlin-react-router-dom** to declare routes.

</details>

<details>
<summary>
    And, at last, create <b>entry/src/jsMain/kotlin/org/example/entry/App.kt</b>
    referenced earlier as <code>entryPoint.rootComponent</code>:
</summary>

```kotlin
package org.example.entry

import ExampleLazyEntryPoint
import react.FC
import react.Suspense
import react.create
import react.dom.html.ReactHTML.p
import react.router.RouteObject
import react.router.RouterProvider
import react.router.dom.Link
import react.router.dom.createBrowserRouter

val App = FC {
    Suspense {
        fallback = SuspenseLoadingIndicator.create()
        RouterProvider {
            router = routes
        }
    }
}

private val SuspenseLoadingIndicator = FC {
    +"Loading..."
}

private val IndexPage = FC {
    p {
        +"This is an index page."
    }
    Link {
        to = "/lazy-page"
        +"Open lazy page"
    }
}

private val routes = createBrowserRouter(
    arrayOf(
        RouteObject(
            path = "/",
            element = IndexPage.create()
        ),
        RouteObject(
            path = "lazy-page",
            element = ExampleLazyEntryPoint.create()
        ),
    )
)
```

The most interesting part here is `ExampleLazyEntryPoint`
which was generated by `lazyModuleAccessors.generateOrGetFor(project(":lazy"))` in **entry/build.gradle.kts**.

</details>

Everything is almost ready.
Run the **kotlinUpgradeYarnLock** Gradle task first: it can be found in the **Gradle** panel in the IDE
or launched with a command like:

```console
./gradlew kotlinUpgradeYarnLock
```

After that you can run **jsBrowserDevelopmentRun** to preview this quick start example app in a browser
or **jsBrowserProductionWebpack** to create its production bundle -
it will appear in **build/kotlin-webpack/js/productionExecutable/**.

This quick start shows just a tiny slice of available features -
read about [All features](#all-features) to get the full picture of what **kjs-box** provides.

# Demos

The easiest way to check out **kjs-box** demos is to clone this repository and open **frontend-example** in the IDE -
this is a project which includes all other modules of the repository, allows to run and test them.

In the IDE you will see a set of run configurations.
Each of these run configurations corresponds to a Gradle task,
so instead of IDE usage it's possible to run the corresponding tasks in a command line.

The most interesting ones to us now are:

1. **frontend-example:jsBrowserDevelopmentRun --continuous** - runs the example project in a fast non-minified manner
   performing hot reloads each time we change something in the codebase.
2. A combination of **frontend-example:jsBrowserProductionWebpack** and **backend-example:kotlinBackendRun** -
   prepares a minified production build and runs it with all caching and compression enabled -
   this variant takes more time but allows to demonstrate some features which are not available in the first variant.

Choose some run configuration (task) from two variants above and launch it.
Open any web browser and navigate to the localhost address prompted in the launch's outputs.

### To-Do List

This example is the easiest one to start from.
Its source code is located only inside a single module - **to-do-list**.

The primary goal of the example is to show how to inject dependencies in constructors -
including on-demand providers.

Start from checking how `ToDoListComponent` and its modules bind and provide dependencies.
Take a look at the `Root` component and the way how it configures an entry point with this `ToDoListComponent`.
Check how `ToDoList` retrieves a new instance of `viewModel` from `Provider<ToDoListViewModel>`
only when its rendering starts.



# ⚠️EVERYTHING BELOW IS IN PROCESS OF WRITING ⚠️

### SpaceX Crew

### Material Design

# All features

*Don't forget to mention the feature to access styles by their names from `DynamicStyleSheet`s.*

# Repository structure
