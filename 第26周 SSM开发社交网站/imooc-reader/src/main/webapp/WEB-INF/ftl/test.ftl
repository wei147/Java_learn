<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#--引入wangEditor-->
    <script src="/resources/wangEditor.min.js"></script>
</head>
<body>
<#--hi here is me ， 随风而去吧-->
<div>
    <button id="btnRead">读取内容</button>
    <button id="btnWrite">写入内容</button>
</div>
<div id="divEditor" style="width: 800px;height: 600px"></div>


<script>
    var E = window.wangEditor;
    var editor = new E("#divEditor"); //完成富文本编辑器初始化
    editor.create(); //创建富文本编辑器,显示在页面上

    //从富文本编辑器中获取当前内容的方式
    document.getElementById("btnRead").onclick = function () {
        var content = editor.txt.html();
        alert(content)
        console.log(content)
    }
    //将当前内容写入富文本编辑器中的方式
    document.getElementById("btnWrite").onclick = function () {
        var content = '<h2><span style="text-decoration-line: underline; color: rgb(249, 150, 59); background-color: rgb(0, 0, 0);">kingdom,about he</span></h2>'
        editor.txt.html(content);
    }
</script>
</body>
</html>