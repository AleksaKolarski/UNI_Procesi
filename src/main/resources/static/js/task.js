var title;
var taskId;
var taskName;
var taskDescription;
var taskForm;
var taskFormButton;
var taskAssigned;
var taskClaimButton;
var fileFieldNumber = 0;

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
      taskAssigned = data.assignee;

      $.ajax({
        url: '/projekat/task/' + taskId + '/form',
        method: 'GET',
        success: function(data, status, xhr){
          render_form(data);
        }
      });
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
    html += '<div class="class_form_field" id="id_form_field_div_'+ formField.id +'">' +
              '<p>' + formField.label + ':</p>';

    if(formField.typeName == 'string'){
      if(formField.properties.enumSource != null){
        formField.typeName = 'enum';
      }
      if(formField.properties.isFileUpload != null){
        if(formField.properties.isFileUpload == "true"){
          formField.typeName = 'fileUpload';
        }
      }
      if(formField.properties.isFileDownload != null){
        if(formField.properties.isFileDownload == 'true'){
          formField.typeName = 'fileDownload';
        }
      }
      if(formField.properties.isDate != null){
        if(formField.properties.isDate == 'true'){
          formField.typeName = 'date';
        }
      }
    }
    switch(formField.typeName){
      case 'string':
        html += '<input class="class_text" id="id_form_field_input_'+ formField.id +'" type="text" value="'+ (formField.defaultValue!=null?formField.defaultValue:'') +'" />';
        break;
      case 'long':
        html += '<input class="class_text" id="id_form_field_input_'+ formField.id +'" type="number" step="1" value="'+ (formField.defaultValue!=null?formField.defaultValue:'') +'" />';
        break;
      case 'boolean':
        html += '<input class="class_boolean" id="id_form_field_input_'+ formField.id +'" type="checkbox" '+ (formField.defaultValue == true?'checked':'') +' />';
        break;
      case 'date':
        html += '<input id="id_form_field_input_'+ formField.id +'" type="date" value="'+ (formField.defaultValue!=null?formField.defaultValue:'') +'"/>';
        break;
      case 'enum':
        html += '<select class="class_enum" id="id_form_field_input_'+ formField.id +'">';
        if(formField.properties.enumSource == null){
          var keys = Object.keys(formField.type.values);
          keys.forEach(key => {
            var value = formField.type.values[key];
            html += '<option value="'+ key +'" '+ (key==formField.defaultValue?'selected':'') +'>'+ value +'</option>';
          });
        }
        else{
          $.ajax({
            url: '/projekat/' + formField.properties.enumSource,
            method: 'GET',
            success: function(data, status, xhr){
              data.forEach(one => {
                $('#id_form_field_input_' + formField.id).append('<option value="'+ one.key +'" '+ (one.key==formField.defaultValue?'selected':'') +'>'+ one.value +'</option>');
              });
            }
          });
        }
        html += '</select>';
        break;
      case 'fileUpload':
        html += '<input id="id_form_field_input_'+ formField.id +'" type="file" name="'+ formField.id +'" />';
        fileFieldNumber++;
        break;
      case 'fileDownload':
        html += '<a href="/projekat/file/download/'+ formField.defaultValue +'">Download</a>';
        break;
    }
    html += '</div>';
    taskForm.append(html);

    add_validation_constraints(formField);
  });
  
  if(taskAssigned == null){
    taskForm.append('<button type="button" class="class_button" id="id_button_claim">Claim task</button>');
  }
  else{
    taskForm.append('<button type="button" class="class_button" id="id_button_complete">Complete</button>');
  }

  taskClaimButton = $('#id_button_claim');
  taskFormButton = $('#id_button_complete');

  taskClaimButton.on('click', function(e){
    $.ajax({
      url: '/projekat/task/'+ taskId +'/claim',
      method: 'POST',
      success: function(data, status, xhr){
        window.location.href = '/tasks.html';
      }
    });
  });

  taskFormButton.on('click', function(e){
    var good = true;
    formFields.forEach(formField => {
      if(check_validation_constraints(formField) == false){
        good = false;
      }
    });
    if(good == false){
      return;
    }
    var fields = [];
    formFields.forEach(formField => {
      var readonly = false;
      formField.validationConstraints.forEach(constraint => {
        if(constraint.name == 'readonly'){
          readonly = true;
        }
      });
      if(readonly == true){
        return;
      }
      if(formField.typeName == 'boolean'){
        fields.push({ 'fieldId': formField.id, 'fieldValue': $('#id_form_field_input_' + formField.id).is(':checked'), 'fieldType': formField.typeName })
      }
      /*
      else if(formField.typeName == 'date'){
        
        var date = new Date($('#id_form_field_input_' + formField.id).val())
        if(date == 'Invalid Date'){
          fields.push({ 'fieldId': formField.id, 'fieldValue': null, 'fieldType': formField.typeName })
        }
        else{
          //fields.push({ 'fieldId': formField.id, 'fieldValue': date.getFullYear()+'-'+('0'+date.getMonth()).slice(-2)+'-'+('0'+date.getDay()).slice(-2)), 'fieldType': formField.typeName })
          fields.push({ 'fieldId': formField.id, 'fieldValue': ('0'+date.getDay()).slice(-2)+'/'+('0'+date.getMonth()).slice(-2)+'/'+date.getFullYear(), 'fieldType': formField.typeName })
        }
      }
      */
      else{
        fields.push({ 'fieldId': formField.id, 'fieldValue': $('#id_form_field_input_' + formField.id).val(), 'fieldType': formField.typeName })
      }
    });

    if(fileFieldNumber == 0){
      formPost(fields);
    }

    formFields.forEach(formField => {
      if(formField.typeName == 'fileUpload'){
        var file = $('#id_form_field_input_' + formField.id).prop('files')[0];
        var data = new FormData();
        data.append('doc', file);
        $.ajax({
          method: 'POST', 
          url: '/projekat/file/upload', 
          data: data, 
          cache: false, 
          contentType: false, 
          processData: false, 
          success: function(fileCode, status, xhr){
            fileFieldNumber--;
            fields.push({ 'fieldId': formField.id, 'fieldValue': fileCode, 'fieldType': formField.typeName });
            if(fileFieldNumber == 0){
              formPost(fields);
            }
          }
        });
      }
    });
  });
}

function formPost(fields){
  $.ajax({
    url: 'projekat/task/' + taskId,
    method: 'POST',
    data: JSON.stringify(fields),
    contentType: 'application/json',
    success: function(data, status, xhr){
      window.location.href = '/tasks.html';
    }
  });
}

function add_validation_constraints(formField){
  var formFieldInput = $('#id_form_field_input_' + formField.id);
  var minlength = null;
  var maxlength = null;
  var min = null;
  var max = null;
  if(taskAssigned == null){
    formField.validationConstraints.push({ name:'readonly', configuration:'true' });
  }
  formField.validationConstraints.forEach(constraint => {
    switch (constraint.name) {
      case 'required':
        minlength = 1;
        break;
      case 'minlength':
        if(minlength == 1 && constraint.configuration < 1){
          minlength = 1;
          break;
        }
        minlength = constraint.configuration;
        break;
      case 'maxlength':
        maxlength = constraint.configuration;
        break;
      case 'min':
        min = constraint.configuration;
        break;
      case 'max':
        max = constraint.configuration;
        break;
      case 'readonly':
        formFieldInput.prop('readonly', true);
        formFieldInput.prop('disabled', true);
        if(formFieldInput.val().length == 0 || $.trim( formFieldInput.val() ) == ''){
          $('#id_form_field_div_'+ formField.id).css('display', 'none');
        }
        break;
    }
  });
  if(minlength != null && maxlength != null){
    add_validation_text(formFieldInput, minlength, maxlength);
  }
  else{
    if(minlength != null){
      add_validation_text_min(formFieldInput, minlength);
    }
    if(maxlength != null){
      add_validation_text_max(formFieldInput, maxlength);
    }
  }
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

function check_validation_constraints(formField){
  var formFieldInput = $('#id_form_field_input_' + formField.id);
  var minlength = null;
  var maxlength = null;
  var min = null;
  var max = null;
  formField.validationConstraints.forEach(constraint => {
    switch (constraint.name) {
      case 'required':
        minlength = 1;
        break;
      case 'minlength':
        if(minlength == 1 && constraint.configuration < 1){
          minlength = 1;
          break;
        }
        minlength = constraint.configuration;
        break;
      case 'maxlength':
        maxlength = constraint.configuration;
        break;
      case 'min':
        min = constraint.configuration;
        break;
      case 'max':
        max = constraint.configuration;
        break;
    }
  });
  if(minlength != null && maxlength != null){
    if(check_text(formFieldInput, minlength, maxlength) == false){
      return false;
    }
  }
  else{
    if(minlength != null){
      if(check_text_min(formFieldInput, minlength) == false){
        return false;
      }
    }
    if(maxlength != null){
      if(check_text_max(formFieldInput, maxlength) == false){
        return false;
      }
    }
  }
  if(min != null && max != null){
    if(check_number(formFieldInput, min, max) == false){
      return false;
    }
  }
  else{
    if(min != null){
      if(check_number_min(formFieldInput, min) == false){
        return false;
      }
    }
    if(max != null){
      if(check_number_max(formFieldInput, max) == false){
        return false;
      }
    }
  }
  return true;
}