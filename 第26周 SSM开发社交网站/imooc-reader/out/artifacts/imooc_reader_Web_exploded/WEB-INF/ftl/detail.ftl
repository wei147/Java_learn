<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="referrer" content="never">
    <title>${book.bookName}</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <link rel="stylesheet" href="/resources/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="/resources/raty/lib/jquery.raty.css">
    <script src="/resources/jquery.3.3.1.min.js"></script>
    <script src="/resources/bootstrap/bootstrap.min.js"></script>
    <script src="/resources/art-template.js"></script>
    <script src="/resources/raty/lib/jquery.raty.js"></script>
    <style>
        .container {
            padding: 0px;
            margin: 0px;
        }

        .row {
            padding: 0px;
            margin: 0px;
        }

        .alert {
            padding-left: 0.5rem;
            padding-right: 0.5rem;
        }

        .col- * {
            padding: 0px;
        }

        .description p {
            text-indent: 2em;
        }

        .description img {
            width: 100%;
        }

        .highlight {
            background-color: lightskyblue !important;
        }

    </style>
    <script>
        $.fn.raty.defaults.path = '/resources/raty/lib/images';
        $(function () {
            $(".stars").raty({readOnly: true});
        })

        $(function () {
            //如果memberReadState存在的时候,则代表当前会员要么点过想看要么点过看过,总之要将对应的按钮高亮显示
            <#if memberReadState ??>
            // $("*[data-read-state='1']") 代表着将当前页面中任何拥有data-read-state这个自定义属性且值为1的元素选中。(当然这里不能写死)
            //readState 用户阅读状态: 想看值为 1;看过值为 2
            //这一句话结合服务器端、Freemarker动态的在页面加载后利用JavaScript将对应按钮的状态置为了高亮
            $("*[data-read-state='${memberReadState.readState}']").addClass("highlight")
            </#if>
            //如果loginMember不存在的情况下,也就是没有登录的情况下 (登录控制)
            <#if !loginMember ??>
            //只要某个标签拥有了data-read-state这个自定义属性的话,那我就给你选中,去绑定点击事件  这里对应会员阅读状态、写短评按钮、点赞短评按钮做登录控制
            $("*[data-read-state],#btnEvaluation,*[data-evaluation-id]").click(function () {
                //未登录情况下提示 "需要登录"
                //首先利用JQuery选择器选中对话框div, 然后.modal()是JQuery bootstrap中提供的对话框函数,而show是执行对应的动作
                $("#exampleModalCenter").modal("show");
            })
            </#if>

            <#--            <#if !loginMember ??>-->
            <#--            //这里对应写短评按钮-->
            <#--            $("*[btnEvaluation]").click(function () {-->
            <#--                //未登录情况下提示 "需要登录"-->
            <#--                $("#exampleModalCenter").modal("show");-->
            <#--            })-->
            <#--            </#if>-->

            <#--            <#if !loginMember ??>-->
            <#--            //这里对应点赞短评操作-->
            <#--            $("*[data-evaluation-id]").click(function () {-->
            <#--                //未登录情况下提示 "需要登录"-->
            <#--                $("#exampleModalCenter").modal("show");-->
            <#--            })-->
            <#--            </#if>-->
            <#-- 用户登录去情况下-->
            <#if loginMember??>
            //选中想看或者看过这两个按钮
                $("*[data-read-state]").click(function () {
                    //首先获取当前点击按钮的data read-state这个自定义的属性值
                    //如果点击想看,这个readState数据就是1;如果点击看过这里就是2,
                    var readState = $(this).data("read-state"); //对应data-read-state="" 中的值,1或者2
                    // console.log(readState+'$(this).data("read-state")')
                    //之后发起Ajax请求
                    $.post("/update_read_state",{
                        memberId:${loginMember.memberId},
                        bookId:${book.bookId},
                        readState:readState
                    },function (json) {
                        if (json.code == "0"){
                            $("*[data-read-state]").removeClass("highlight"); //清楚css样式,让两个按钮回到默认的状态
                            $("*[data-read-state='"+ readState+"']").addClass("highlight");  //将与状态对应的按钮设为高亮
                        }
                    })
                })
            </#if>

        })
    </script>
</head>
<body>
<!--<div style="width: 375px;margin-left: auto;margin-right: auto;">-->
<div class="container ">
    <nav class="navbar navbar-light bg-white shadow mr-auto">
        <ul class="nav">
            <li class="nav-item">
                <a href="/">
                    <img src="https://m.imooc.com/static/wap/static/common/img/logo2.png" class="mt-1"
                         style="width: 100px">
                </a>
            </li>

        </ul>
    </nav>


    <div class="container mt-2 p-2 m-0" style="background-color:rgb(127, 125, 121)">
        <div class="row">
            <div class="col-4 mb-2 pl-0 pr-0">
                <img style="width: 110px;height: 160px"
                     src="${book.cover}">
            </div>
            <div class="col-8 pt-2 mb-2 pl-0">
                <h6 class="text-white">${book.bookName}</h6>
                <div class="p-1 alert alert-warning small" role="alert">
                    ${book.subTitle}
                </div>
                <p class="mb-1">
                    <span class="text-white-50 small">${book.author}</span>
                </p>
                <div class="row pl-1 pr-2">
                    <div class="col-6 p-1">
                        <#-- data-read-state 用户阅读状态: 想看值为 1;看过值为 2-->
                        <button type="button" data-read-state="1" class="btn btn-light btn-sm w-100">
                            <img style="width: 1rem;" class="mr-1"
                                 src="https://img3.doubanio.com/f/talion/cf2ab22e9cbc28a2c43de53e39fce7fbc93131d1/pics/card/ic_mark_todo_s.png"/>想看
                        </button>
                    </div>
                    <div class="col-6 p-1">
                        <button type="button" data-read-state="2" class="btn btn-light btn-sm  w-100">
                            <img style="width: 1rem;" class="mr-1"
                                 src="https://img3.doubanio.com/f/talion/78fc5f5f93ba22451fd7ab36836006cb9cc476ea/pics/card/ic_mark_done_s.png"/>看过
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row" style="background-color: rgba(0,0,0,0.1);">
            <div class="col-2"><h2 class="text-white">4.9</h2></div>
            <div class="col-5 pt-2">
                <span class="stars" data-score="${book.evaluationScore}"></span>
            </div>
            <div class="col-5  pt-2"><h5 class="text-white">${book.evaluationQuantity}人已评</h5></div>
        </div>
    </div>
    <div class="row p-2 description">
        ${book.description}
    </div>


    <div class="alert alert-primary w-100 mt-2" role="alert">短评
        <button type="button" id="btnEvaluation" class="btn btn-success btn-sm text-white float-right"
                style="margin-top: -3px;">
            写短评
        </button>
    </div>
    <div class="reply pl-2 pr-2">
        <#list evaluationList as evaluation>
            <div>
                <div>
                    <#--对于日期进行格式化-->
                    <span class="pt-1 small text-black-50 mr-2">${evaluation.createTime?string('yy-MM-dd')}</span>
                    <span class="mr-2 small pt-1">${evaluation.member.nickname}</span>
                    <span class="stars mr-2" data-score="${evaluation.score}"></span>

                    <button type="button" data-evaluation-id="41"
                            class="btn btn-success btn-sm text-white float-right" style="margin-top: -3px;">
                        <img style="width: 24px;margin-top: -5px;" class="mr-1"
                             src="https://img3.doubanio.com/f/talion/7a0756b3b6e67b59ea88653bc0cfa14f61ff219d/pics/card/ic_like_gray.svg"/>
                        <span>${evaluation.enjoy}</span>
                    </button>
                </div>

                <div class="row mt-2 small mb-3">
                    ${evaluation.content}
                </div>
                <hr/>
            </div>
        </#list>
    </div>
</div>


<!-- Modal 对话框 -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                您需要登录才可以操作
            </div>
            <div class="modal-footer">
                <a href="/login.html" type="button" class="btn btn-primary">去登录</a>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="dlgEvaluation" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <h6>为"从 0 开始学爬虫"写短评</h6>
                <form id="frmEvaluation">
                    <div class="input-group  mt-2 ">
                        <span id="score"></span>
                    </div>
                    <div class="input-group  mt-2 ">
                        <input type="text" id="content" name="content" class="form-control p-4" placeholder="这里输入短评">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="btnSubmit" class="btn btn-primary">提交</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>