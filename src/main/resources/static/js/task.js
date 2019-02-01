var title;
var taskId;
var taskName;
var taskDescription;
var taskForm;
var taskFormButton;

$(document).ready(function(e){

  init_search_params();

  title = $('#id_title');
  taskName = $('#id_task_name');
  taskDescription = $('#id_task_description');
  taskForm = $('#id_form');

  $.ajax({
    url: '/projekat/task/' + taskId,
    method: 'GET',
    success: function(data, status, xhr){
      title.text(data.name);
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

function render_form(formFields){
  formFields.forEach(formField => {
    var html = '';
    html += '<div id="id_form_field">' +
              '<p>' + formField.label + '</p>';
    switch(formField.typeName){
      case 'string':
        html += '<input id="id_form_field_input_'+ formField.id +'" type="text" value="'+ (formField.defaultValue!=null?formField.defaultValue:'') +'">'
        break;
      case 'long':
        html += '<input id="id_form_field_input_'+ formField.id +'" type="number" step="1" value="'+ (formField.defaultValue!=null?formField.defaultValue:'') +'">';
        break;
      case 'boolean':
        html += '<input id="id_form_field_input_'+ formField.id +'" type="checkbox" '+ (formField.defaultValue == 'true'?'checked':'') +'>';
        break;
      case 'date':
        html += '<input id="id_form_field_input_'+ formField.id +'" type="date">';
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
    taskForm.append(html);

    get_validation_constraints(formField);
  });
  
  taskForm.append('<button type="button" id="id_button_complete">Complete</button>');

  taskFormButton = $('#id_button_complete');

  taskFormButton.on('click', function(e){
    // AJAX
  });
}

function get_validation_constraints(formField){
  var formFieldInput = $('#id_form_field_input_' + formField.id);
  var min = null;
  var max = null;
  formField.validationConstraints.forEach(constraint => {
    switch (constraint.name) {
      case 'required':
        add_validation_text_required(formFieldInput);
        break;
      case 'minlength':
        add_validation_text_min(formFieldInput, constraint.configuration);
        break;
      case 'maxlength':
        add_validation_text_max(formFieldInput, constraint.configuration);
        break;
      case 'min':
        min = constraint.configuration;
        break;
      case 'max':
        max = constraint.configuration;
        break;
      case 'readonly':
        formFieldInput.prop('readonly', true);
        break;
      default:
        break;
    }
  });
  if(min != null && max != null){
    add_validation_number(formFieldInput, min, max);
  }
  else{
    if(min != null){
      add_validation_number_min(formFieldInput, min);
    }
    if(max != null){
      add_validation_number_max(formFieldInput, max);
    }
  }
}