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
	<table class="table table-striped table-hover table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>课程名称</th>
				<th>群组</th>
				<th>收费模式</th>
				<th>价格</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list.data.result}" varStatus="key"
				var="Recourse">
				<tr>
					<td><input type="checkbox" name="acid" value="${Recourse.course.id}" onclick="javascript:checkCourse();"
						 /></td>
					<td>${fn:substring(Recourse.course.title, 0, 10)}</td>
					<td>${Recourse.groupName}</td>
					<td>
						<c:if test="${Recourse.course.pricemodel=='0'}">
							免费
						</c:if>
						<c:if test="${Recourse.course.pricemodel=='1'}">
							付费
						</c:if>
						<c:if test="${Recourse.course.pricemodel=='2'}">
							打赏
						</c:if>
					</td>
					<td>${Recourse.course.price}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<p class="text-danger">*每个群组最多只能选择两门课程*</p>
</c:if>
<script type="text/javascript">
	function preview(){//返回选择群组页面
		var url = '${cbasePath}subject/findByothers?pageType=selectGroup&dataType=group&boxPostId=${boxPostId}&categoryId=${categoryId}&ctime=${ctime}&gid=${gid}&logoUrl=${logoUrl}&h5Url=${h5Url}';
		$("#modalHtml").load(url);
	}

	function checkCourse(){//此方法控制最多只能选择两个课程
		var length = $("input[name='acid']:checked").length;
		if(length == '2'){
			$("input[name='acid']:checkbox").each(function(i){
			    if(this.checked == false){

			    	$(this).attr("disabled",true);

			   }

			});
		}else{
			$("input[name='acid']:checkbox").each(function(i){

			    	$(this).attr("disabled",false);


			});
		}
	}
	/**
	点击确定按钮时
	*/
	function closeDialog(){
		var acids = '';
		/* var length = $("input[name='acid']:checked").length;
		if(length != '2'){
			alert('只能添加两门课程!');
			return;			
		} */
		$("input[name='acid']:checkbox").each(function(i){
		    if(this.checked == true){

		      acids += $(this).val() + ",";

		   }

		});
		acids = acids.substr(0, acids.length - 1);
		var boxPostId = '${boxPostId}';
		var ctime = '${ctime}';
		var categoryId = '${categoryId}';
		var h5Url = '${h5Url}';
		var logoUrl = '${logoUrl}';
		var sourceType = 'course';
		var gid = '${gid}';
		alert(gid);
		var data = {'sourceId' : acids, 'boxPostId' : boxPostId, 'ctime' : ctime, 'sourceType' : sourceType, 'gid' : gid};
		$.ajax({
			url : '${cbasePath}subject/addDataInBox',
			data : data,
			type : 'post',
			success : function(data){
				if(data == 'success'){
					alert('添加活动成功');
					var url = '${cbasePath}subject/subjectDetail?id=' + boxPostId + '&type=onecoursespecial&h5Url=' + h5Url + '&logoUrl=' + logoUrl;
					$("#circleLoader").shCircleLoader();
					window.location.href = url;
				}
			}
			
		});
	}
	$(function(){
		$("#pagination li a").attr("href", "#");
		$("#myModalLabel").html('课程选择');
		$("#preBtn").show();
		$("#okBtn").show();
		$("#nextBtn").hide();
	});
	/**
	分页重写方法
	*/
	function searchFunction(n, s){
		var url = '${cbasePath}subject/selectCourseForGroup?boxPostId=${boxPostId}&categoryId=${categoryId}&ctime=${ctime}&gid=${gid}&logoUrl=${logoUrl}&h5Url=${h5Url}';
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

