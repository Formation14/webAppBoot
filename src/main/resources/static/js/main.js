$(document).ready(function () {

    $('.nBtn, .table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text(); //return New or Edit

        if (text === 'Edit') {
            $.get(href, function (user, status) {
                $('.myForm #id').val(user.id);
                $('.myForm #username').val(user.username);
                $('.myForm #age').val(user.age);
                $('.myForm #password').val(user.password);
                $('.myForm #email').val(user.email);
                $('.myForm #role').val(user.role);
            });
            $('.myForm #exampleModal').modal();
        } else {
            $('.myForm #id').val('');
            $('.myForm #username').val('');
            $('.myForm #age').val('');
            $('.myForm #password').val('');
            $('.myForm #email').val('');
            $('.myForm #role').val('');
            $('.myForm #exampleModal').modal();
        }
    });

    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });
});