[#ftl/]
[#-- @ftlvariable name="user" type="org.springframework.security.oauth2.core.oidc.user.OidcUser" --]
[#include "_macros/base.ftl"/]
[@base title="Access Denied"]
  <div class="jumbotron">
    <h1>Access Denied</h1>
    <p>You do not have permission to access that page.</p>

      [#if loggedIn && !hasRoles]
        <div class="alert alert-warning">
          <h4 class="alert-heading">Warning:</h4>
          <div class="alert-body">You are logged in but not registered. Click the register link above!</div>
        </div>
      [/#if]
  </div>
[/@base]
