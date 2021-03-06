<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pageNation" uri="/WEB-INF/tld/pagenation.tld"%>
<%@ taglib prefix="Date" uri="/WEB-INF/tld/datetag.tld"%>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>用户管理</title>
<script src="${sourcePath}/resources/assets/js/jquery.min.js"></script>
<script src="${sourcePath}/resources/assets/js/bootstrap.min.js"></script>
<link href="${sourcePath}/resources/assets/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${sourcePath}/resources/assets/css/font.css" rel="stylesheet">
<style>
#userInfoDiv div {
	padding: 10px;
}
</style>

 <script>
		function setinfo(context){
				/* $.post("${cbasePath}/mail/getById",{"id":id},function(data){
					ue.setContent(data.context);
					$("#tempid").val(""+id);
					
				}); */
				alert(context);
				ue.setContent(context);
			}
		
		 
   </script>


</head>
<body>
	<div class="container-fluid">
		<ol class="breadcrumb">
			<li><a href="#">课程仓库</a></li>
			<li><a href="${cbasePath}course/courseList">课程列表</a></li>
			<li><a
				href="${cbasePath}course/courseDetail?cid=${courseDetail.data.result.id}">课程详情

			</a></li>
			<li class="active">课程编辑</li>
		</ol>

		<div class="row">
			<div class="col-xs-3">
				<img class="thumbnail col-xs-12" name="picUrl" id="picUrl"
					src="${courseDetail.data.result.logoUrl}" alt="" />

				<%-- <c:forEach items="${imgUrls}" varStatus="key" var="img">
					<img src="${img}" alt="" />
				</c:forEach> --%>
			</div>

			<div class="col-xs-9">
				<%-- <a href="${url }" target="_blank">${resuserTopic.data.result.message }</a> --%>
				<form role="form" method="post"
					action="${cbasePath}course/updateCourse?cid=${courseDetail.data.result.id}"
					enctype="multipart/form-data">

					<input type="hidden" value="${courseDetail.data.result.logoUrl}" name="oldLogoUrl"/>
					<input type="hidden" value="" id="logoUrl" name="logoUrl"/>
					<div class="form-group">
						<!-- <label for="exampleInputEmail1">文件地址（图片/语音）</label> <input type="text"
							name="fileUrl" class="form-control" id="exampleInputEmail1"
							placeholder="">  -->
						<div class="media" id="picFrame">
							<div class="media-body">
								<div id="container">
									<div class="row">
										<div class="col-xs-9">
											<label for="exampleInputEmail1">修改课程图片</label>
											<button id="pic" type="button" class="btn btn-default">上传图片</button>
										</div>
										<div class="col-xs-9">图片尺寸最低为640*380，且宽和高的比例必须为640 * 380<span style="color:red;">*</span></div>
									</div>

								</div>
							</div>

						</div>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">标题</label> <input type="text"
							name="title" class="form-control" id="exampleInputEmail1"
							value="${courseDetail.data.result.title }" placeholder="">
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1">课程信息</label>
						<textarea name="intro" class="form-control" rows="3" cols="20" value="${courseDetail.data.result.intro } ">${courseDetail.data.result.intro } </textarea>
					</div>
					
<%--   
  <div>
    <script id="container" name="content" type="text/plain">
        这里写你的初始化内容${courseDetail.data.result.intro }
    </script>
  
    <!-- 配置文件 -->
    <script type="text/javascript" src="${cbasePath}resources/ue/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${cbasePath}resources/ue/ueditor.all.js"></script>
    <!-- 实例化编辑器 -->
    <script type="text/javascript">
        var ue = UE.getEditor('container',{
        	initialFrameWidth :650,//设置编辑器宽度
        	initialFrameHeight:250,//设置编辑器高度
        	scaleEnabled:true
        	});
    </script>
	
    </div>	 --%>
					
					
					<div class="form-group clearfix">
						<label for="pricemodel">付费模式:</label>
						<label class="radio-inline"><input type="radio" name="pricemodel" value="0" checked onclick="javascript:checkPriceModel(this);"/>免费</label>
						<label class="radio-inline"><input type="radio" name="pricemodel" value="1" onclick="javascript:checkPriceModel(this);"/>付费</label>
						<label class="radio-inline"><input type="radio" name="pricemodel" value="2" onclick="javascript:checkPriceModel(this);"/>打赏</label>
					</div>
					<div class="form-group clearfix">
						<label for="price">价格</label>
						<input type="text" class="form-control" name="price" id="price" value="${courseDetail.data.result.price}" disabled="disabled" onblur="javascript:checkIsNum(this);"/>
					</div>
					<div class="form-group clearfix"  style="display:none;" id="scaleDiv">
						<label for="scale">分成比例 (格式：0为没有分成，0.1为云学堂占一成，1为不与平台分成，必填)</label>
						<input type="text" class="form-control" name="scale" id="scale" value="${courseDetail.data.result.scale}" >
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">话题分类</label>
						<select  class="form-control" id="categorySelect" name="categoryId">
							<c:if test="${categoryList != null}">
								<c:forEach items="${categoryList}" var="cate">
									<option <c:if test="${cate.id == courseDetail.data.result.categoryId}">selected</c:if>  value="${cate.id}">${cate.categoryName}</option>
								</c:forEach> 
							</c:if>
						</select>
						原课程分类为：<c:if test="${currCategory.data.result != ''}">${currCategory.data.result.categoryName}</c:if>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">二级分类</label>
						<select class="form-control" id="childCategorySelect" name="childCategoryId">
							<c:if test="${currCategory.data.result != ''}">
								<c:forEach items="${currCategory.data.result.childCategory}" var="cate">
									<option <c:if test="${cate.id == courseDetail.data.result.childCategoryId}">selected</c:if> value="${cate.id}" >${cate.categoryName}</option>
								</c:forEach>
							</c:if>
						</select>
						原课程二级分类为：<c:if test="${currChildCategory.data.result != ''}">${currChildCategory.data.result.categoryName}</c:if>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">标签</label>
						<input type="text" class="form-control" name="tagNames" value="${courseDetail.data.result.tagNames}">
					</div>


					<button type="submit" class="btn btn-default"  >Submit</button>
				</form>
			</div>
		</div>

		
	</div>
	<script src="${cbasePath}/resources/assets/js/html5shiv.js"></script>
	<script src="${cbasePath}/resources/assets/js/plupload.full.min.js"></script>
	<script src="${cbasePath}/resources/assets/js/qiniu.js"></script>
	<script src="${cbasePath}/resources/assets/js/moxie.min.js"></script>
	<script>
		$(function() {
			$("input[name='pricemodel'][value='${courseDetail.data.result.pricemodel}']").attr("checked",true); 
			$("input:radio[name=pricemodel]:checked").click();
			//二级分类联动
			$("#categorySelect").change(function(){
				//
				var categoryId = $("#categorySelect").val();
				if(categoryId != ''){
					$.ajax({
						url : '${cbasePath}category/getChildCategory',
						data : {'categoryId' : categoryId},
						type : 'post',
						dataType : 'json',
						success : function(result){
							var categorys = result.data.result.childCategory;
							var $childCategorySelect = $("#childCategorySelect");
							$childCategorySelect.empty();
							$.each(categorys, function(index, content){
								if(content.id != ''){
									$childCategorySelect.append("<option value='" + content.id + "'>" + content.categoryName + "</option>");
								}
							});
						}
					});
				}
			});	
			upload("10007");
		});
		function upload(s){
		    var uploader = Qiniu.uploader({
		        runtimes: 'html5,flash,html4',
		        browse_button: 'pic',
		        container: 'container',
		        drop_element: 'container',
		        max_file_size: '100mb',
		        flash_swf_url: '${cbasePath}/js/Moxie.swf',
		        dragdrop: true,
		        chunk_size: '4mb',
		        uptoken_url: '${cbasePath}/knowledge/getToken?ckey='+s,
		        domain: 'tpublic.qiniudn.com',
		       	filters:"{mime_types : [{ title : \"Image files\", extensions : \"jpg,gif,png\" }  ],  max_file_size : '2mb', \r\n  prevent_duplicates : true}",
		        auto_start: true,
		        multi_selection:false,
		        
		        init: {
		             'UploadComplete': function() {
		             	
		            }, 
		            'FileUploaded': function(up, file, info) {//完成一张图片之后
		            	
		            	var res=$.parseJSON(info);
		            	var imgUrl = 'http://tpublic.qiniudn.com/' + res.key;
		            	jQuery.getJSON(imgUrl + '?imageInfo',
		            			function(result){
	            			var height = result.height; //图片高度
	            			var width = result.width; //图片宽度
	            			//通过dom动态添加image
	            			var $imgContainer = jQuery("#picUrl");
	            			$imgContainer.attr("src", imgUrl);
	            			jQuery("#logoUrl").val(imgUrl);
	            			alert("上传成功");
		            	});
		            	
		            },
		            'Error': function(up, err, errTip) {
		                alert(errTip);
		            }
		            ,
		            'Key': function(up, file) {
		            	var key="";
		            	 $.ajax({
								url :"${cbasePath}topic/getFileName",
								type : "POST",
								async : false,
								data :{
									"name" : file.name,
									"pathrule" : "course/imgs/{yyyymm}/"
								},
								success : function(result) {
									key=result;
								}
						 });
		                 return key;
		             }
		        }
		    });
			
		}
		/*判断收费模式*/
		function checkPriceModel(obj){
			var val = $(obj).val();
			if(val == '1'){
				$("#price").attr("disabled", false);
				$("#price").val('${courseDetail.data.result.price}');
				$("#scaleDiv").show();
			}else{
				$("#price").attr("disabled",true);
				$("#price").val("0");
				$("#scale").val("0");
				$("#scaleDiv").hide();
			}
			
		}
		
		 
			function testqqq(){
				var content=ue.getContent();
				var id=$("#tempid").val();
				alert(content);
				/* $.post("${cbasePath}/mail/modify",{"id":id,"content":content},function(data){
					alert("修改成功");
					
				}); */
			}
	   
		
	</script>
</body>
</html>

