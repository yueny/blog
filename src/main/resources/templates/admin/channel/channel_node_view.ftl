<div class="modal-content">
<#--  modal-content -->
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
    </div>


    <form method="POST" name="myform" action="<c:url value='/admin/news/readerUpdateSave.htm' />"
          target="_self" class="form-horizontal" enctype="multipart/form-data">
        <div class="modal-body">
            按下 ESC 按钮退出。
        ${channelNodeVO.nodeType}
        </div>

        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary">提交更改</button>
        </div>
    </form>

</div>