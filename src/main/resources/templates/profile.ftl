[#ftl/]
[#-- @ftlvariable name="user" type="io.fusionauth.security.FusionAuthUserDetails" --]
[#include "_macros/base.ftl"]
[@base title="profile"]
  <h1>Profile</h1>

  Roles:
  <ul>
    [#list user.authorities as authority]
      <li>${authority}</li>
    [/#list]
  </ul>
[/@base]
