StardogPetStore
===============

Showcase example of Stardog and Spring Framework

Key take aways from this example:

1. Take a look at the build.gradle file, note that mavenLocal appears as a higher priority than Maven Central. This is to make sure you pick up dependencies that were installed locally via the `mavenInstall` script.
2. the `DogDAO` class uses the `SnarlTemplate`, with an injected `DataSource`



## Building 

1. Download Stardog from stardog.com
2. Run `mavenInstall.sh` from the Stardog distribution
3. Make sure you have Gradle 1.9 installed
4. Run `gradle build`

In general, a Spring managed application with a Stardog database will look and feel much like any other Spring applicaiton.  There's a datasource, a SNARLTemplate, and some dependencies in your build. 


## License

Apache License 2.0 - https://www.apache.org/licenses/LICENSE-2.0.html



