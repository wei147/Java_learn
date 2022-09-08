#### 关于index.ftl 为什么要用到隐藏域的理解

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="referrer" content="never">
    <title>慕课书评网</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <link rel="stylesheet" href="./resources/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="./resources/raty/lib/jquery.raty.css">
    <script src="./resources/jquery.3.3.1.min.js"></script>
    <script src="./resources/bootstrap/bootstrap.min.js"></script>
    <script src="./resources/art-template.js"></script>
    <script src="./resources/raty/lib/jquery.raty.js"></script>

    <style>
        .highlight {
            color: red !important;
        }

        a:active {
            text-decoration: none !important;
        }
    </style>


    <style>
        .container {
            padding: 0px;
            margin: 0px;
        }

        .row {
            padding: 0px;
            margin: 0px;
        }

        .col- * {
            padding: 0px;
        }
    </style>

    <#--定义模板-->
    <#--type说明script中内容的类型 tpl英文模板template的简写-->
    <script type="text/html" id="tpl">
        <a href="/book/{{bookId}}" style="color: inherit">
            <div class="row mt-2 book">
                <div class="col-4 mb-2 pr-2">
                    <img class="img-fluid" src="{{cover}}">
                </div>
                <div class="col-8  mb-2 pl-0">
                    <h5 class="text-truncate">{{bookName}}</h5>

                    <div class="mb-2 bg-light small  p-2 w-100 text-truncate">{{author}}</div>


                    <div class="mb-2 w-100">{{subTitle}}</div>

                    <p>
                        <#--data-score并不是我们自定义的属性,而是raty强制的要求。通过data-score属性描述组件当前的评分是多少-->
                        <span class="stars" data-score="{{evaluationScore}}" title="gorgeous"></span>
                        <span class="mt-2 ml-2">{{evaluationScore}}</span>
                        <span class="mt-2 ml-2">{{evaluationQuantity}}人已评</span>
                    </p>
                </div>
            </div>
        </a>
    </script>

    <script>
        $(function () {
            //指定存储星型图片的目录在哪
            $.fn.raty.defaults.path = "./resources/raty/lib/images";

            //loadMore() 加载更多数据
            //isReset参数设置为true,代表从第一页开始查询,否则按nextPage查询后续页
            function loadMore(isReset) {
                if (isReset == true) {
                    //将原有已经查询的所有图书信息来给予清空。 [不然的话会增加到已有信息的后面]
                    $("#bookList").html("");
                    $("#nextPage").val(1);
                }
                var nextPage = $("#nextPage").val();
                //获取两个隐藏域的数值 分类id和排序编号
                var categoryId = $("#categoryId").val();
                var order = $("#order").val();
                $.ajax({
                    url: "/books",
                    //JavaScript处理的时候会给p加上双引号,当做字符串处理
                    data: {p: nextPage, "categoryId": categoryId, "order": order},
                    type: "get",
                    dataType: "json",
                    //服务器返回数据时,用success函数来接收
                    success: function (json) {
                        var list = json.records;
                        for (var i = 0; i < list.length; i++) {
                            var book = json.records[i];
                            // var html = "<li>"+book.bookName+"</li>";
                            //将数据结合tpl模板,生成html
                            var html = template("tpl", book);
                            // console.info(html); 打印这个模板 Template
                            $("#bookList").append(html);
                        }
                        //显示星型评价组件  [选中class为stars的span标签,利用.raty便可以将对应的span转换为可视的星型组件。 readonly:true这里只是对用户进行显示并不容许用户更改]
                        $(".stars").raty({readonly: true});

                        //判断是否到最后一页
                        //json.current代表当前页号, json.pages是总页数。这里还有一个小细节:json.current可能会被JavaScript当成字符串来处理,解决方法是增加parseInt转换为数字
                        if (json.current < json.pages) {
                            $("#nextPage").val(parseInt(json.current) + 1);
                            $("#btnMore").show();
                            $("#divNoMore").hide();
                        } else {
                            $("#btnMore").hide();
                            $("#divNoMore").show();
                        }
                    }
                })
            }

            //这里设置为ture,即默认加载第一页(nextPage=1)
            loadMore(true)

            //绑定加载更多按钮的单击事件
            $(function () {
                $("#btnMore").click(function () {
                    loadMore();
                })

                //.category css类是每一个分类超链接都必然拥有的css类。通过.category对当前页面所有的分类的超链接进行捕获
                $(".category").click(function () {
                    $(".category").removeClass("highlight");    //移除所有高亮显示
                    $(".category").addClass(" text-black-50");  //设置为灰色
                    $(this).addClass("highlight");  //捕获当前当前点击的超链接并设置高亮
                    //这里对应的是自定义属性data后边的值   <span data-category="-1"...
                    //一旦执行了这句话,那么它就会得到当前点击的超链接它的categoryId编号,接着将编号赋值给隐藏域categoryId。
                    // 一旦赋值后,在进行数据加载过程中就会读取这个结果来完成数据的筛选
                    //2022年9月8日20:21:25,针对上面文本的说明:今晚我会疑惑于为什么要放数据到隐藏域。把分类id放到隐藏域是因为后面loadMore方法会拿到
                    // 隐藏域的id传到给后台进行查询然后响应数据展示  现在的问题是:不能直接把分类id给loadMore方法吗 我想清楚了一些,
                    // 因为class="order"的这个标签里面的data="热度或者评分"是不应该变的,转而需要一个中间人、不影响页面内容的情况下接下"保存变量"这个任务。over
                    var categoryId = $(this).data("category");
                    $("#categoryId").val(categoryId);
                    //每点击图书类别的时候,相当于进行重新的查询
                    loadMore(true);
                })

                //.order css类是每一个排序超链接都必然拥有的css类
                $(".order").click(function () {
                    $(".order").removeClass("highlight");    //移除所有高亮显示
                    $(".order").addClass(" text-black-50");  //设置为灰色
                    $(this).addClass("highlight");  //捕获当前当前点击的超链接并设置高亮
                    var order = $(this).data("order");  //这里$(this).data("order"); 拿到的实际就是 data-order="quantity/score"
                    $("#order").val(order);     //$("#order").val(order);  这里是为对应的隐藏域进行赋值 id="order"
                    loadMore(true);
                })
            })
        })
    </script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-light bg-white shadow mr-auto">
        <ul class="nav">
            <li class="nav-item">
                <a href="/">
                    <img src="https://m.imooc.com/static/wap/static/common/img/logo2.png" class="mt-1"
                         style="width: 100px">
                </a>
            </li>

        </ul>
        <#--流啤 ! 在MemberController放入的Session信息可以在这里被拿到  (session.setAttribute("loginMember",member);)-->
        <#--如果loginMember存在的时候 [??两个问号代表loginMember(即前提条件)存在的情况下]  已登录和未登录两种情况区分开-->
        <#if loginMember??>
            <h6>
                <img style="width: 2rem;margin-top: -5px" class="mr-1"
                     src="./images/user_icon.png">${loginMember.nickname}
            </h6>
        <#else >
            <a href="/login.html" class="btn btn-light btn-sm">
                <img style="width: 2rem;margin-top: -5px" class="mr-1" src="./images/user_icon.png">登录
            </a>
        </#if>
    </nav>
    <div class="row mt-2">


        <div class="col-8 mt-2">
            <h4>热评好书推荐</h4>
        </div>

        <div class="col-8 mt-2">
            <span data-category="-1" style="cursor: pointer" class="highlight  font-weight-bold category">全部</span>
            |
            <#list categoryList as category>
            <#--data-category是我们自定义的一个属性 它代表了当前点击的这个超链接所对应的分类编号-->
                <a style="cursor: pointer" data-category="#{category.categoryId}"
                   class="text-black-50 font-weight-bold category">${category.categoryName}</a>
            <#--category_has_next 代表是否后面还有其他元素呢? 如果有则执行if块中的语句;如果没有就不会执行   [注:用于去除最后面还有 | 的情况,不够美观]-->
                <#if category_has_next>|</#if>
            </#list>
        </div>

        <div class="col-8 mt-2">
            <span data-order="quantity" style="cursor: pointer"
                  class="order highlight  font-weight-bold mr-3">按热度</span>

            <span data-order="score" style="cursor: pointer"
                  class="order text-black-50 mr-3 font-weight-bold">按评分</span>
        </div>
    </div>
    <div class="d-none">
        <input type="hidden" id="nextPage" value="2">
        <#--如果设置categoryId=-1的话,则默认代表查询所有数据。如果设置为非-1的话,那就代表筛选指定的类目。
         与此同时,order这里设置是quantity,也就是按热度/评分来进行默认排序 默认按热度来排序?但我隐藏域这里改了score没有变化的 -->
        <input type="hidden" id="categoryId" value="-1">
<#--        <input type="hidden" id="order" value="quantity">-->
        <input type="hidden" id="order" value="score">
    </div>

    <div id="bookList">


        <hr>
    </div>
    <button type="button" id="btnMore" data-next-page="1" class="mb-5 btn btn-outline-primary btn-lg btn-block">
        点击加载更多...
    </button>
    <div id="divNoMore" class="text-center text-black-50 mb-5" style="display: none;">没有更多数据了</div>
</div>

</body>
</html>
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220908203946206.png" alt="image-20220908203946206" style="zoom: 80%;" />

```html
//默认样式是直接加在这里的 highlight,而隐藏域就是一个存放分类Id的中间人
<div class="col-8 mt-2">
    <span data-order="quantity" style="cursor: pointer"
          class="order highlight  font-weight-bold mr-3">按热度</span>

    <span data-order="score" style="cursor: pointer"
          class="order text-black-50 mr-3 font-weight-bold">按评分</span>
</div>
```