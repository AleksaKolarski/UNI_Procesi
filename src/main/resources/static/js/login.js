var input_username;
var input_password;
var button;

$(document).ready(function(e){
  input_username = $('#id_input_username');
  input_password = $('#id_input_password');
  button = $('#id_button_login');

  button.on('click', function(e){
    $.ajax({
      url: '/api/admin/auth/user/default/login/welcome',
      method: 'POST',
      data: { username: input_username.val(), password: input_password.val() },
      success: function(data, status, xhr){
        console.log(data);
        window.location.href = '/tasks.html';
      },
      beforeSend: function(xhr){
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('Accept', 'application/json, text/plain, */*');
      }
    });
  });
});