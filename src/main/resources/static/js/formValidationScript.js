$(document).ready(main);

function main() {

    /** Contact form validation */
    $('#contactForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },

        fields: {
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email is required'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            name: {
                validators: {
                    notEmpty: {
                        message: 'The name is required'
                    }
                }
            },
            subject: {
                validators: {
                    notEmpty: {
                        message: 'The subject is required'
                    }
                }
            },
            message: {
                validators: {
                    notEmpty: {
                        message: 'The message is valued is required'
                    }
                }
            }

        }
    });

    /** Starting tax returns for client form validation */
    $('#startForClient').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },

        fields: {
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email is required'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            sendNotification: {
                validators: {
                    notEmpty: {
                        message: 'An option is required'
                    }
                }
            }
        }
    });


    /** Dashboard Message form validation */
    $('#message-form').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },

        fields: {
            subject: {
                validators: {
                    notEmpty: {
                        message: 'The subject is required'
                    }
                }
            }
        }
    });

    /** Login form validation */
    $('#loginform').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: 'The email is required'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    }
                }
            }
        }

    });


    /** Sign up form validation */
    $('#signUp').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: 'The username is required'
                    },
                    stringLength: {
                        message: 'Login ID must be 4 - 25 characters long',
                        min: 4,
                        max: function (value) {
                            return 25 - (value.match(/\r/g) || []).length;
                        }
                    },
                    different: {
                        field: 'email',
                        message: "The username and email must be different"
                    },
                    callback: {
                        message: 'Username cannot be an email',
                        callback: username => {
                            const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                            return !re.test(String(username).toLowerCase());
                        }
                    },
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email is required'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    },
                    different: {
                        field: 'username',
                        message: "The username and email must be different"
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: "The password must match"
                    },
                    stringLength: {
                        message: 'Password must be at least 6 characters long',
                        min: 6
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    },
                    identical: {
                        field: 'password',
                        message: "The password must match"
                    }
                }
            },
            agree: {
                validators: {
                    notEmpty: {
                        message: 'You must agree before submitting.'
                    }
                }
            }
        }

    });

    /** Sign up form validation */
    $('#profileUpdateForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: 'Please provide your full name'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email is required'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            dob: {
                validators: {
                    notEmpty: {
                        message: 'Birth date is required'
                    },
                    date: {
                        format: 'MM/DD/YYYY',
                        max: function () {
                            let date = new Date();
                            date.setFullYear(date.getFullYear() - 18);
                            return date;
                        },
                        message: 'A valid date in the past is required',
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: 'The phone is required.'
                    },
                    phone: {
                        country: "US",
                        message: 'Please enter a valid phone number.'
                    }
                }
            },
            ssnIgnore: {
                validators: {
                    notEmpty: {
                        message: 'The ssn is required'
                    },
                    regexp: {
                        regexp: /^(?!000|666)[0-8][0-9]{2}-?(?!00)[0-9]{2}-?(?!0000)[0-9]{4}$/i,
                        message: 'The ssn should be a valid ssn'
                    }
                }
            }
        }

    });

    /** Password Reset Validation */
    $('#passwordResetForm').formValidation({

        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },

        fields: {
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: "The password must match"
                    },
                    stringLength: {
                        message: 'Password length must be  6 characters long',
                        min: 6
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    },
                    identical: {
                        field: 'password',
                        message: "The password must match"
                    }
                }
            }
        }
    });

    /** Confirm password Validation */
    $('#recoverform').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email is required'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            }
        }
    });

    /** Appointment form validation */
    $('#appointmentForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            firstName: {
                validators: {
                    notEmpty: {
                        message: 'First Name is required'
                    }
                }
            },
            lastName: {
                validators: {
                    notEmpty: {
                        message: 'Last Name is required'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email is required'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            timeSlot: {
                validators: {
                    notEmpty: {
                        message: 'Time is required'
                    }
                }
            },
            start: {
                validators: {
                    notEmpty: {
                        message: 'Start date is required'
                    },
                    date: {
                        format: 'MM/DD/YYYY',
                        max: new Date('04/09/2020'),
                        min: function () {
                            let date = new Date();
                            if (date.getHours() >= 18) {
                                date.setHours(18, 0, 0, 0);
                            } else {
                                date.setHours(0, 0, 0, 0);
                            }
                            return date;
                        },
                        message: 'A valid date is required',
                    },
                    callback: {
                        message: 'The date cannot be Friday or Weekend',
                        callback: date => {
                            const day = new Date(date).getDay();
                            return day !== 0 && day !== 5 && day !== 6;
                        }
                    },
                }
            },
            title: {
                validators: {
                    notEmpty: {
                        message: 'Title is required'
                    }
                }
            },
            titleText: {
                validators: {
                    notEmpty: {
                        message: 'Title is required'
                    },
                    stringLength: {
                        message: 'Reason must be 4 - 50 characters long',
                        min: 4,
                        max: function (value) {
                            return 25 - (value.match(/\r/g) || []).length;
                        }
                    },
                }
            }
        }
    });

    /** Confirm password Validation */
    $('#statusCheckByYearForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            year: {
                validators: {
                    notEmpty: {
                        message: 'The year is required'
                    }
                }
            }
        }
    });

}
