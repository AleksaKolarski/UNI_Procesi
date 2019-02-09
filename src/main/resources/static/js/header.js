$(document).ready(function(e){
  $.ajax({
    url: '/projekat/user/currentUser',
    method: 'GET',
    success: function(user, status, xhr){
      var header = $('#id_div_header');
      var html = '';
      html += '<p>'+ user.firstName +' '+ user.lastName +'</p>';
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
    },
    error: function(xhr, status, error){
      if(xhr.status == 404 || xhr.status == 401){
        window.location.href = '/login.html';
      }
    }
  });
});