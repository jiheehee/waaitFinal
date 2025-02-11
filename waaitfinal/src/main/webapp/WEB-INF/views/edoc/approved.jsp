<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<section class="section">
	<div class="card">
		<div class="card-header">
            <h2 class="card-title">
           	Work-Flow
			</h2>
        </div>
		<div class="card-body ">
			<div class="d-flex">
				<div style="width: 90%; margin-left:5px;">
					<h5>완료문서함</h5>
				</div>
			</div>
			<div class="mt-5">
	        	<table class="table" id="table1">
					<thead>
						<tr>
							<th>문서번호</th>
							<th>문서종류</th>
							<th>문서제목</th>
							<th>상신일</th>
							<th>결재일</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty documents }">
							<c:forEach items="${documents }" var="d">
								<tr onclick = "isFirstOpened('${d.docId}','${d.type.docType }','${d.docWriter }');">
									<td>${d.docNumber }</td>
									<td>${d.type.type }</td>
									<td>${d.docTitle }</td>
									<td>${d.docDate }</td>
									<td>
										<fmt:formatDate value = "${d.approvalOne.appDate }" type="date" pattern = "yyyy-MM-dd" />
									</td><!-- appDate -->
								</tr>
							</c:forEach>
						</c:if> 
					</tbody>
				</table>
				${pageBar1 }
			</div>
		</div> 
	</div>    
	<script type="text/javascript">
		const isFirstOpened = (docId,docType,docWriter) => {
			window.open("${path}/edoc/openedoc"+docId+"/"+docType+"/"+docWriter,"_blank" ,"width=1200, height=1000");
			// 새 창 열기로 오픈하면 , 여러 문서를 동시에 작업할 수 있음 ~ 
		}
	</script>
	
</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />