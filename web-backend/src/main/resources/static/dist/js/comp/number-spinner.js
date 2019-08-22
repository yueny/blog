/**
 * 数据化输入input插件
 *
 * <pre>
 *     <input type="number" class="form-control" name="weightq" value="${permission.weight}">
 * </pre>
 * 或者
 * <pre>
 *     <link rel="stylesheet" href="${base}/dist/js/comp/number-spinner.css">
 *     <script src="${base}/dist/js/comp/number-spinner.js"></script>
 *
 *     <div class="row form-group">
         <label class="control-label col-sm-1" for="name">权重</label>
         <div class="input-group spinner">
         <input type="number" class="form-control" min="0" max="128"
         name="weight" value="${permission.weight}">
         <div class="input-group-btn-vertical">
         <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>
         <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>
         </div>
         </div>
     </div>
 * </pre>
 */
(function ($) {
    input = $('.spinner input');
    $('.spinner .btn:first-of-type').on('click', function() {
        if (input.attr('max') == undefined ||
            parseInt(input.val()) < parseInt(input.attr('max'))) {
            input.val(parseInt(input.val(), 10) + 1);
        }else{
            input.val(parseInt(input.attr('max')));
        }
    });
    $('.spinner .btn:last-of-type').on('click', function() {
        if ( input.attr('min') == undefined ||
            parseInt(input.val()) > parseInt(input.attr('min')) ) {
            input.val(parseInt(input.val(), 10) - 1);
        }else{
            input.val(parseInt(input.attr('min')));
        }
    });
})(jQuery);