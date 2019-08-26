/*
 * bootstrap-table的格式化方法
 */
/* 超链接格式化 */
function operateLinkFormatter(value, row, url){
    return '<a target="_blank" href="' + url + '">' + value + '</a>';
}

//连接字段格式化
function linkFormatter(value, row, index) {
    return "<a href='" + value + "' title='单击打开连接' target='_blank'>" + value + "</a>";
}

//Email字段格式化
function emailFormatter(value, row, index) {
    return "<a href='mailto:" + value + "' title='单击打开连接'>" + value + "</a>";
}

// 图标显示
function iconFormatter(value,row,index) {
    return '<i class=">' + value + '" title="' + value + '"></i>';
}

//性别字段格式化
function sexFormatter(value, row, index) {
    if (value == "女") {
        color = 'Red';
    } else if (value == "男") {
        color = 'Green';
    } else {
        color = 'Yellow';
    }

    return '<div  style="color: ' + color + '">' + value + '</div>';
}
