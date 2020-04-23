$(function () {

    const form = $('#contactForm2');

    const formMessages = $('.form-message');
    $(form).submit(function (e) {

        e.preventDefault();
        const formData = $(form).serialize();

        if ($('#message').val() !== '' && $('#email').val() !== ''
            && $('#subject').val() !== '' && $('#name').val() !== '') {

            $.ajax({
                type: 'POST',
                url: $(form).attr('action'),
                data: formData
            }).done(function (response) {

                $(formMessages).removeClass('error');
                $(formMessages).addClass('success');
                $(formMessages).text(response);
                $('#contact-form input,#contact-form textarea').val('');
            }).fail(function (data) {
                $(formMessages).removeClass('success');
                $(formMessages).addClass('error');
                if (data.responseText !== '') {
                    $(formMessages).text(data.responseText);
                } else {
                    $(formMessages).text('Oops! An error occurred and your message could not be sent.');
                }
            });
        }
    });

});