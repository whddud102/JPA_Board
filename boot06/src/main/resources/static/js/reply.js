/**
 * 댓글 관련 동작을 처리하는 replyManager 객체 생성 코드
 */

var replyManager = (function() {
	
	var getAll = function(obj, callback) {
		console.log("--- 댓글 목록 조회 -------");
		
		$.getJSON("/replies/" + obj, callback);
		
	};
	
	var add = function(obj, callback) {
		console.log("--- 댓글 등록 요청  -------");
	};
	
	var update = function(obj, callback) {
		console.log("--- 댓글 수정 요청 -------");
	};
	
	var remove = function(obj, callback) {
		console.log("--- 댓글 삭제 요청 -------");
	};
	
	return {
		getAll : getAll,
		add : add,
		update : update,
		remove : remove
	};
	
})();