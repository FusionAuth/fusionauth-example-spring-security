FusionAuth Spring Security Example
====
A quick and easy example of how to use [OpenId Connect](https://openid.net/connect/) to integrate a spring application with [FusionAuth](https://fusionauth.io).

Usage
----

1. Download and install FusionAuth
1. [Create an Application](https://fusionauth.io/docs/v1/tech/tutorials/create-an-application)
    1. While you are creating an application, create two roles user and admin
    1. Add a valid redirect URL to your OAuth configuration. For this example use `http://localhost:8081/login`.
    1. Add `http://localhost:8081/logout` as the logout url.
    1. Click save (blue icon at the top right)
1. Copy `application-example.properties` to `application.properties`
1. Copy your Client id and Client secret from the Application configuration into `application.properties`
 under the `fusionAuth.clientId` and `fusionAuth.clientSecret` properties (respectively).
1. Copy your FusionAuth Application ID into `fusionAuth.applicationId`
1. Modify the existing `localhost:9011` urls to be the location of your fusion auth instance if it is not running locally.
1. Start the example with `mvn spring-boot:run` and navigate to [http://localhost:8081](http://localhost8081)

For an in depth explanation and tutorial checkout our [blog](https://fusionauth.io/blog/2018/10/24/easy-integration-of-fusionauth-and-spring).

License
----
Some portions of this code were forked/based on the code available here: https://github.com/eugenp/tutorials
which are licensed under MIT. The full license is available under [LICENSE](LICENSE).
