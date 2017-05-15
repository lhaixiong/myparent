<html>
<script type="text/javascript" src="scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    $(function(){
       $("#testJson").click(function(){
           var url=this.href;
           var args={};
           $.post(url,args,function(data){
              for(var i=0;i<data.length;i++){
                  console.info(data[i]);
              }
           });
       }) ;
    });
</script>
<body>
<h2>Hello World!</h2>
<a href="/emp/testExceptionResolver?id=1">testExceptionResolver</a><br/>
<form action="/emp/testMultiFileResolver" method="post" enctype="multipart/form-data">
    file:<input type="file" name="file"/>
    description:<input type="text" name="description" value="aaaaaaa"/><br/>
    <input type="submit" value="testMultiFileResolver"/>
</form><br/>
<a href="/emp/i18n">i18n</a><br/>
<a href="/emp/testResponseEntity">testResponseEntity</a><br/>
<form action="/emp/testHttpMessageConverter" method="post" enctype="multipart/form-data">
    file:<input type="file" name="file"/>
    description:<input type="text" name="description" value="22addadad"/><br/>
    <input type="submit" value="testHttpMessageConverter"/>
</form><br/>
<a href="/emp/testJson" id="testJson">testJson</a><br/>
<a href="/emp/list">emp list</a><br/>

<form action="/springmvc/testModelAttribute" method="post">
    <input type="hidden" name="id" value="1">
    username:<input type="text" name="username" value="lhx"/><br/>
    <%--password:<input type="password" name="password" value="lhx123"/><br/>--%>
    email:<input type="text" name="email" value="lhx@qq.com"/><br/>
    age:<input type="text" name="age" value="22"/><br/>
    <input type="submit" value="submit"/>
</form><br/>
<a href="/springmvc/testSessionAttributes">testSessionAttributes</a><br/>
<a href="/springmvc/testMap">testMap</a><br/>
<a href="/springmvc/testModelAndView">testModelAndView</a><br/>
<a href="/springmvc/testServletAPI">testServletAPI</a><br/>
<form action="/springmvc/testPojo" method="post">
    username:<input type="text" name="username" value="lhx"/><br/>
    password:<input type="password" name="password" value="lhx123"/><br/>
    email:<input type="text" name="email" value="lhx@qq.com"/><br/>
    age:<input type="text" name="age" value="22"/><br/>
    province:<input type="text" name="address.province" value="guangdong"/><br/>
    city:<input type="text" name="address.city" value="guangzhou"/><br/>
    <input type="submit" value="submit"/>
</form>

<a href="/springmvc/testSessionAttributes">testSessionAttributes</a><br/>
<a href="/springmvc/testCookieValue">testCookieValue</a><br/>
<a href="/springmvc/testRequestHeader">testRequestHeader</a><br/>
<a href="/springmvc/testRequestParam?username=lhx&age=11">testRequestParam</a><br/>

<form action="/springmvc/testRest/7" method="post">
    <input type="hidden" name="_method" value="PUT">
    <input type="submit" value="rest put" />
</form><br/>
<form action="/springmvc/testRest/5" method="post">
    <input type="hidden" name="_method" value="DELETE">
    <input type="submit" value="rest delete" />
</form><br/>
<form action="/springmvc/testRest" method="post">
    <input type="submit" value="rest post" />
</form><br/>
<a href="/springmvc/testRest/8">testRestGet</a><br/>
<a href="/springmvc/testPathVariable/11">testPathVariable</a><br/>
<a href="/springmvc/testAntPath/abc/abc">testAntPath</a><br/>
<a href="/springmvc/testParamsAndHeaders?username=aaa&age=10">testParamsAndHeaders</a><br/>
<form action="/springmvc/testMethod" method="post">
        <input type="submit" value="submit" />
    </form><br/>
<a href="/springmvc/testMethod">testMethod</a><br/>
    <a href="/springmvc/testRequestMapping">testRequestMapping</a><br/>
    <a href="/helloWorld">helloWorld</a><br/>
</body>
</html>
