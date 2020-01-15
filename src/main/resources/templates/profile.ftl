[#ftl/]
[#-- @ftlvariable name="user" type="org.springframework.security.oauth2.core.oidc.user.OidcUser" --]
[#include "_macros/base.ftl"]
[@base title="profile"]
  <div class="jumbotron">
    <h1>Profile</h1>

    <p>Only logged in users can see this.</p>

    Raw user: [${user}]
  </div>
[/@base]
