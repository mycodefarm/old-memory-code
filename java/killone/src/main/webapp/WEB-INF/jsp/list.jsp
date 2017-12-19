<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 17-5-27
  Time: 上午8:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bootstrap 模板</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>库存</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>创建时间</th>
                    <th>详情</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="k" items="${list}">
                    <tr>
                        <th>${k.name}</th>
                        <th>${k.number}</th>
                        <th>
                            <fmt:formatDate value="${k.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </th>
                        <th>
                            <fmt:formatDate value="${k.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </th>
                        <th>
                            <fmt:formatDate value="${k.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </th>
                        <th><a href="/killone/${k.killId}/detail" target="_blank">link</a></th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
