$(document).ready(function () {
    restartAllUser();
    $('.AddBtn').on('click', function (event) {
        let user = {
            username: $("#username1").val(),
            age: $("#age1").val(),
            email: $("#email1").val(),
            password: $("#password1").val(),
            roles: getRole("#selectRole")

        }
        console.log(user);
        fetch("api/newUser", {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(user)
        }).then(() => openTabById('nav-home'))
            .then(() => restartAllUser());
        $('input').val('');
    });
});

function createTableRow(u) {
    let mass = ''
    if (u.roles.length == 2) {
        mass = u.roles[0].role;
        mass += u.roles[1];
    } else {
        mass = u.roles[0];
    }

    return `<tr id="user_table_row">
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.age}</td>
            <td>${u.email}</td>
            <td>${mass.toString().replaceAll('ROLE_', ' ')}</td>
            <td>
            <a  href="/api/${u.id}" class="btn btn-info eBtn" >Edit</a>
            </td>
            <td>
            <a  href="/api/${u.id}" class="btn btn-danger delBtn">Delete</a>
            </td>
        </tr>`;
}

function getRole(address) {
    let data = [];
    $(address).find("option:selected").each(function () {
        data.push({id: $(this).val(), role: $(this).attr("name"), authority: $(this).attr("name")})
    });
    return data;
}

function restartAllUser() {
    let UserTableBody = $("#user_table_body")

    UserTableBody.children().remove();

    fetch("api/users")
        .then((response) => {
            response.json().then(
                data => data.forEach(function (item) {
                    let TableRow = createTableRow(item);
                    UserTableBody.append(TableRow);

                }));
        }).catch(error => {
        console.log(error);
    });
}

document.addEventListener('click', function (event) {
    event.preventDefault()

    if ($(event.target).hasClass('delBtn')) {
        let href = $(event.target).attr("href");
        $(".deleteUser #deleteModal").modal();
        $.get(href, function (user) {
            fetch('/api/' + user.id, {method: 'GET'})
                .then(function (response) {
                    response.json().then(function (user) {
                        console.log(user)
                        let userRole = "";
                        for (let i = 0; i < user.roles.length; i++) {
                            userRole += " " + user.roles[i].role.replace('ROLE_', '');
                        }
                        $(".deleteUser #idDel").val(user.id);
                        $(".deleteUser #usernameDel").val(user.username);
                        $(".deleteUser #ageDel").val(user.age);
                        $(".deleteUser #emailDel").val(user.email);
                        $(".deleteUser #passwordDel").val(user.password);
                        $(".deleteUser #selectRoleDel").val(userRole);

                    });
                });
        });
    }

    if ($(event.target).hasClass('delBtn2')) {
        let id = $('#idDel').val()
        delModalButton(id)
    }


    if ($(event.target).hasClass('eBtn')) {
        let href = $(event.target).attr("href");
        $(".editUser #exampleModal").modal();
        $.get(href, function (user) {
            fetch('/api/' + user.id, {method: 'GET'})
                .then(function (response) {
                    response.json().then(function (user) {
                        console.log(user);
                        $(".editUser #idEd").val(user.id);
                        $(".editUser #usernameEd").val(user.username);
                        $(".editUser #ageEd").val(user.age);
                        $(".editUser #emailEd").val(user.email);
                        $(".editUser #passwordEd").val(user.password);
                        $(".editUser #selectRoleEd").val(user.roles);
                    });
                });
        });
    }

    if ($(event.target).hasClass('editButton')) {
        let user = {
            id: $('#idEd').val(),
            username: $('#usernameEd').val(),
            age: $('#ageEd').val(),
            email: $('#emailEd').val(),
            password: $('#passwordEd').val(),
            roles: getRole("#selectRoleEd")

        }
        editModalButton(user)
        console.log(user);
    }

    if ($(event.target).hasClass('logout')) {
        logout();
    }
    if ($(event.target).hasClass('btnUserTable')) {
        userTable();
    }

});

function delModalButton(id) {
    fetch('api/' + id, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    }).then(function (response) {
        $('input').val('');
        $('.deleteUser #deleteModal').modal('hide');
        restartAllUser();
    })
}


function editModalButton(user) {
    fetch("api/edit", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('input').val('');
        $('.editUser #exampleModal').modal('hide');
        restartAllUser();
    })
}

function openTabById(tab) {
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
}

function logout() {
    document.location.replace("/logout");
}

function userTable() {
    document.location.replace("/user");
}
