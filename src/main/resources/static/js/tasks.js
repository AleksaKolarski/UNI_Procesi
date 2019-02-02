var div_tasks_user;
var div_tasks_group;

$(document).ready(function(e){

  div_tasks_user = $('#id_div_tasks_user');
  div_tasks_group = $('#id_div_tasks_group');

  $.ajax({
    url: '/projekat/task/currentUser',
    method: 'GET', 
    success: function(data, status, xhr){
      render_tasks(div_tasks_user, data);
    }
  });

  $.ajax({
    url: '/projekat/task/currentUserGroup',
    method: 'GET', 
    success: function(data, status, xhr){
      render_tasks(div_tasks_group, data);
    }
  });
});

function render_tasks(target, tasks){
  var html = '';
  tasks.forEach(task => {
    html += '<div class="class_div_task">' + 
              '<a href="/task.html?taskId='+ task.id +'">' + 
                '<p>name: '+ task.name +'</p>' +
                '<p>'+ (task.created!=null?'created: '+ moment(task.created).format('DD.MMM.YYYY. hh:mm:ss'):'') +'</p>' + 
                '<p>'+ (task.due!=null?'due: '+ moment(task.due).format('DD.MMM.YYYY. hh:mm:ss'):'') +'</p>' + 
                '<p>'+ (task.description!=null?'description: ' + task.description:'') + '</p>' + 
              '</a>' + 
            '</div>';
  });
  target.append(html);
}