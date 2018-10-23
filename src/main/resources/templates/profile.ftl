[#ftl/]
[#-- @ftlvariable name="user" type="io.fusionauth.security.FusionAuthUserDetails" --]
[#include "_macros/base.ftl"]
[@base title="profile"]
  <div class="jumbotron">
    <h1>Profile</h1>

    <p>Only logged in users can see this.</p>

    Roles:
    <ul>
    [#list user.authorities as authority]
      <li>${authority}</li>
    [/#list]
    </ul>
  </div>
[/@base]
