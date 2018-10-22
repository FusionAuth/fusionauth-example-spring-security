[#ftl]
[#-- @ftlvariable name="loggedIn" type="boolean" --]
[#include "_macros/base.ftl"]
[@base title="Register"]
  <form method="post" action="/register">
    [#if !loggedIn]
      <p>Already Have an account? <a href="/login">Login</a> first. (You can register for this site using an existing account).</p>
      <label>
        Username
        <input type="text" name="username">
      </label>

      <label>
        Email
        <input type="text" name="email">
      </label>

      <label>
        Password
        <input type="password" name="password">
      </label>
    [#else]
      You already have an account. Click the button to register for this site.
    [/#if]
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit">Register</button>
  </form>
[/@base]