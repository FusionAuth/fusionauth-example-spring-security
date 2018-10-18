FusionAuth Spring Security Example
====
A quick and easy example of how to use [OpenId Connect](https://openid.net/connect/) to integrate a spring application with [FusionAuth](https://fusionauth.io).

Usage
----

- TODO Setup fusion auth and get client id and secret
- Copy `application-example.properties` to `application.properties`
- Modify the urls to match the urls of your fusion auth application. (It can run locally)
- Once you have your client id and secret, make sure you add them to the `application.properties` of the project

```
mvn spring-boot:run
```

License
----
Some portions of this code were forked/based on the code available here: https://github.com/eugenp/tutorials
which are licensed under MIT. The full license is available under [LICENSE](LICENSE).
