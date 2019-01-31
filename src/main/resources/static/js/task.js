var taskId;
var taskName;
var taskDescription;
var taskForm;

$(document).ready(function(e){

  init_search_params();

  taskName = $('#id_task_name');
  taskDescription = $('#id_task_description');
  taskForm = $('#id_form');

  $.ajax({
    url: '/projekat/task/' + taskId,
    method: 'GET',
    success: function(data, status, xhr){
      taskName.text(data.name);
      taskDescription.text(data.description);
    }
  });

  $.ajax({
    url: '/projekat/task/' + taskId + '/form',
    method: 'GET',
    success: function(data, status, xhr){
      render_form(data);
    }
  });
})

function init_search_params(){
  var searchParams;
  searchParams = new URLSearchParams(window.location.search);
  taskId = searchParams.get('taskId');
}

function render_task(task){

}

function render_form(formFields){
  console.log(formFields);
  var html = '';
  formFields.forEach(formField => {
    html += '<div id="id_form_field">' +
              '<p>' + formField.label + '</p>';
    
    switch(formField.typeName){
      case 'string':
        html += '<input type="text" value="'+ formField.defaultValue +'">'
        break;
      case 'long':
        html += '<input type="number" step="1" value="'+ formField.defaultValue +'">';
        break;
      case 'boolean':
        html += '<input type="checkbox" '+ (formField.defaultValue == true?'checked':'') +'>';
        break;
      case 'date':
        html += '<input type="date">';
        break;
      case 'enum':
        html += '<select>';
        
        var keys = Object.keys(formField.type.values);

        keys.forEach(key => {
          var value = formField.type.values[key];
          html += '<option value="'+ key +'">'+ value +'</option>';
        });
        break;
    }

    html += '</div>';
  });

  taskForm.html(html);
}