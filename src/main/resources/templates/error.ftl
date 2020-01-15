[#ftl/]
[#include "_macros/base.ftl"/]
[@base title="Error"]
  <div class="alert alert-danger">
    <h4 class="alert-heading">Something went wrong</h4>
    <fieldset>
      <legend>Status:</legend>
      <var>${status!'unknown'}</var>
    </fieldset>

    <fieldset>
      <legend>Error:</legend>
      <samp>${message!'unknown'}</samp>
    </fieldset>
  </div>
[/@base]

