<html>
<head>
    <meta charset="utf-8">
    <title>首页</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/sell/css/style.css">
</head>
<body>
<div class="container">

    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-striped table-hover">
                <thead>
                <tr class="info">
                    <th >
                        娱乐
                    </th>
                    <th>
                        类型
                    </th>
                    <th>
                        <a href="#">更多</a>
                    </th>
                </tr>
                </thead>
                <tbody>
                    <#list newsDtoList as news>
                    <tr>
                        <th><a href="${news.newsId}">${news.newsTitle}</a></th>
                        <th>${news.detailName}</th>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
