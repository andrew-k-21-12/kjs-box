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
3. **1 GB** of **RAM** at least to be delegated for the Gradle Daemon's build VM heap memory.
   It can be configured in your project's **gradle.properties** file
   by setting `-Xms` and `-Xmx` values for the `org.gradle.jvmargs` key.

# Quick start

**Note** - if you want to check out some out-of-the-box demos without writing any code yourself,
see the [Demos](#demos) section.

Create a new Kotlin project with Gradle used as a build system and Kotlin preferred as Gradle DSL.

<details id=write-the-following-code-inside-settings-gradle-kts>
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
read [All features](#all-features) to get the full picture of what **kjs-box** provides.

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

<details id=to-do-list>
<summary><b>To-Do List</b></summary>

This example is the easiest one to start from.
Its source code is located only inside a single module - **to-do-list**.

The primary goal of the example is to show how to inject dependencies in constructors -
including on-demand providers.

Start from checking how `ToDoListComponent` and its modules bind and provide dependencies.
Take a look at the `Root` component and the way how it configures an entry point with this `ToDoListComponent`.
Check how `ToDoList` retrieves a new instance of `viewModel` from `Provider<ToDoListViewModel>`
only when its rendering starts.

</details>

<details id=spacex-crew>
<summary><b>SpaceX Crew</b></summary>

The main sources for this example are inside the **spacex-crew** module.

The key point of the example is to show how to integrate with third-party network APIs.

Review `CrewRemoteDataSource` and its related classes inside the `data` package
to see one of possible ways to execute network requests, parse their responses with
[kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) and catch errors.
Then check how `CrewRemoteDataSource` is integrated inside the `RootViewModel`
and how UI represented by the `Root` component interacts with this `RootViewModel`.

</details>

<details id=material-design>
<summary><b>Material Design</b></summary>

This is the most complicated example which is primarily focused on demonstration of UI designing capabilities.
Note that **I** obviously **do not own any copyrights related to Google's Material Design**:
this example is just an attempt to replicate one of official Material Design web pages
located at [m2.material.io/design](https://m2.material.io/design).

The example is powered by multiple modules:

1. **shared-utility** - the simplest module to provide reusable date and time formats.
2. **localization** - provides type-safe implementations and configurations for the `I18NextLocalizationEngine`.
3. **design-tokens** - a compilation of common design tokens can be used everywhere in **frontend-example**.
   It includes:
    1. Sets of end values for font sizes, colors and spacing inside the `reference` package
       which are joined altogether in `ReferenceDesignTokens`.
    2. Sets of `Context`-dependent font sizes and colors inside the `system` package
       which can take different end-values depending on the current `ScreenSize` or `ColorMode`.
       All of them are gathered together in `SystemDesignTokens`.
    3. `DesignTokensContextProvider` which sets up, invalidates and provides the `Context` for all child components
       allowing it to be read by the `useDesignTokensContext()` hook.
    4. `DesignTokens` which just combines all groups of design tokens mentioned above.
4. **dependency-injection** - contains only abstract `RootComponent`
   describing which global app dependencies are going to be provided by it.
5. **dependency-injection-kodein** - provides a [KODEIN](https://github.com/kosi-libs/Kodein)-based implementation
   for the `RootComponent` and a set of `modules` describing how each particular dependency is going to be injected.
6. **dependency-injection-utility** - sets up a particular `RootComponent` and provides its accessor
   with a combination of convenience hooks to handle localizations.
7. **material-design** - the biggest module of the example which consists of:
    1. Multiple groups of resources inside **modules/material-design/src/jsMain/resources/**.
    2. A compilation of endpoints to external web pages inside the `resources.endpoints` package.
    3. Sets of design tokens used only for the Material Design page inside the `designtokens` package.
       They are organized pretty much the same as the common design tokens described earlier above,
       but there is one more type of tokens added - `ComponentDesignTokens`.
       It combines all tokens from the `designtokens.component` package
       which describe reusable complex styles for full-fledged UI components.
    4. Dependency injection configurations and providers inside the `dependencyinjection` package.
       Quite similar to the dependency injection modules described above, but there are some differences:
        1. There is no abstraction extracted for `MaterialDesignComponent`.
        2. `MaterialDesignComponent` depends on the `RootComponent` -
           see how `RootComponent`'s dependencies are passed to the `MaterialDesignComponent`
           inside the `rootComponentMappingModuleFactory`.
        3. It's possible to provide or substitute different instances of `MaterialDesignComponent`
           by `ProvideMaterialDesignComponent` when it's accessed with the `useMaterialDesignComponent()` hook.
    5. All React components to set up the example's UI inside the `components` package.
       Pay closer attention to the way how style sheets (classes extending `DynamicStyleSheet`) are described and used.
       They can have dependencies listed as constructor arguments for `DynamicStyleSheet`
       which contribute to generated CSS class names -
       this allows, for example, to substitute different sets of design tokens to adjust styling for exceptional cases.
       Also, accessing instances of `DynamicStyleSheet`s is done via `useMemoWithReferenceCount`:
       it makes possible to use the same shared instance of a `DynamicStyleSheet` from multiple places
       preparing it for garbage collection when no usages are left.

</details>

# All features

To apply any Gradle plugin or add any dependency from **kjs-box**, it's needed to have:

1. A **GitHub account** with an access token having the **read:packages** scope - see the instructions on
   [creating a personal access token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic).
2. **kjs-box** repositories included in your project's **settings.gradle.kts** file,
   see [Write the following code inside **settings.gradle.kts**](#write-the-following-code-inside-settings-gradle-kts)
   block in the beginning of the [Quick start](#quick-start) section.

### Boilerplate setup and code generation

<details id=boilerplate-project-setup>
<summary><b>Boilerplate project setup</b></summary>

**Note** - it's not strictly required to apply the boilerplate setup described further
if you don't want to include the corresponding out-of-the-box configurations,
use [lazy](https://react.dev/reference/react/lazy) React components
(see [Lazy modules](#lazy-modules)),
generate resource wrappers (see [Type-safe resource wrappers generation](#type-safe-resource-wrappers-generation))
or perform different types of supported deployment (see [Deployment](#deployment)).

The boilerplate project setup starts from application of **frontend-main** plugin
in the root (located in the same directory with **settings.gradle.kts**) **build.gradle.kts** file:

```kotlin
plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-main") version "1.0.0"
}
```

<details>
<summary>Having this plugin applied, your project gets the following:</summary>

1. A default root [index.html](frontend/modules/buildscript/main/src/main/resources/index-template.html)
   to bootstrap the application.
   **If you want to use your own custom index.html**:
    1. Create it inside your project's **src/jsMain/resources/** directory. Do not use **index.html** as its name!
    2. Inside the root **build.gradle.kts** point to your created HTML file, for example:
       ```kotlin
       main {
           customIndexHtmlTemplateFile = "my-index.html"
       }
       ```
2. Multiple webpack configuration files
   to run development builds and assemble minified bundles for production deployment.
   Some of these files are static and just copied -
   see the contents of [webpack](frontend/modules/buildscript/main/src/main/resources/webpack),
   some of them are generated dynamically.
3. Included sources of a default Service Worker.
   If it will be registered,
   the root **index.html** will be cached whatever route path is used to load it in your single page application.
   This default Service Worker also caches all static sources and resources of the application,
   so they will be loaded even if there is no network available.
   To review the implementation of the Service Worker,
   see [service-worker-source.js](frontend/modules/buildscript/main/src/main/resources/service-worker-source.js).
4. Gradle tasks to copy, configure and properly include for compilation
   sources, resources and webpack files mentioned above.
   These Gradle tasks are also responsible for generation of dynamic webpack configs.
   See
   [MainModuleTasks](frontend/modules/buildscript/main/src/main/kotlin/io/github/andrewk2112/kjsbox/frontend/buildscript/main/MainModuleTasks.kt)
   for details.
5. All development JavaScript dependencies and particular versions of Node.js and Yarn.
   Check out [js.toml](dependencies/js.toml): all dependencies from the `kjsbox-frontend-main` bundle
   will be used for compilation.
6. Basic Gradle configurations which tie everything together
   and turn your project into Kotlin Multiplatform JavaScript browser project.

</details>

When the project is getting bundled for production with the **jsBrowserProductionWebpack** Gradle task,
its static sources and resources are going to be placed
inside the **build/kotlin-webpack/js/productionExecutable/static/** directory.
You need to specify a particular version of bundle
by either setting a `version` inside the root **build.gradle.kts**:

```kotlin
version = "1.0.0"
```

or by configuring `customBundleStaticsDirectory` for the **frontend-main** plugin (in the same Gradle file):

```kotlin
main {
    customBundleStaticsDirectory = "some-custom-name"
}
```

It's also possible to have both `version` and `customBundleStaticsDirectory` set,
in this case `customBundleStaticsDirectory` takes precedence over `version`.

After having **frontend-main** configured,
**it's needed to create an entry point** referencing a React component or function to be loaded in the first order.
Declare the corresponding Gradle project in **settings.gradle.kts** (there is no strict requirement to call it "entry"):

```kotlin
include("entry")
```

Create a folder with the same name ("entry") in the root Gradle project's directory
and put a **build.gradle.kts** file into it with the **frontend-entry-point** plugin applied:

```kotlin
plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-entry-point") version "1.0.0"
}
```

There are two ways to set an entry point inside this **entry/build.gradle.kts**:

1. By creating a React component and pointing to it.
   The component should be created inside **entry/src/jsMain/kotlin/**, for example:

   ```kotlin
   val App = react.FC {
       +"Hello, world!"
   }
   ```

   After that it's needed to write its full name (including package) in **entry/build.gradle.kts**:

   ```kotlin
   entryPoint.rootComponent = "App"
   ```

   This is the simplest way which also includes required compilation dependencies,
   applies clearfix CSS, registers a default Service Worker
   and automatically renders the declared React component inside the `#root` `div`.
2. Or by creating a bootstrap function and pointing to it.
   The function should be created inside **entry/src/jsMain/kotlin/**, for example:

   ```kotlin
   import react.create
   import react.dom.client.createRoot
   import web.dom.document
    
   fun bootstrap() {
       createRoot(document.getElementById("root")!!)
           .render(App.create())
   }
    
   private val App = react.FC {
       +"Hello, world!"
   }
   ```

   After that it's needed to write its full name (including package)
   and add required dependencies in **entry/build.gradle.kts**:

   ```kotlin
   kotlin.sourceSets.jsMain {
       dependencies {
           implementation(dependencies.platform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:1.0.0-pre.757"))
           implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
           implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
       }
   }

   entryPoint.customInitializationFunction = "bootstrap"
   ```

   This way requires more efforts as it doesn't provide any out-of-the-box configurations,
   but it's the way more flexible.

Now the boilerplate project setup is complete, you can try to build and preview it in a browser
by running the **jsBrowserDevelopmentRun --continuous** Gradle task from the root Gradle project.
It may require to run **kotlinUpgradeYarnLock** first.

</details>

<details id=lazy-modules>
<summary><b>Lazy modules</b></summary>

**Note** - **kjs-box** lazy modules are only available
when the [Boilerplate project setup](#boilerplate-project-setup) is applied.

Lazy modules are Gradle modules containing sources to be packed as separate JavaScript modules
and loaded in web browsers only by explicit demand -
for example, when we navigate to a particular route of a single page application.
In other words, it can be treated just as a way
to use React's [lazy](https://react.dev/reference/react/lazy) components in **kjs-box**.

To create a lazy module, just declare a regular Gradle project in **settings.gradle.kts**, for example:

```kotlin
include("lazy")
```

Make the corresponding directory for this project with a **build.gradle.kts** file inside of it.
This **build.gradle.kts** should have the **frontend-lazy-module** plugin applied:

```kotlin
plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-lazy-module") version "1.0.0"
}
```

The **frontend-lazy-module** plugin does the following for the lazy module:

1. Includes basic React dependencies for it.
2. Creates a Gradle task generating the code to properly export (bundle) it.
3. Declares it to be compiled by the root (**frontend-main**) project.

The only requirement for such lazy modules is to have an entry point React component
(do not mix it with the app's main entry point provided by the **frontend-entry-point** plugin).
For demonstration purposes we can create such entry point inside **lazy/src/jsMain/kotlin/LazyBlock.kt**:

```kotlin
val LazyBlock = react.FC {
    +"This is text from `LazyBlock`."
}
```

And this entry point should be declared in **lazy/build.gradle.kts**:

```kotlin
lazyModule.exportedComponent = "LazyBlock"
```

The only thing left - is to include this lazy module where it is needed.
It can not be done just by regular Gradle project dependencies way:
in this case all sources of the lazy module will become an intrinsic part of the project dependent on it.
To do this properly, there is a special **frontend-lazy-module-accessors** plugin:
apply it in Gradle projects which are going to include lazy modules, for example - in **entry/build.gradle.kts**:

```kotlin
plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-entry-point") version "1.0.0"
    id("io.github.andrew-k-21-12.kjs-box.frontend-lazy-module-accessors") version "1.0.0"
}
```

After that, it's possible to add generated lazy module accessors for **entry/build.gradle.kts**:

```kotlin
kotlin.sourceSets.jsMain {
    kotlin.srcDirs(
        lazyModuleAccessors.generateOrGetFor(
            project(":lazy")
        )
    )
    dependencies {
        // ...
    }
}
```

There are two implementation notes on this `lazyModuleAccessors.generateOrGetFor`:

1. It supports both traditional string-based project-locating API (`project(":lazy")`)
   and new [type-safe project accessors](https://docs.gradle.org/7.0/release-notes.html#type-safe-project-accessors).
2. Each project including an accessor to the same lazy module gets its own copy of the accessor sources.
   If there are lots of places (Gradle projects) requiring the same accessors,
   consider extracting these accessors into a separate Gradle module.

And, at last, we can load our lazy module from some other React component.
For simplicity purposes we will load it in **entry/src/jsMain/kotlin/App.kt**
which was created during the steps described in [Boilerplate project setup](#boilerplate-project-setup):

```kotlin
val App = react.FC {
    +"Hello, world! "
    ExampleLazyEntryPoint()
}
```

Note that the name for `ExampleLazyEntryPoint` above is constructed in the following way:

1. **Example**`LazyEntryPoint` - the name of the root Gradle project.
2. `Example`**Lazy**`EntryPoint` - the name of the lazy module Gradle project.

This is it - launch the app in a browser
and check on the **Network** tab that JavaScript sources for the lazy module are loaded separately.

You can also check the [Quick start](#quick-start) example
to see how to configure such lazy modules to be loaded when a particular route is opened.

</details>

<details id=type-safe-resource-wrappers-generation>
<summary><b>Type-safe resource wrappers generation</b></summary>

**Note** - to make this feature work properly,
make sure the [Boilerplate project setup](#boilerplate-project-setup) is applied.

**Note** - to generate resource wrappers,
it's strictly required to have a `group` set for the root Gradle project
(the one the **frontend-main** plugin was applied to).
For proper production bundle generation with resource wrappers this root Gradle project should also have
either `version` or `main.customBundleStaticsDirectory` set -
see the [Boilerplate project setup](#boilerplate-project-setup) section.

To enable resource wrappers generation feature,
it's needed to apply the **frontend-resource-wrappers** plugin for a target Gradle project
having its resources inside the **src/jsMain/resources/** directory:

```kotlin
plugins {
    id("io.github.andrew-k-21-12.kjs-box.frontend-resource-wrappers") version "1.0.0"
}
```

This plugin can be applied to any types of Kotlin Multiplatform browser JavaScript projects
including **frontend-entry-point** (see [Boilerplate project setup](#boilerplate-project-setup))
and **frontend-lazy-module** (see [Lazy modules](#lazy-modules)) projects.
Having the plugin applied, all required dependencies for generated wrappers will be added,
all original resources will be bundled to folders named according to their Gradle project names.

**Build the project to create or refresh resource wrappers!**

Type-safe wrappers can be generated for
SVG icons, common formats of raster images, fonts and translation strings JSONs.

#### SVG icons

SVG icons should be placed in **src/jsMain/resources/icons/**,
nested folders inside of this directory are supported as well.

Required naming format for icons is **kebab-case**.

Full names (including packages) for generated icon wrappers are constructed in the following way:

```
<root.project.group>.<root.project.name>.resourcewrappers.icons.<projectname>.[nested.folder.name][.]<IconName>Icon
```

Such generated icon wrappers can be used as regular React components, class names are supported to apply some styling:

```kotlin
// Adding "arrow-right-thin.svg" without any class...
ArrowRightThinIcon()
// ...and with some class:
ArrowRightThinIcon {
    className = ClassName("some-class")
}
```

All SVG icons are getting inlined when bundled - there won't be separate files for them in production builds.

#### Raster images

All common raster image formats including **WebP**, **PNG**, **GIF** and **JPEG** are supported.

Images should be placed in **src/jsMain/resources/images/**,
nested folders inside of this directory are supported as well.

Required naming format for images is **kebab-case**.

Full names (including packages) for generated image wrappers are constructed in the following way:

```
<root.project.group>.<root.project.name>.resourcewrappers.images.<projectname>.[nested.folder.name][.]<ImageName>Image
```

Each generated image wrapper extends the
[Image](frontend/modules/core/image/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/image/resources/Image.kt)
interface.
It is intended to be used with the
[Image](frontend/modules/core/image/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/image/components/Image.kt)
component allowing a browser to pick the best image format from all available ones:

```kotlin
// Rendering "test.png" image.
Image(TestImage, "Some alternative text", "some-class")
```

In production builds all original images are converted only to two formats: **WebP** and **PNG**.
Their encoding options can be checked
in the [production.js](frontend/modules/buildscript/main/src/main/resources/webpack/production.js) webpack config.
All image resources are bundled by the following path:

```
static/<version>/images/<project-name>/[nested-folder-name][/]<image-name>.<hash>.<format>
```

#### Fonts

Only **WOFF2** fonts were checked to be working, but other formats might work as well.

Fonts should be placed in **src/jsMain/resources/fonts/**,
nested folders inside of this directory are supported as well.

Expected naming format for fonts includes two parts divided with a dash:

1. Font name itself in **UpperCamelCase**.
2. Font variant name represented by one capitalized word.

The second part is super limited now - it can recognize only **Light** as variant name
(see
[FontIndependentWrappersWriter](frontend/modules/buildscript/resource-wrappers/src/main/kotlin/io/github/andrewk2112/kjsbox/frontend/buildscript/resourcewrappers/wrappers/writers/independent/FontIndependentWrappersWriter.kt)
for details),
for example: **Roboto-Light.woff2**.
By using this **Light** variant name,
there will be an additional working style property generated inside the same Kotlin `object`
for a single font family name.
If it sounds complicated or unreliable, just name all your fonts as:

```
<FontName>-Regular.<format>
```

Full names (including packages) for generated font wrappers are constructed in the following way:

```
<root.project.group>.<root.project.name>.resourcewrappers.fonts.<projectname>.[nested.folder.name][.]<FontFamilyName>FontStyles
```

Each generated font wrapper extends the
[DynamicStyleSheet](frontend/modules/core/dynamic-style-sheet/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/dynamicstylesheet/DynamicStyleSheet.kt)
class and provides a separate style property for each font variant.

An example usage of some **Roboto-Regular.woff2** can be as following:

```kotlin
// Directly by the class name.
p {
    className = ClassName(RobotoFontStyles.regular.name)
    +"Some paragraph with a custom font."
}

// When composing styles - by style rules.
class MaterialDesignSystemFontStyles : DynamicStyleSheet() {
    val bold: NamedRuleSet by css {
        +RobotoFontStyles.regular.rules
        fontWeight = FontWeight.w600
    }
}
```

All fonts are bundled by the following path in production builds:

```
static/<version>/fonts/<project-name>/[nested-folder-name][/]<FontFamilyName>-<Variant>.<hash>.<format>
```

#### Translation JSONs

By default, translation JSONs used in **kjs-box** projects
are expected to be handled by the [i18next](https://www.i18next.com/) engine,
so it's better to stick to its rules at least in some points.
Keys inside of translation JSONs should be in **lowerCamelCase**, nested keys are supported.

Translation files are placed in **src/jsMain/resources/locales/**.
Inside of this directory there should be only one additional level of nested folders - for each language code.
Names for translation JSONs can be arbitrary but must be the same for all language variants,
check this [locales](frontend-example/modules/spacex-crew/src/jsMain/resources/locales) directory as an example.

Full names (including packages) of generated wrappers for translation keys are constructed in the following way:

```
<root.project.group>.<root.project.name>.resourcewrappers.locales.<projectname>.<TranslationsFileName>LocalizationKeys
```

In addition to all keys from original translation JSONs
there is also a `NAMESPACE` key gets created in each generated wrapper.
It is needed to load the corresponding group of translations lazily.

Keep in mind that by just applying the **frontend-resource-wrappers** plugin
there won't be any particular localization engine included as a dependency.
You can check possible ways to apply localizations in the sources of provided demos (see [Demos](#demos))
or you can add the following dependencies to your Gradle project:

```
io.github.andrew-k-21-12.kjs-box:frontend-localization:1.0.0
io.github.andrew-k-21-12.kjs-box:frontend-localization-i18next:1.0.0
```

And use this very simplified (missing invalidations on language changes) example code:

```kotlin
val localizationEngine = I18NextLocalizationEngine
    .getInstance("en", false)
    .apply {
        loadLocalizations(TranslationLocalizationKeys.NAMESPACE)
    }
p {
    +localizationEngine.getLocalization(TranslationLocalizationKeys.YOUR_LOCALIZATION_KEY)
}
```

In production builds all translation keys which were not used in the code are getting dropped.
Translations are bundled as JavaScript files to the following output directory:

```
static/<version>/js/
```

</details>

### Core libraries

<details id=design-tokens>
<summary><b>Design tokens</b></summary>

Represents a compilation of skeleton interfaces to implement your
[DesignTokens](frontend/modules/core/design-tokens/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/designtokens/DesignTokens.kt)
including
[ReferenceDesignTokens](frontend/modules/core/design-tokens/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/designtokens/ReferenceDesignTokens.kt)
and
[SystemDesignTokens](frontend/modules/core/design-tokens/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/designtokens/SystemDesignTokens.kt)
(component design tokens are left to be implemented by any arbitrary type).

Can be added as a dependency by:

```
io.github.andrew-k-21-12.kjs-box:frontend-design-tokens:1.0.0
```

Check out some example implementations of these interfaces in the [Material Design](#material-design) demo, see
[DesignTokens](frontend-example/modules/design-tokens/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/designtokens/DesignTokens.kt)
and
[MaterialDesignTokens](frontend-example/modules/material-design/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/materialdesign/designtokens/MaterialDesignTokens.kt).

</details>

<details id=dynamic-style-sheet>
<summary><b>Dynamic style sheet</b></summary>

Can be added as a dependency by:

```
io.github.andrew-k-21-12.kjs-box:frontend-dynamic-style-sheet:1.0.0
```

Make sure the following dependencies are included as well or add them explicitly
to construct styles for dynamic style sheets:

```
dependencies.platform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:1.0.0-pre.757")
org.jetbrains.kotlin-wrappers:kotlin-css
```

The key point of classes and `object`s extending
the [DynamicStyleSheet](frontend/modules/core/dynamic-style-sheet/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/dynamicstylesheet/DynamicStyleSheet.kt) -
is to declare named styles.
It is possible to declare both static and dynamic styles:

```kotlin
object MyStyleSheet : DynamicStyleSheet() {

    val staticStyle by css {
        color = Color.red
    }

    val dynamicStyle by dynamicCss<Boolean> {
        color = if (it) Color.green else Color.blue
    }

}
```

The first one - `MyStyleSheet#staticStyle` - will be named as **MyStyleSheet-staticStyle** and can be used as:

```kotlin
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke

// Note the way to apply classes to elements shortly by using the extension imported above.
+p(MyStyleSheet.staticStyle.name) {
    +"Having the static style."
}
```

The second one - `MyStyleSheet#dynamicStyle` - requires an argument of `Boolean` type at his call place,
its name depends on a particular argument provided, for example:

```kotlin
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke

// Will be named as "MyStyleSheet-dynamicStyle-true".
+p(MyStyleSheet.dynamicStyle(true).name) {
    +"Having the dynamic style with a positive argument."
}

// Will be named as "MyStyleSheet-dynamicStyle-false".
+p(MyStyleSheet.dynamicStyle(false).name) {
    +"Having the dynamic style with a negative argument."
}
```

Such approach with dynamic styles is convenient when there is some context which can affect styling:
check out system design tokens with their
[Context](frontend-example/modules/design-tokens/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/designtokens/Context.kt),
[ContextProviderAndReader.kt](frontend-example/modules/design-tokens/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/designtokens/ContextProviderAndReader.kt)
and usages in, for example,
[ExercisesList.kt](frontend-example/modules/exercises/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/exercises/components/ExercisesList.kt)
and
[ExerciseLink.kt](frontend-example/modules/exercises/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/exercises/components/ExerciseLink.kt).

Dynamic styles support the following types as their arguments:
`Boolean`, `Number`, `String`,
implementations of
[HasCssSuffix](frontend/modules/core/dynamic-style-sheet/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/dynamicstylesheet/HasCssSuffix.kt),
`Enum<*>` and `KProperty<*>`.

Both static and dynamic styles can be used not only by their `name`s
but can also contribute to some composite styles:

```kotlin
val staticStyle by css {
    color = Color.red
}

val compositeStyle by css {
    +staticStyle.rules
    backgroundColor = Color.yellow
}
```

</details>

<details id=image>
<summary><b>Image</b></summary>

Can be added as a dependency by:

```
io.github.andrew-k-21-12.kjs-box:frontend-image:1.0.0
```

Mostly used in conjunction with [Type-safe resource wrappers generation](#type-safe-resource-wrappers-generation)
for [Raster images](#raster-images):
it's very unlikely that this library will be needed for you in isolation from it.

The library is represented by the
[Image](frontend/modules/core/image/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/image/resources/Image.kt)
type (with some supporting types) and the corresponding
[Image](frontend/modules/core/image/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/image/components/Image.kt)
React component to include generated resource wrappers for images into your components
and pick the best matching image format from all available variants of them.

</details>

<details id=localization>
<summary><b>Localization</b></summary>

Can be added as a dependency by:

```
io.github.andrew-k-21-12.kjs-box:frontend-localization:1.0.0
```

This is a compilation of interfaces to cover basic localization features.
There are examples of implementations for these interfaces worth to check:
[I18NextLocalizationEngine](frontend/modules/core/localization-i18next/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/localization/i18next/I18NextLocalizationEngine.kt),
[LocalizationEngine](frontend-example/modules/localization/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/localization/LocalizationEngine.kt).

</details>

<details id=localization-i18next>
<summary><b>Localization - i18next</b></summary>

Provides an [i18next](https://www.i18next.com/)-backed implementation for localization interfaces.

Can be included by adding the following dependencies:

```
io.github.andrew-k-21-12.kjs-box:frontend-localization:1.0.0
io.github.andrew-k-21-12.kjs-box:frontend-localization-i18next:1.0.0
```

This
[I18NextLocalizationEngine](frontend/modules/core/localization-i18next/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/localization/i18next/I18NextLocalizationEngine.kt)
involves the following features from [i18next](https://www.i18next.com/):

1. [i18next-resources-to-backend](https://github.com/i18next/i18next-resources-to-backend) -
   to download translations (which are originally put to the **locales/** resources folder) on demand
   and bundle them in a minified way as JavaScript files.
2. [i18next-browser-languageDetector](https://github.com/i18next/i18next-browser-languageDetector) -
   to detect user language in the browser.
3. [react-i18next](https://github.com/i18next/react-i18next) - to integrate with React.

To use the engine:

1. Put some localizations into your Gradle project's **src/jsMain/resources/locales/** directory,
   check this [locales](frontend-example/modules/spacex-crew/src/jsMain/resources/locales) directory as an example.
2. Get an instance of the engine:
   ```kotlin
   val localizationEngine = I18NextLocalizationEngine.getInstance("en", false)
   ```
3. Load a group of translations by stating their path (if there is any) and base file name without extension
   (so the corresponding file with translations will be fetched in a browser):
   ```kotlin
   localizationEngine.loadLocalizations("translation")
   ```
4. Monitor the current language and change it
   by observing `localizationEngine.currentLanguage`
   and calling `localizationEngine.changeLanguage(...)` correspondingly.
5. Get translations according to the current language by:
   ```kotlin
   localizationEngine.getLocalization("key")
   ```

See also [Translation JSONs](#translation-jsons)
in [Type-safe resource wrappers generation](#type-safe-resource-wrappers-generation)
and
[LocalizationEngine](frontend-example/modules/localization/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/localization/LocalizationEngine.kt).

</details>

<details id=route>
<summary><b>Route</b></summary>

Can be added as a dependency by:

```
io.github.andrew-k-21-12.kjs-box:frontend-route:1.0.0
```

The library is represented by the only
[Route](frontend/modules/core/route/src/commonMain/kotlin/io/github/andrewk2112/kjsbox/frontend/route/Route.kt)
interface.
Its purpose - is to simplify declarations and usages of routes including nested ones -
see example implementations of this interface in the
[routes](frontend-example/modules/routes/src/commonMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/routes)
module.

It is convenient to declare app routes by referencing `Route#path`s
(see `routes` in
[App.kt](frontend-example/modules/index/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/index/App.kt))
and use `Route.absolutePath`s to set destinations for links located in any React component
(see
[ExercisesList.kt](frontend-example/modules/exercises/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/exercises/components/ExercisesList.kt)).

</details>

<details id=utility>
<summary><b>Utility</b></summary>

**Note** - to use this library,
you need the default [Boilerplate project setup](#boilerplate-project-setup) applied.

Can be added as a dependency by:

```
io.github.andrew-k-21-12.kjs-box:frontend-utility:1.0.0
```

Represented only by the
[Environment](frontend/modules/core/utility/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/utility/Environment.kt)
`object` allowing to check whether the current build mode is a development or production one.

</details>

### Deployment

<details id=pushing-the-entire-bundle>
<summary><b>Pushing the entire bundle</b></summary>

This way of deployment makes all bundled sources and resources required to be downloaded again -
even if some of them were not updated in the recent version.

**Note** - to use this deployment variant,
you need the default [Boilerplate project setup](#boilerplate-project-setup)
and your backend should be configured to cache served single page application's files by their last modified time
(see the [backend-example](backend-example)).

If this is the first release you are about to perform
then just run the **jsBrowserProductionWebpack** task of the root Gradle project
and copy all contents of the generated **build/kotlin-webpack/js/productionExecutable/** folder
to the directory expected by your backend to be served as a single page application.
Otherwise, the deployment procedure itself happens by the following steps:

1. Update the `version` or `main.customBundleStaticsDirectory` in the root **build.gradle.kts** file.
2. Run the **jsBrowserProductionWebpack** task of the root Gradle project.
3. Copy the folder named as the updated version from **build/kotlin-webpack/js/productionExecutable/static/**
   into a similar **static/** folder expected to be served by the backend.
4. Atomically replace your old **index.html** with the freshly generated one -
   **build/kotlin-webpack/js/productionExecutable/index.html**.

</details>

<details id=patching-only-updated-files>
<summary><b>Patching only updated files</b></summary>

This way of deployment tries to update only those sources and resources in the output bundle
which were modified in the recent version, other unrelated updates are avoided as much as possible.

**Note** - to use this deployment variant,
you need the default [Boilerplate project setup](#boilerplate-project-setup)
and your backend should be configured to cache served single page application's files by their last modified time
(see the [backend-example](backend-example)).

The key point of this deployment type is that it can be applied only on top of some previously released version.
You can get acquainted with its underlying algorithm by reading the docs for
[PatchingDeployAction](frontend/modules/utility/patching-deployer/src/commonMain/kotlin/io/github/andrewk2112/kjsbox/frontend/utility/patchingdeployer/action/PatchingDeployAction.kt).
Keep in mind that if this deployment approach fails, it will be needed to use the full deployment
described in the [Pushing the entire bundle](#pushing-the-entire-bundle) section.

This patching deployment is backed by the [patching-deployer](frontend/modules/utility/patching-deployer) utility.
Compile it first by either running the **frontend:frontend-patching-deployer:fatJar** run configuration in IDE
or by executing the corresponding **fatJar** Gradle task from the mentioned utility module.
While it's possible to be compiled to different output executables,
its Java version will be used in the deployment steps described below:

1. Make sure your previous bundle outputs in **build/kotlin-webpack/js/productionExecutable/static/**
   are still available and were not removed.
   They are required to be kept to make the bundling operation update only those output files
   which were actually modified in the latest version and keep all the rest ones with the same last modified metadata.
   If these bundle outputs were lost, you can try to copy them from your current application's served files:
   the main thing - is to preserve the right last modified timestamps for these files.
2. Perform all required updates in the codebase of your application
   and make sure its output version folder will keep the same name
   in **build/kotlin-webpack/js/productionExecutable/static/**.
   If you need to update the `version` in the root Gradle project,
   it's possible to set the `main.customBundleStaticsDirectory` to the previous `version` value,
   so the output folder's name will not be changed.
3. Run the **jsBrowserProductionWebpack** task of the root Gradle project.
4. Execute the compiled [patching-deployer](frontend/modules/utility/patching-deployer)
   providing paths to the fresh bundle's **static/** folder as **--source-bundle**
   and to the target **static/** folder being served as **--deployment-destination**, for example:

   ```console
   java -jar frontend-patching-deployer-jvm-fat-1.0.0.jar --source-bundle="./from/static/" --deployment-destination="./to/static/"
   ```

5. If everything was successful,
   atomically replace your old **index.html** with the freshly generated one to apply the patch.

</details>

### Utility libraries

<details id=bytes>
<summary><b>Bytes</b></summary>

Supported platforms:

- JS (browser)
- JVM
- Linux (ARM64, X64)
- macOS (ARM64, X64)
- MinGW (X64)

```
io.github.andrew-k-21-12.utility:bytes:1.0.0
```

Includes just a couple of extension functions for `ByteArray`s
to read their values in the little-endian order as `Int`s.

</details>

<details id=common>
<summary><b>Common</b></summary>

Supported platforms:

- JS (browser)
- JVM
- Linux (ARM64, X64)
- macOS (ARM64, X64)
- MinGW (X64)

```
io.github.andrew-k-21-12.utility:common:1.0.0
```

Contains various small utility difficult to group or extract further into separate modules.
You can [check its contents](utility/modules/common/src) yourself - all sources are documented.

Perhaps the most interesting interface here is
[Result](utility/modules/common/src/commonMain/kotlin/io/github/andrewk2112/utility/common/utility/Result.kt).
It provides an alternative to the built-in Kotlin's `Result` which has `Failure`s as type-safe values as well.
It helps to prevent creation of thousands `sealed class`es to return typed results from functions in a Kotlin way:

```kotlin
suspend fun getArticlesByName(nameQuery: String): Result<List<Article>, RemoteDataSourceException> = // ...
```

</details>

<details id=coroutines-react>
<summary><b>Coroutines - React</b></summary>

Supported only for browser JavaScript projects.

```
io.github.andrew-k-21-12.utility:coroutines-react:1.0.0
```

Provides
[asReactState](utility/modules/coroutines-react/src/jsMain/kotlin/io/github/andrewk2112/utility/coroutines/react/extensions/Flow.kt)
extensions for coroutines `Flow`s and `StateFlow`s to convert them
into [React states](https://react.dev/learn/state-a-components-memory).

This feature is super handy when you write framework-agnostic view models
and want to observe their UI states in React components:

```kotlin
// Inside a view model.
val someUiState = MutableStateFlow("Hello!")

// Inside a React component.
val state by viewModel.someUiState.asReactState()
p {
    +state
}
```

Check out [To-Do List](#to-do-list) and [SpaceX Crew](#spacex-crew) sources for additional examples.

</details>

<details id=gradle>
<summary><b>Gradle</b></summary>

Supported only for JVM projects.

```
io.github.andrew-k-21-12.utility:gradle:1.0.0
```

[Contains various extensions and properties](utility/modules/gradle/src/main/kotlin/io/github/andrewk2112/utility/gradle)
to simplify writing of the code for Gradle plugins.

</details>

<details id=js>
<summary><b>JS</b></summary>

Just a small compilation of
[utility functions and extensions](utility/modules/js/src/jsMain/kotlin/io/github/andrewk2112/utility/js)
for browser JavaScript projects:

```
io.github.andrew-k-21-12.utility:js:1.0.0
```

</details>

<details id=kodein>
<summary><b>KODEIN</b></summary>

Supported only for browser JavaScript projects (but there are no restrictions to make it support all other platforms).

```
io.github.andrew-k-21-12.utility:kodein:1.0.0
```

Provides a couple of convenience means
for the [KODEIN](https://github.com/kosi-libs/Kodein) dependency injection library.

It includes
[KodeinDirectInjection](utility/modules/kodein/src/commonMain/kotlin/io/github/andrewk2112/utility/kodein/KodeinDirectInjection.kt)
which helps to incorporate multiple `DI.Module`s and retrieve dependencies from them,
see
[ToDoListComponent](frontend-example/modules/to-do-list/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/todolist/dependencyinjection/ToDoListComponent.kt),
[KodeinRootComponent](frontend-example/modules/dependency-injection-kodein/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/dependencyinjection/kodein/KodeinRootComponent.kt)
and [MaterialDesignComponent](frontend-example/modules/material-design/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/materialdesign/dependencyinjection/MaterialDesignComponent.kt)
as examples.

Also, there is an additional
[bindProvider](utility/modules/kodein/src/commonMain/kotlin/io/github/andrewk2112/utility/kodein/extensions/DI.Builder.kt)
extension function to create special dependency injection factories
allowing to retrieve new instances of dependencies on explicit demand in constructors.
An example declaration:

```kotlin
class A {
    init {
        console.log("New instance of A: ${hashCode()}")
    }
}

// The `Provider` interface below should be from "io.github.andrew-k-21-12.utility:common:1.0.0".
class B(private val a: Provider<A>) {
    fun doWithNewInstanceOfA() {
        a.get() // ...
    }
}

val injection = KodeinDirectInjection(
    DI.Module("AB") {
        bindProvider { A() }
        bindProvider { B(instance()) }
    }
)
```

And usage:

```kotlin
val b: B = injection()
b.doWithNewInstanceOfA() // prints: "New instance of A: 962551772"
b.doWithNewInstanceOfA() // prints: "New instance of A: 1336283662"
```

</details>

<details id=react>
<summary><b>React</b></summary>

Supported only for browser JavaScript projects.

```
io.github.andrew-k-21-12.utility:react:1.0.0
```

Includes various common React utility:

#### FC

[FC](utility/modules/react/src/jsMain/kotlin/io/github/andrewk2112/utility/react/components/FC.kt) functions
from this library allow to declare React components named as their variables in the components inspector
what makes debugging a little bit more convenient:

```kotlin
val MyComponent by FC { // will be named as "MyComponent" in the components inspector
    // ...
}
```

#### FunctionalComponentFactory

[FunctionalComponentFactory](utility/modules/react/src/jsMain/kotlin/io/github/andrewk2112/utility/react/components/FunctionalComponentFactory.kt)
is helpful when you want to use constructor dependency injections when declaring React components.
Check
[ToDoList](frontend-example/modules/to-do-list/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/todolist/components/ToDoList.kt)
as an example, make sure to review how its dependencies are prepared in the
[dependencyinjection](frontend-example/modules/to-do-list/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/todolist/dependencyinjection)
package and how this factory is accessed and used in the
[Root](frontend-example/modules/to-do-list/src/jsMain/kotlin/io/github/andrewk2112/kjsbox/frontend/example/todolist/components/Root.kt)
React component.

#### useMemoWithReferenceCount

[useMemoWithReferenceCount](utility/modules/react/src/jsMain/kotlin/io/github/andrewk2112/utility/react/hooks/useMemoWithReferenceCount.kt)
hook allows to reuse instances of the same type when these instances are accessed from multiple React components.
When the last React component using an instance of particular type by this `useMemoWithReferenceCount` gets released,
the instance (its type) loses all of its references and can be released as well,
so the next time such React component will be used again it will create a new instance of the required type as well.

In other words, pretend there are `ReactComponent1`, `ReactComponent2`, `ReactComponent3`.
`ReactComponent2` can be rendered inside `ReactComponent1` when some condition is met,
`ReactComponent3` can be, in turn, rendered inside `ReactComponent2` when some other condition is met.
So the overall picture is something like:

```
ReactComponent1 -> ReactComponent2 -> ReactComponent3
```

Now also assume that `ReactComponent2` and `ReactComponent3` exploit `useMemoWithReferenceCount`
to use an instance of some `Feature` class.
When both conditions to render `ReactComponent2` and `ReactComponent3` are not satisfied,
there will be no instance of `Feature` at all.
When `ReactComponent2` starts to be rendered:

```
ReactComponent1 -> ReactComponent2
```

a new instance of `Feature` is created.
When `ReactComponent3` starts to be rendered as well:

```
ReactComponent1 -> ReactComponent2 -> ReactComponent3
```

the previous instance of `Feature` will be reused in both `ReactComponent2` and `ReactComponent3`.
Only when both `ReactComponent2` and `ReactComponent3` will be released again,
it will be possible to get a fresh instance of `Feature`.

#### usePrevious

The functionality of the
[usePrevious](utility/modules/react/src/jsMain/kotlin/io/github/andrewk2112/utility/react/hooks/usePrevious.kt) hook
can be illustrated with the following code:

```kotlin
var currentValue by useState(0)
val previousValue = usePrevious(currentValue)
p {
    +"Current = $currentValue"
}
p {
    +"Previous = $previousValue"
}
button {
    +"Increment"
    onClick = {
        ++currentValue
    }
}
```

Each time after tapping on the "Increment" button it will re-render both values
and the previous one will always be less by one.

</details>

<details id=react-dom>
<summary><b>React - DOM</b></summary>

Supported only for browser JavaScript projects.

```
io.github.andrew-k-21-12.utility:react-dom:1.0.0
```

Includes a bit of small DOM-related
[extensions](utility/modules/react-dom/src/jsMain/kotlin/io/github/andrewk2112/utility/react/dom/extensions).

</details>

<details id=string>
<summary><b>String</b></summary>

Supported platforms:

- JS (browser)
- JVM

```
io.github.andrew-k-21-12.utility:string:1.0.0
```

Includes a bit of tiny `String` extensions and variants of the
[changeFormat](utility/modules/string/src/commonMain/kotlin/io/github/andrewk2112/utility/string/formats/changeFormat.kt)
extension. The latter one allows to convert `String`s between some common formats, for example:

```kotlin
// Turns "CamelCaseString" into "camel_case_string".
"CamelCaseString".changeFormat(CamelCase, SnakeCase)
```

</details>

# Repository structure

- **dependencies** - stores all dependencies (both Kotlin and JavaScript) with their versions as TOML catalogs:
  this is a source of truth for all dependencies, if you want to upgrade one, do it here
- **frontend** - contains multiple modules representing the framework itself:
  [buildscript](frontend/modules/buildscript) - with all buildscript sources to be applied in Gradle,
  [core](frontend/modules/core) - with all libraries providing **kjs-box** features,
  [utility](frontend/modules/utility) - with all supporting tools
- **frontend-example** - provides a compilation of various demos and use cases - see the [Demos](#demos) section
- **backend-example** - shows an example of backend
  providing compression and caching features for **kjs-box** single page applications
- **utility** - a compilation of independent libraries which can be used in any projects,
  see [Utility libraries](#utility-libraries) for details
- **version-catalogs-generator** - a Gradle plugin to generate Kotlin sources describing TOML catalogs provided to it,
  see its example usage [here](frontend/modules/buildscript/version-catalogs/build.gradle.kts)
- **github-packages-publisher** - a Gradle utility plugin
  to simplify publishing of all **kjs-box** libraries and plugins to GitHub Packages, see the docs for
  [GithubPackagesPublisherPlugin](github-packages-publisher/src/main/kotlin/io/github/andrewk2112/githubpackagespublisher/GithubPackagesPublisherPlugin.kt)
  to get some basic insights on it
- **publishing.properties** - just contains a `github.publish.url` config
  pointing where to publish all required packages
