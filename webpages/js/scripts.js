(function () {
    $(window).scroll(function () {
        var top = $(document).scrollTop();
        $('.splash').css({
            'background-position': '0px -' + (top / 3).toFixed(2) + 'px'
        });
        if (top > 50) $('#home > .navbar').removeClass('navbar-transparent');
        else $('#home > .navbar').addClass('navbar-transparent');
    });

    $("a[href='#']").click(function (e) {
        e.preventDefault();
    });

    var $button = $("<div id='source-button' class='btn btn-primary btn-xs'>< ></div>").click(function () {
        var html = $(this).parent().html();
        html = cleanSource(html);
        $("#source-modal pre").text(html);
        $("#source-modal").modal();
    });

    $('.bs-component [data-toggle="popover"]').popover();
    $('.bs-component [data-toggle="tooltip"]').tooltip();

    $(".bs-component").hover(function () {
        $(this).append($button);
        $button.show();
    }, function () {
        $button.hide();
    });

    function cleanSource(html) {
        html = html.replace(/×/g, "×")
            .replace(/«/g, "«")
            .replace(/»/g, "»")
            .replace(/←/g, "←")
            .replace(/→/g, "→");

        var lines = html.split(/\n/);

        lines.shift();
        lines.splice(-1, 1);

        var indentSize = lines[0].length - lines[0].trim().length,
            re = new RegExp(" {" + indentSize + "}");

        lines = lines.map(function (line) {
            if (line.match(re)) {
                line = line.substring(indentSize);
            }

            return line;
        });

        lines = lines.join("\n");

        return lines;
    }

    /* INITIALISE DatePicker */
    // First find all controls with the class 'datepicker'
    var datepickers = $('body').find('.datepicker');

    // loop through array of datepickers and initialise one at a time
    for (var i = 0; i < datepickers.length; i++) {
        $(datepickers[i]).datepicker({
            format: 'yyyy-dd-mm',
            uiLibrary: 'bootstrap4',
            iconsLibrary: 'fontawesome',
            icons: {
                rightIcon: '<i class="fas fa-calendar"></i>'
            }
        });
        console.log('initialised: ', datepickers[i]);
    }
})();