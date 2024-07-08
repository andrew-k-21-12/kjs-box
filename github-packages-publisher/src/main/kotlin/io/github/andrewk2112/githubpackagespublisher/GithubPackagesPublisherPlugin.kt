package io.github.andrewk2112.githubpackagespublisher

import io.github.andrewk2112.githubpackagespublisher.extensions.Properties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.io.FileNotFoundException
import java.io.IOException
import java.util.Properties

/**
 * Adds GitHub Packages publishing tasks for a [Project].
 *
 * Reads publishing configs with a higher priority from a file specified by Gradle properties first,
 * tries to read publishing configs from Gradle properties as a fallback.
 *
 * Published artifacts include sources downloadable for clients.
 *
 * No utility or other modules from the main project are used to avoid circular dependencies.
 */
internal class GithubPackagesPublisherPlugin : Plugin<Project> {

    // Public.

    @Throws(Exception::class)
    override fun apply(target: Project) {
        val publishConfigs = try {
            getPublishConfigs(target, readCustomPublishProperties(target))
        } catch (exception: Exception) {
            logPublishConfigsException(target, exception)
            return
        }
        target.plugins.apply(MavenPublishPlugin::class.java)
        // Strictly required to detect plugin projects and avoid empty metadata for publishing of non-plugin projects.
        target.afterEvaluate {
            target.extensions.configure(PublishingExtension::class.java) { publishing ->
                // Simple JVM projects strictly require manual configuration, or no artifacts will be published.
                if (isNonPluginProject(target) && isNonKotlinMultiPlatformProject(target)) {
                    configureArtifactPublication(target, publishing)
                }
                configurePublishingRepository(publishing, publishConfigs)
            }
        }
    }



    // Private.

    /**
     * Tries to read custom publish properties if a path to them was specified in Gradle properties,
     * returns `null` otherwise.
     */
    @Throws(FileNotFoundException::class, IOException::class)
    private fun readCustomPublishProperties(project: Project): Properties? =
        project.properties["github.publish.properties"]?.toString()
                                                       ?.let { Properties(it) }

    /**
     * Gets all required [PublishConfigs], see [getPublishConfig].
     *
     * @throws IllegalStateException If any of required configs is missing.
     */
    @Throws(IllegalStateException::class)
    private fun getPublishConfigs(project: Project, customPublishProperties: Properties?) = PublishConfigs(
        repositoryUrl = getPublishConfig(project, customPublishProperties, "github.publish.url"),
        username      = getPublishConfig(project, customPublishProperties, "github.publish.username"),
        password      = getPublishConfig(project, customPublishProperties, "github.publish.password")
    )

    /**
     * Gets a config value from [customPublishProperties] by the [key],
     * tries to get the same config from [Project.getProperties] if it's not present in the [customPublishProperties].
     *
     * @throws IllegalStateException If no config with the [key] was found.
     */
    @Throws(IllegalStateException::class)
    private fun getPublishConfig(project: Project, customPublishProperties: Properties?, key: String): String =
        customPublishProperties?.getProperty(key)
            ?: project.properties[key]?.toString()
            ?: throw IllegalStateException("No required config present to configure GitHub Packages publishing: $key")

    /**
     * Logs any kinds of [exception]s describing why the preparation of [PublishConfigs] has failed.
     */
    private fun logPublishConfigsException(project: Project, exception: Exception) {
        project.logger.run {
            warn(
                "No configs for GitHub Packages publishing are provided or they can't be read:\n" +
                        "it's OK if you are not a publisher, otherwise inspect it with \"--info\" option"
            )
            info("The publishing has failed to be configured because:", exception)
        }
    }

    /**
     * Checks if the [project] doesn't declare any Gradle plugin.
     */
    private fun isNonPluginProject(project: Project): Boolean =
        project.extensions.findByType(GradlePluginDevelopmentExtension::class.java) == null

    /**
     * Checks if the [project] is not configured as a Kotlin Multiplatform one.
     */
    private fun isNonKotlinMultiPlatformProject(project: Project): Boolean =
        project.extensions.findByType(KotlinMultiplatformExtension::class.java) == null

    /**
     * Setups all metadata to publish the target [project] with.
     */
    private fun configureArtifactPublication(project: Project, publishing: PublishingExtension) {
        publishing.publications {
            it.create("maven", MavenPublication::class.java) { publication ->
                publication.apply {
                    groupId    = project.group.toString()
                    artifactId = project.name
                    version    = project.version.toString()
                    from(project.components.findByName("java")) // strictly required or no artifacts will be published
                }
            }
        }
    }

    /**
     * Applies all configurations to reach the destination repository for publishing.
     */
    private fun configurePublishingRepository(publishing: PublishingExtension, publishConfigs: PublishConfigs) {
        publishing.repositories.maven { repository ->
            repository.apply {
                name = "GitHubPackages"
                setUrl(publishConfigs.repositoryUrl)
                credentials {
                    it.username = publishConfigs.username
                    it.password = publishConfigs.password
                }
            }
        }
    }

}
