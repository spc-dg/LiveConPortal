<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  ~ Copyright (c) 2013.
  ~ LICENSE: GNU General Public License v3 (GPL-3).
  --%>

<script type="text/javascript">
    /**
     * Does a POST redirect.
     * @param url the URL to redirect to
     * @param dat key/value object
     * @param target target to go into (_blank, _self etc)
     */
    function goPost(url, dat, target) {

        // default is self
        target = typeof target !== 'undefined' ? target : "_self";

        var formId = "post_form_" + Math.floor(Math.random() * 3571);

        var formString = '<form action="' + url + '" method="post" target = "' + target + '" id="' + formId + '" name="' + formId + '">';

        for (var key in dat) {
            var val = dat[key];

            formString += '<input type="hidden" name="' + key + '" id="' + key + '" value="' + val + '" />';

            if (key == 'req') {
                var secObj = dat[key];

                for (var secKey in secObj) {
                    var secVal = secObj[secKey];
                    formString += '<input type="hidden" name="' + secKey + '" id="' + secKey + '" value="' + secVal + '" />';
                }
            }
        }


        formString += '</form>';
        var form = $(formString);
        $('body').append(form);
        $(form).submit();
    }

    /**
     * Return if there is a value there.
     * Javascript has many stupid ways of having a 'null' value.
     *
     * @param value the value to test
     * @returns if the value 'is there'
     */
    function isThere(value) {
        return typeof value !== 'undefined' && value && value != null && value != 'null' && String(value).trim() != '';
    }
</script>