$(document).ready(function() {
    let API = function () {
        return {
            getUsers: function (callback) {
                $.get("/api/users", callback)
            },
            saveUser: function (user, callback) {
                $.ajax({
                    url: '/api/add',
                    type: 'POST',
                    data: JSON.stringify(user),
                    // contentType: "application/json; charset=utf-8",
                    success: callback
                });
            },
            deleteUser: function (id, callback) {
                $.ajax({
                    url: '/api/delete/' + id,
                    type: 'DELETE',
                    success: callback
                });
            }
        }
    }

    $(function () {
        let api = API();

        function updateUsers() {
            let tbody = $('#all-users-in-admin-panel');
            tbody.empty();
            api.getUsers(function (users) {
                users.forEach(function (user) {
                    let tr = $('<tr/>')
                        .append($('<td/>').text(user.id))
                        .append($('<td/>').text(user.name))
                        .append($('<td/>').text(user.age))
                        .append($('<td/>').text(user.email))
                        .append($('<td/>').text(user.password).hide())
                        .append($('<td/>')
                            .append($('<span/>').text(user.role.join(', '))))
                        .append($('<td/>')
                            .append('<button class="btn btn-info btn-sm editBtn" data-target="#editModal" data-toggle="modal" type="button">Edit</button>'))
                        .append($('<td/>')
                            .append('<button class="btn btn-danger btn-sm deleteBtn" data-target="#deleteModal" data-toggle="modal" type="button">Delete</button>'));
                    tbody.append(tr);
                });

                $('.editBtn').click(function () {
                    let editModal = $('#editModal');
                    let tdArray = $(this).parent().parent().find('td');
                    editModal.find('#userId').val(tdArray[0].innerText);
                    editModal.find('#userName').val(tdArray[1].innerText);
                    editModal.find('#userAge').val(tdArray[2].innerText);
                    editModal.find('#userEmail').val(tdArray[3].innerText);
                    editModal.find('#userPassword').val(tdArray[4].innerText);
                    let userRoles = tdArray[5].innerText.split(", ");
                    editModal.find('#userRoles option').each(function () {
                        $(this).attr('selected', userRoles.includes($(this).text()));
                    });
                    let editButton = editModal.find('#userEditButton');
                    editButton.off('click');
                    editButton.click(function () {
                        let user = {};
                        editModal.find('input').each(function () {
                            user[$(this).attr('name')] = $(this).val();
                        });
                        let userRolesSelect = editModal.find('#userRoles');
                        user[userRolesSelect.attr('name')] = userRolesSelect.find('option:selected').map(function () {
                            return $(this).val();
                        }).toArray();
                        api.saveUser(user, function (responseUser) {
                            editModal.find('#userCloseButton').click();
                            updateUsers();
                        });
                    });
                });

                $('.deleteBtn').click(function () {
                    let deleteModal = $('#deleteModal');
                    let tdArray = $(this).parent().parent().find('td');
                    deleteModal.find('#idDelete').val(tdArray[0].innerText);
                    deleteModal.find('#nameDelete').val(tdArray[1].innerText);
                    deleteModal.find('#ageDelete').val(tdArray[2].innerText);
                    deleteModal.find('#emailDelete').val(tdArray[3].innerText);
                    deleteModal.find('#passwordDelete').val(tdArray[4].innerText).hide();
                    let userRoles = tdArray[6].innerText.split(", ");
                    deleteModal.find('#rolesDelete option').each(function () {
                        $(this).attr('selected', userRoles.includes($(this).text()));
                    });
                    let deleteButton = deleteModal.find('#userDeleteButton');
                    deleteButton.off('click');
                    deleteButton.click(function () {
                        api.deleteUser(tdArray[0].innerText, function () {
                            deleteModal.find('#userCloseButtonDelete').click();
                            updateUsers();
                        });
                    });
                });

                $('.addUser').click(function () {
                    let addUser = $('#nav-profile');
                    let addButton = addUser.find('#addUserButton');
                    addButton.off('click');
                    addButton.click(function () {
                        let user = {};
                        addUser.find('input').each(function () {
                            user[$(this).attr('name')] = $(this).val();
                        });
                        let userRolesSelect = addUser.find('#roleAdd');
                        user[userRolesSelect.attr('name')] = userRolesSelect.find('option:selected').map(function () {
                            return $(this).val();
                        }).toArray();
                        api.saveUser(user, function (responseUser) {
                            addUser.find('input').val('');
                            updateUsers();
                            $('#nav-home-tab').tab('show');

                        });
                    });
                });

            });
        }

        updateUsers();
    });
});