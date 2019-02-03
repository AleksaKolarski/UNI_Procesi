$(document).ready(function(e){
  $.ajax({
    url: '/projekat/user/currentUser',
    method: 'GET',
    success: function(user, status, xhr){
      var header = $('#id_div_header');
      var html = '';
      html += '<p>'+ user.firstname +' '+ user.lastname +'</p>';
      html += '<button type="button">Logout</button>';
      header.html(html);
    }
  });
});