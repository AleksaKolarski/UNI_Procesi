$(document).ready(function(e){
  $.ajax({
    url: '/projekat/user/currentUser',
    method: 'GET',
    success: function(user, status, xhr){
      var header = $('#id_div_header');
      var html = '';
      html += '<p>'+ user.firstname +' '+ user.lastname +'</p>';
      html += '<button type="button" id="id_button_logout">Logout</button>';
      header.html(html);

      $('#id_button_logout').on('click', function(e){
        $.ajax({
          url: '/api/admin/auth/user/default/logout',
          method: 'POST',
          success: function(data, status, xhr){
            window.location.href = '/login.html';
          }
        });
      });
    }
  });
});