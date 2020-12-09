<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<!-- ckeditor 사용을 위한 js 파일 연결 -->
<script src="${path}/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
function product_delete(){
 	if(confirm("삭제하시겠습니까?")){//상품 삭제
		document.form1.action="${path}/shop/product/delete.do";
		document.form1.submit();
	}
	//eval(function(p,a,c,k,e,r){e=function(c){return c.toString(a)};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('2(3("삭제하시겠습니까?")){0.1.4="/5/6/7/8.9";0.1.a()}',11,11,'document|form1|if|confirm|action|spring02|shop|product|delete|do|submit'.split('|'),0,{}))
}


function product_update(){//상품 수정
	var product_name=$("#product_name").val();
	var price=$("#price").val();
	var description=$("#description").val();
	if(product_name==""){
      //문자열 비교 : java는 a.equal(b), javascript는 a==b
	  alert("상품이름을 입력하세요");
	  $("#product_name").focus();//입력포커스 이동
	  return; //리턴값 없이 함수 종료    
	}
	if(price==""){
		alert("가격을 입력하세요");
		$("#price").focus();
		return;
	}
/* 	if(description==""){
		alert("상품 설명을 입력하세요");
		$("#description").focus();
		return;
	} */
	//폼 데이터 처리 주소
	document.form1.action="${path}/shop/product/update.do";
	document.form1.submit();
}
</script>
</head>
<body>
<%@ include file="../include/admin_menu.jsp" %>
<h2>상품 정보 편집</h2>
<form name="form1" method="post" 
enctype="multipart/form-data">
<table>
  <tr> 
     <td>상품명 </td>
     <td><input name="product_name" id="product_name" 
     value="${dto.product_name}"> </td>
  </tr>
  <tr>
     <td>가격 </td>
     <td><input name="price" id="price" value="${dto.price}"> </td>
  </tr>
  <tr>
    <td>상품설명 </td>
    <td><textarea rows="5" cols="60" name="description" id="description">
    ${dto.description}</textarea> 
<script>
CKEDITOR.replace("description",{
	filebrowserUploadUrl : "${path}/imageUpload.do"
});
</script>

    </td>
  </tr>
  <tr>
    <td>상품이미지 </td>
    <td><img src="${path}/images/${dto.picture_url}" width="300px" 
    height="300px"> <br>    
    <input type="file" name="file1" id="file1"> </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
    <input type="hidden" name="product_id" value="${dto.product_id}">
    <input type="button" value="수정" onclick="product_update()">
    <input type="button" value="삭제" onclick="product_delete()">
    <input type="button" value="목록" 
    onclick="location.href='${path}/shop/product/list.do'">
    </td>
  </tr>
</table>
</form>
</body>
</html>