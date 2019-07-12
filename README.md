FusionAuth Spring Security Example
====
A quick and easy example of how to use [OpenId Connect](https://openid.net/connect/) to integrate a spring application with [FusionAuth](https://fusionauth.io).

Usage
----

1. Download and install FusionAuth
1. [Create an Application](https://fusionauth.io/docs/v1/tech/tutorials/create-an-application)
    1. While you are creating an application, create two roles user and admin
    1. Set your redirect url to `http://localhost:8081/login/oauth2/code/` but with respect to the scheme/host/port you are using.
    1. Add `http://localhost:8081/` as the logout url.
    1. Click save (blue icon at the top right)
1. Copy `application-example.properties` to `application.properties`
1. Copy your Client id and Client secret from the Application configuration into `application.properties`
 under the `fusionAuth.clientId` and `fusionAuth.clientSecret` properties (respectively).
1. Copy your FusionAuth Application ID into `fusionAuth.applicationId`
1. Modify the existing `localhost:9011` urls to be the location of your fusion auth instance if it is not running locally.
1. Start the example with `mvn spring-boot:run` and navigate to [http://localhost:8081](http://localhost8081)

TODO new blog entry

TODO add these steps:
1. Spring does not support our default keys, you have to use RS256 for access token AND Id token signing
1. Spring does not support a non URL issuer, so configure FusionAuth to use its full url as the issuer. (Or whatever url you like, but it must be fully qualified)
(This technically breaks the spec, but oh well...)

TODO Add all the details for a full setup to work

TODO Add details on how logout works now (We logout locally, then remotely, then come back to landing page)

TODO Talk about the built in/self registration via fusionauth, also test the registration stuff locally

License
----
Some portions of this code were forked/based on the code available here: https://github.com/eugenp/tutorials
which are licensed under MIT. The full license is available under [LICENSE](LICENSE).

TODO Add our own license and make it obvious we referenced eugenp
