/*===============================================================================
			[ INDEX ]
=================================================================================

	Banner Height
    Sticky Header
    Custom Bar
    Scroll Parallax
    Only Content Sticky Blog
    Contact Effect
    Advisor Effect
    Menu Dropdown Last

=================================================================================
			[ END INDEX ]
================================================================================*/

(function ($) {
    'use strict';

    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    /* Banner Height */
    function bannerHeight() {
        var header = $('.header'),
            headerHeight = header.innerHeight(),
            banner = $('.banner__single'),
            nextElem = header.next();

        if (header.hasClass('sticky--header') === true) {
            banner.css({
                'min-height': 'calc(100vh - ' + headerHeight + 'px)',
                'margin-top': headerHeight + 'px'
            });
            nextElem.css({
                'margin-top': headerHeight + 'px'
            });
        }
    }

    bannerHeight();


    /* Sticky Header */
    function stickyHeader() {
        var header = $('.sticky--header');
        $(window).on('scroll', function () {
            var scrollOffset = $(this).scrollTop();
            if (scrollOffset > 300) {
                header.addClass('is-sticky');
            } else {
                header.removeClass('is-sticky');
            }
        });
    }

    stickyHeader();


    /* Custom Bar */
    function customBar() {
        var bar = $('.cr-bar'),
            $this = bar;
        bar.each(function () {
            var barPercent = $(this).data('bar-percent');

            if ($(this).hasClass('cr-bar--horaizontal') == true) {

                $('<span class="cr-bar__progress"></span><div class="cr-bar__inner"><span class="cr-bar__title"></span></div>').appendTo(this);
                $(this).find('.cr-bar__title').text($(this).data('bar-title'));
                $(this).find('.cr-bar__progress').css({
                    'left': 'calc(' + barPercent + '% - 33px)',
                    'width': 'auto'
                }).text(barPercent + '%');
                $(this).find('.cr-bar__inner').css({
                    'width': barPercent + '%',
                });

                barEffectHoraizontal();

            } else {

                $('<span class="cr-bar__progress"></span><div class="cr-bar__inner"><span class="cr-bar__title"></span></div>').appendTo(this);
                $(this).find('.cr-bar__title').text($(this).data('bar-title'));
                $(this).find('.cr-bar__progress').css({
                    'bottom': 'calc(' + barPercent + '% + 0px)'
                }).text(barPercent + '%');
                $(this).find('.cr-bar__inner').css({
                    'height': barPercent + '%',
                });


                barEffect();

            }

        });

        function barEffect() {
            $(window).on('scroll', function () {
                var scrollPos = Math.round($(window).scrollTop()),
                    screenHeight = Math.round($(window).height()),
                    triggerPos = Math.round(bar.offset().top);

                if (scrollPos > (triggerPos - (screenHeight * 1.2))) {
                    $this.css({
                        '-webkit-transform': 'scaleY(1)',
                        '-moz-transform': 'scaleY(1)',
                        '-ms-transform': 'scaleY(1)',
                        '-o-transform': 'scaleY(1)',
                        'transform': 'scaleY(1)'
                    });
                }
            });
        }

        function barEffectHoraizontal() {
            $(window).on('scroll', function () {
                var scrollPos = Math.round($(window).scrollTop()),
                    screenHeight = Math.round($(window).height()),
                    triggerPos = Math.round(bar.offset().top);

                if (scrollPos > (triggerPos - (screenHeight * 1.2))) {
                    $this.css({
                        '-webkit-transform': 'scaleX(1)',
                        '-moz-transform': 'scaleX(1)',
                        '-ms-transform': 'scaleX(1)',
                        '-o-transform': 'scaleX(1)',
                        'transform': 'scaleX(1)'
                    });
                }
            });
        }

    }

    customBar();


    /* Only Content Sticky Blog */
    function onlyContentStickyBlog() {
        $('.blog').each(function () {
            var check = $(this).find('.blog__thumb').length;
            if (!check) {
                $(this).addClass('blog--onlycontent');
            }
        });
    }

    onlyContentStickyBlog();


    /* Contact Effect */
    function contactEffect() {
        var container = $('.single-input');
        container.on('focus', 'input, textarea', function () {
            $(this).siblings('label').addClass('state-change');
        });
        container.on('focusout', 'input, textarea', function () {
            $(this).siblings('label').removeClass('state-change');
            var $this = $(this);
            if ($this.val().trim().length !== 0) {
                $(this).siblings('label').addClass('state-change');
            }
        });
    }

    contactEffect();


    /* Advisor Effect */
    function advisorEffect() {
        var singleTeam = $('.single-team');
        if ($(window).width() > 991) {
            singleTeam.hover(
                function () {
                    if (!$(this).hasClass('active')) {
                        singleTeam.removeClass('active');
                        $(this).addClass('active');
                    }
                }
            );
        }
    }

    advisorEffect();


    /* Menu Dropdown Last */
    function menuDropdownLast() {
        $('.main-navigation > ul > li').slice(-3).addClass('last-elements');
    }

    menuDropdownLast();


    $('.locale').on('click', function (e) {
        let value = e.target.id;
        if (value && history.pushState) {
            let newUrl = window.location.protocol + "//" + window.location.host + window.location.pathname + '?locale=' + value;
            window.history.pushState({path: newUrl}, '', newUrl);
            localStorage.setItem('locale', value);
            location.reload();
        }
        e.preventDefault();
    });


    // Login - Sign Up
    $(".preloader").fadeOut();

    // ==============================================================
    // Login and Recover Password
    // ==============================================================
    $('#to-recover').on("click", function (e) {
        e.preventDefault();
        $("#messages").hide();
        $("#loginform").slideUp();
        $("#recoverform").fadeIn();
        $("#recover").focus();
    });

    $('#cancel-reset').on("click", function (e) {
        e.preventDefault();
        $("#messages").hide();
        $("#loginform").slideDown();
        $("#recoverform").fadeOut();
        $("#email").focus();
    });

    // ==============================================================
    // Sign Up Toggle Password
    // ==============================================================
    $(".toggle-password").click(function () {

        $(this).toggleClass("fa-eye fa-eye-slash");
        let input = $($(this).attr("id"));
        if (input.attr("type") === "password") {
            input.attr("type", "text");
        } else {
            input.attr("type", "password");
        }
    });


})(jQuery);
