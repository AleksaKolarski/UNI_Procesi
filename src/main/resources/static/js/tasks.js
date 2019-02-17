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
    html += '<a href="/task.html?taskId='+ task.id +'">' + 
              '<div class="class_div_task">' + 
                '<p class="class_p_task_name">name: '+ task.name +'</p>' +
                '<p class="class_p_task_created">'+ (task.created!=null?'created: '+ moment(task.created.slice(0, -9)).format('DD.MMM.YYYY. hh:mm:ss'):'') +'</p>' + 
                (task.due!=null?'<p class="class_p_task_due">due: '+ moment(task.due.slice(0, -9)).format('DD.MMM.YYYY. hh:mm:ss') +'</p>':'') + 
                '<p class="class_p_task_description">'+ (task.description!=null?'description: ' + task.description:'') + '</p>' + 
              '</div>' + 
            '</a>';
  });
  target.append(html);
}