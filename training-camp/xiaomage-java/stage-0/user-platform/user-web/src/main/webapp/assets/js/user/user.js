let modalTitle = $('.modal-title');
let modalBody = $('.modal-body');
let modalFooter = $('.modal-footer');
let closeBookModal = $(
    '<button id="close-book-modal" type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>');

function viewUser(commonModal, userId) {
  let viewUserTableHidden = $('#viewUserTable:hidden');
  $(commonModal).find(modalTitle).text('View User');
  $(commonModal).find(modalBody).html(viewUserTableHidden.clone());
  let viewUserTable = $('#viewUserTable');
  $(commonModal).find(viewUserTable).css('display', 'block');
  $(commonModal).find(modalFooter).append(closeBookModal);

  fetch('users/view?id=' + userId)
  .then(response => ({status: response.status, data: response.json()}))
  .then(response => {
    if (response.status == 200) {
      response.data.then(value => {
        $(commonModal).find('#user-id').text(value.id);
        $(commonModal).find('#user-name').text(value.name);
        $(commonModal).find('#user-password').text(value.password);
        $(commonModal).find('#user-email').text(value.email);
        $(commonModal).find('#user-phone').text(value.phoneNumber);
      });
    } else if (response.status == 404) {
      response.data.then(value => {
        $(commonModal).find('.modal-body').html(value.message);
      });
    } else {
      $(commonModal).find('.modal-body').html(
          'An unknown error occurred. Please contact Administrator.');
    }
  })
  .catch(error => {
    console.log('Request failed', error);
    $(commonModal).find('.modal-body').html(
        'An unknown error occurred. Please contact Administrator.');
  });
}

function clearModal(modal) {
  $(modal).find(modalTitle).html('');
  $(modal).find(modalBody).html('');
  $(modal).find(modalFooter).html('');
}

$(document).ready(() => {
  $('#commonModal').on('show.bs.modal', function (event) {
    var commonModal = $(this);
    var button = $(event.relatedTarget);
    var userAction = button.data('user-action');
    var userId = button.data('user-id');

    switch (userAction) {
      case 'addUser':
        addUser(commonModal);
        break;

      case 'viewUser':
        viewUser(this, userId);
        break;
    }
  }).on('hidden.bs.modal', function (event) {
    clearModal(this);
  });
});