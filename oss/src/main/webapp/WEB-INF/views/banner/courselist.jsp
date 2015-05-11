<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <c:if test="${courseList.status == '200'}"> 
	<nav>
		<!-- 分页开始 -->
		<ul class="pagination" id="pagination">
			<pageNation:PageNation currPage="${courseList.data.curr_page}"
				totalPages="${courseList.data.page_rows}" perPageRows="10"
				totalRows="${courseList.data.total_rows}"
				linkBaseUrl="${cbasePath}banner/searchCourse">
			</pageNation:PageNation> 
		</ul>

		<!-- 分页结束 -->
	</nav>

	<!---数据显示区域-->
	<table class="table">
		<thead>
			<tr>
				<th>选择</th>
				<th>课程名称</th>
				<th>所属群组</th>
				<!-- <th>简介</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${courseList.data.result}" varStatus="key"
				var="Recourse">
				<tr>
					<td><input type="radio" name="groupSelectRadio" value="" onclick="javascript:selectCourse('${Recourse.groupcourse.id}', '${Recourse.groupcourse.course}', '${Recourse.coursename}', '${Recourse.groupcourse.group}');"
						 /></td>
					<td>${Recourse.coursename}</td>
					<td>${Recourse.groupname}</td>
					<%-- <td>
						<c:if test="${Recourse.description != null && Recourse.description != 'null'}">					
						<c:choose>
				          <c:when test="${fn:length(Recourse.description) > 10}"> 
				          
				              <c:out value="${fn:substring(Recourse.description, 0, 10)}......" />
				         </c:when>
				         <c:otherwise>
				            <c:out value="${Recourse.description}" />
				          </c:otherwise>
				      </c:choose>
				      </c:if> 
				      
					</td> --%>
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
		var url = '${cbasePath}banner/searchCourse';
		var keywords = $("#keywords").val();
		$.ajax({
			url : url,
			data : {'keyword' : keywords, 'n' : n, 's' : s},
			type : 'post',
			dataType : 'html',
			success : function(data){
				$("#searchContent").html(data);
			}
			
		});
	}
	function selectCourse(groupCourseId, courseId, courseName, groupId){
		if($("#searchType").val() == ''){
			alert('类型错误!');
			return;
		}
		$("#courseId").val(courseId);
		$("#contentText").val(courseName);
		$("#groupId").val(groupId);
		$("#groupCourseId").val(groupCourseId);
		$("#adSellerName").val($("#searchType").find("option:selected").text());
		$("#typeText").val($("#searchType").find("option:selected").text());
		$("#adSellerId").val($("#searchType").val());
		
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
					var toPageNo = ${courseList.data.page_rows};
					searchFunction(toPageNo - 1, 10);
				}
			}
		}
	});
<!--

//-->
</script>

