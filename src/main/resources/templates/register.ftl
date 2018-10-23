[#ftl]
[#-- @ftlvariable name="loggedIn" type="boolean" --]
[#include "_macros/base.ftl"]
[@base title="Register"]
  <form method="post" action="/register" class="p-3">
    [#if !loggedIn]
      <p>Already Have an account? <a href="/login">Login</a> first. (You can register for this site using an existing account).</p>

      [@textControl name="email" displayName="Email"/]
      [@textControl name="password" displayName="Password" type="password"/]
      [@textControl name="confirmPassword" displayName="Confirm Password" type="password"/]
    [#else]
      <p>You already have an account. Click the button to register for this site.</p>
    [/#if]
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-primary">Register</button>
  </form>
[/@base]