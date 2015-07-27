<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${buyusers.status == '200'}">
<ol class="breadcrumb">
	<li>购买用户列表</li>
</ol>
	<nav>
		<!-- 分页开始 -->
		<ul class="pagination" id="pagination">
			<pageNation:PageNation currPage="${buyusers.data.curr_page}"
				totalPages="${buyusers.data.page_rows}" perPageRows="10"
				totalRows="${buyusers.data.total_rows}"
				linkBaseUrl="${cbasePath}dry/tableuserList?">
			</pageNation:PageNation>
		</ul>

		<!-- 分页结束 -->
	</nav>

	<!---数据显示区域-->
	<table class="table">
		<thead>
			<tr>
				<th>昵称</th>
				<th>价格</th>
				<th>所在群组</th>
				<th>购买时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${buyusers.data.result.result.content}" varStatus="key"
				var="Recourse">
				<tr>
					<td>
						${Recourse.nickName}
					</td>
					<td>${Recourse.coursePrice}</td>
					<td>
						<c:if test='${Recourse.groupName!=null}'>${Recourse.groupName}</c:if>
					</td>
					<td>
						<Date:date
										value="${Recourse.ctime}"></Date:date>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>
<script type="text/javascript">
	$(function(){
		$("#pagination li a").attr("href", "#");
	});
	function searchFunction(n, s){
		var url = '${cbasePath}buyusers';
		var cid = '${buyusers.data.result.courseId}';
		$.ajax({
			url : url,
			data : {'cid' : cid, 'n' : n, 's' : s},
			type : 'post',
			success : function(data){
				alert(data);
				$("#buyusers").html("").html(data);
			}
			
		});
	}
	$("#searchItBtn").click(function(){
		searchFunction(0, 10);
	});
	$("#pagination li  a").click(function(){
		if($(this).parent().hasClass("disabled")==false){ 
			var pageNo = $(this).html();
			if(!isNaN(pageNo)){
				pageNo--;
				searchFunction(pageNo, 10);
			}else{
				if(pageNo == '首页'){
					searchFunction(0, 10);
				}else if(pageNo == '上一页'){
					var toPageNo = $("#pagination .active a").html();
					toPageNo = toPageNo - 2;
					searchFunction(toPageNo, 10);
				}else if(pageNo == '下一页'){
					var toPageNo = $("#pagination .active a").html();
					searchFunction(toPageNo, 10);
				}else if(pageNo == '末页'){
					var toPageNo = ${buyusers.data.page_rows};
					searchFunction(toPageNo - 1, 10);
				}
			}
		}
	});
<!--

//-->
</script>

