[#ftl/]
[#-- @ftlvariable name="user" type="org.springframework.security.oauth2.core.oidc.user.OidcUser" --]
[#include "_macros/base.ftl"/]
[@base title="Home"]
<div class="jumbotron">
  <h1>Home page</h1>
  <p>Everybody can see this content. It is the landing page of our app.</p>

    [#if loggedIn && !hasRoles]
  <div class="alert alert-warning">
    <h4 class="alert-heading">Warning:</h4>
    <div class="alert-body">You are logged in but not registered. Click the register link above!</div>
      [/#if]
  </div>
    [/@base]
