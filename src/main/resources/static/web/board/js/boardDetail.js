(function () {

    $(document).on('click', '#deleteBoard', function (e) {
        e.preventDefault();

        if (!confirm('삭제하시겠습니까?')) {
            return false;
        }
        $.ajax({
            url: $(this).attr('href'),
            method: 'post',
            dataType: 'json',
            success: function (result) {
                console.log(result);
                location.href = '/web/boards';
            }
        });
    });

    $(document).ready(function () {
        //
    });

})();

