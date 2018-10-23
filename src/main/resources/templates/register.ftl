[#ftl]
[#-- @ftlvariable name="loggedIn" type="boolean" --]
[#include "_macros/base.ftl"]
[@base title="Register"]
  <form method="post" action="/register">
    [#if !loggedIn]
      <p>Already Have an account? <a href="/login">Login</a> first. (You can register for this site using an existing account).</p>

      <div class="form-group">
        <label>
          Email
          <input type="text" name="email">
        </label>
      </div>

      <div class="form-group">
        <label>
          Password
          <input type="password" name="password">
        </label>
      </div>

      <div class="form-group">
        <label>
          Confirm Password
          <input type="password" name="confirmPassword">
        </label>
      </div>

    [#else]
      You already have an account. Click the button to register for this site.
    [/#if]
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-primary">Register</button>
  </form>
[/@base]