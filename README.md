# organicity-client

A java client implementation of the OrganiCity API for Android and Standalone Java 1.7 applications.

## Examples

To see how to use the OrganiCity Java Client check the [tests](client/src/test/java/eu/organicity/client/test)

## Usage via Maven for Java

To use this library you need to include in your maven project the following repository:

    <repositories>
        <repository>
            <id>organicity</id>
            <url>https://maven.organicity.eu/content/repositories/snapshots</url>
        </repository>
    </repositories>

And also the following dependency:

    <dependency>
        <groupId>eu.organicity</groupId>
        <artifactId>client</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    
## Usage via Gradle for Android

To use this library you need to include in your maven project the following repository:


    repositories {
        maven { url 'https://maven.organicity.eu/content/repositories/snapshots' }
    }

And also the following dependency:


    dependencies {
        compile('eu.organicity.client-parent:client-android:1.0-SNAPSHOT')
    }
