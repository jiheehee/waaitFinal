package com.waait.service;

import java.util.List;
import java.util.Map;

import com.waait.dto.Mypage;
import com.waait.dto.SpamDomain;
import com.waait.dto.Work;

public interface MypageService {
	
	//사원 정보 불러오기
	List<Mypage> myInfoList(Long empNumber);
	//총 연차,잔여연차 출력
	List<Mypage> myVacation(Long empNumber);
	//사원 출퇴근시간 불러오기 
	List<Work> myTodayWork(Long empNumber);
	//로그인된 유저 안읽은 채팅 수
	int selectChatHistoryCount(Long loginEmpNo);
	// 안읽은메일
	List<SpamDomain> getSpamDomain(long empNo);	
	int getNotReadMailCount(Map<String, Object> sqlParam);
	//승인대기중 문서 count
	int awaitingApprovalTotal(Long empNumber);
	

}
