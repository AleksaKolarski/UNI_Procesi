var div_tasks;

$(document).ready(function(e){

  div_tasks = $('#id_div_tasks');

  $.ajax({
    url: '/projekat/task/currentUser',
    success: function(data, status, xhr){
      render_tasks(data);
    }
  });
});

function render_tasks(tasks){
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
  div_tasks.html(html);
}