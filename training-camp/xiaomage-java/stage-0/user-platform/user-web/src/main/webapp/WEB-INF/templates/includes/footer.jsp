<script src="${pageContext.request.contextPath}/assets/vendor/jquery/jquery-3.3.1.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/4.1.3/js/bootstrap.min.js"></script>

<script type="application/javascript">

  $(document).ready(() => {
    getApplicationName();
  });

  function getApplicationName() {
    fetch('main/application/name')
    .then(response => ({status: response.status, data: response.json()}))
    .then(response => {
      if (response.status == 200) {
        response.data.then(value => {
          $("#application-name").text(value.applicationName);
        });
      } else if (response.status == 404) {
        response.data.then(value => {
          $("#application-name").text('404');
        });
      } else {
        $("#application-name").text('Error');
      }
    })
    .catch(error => {
      $("#application-name").text('Error');
    });
  }

</script>

</body>
</html>

