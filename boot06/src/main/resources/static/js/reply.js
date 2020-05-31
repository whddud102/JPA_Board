/**
 * 댓글 관련 동작을 처리하는 replyManager 객체 생성 코드
 */

var replyManager = (function() {
	
	/**
	 * obj : 댓글 객체
	 * callback : 성공 콜백 함수
	 */
	var getAll = function(obj, callback) {
		console.log("--- 댓글 목록 조회 요청 ---");
		
		$.getJSON("/replies/" + obj, callback);
		
	};
	
	var add = function(obj, callback) {
		console.log("--- 댓글 등록 요청  ---");
		
		$.ajax({
			type: "post",
			url : "/replies/" + obj.bno,
			data : JSON.stringify(obj),
			dataType : 'json',
			contentType : "application/json",
			success : callback
		});
	};
	
	var update = function(obj, callback) {
		console.log("--- 댓글 수정 요청 -------");
	};
	
	var remove = function(obj, callback) {
		console.log("--- 댓글 삭제 요청 ----");
		
		$.ajax({
			type: "delete",
			url : "/replies/" + obj.bno + "/" + obj.rno,
			dataType : "json",
			contentType : "application/json",
			success : callback
		});
	};
	
	return {
		getAll : getAll,
		add : add,
		update : update,
		remove : remove
	};
	
})();