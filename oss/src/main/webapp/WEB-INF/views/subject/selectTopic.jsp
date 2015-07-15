<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${list.status == '200'}">
	<nav>
		<!-- 分页开始 -->
		 <ul class="pagination" id="pagination">
			<pageNation:PageNation currPage="${list.data.curr_page}"
				totalPages="${list.data.page_rows}" perPageRows="10"
				totalRows="${list.data.total_rows}"
				linkBaseUrl="${cbasePath}dry/tablelist?">
			</pageNation:PageNation>
		</ul> 

		<!-- 分页结束 -->
	</nav>
	<!---数据显示区域-->
	<table class="table">
		<thead>
			<tr>
				<th>选择</th>
				<th>标题</th>
				<th>发布者</th>
				<th>群组</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list.data.result}" varStatus="key"
				var="Recourse">
				<tr>
					<td><input type="checkbox" name="acid" value="${Recourse.topicId}"
						 /></td>
					<td>${fn:substring(Recourse.title, 0, 15)}</td>
					<td>${Recourse.authorName}</td>
					<td>${Recourse.sourceName}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>
<script type="text/javascript">
	/**
	点击确定按钮时
	*/
	function closeDialog(){
		var acids = '';
		$("input[name='acid']:checkbox").each(function(i){
		    if(this.checked == true){

		      acids += $(this).val() + ",";

		   }

		});
		acids = acids.substr(0, acids.length - 1);
		var boxPostId = '${boxPostId}';
		var ctime = '${ctime}';
		var categoryId = '${categoryId}';
		var specialName = $("#specialName").html();
		var logoUrl = $("#logoUrl").attr("src");
		var sourceType = 'topic';
		var h5Url = $("#h5Url").val();
		var data = {'sourceId' : acids, 'boxPostId' : boxPostId, 'ctime' : ctime, 'sourceType' : sourceType};
		$.ajax({
			url : '${cbasePath}subject/addDataInBox',
			data : data,
			type : 'post',
			success : function(data){
				if(data == 'success'){
					alert('添加活动成功');
					var url = '${cbasePath}subject/subjectDetail?chinaName=' + encodeURI(specialName) + "&id=" + boxPostId + "&type=topic&logoUrl=" + logoUrl + '&ctime=' + ctime + '&h5Url=' + h5Url + '&categoryId=' + categoryId;
					$("#circleLoader").shCircleLoader();
					window.location.href = url;
				}
			}
			
		});
	}
	$(function(){
		$("#pagination li a").attr("href", "#");
	});
	/**
	分页重写方法
	*/
	function searchFunction(n, s){
		var url = '${cbasePath}subject/findByothers?pageType=selectTopic&boxPostId=${boxPostId}&dataType=topic&categoryId=${categoryId}';
		$.ajax({
			url : url,
			data : {'n' : n, 's' : s},
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#modalHtml").html(data);
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
					var toPageNo = ${list.data.page_rows};
					searchFunction(toPageNo - 1, 10);
				}
			}
		}
	});
<!--

//-->
</script>

