var user;

$(document).ready(function (e) {

  $.ajax({
    url: '/projekat/user/currentUser',
    method: 'GET',
    success: function (userDTO, status, xhr) {
      user = userDTO;

      if (user.groupId == 'students') {
        $.ajax({
          url: '/projekat/process/getByUser',
          method: 'GET',
          success: function (processIdList, status, xhr) {
            if (processIdList.length == 0) {
              $('#id_div_main').html('<p>Start process</p>' +
                '<button id="id_button_start" type="button">Start process</button>');
              $('#id_button_start').on('click', function (e) {
                $.ajax({
                  url: '/projekat/process/start',
                  method: 'GET',
                  success: function (data, status, xhr) {
                    window.location.href = '/tasks.html';
                  }
                });
              });
            }
            else {
              window.location.href = '/tasks.html';
            }
          }
        });
      }
      else{
        window.location.href = '/tasks.html';
      }
    },
    error: function () {
      window.location.href = '/login.html';
    }
  });

});