/**
 * Precompiled [xyz.thingummy.lib-java-conventions.gradle.kts][Xyz_thingummy_lib_java_conventions_gradle] script plugin.
 *
 * @see Xyz_thingummy_lib_java_conventions_gradle
 */
public
class Xyz_thingummy_libJavaConventionsPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Xyz_thingummy_lib_java_conventions_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
