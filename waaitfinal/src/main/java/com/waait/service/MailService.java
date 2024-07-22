package com.waait.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waait.dao.MailDao;
import com.waait.dto.Mail;
import com.waait.dto.MailFile;
import com.waait.dto.MailSetting;
import com.waait.dto.MyMailBox;
import com.waait.dto.RecentSearch;
import com.waait.dto.SpamDomain;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private final MailDao dao;
	private final SqlSession session;
	
	public List<Mail> getReceiveMail(Map<String, Object> mailSettings) {
		return dao.getReceiveMail(session, mailSettings);
	}

	public List<SpamDomain> getSpamDomain(long empNo) {
		return dao.getSpamDomain(session, empNo);
	}

	public MailSetting getMailSetting(Long empNo) {
		return dao.getMailSetting(session, empNo);
	}

	public void setMailSetting(long empNo) {
		dao.setMailSetting(session, empNo);
	}

	public int insertSpamDomain(Map<String, Object> param) {
		return dao.insertSpamDomain(session, param);
	}

	public List<Mail> getAllMail(String loginMemberEmailDomain) {
		return dao.getAllMail(session, loginMemberEmailDomain);
	}

	public List<Mail> getSpamMail(Map<String, Object> param) {
		return dao.getSpamMail(session, param);
	}
	
	public int getTotalData(Map<String, Object> mailSettings) {
		return dao.getTotalData(session, mailSettings);
	}

	public int enrollUserMailBox(Map<String, Object> param) {
		int myMailBoxSeq = dao.getMyMailBoxSeq(session);
		param.put("myMailBoxNo", myMailBoxSeq);
		dao.enrollUserMailBox(session, param);
		return myMailBoxSeq;
	}

	public List<MyMailBox> getMyMailBox(long empNo) {
		return dao.getMyMailBox(session, empNo);
	}
	
	@Transactional
	public int deleteSpamDomain(Map<String, Object> param) {
		int result = 0;
		String[] domainAddressArr = ((String) param.get("domainAddresses")).split(",");
		for(String domainAddress : domainAddressArr) {
			param.put("domainAddress", domainAddress);
			result = dao.deleteSpamDomain(session, param);
			if(result == 0) return result;
		}
		return result;
	}

	public Mail getMailDetailByNo(Map<String, Object> param) {
		return dao.getMailDetailByNo(session, param);
	}
	
	@Transactional
	public void updateReadStatus(int mailNo) {
		dao.updateReadStatus(session, mailNo);
	}
	
	@Transactional
	public int addFavoriteMail(String mailNo) {
		int result = 0;
		if(mailNo.contains(",")) {
			String[] mailNoArr = mailNo.split(",");
			for(int i = 0; i < mailNoArr.length; i++) {
				System.out.println("mailNo 배열 : " + mailNoArr[i]);
				result = dao.addFavoriteMail(session, mailNoArr[i]);
				if(result == 0) return result;
			}
		} else {
			return dao.addFavoriteMail(session, mailNo);			
		}
		return result;
	}

	public int cancelAddFavorite(String mailNo) {
		return dao.cancelAddFavorite(session, mailNo);
	}
	
	@Transactional
	public int sendMail(Map<String, Object> param) {
		int result = 0;
		int mailSequence = dao.selectSequence(session);
		System.out.println("mailSequence : " + mailSequence);
		param.put("mailNo", mailSequence);
		
		dao.enrollSendMailInfo(session, param);
		
		String mailReceiverAddress = (String) param.get("mailReceiverAddress");
		
		if(mailReceiverAddress.contains(",")) {
			String[] mailReceiverAddressArr = mailReceiverAddress.split(",");
			for(int i = 0; i < mailReceiverAddressArr.length; i++) {
				param.put("mailReceiverAddress", mailReceiverAddressArr[i]);
				result = dao.enrollReceiverInfo(session, param);
			}
		} else {
			result = dao.enrollReceiverInfo(session, param);
		}
		return mailSequence;
	}
	
	@Transactional
	public int addMailMyMailBox(Map<String, Object> param) {
		int result = 0;
		String mailNoStr = (String) param.get("mailNoStr");
		
		if(mailNoStr.contains(",")) {
			String[] mailNoArr = mailNoStr.split(",");
			for(int i = 0; i < mailNoArr.length; i++) {
				param.put("mailNoStr", mailNoArr[i]);
				result = dao.addMailMyMailBox(session, param);
			}
		} else {
			result = dao.addMailMyMailBox(session, param);
		}
		return result;
	}

	public List<Mail> joinMyMailBoxDetail(int myMailBoxNo, Map<String, Integer> pagingParam) {
		return dao.joinMyMailBoxDetail(session, myMailBoxNo, pagingParam);
	}
	
	public int getMyMailBoxTotalData(int myMailBoxNo) {
		return dao.getMyMailBoxTotalData(session, myMailBoxNo);
	}

	public List<Mail> joinFavoriteMailBox(String loginMemberEmailDomain) {
		return dao.joinFavoriteMailBox(session, loginMemberEmailDomain);
	}

	public List<Mail> joinTempoSaveMailBox(long empNo) {
		return dao.joinTempoSaveMailBox(session, empNo);
	}

	public Mail joinTempoSaveMailByMailNo(int mailNo) {
		return dao.joinTempoSaveMailByMailNo(session, mailNo);
	}

	public int deleteMail(String mailNoStr) {
		int result = 0;
		if(mailNoStr.contains(",")) {
			String[] mailNoArr = mailNoStr.split(",");
			for(int i = 0; i < mailNoArr.length; i++) {
				result = dao.deleteMail(session, mailNoArr[i]);
			}
		} else {
			result = dao.deleteMail(session, mailNoStr);			
		}
		return result;
	} //안쓸지도?
	
	@Transactional
	public int deleteReceiveMail(Map<String, String> sqlParam) {
		int result = 0;
		String mailNoStr = sqlParam.get("mailNoStr");
		
		if(mailNoStr.contains(",")) {
			String[] mailNoArr = mailNoStr.split(",");
			for(int i = 0; i < mailNoArr.length; i++) {
				sqlParam.put("mailNoStr", mailNoArr[i]);
				result = dao.deleteReceiveMail(session, sqlParam);
			}
		} else {
			result = dao.deleteReceiveMail(session, sqlParam);			
		}
		return result;
	}
	
	@Transactional
	public int deleteSendingMail(String mailNoStr) {
		int result = 0;
		if(mailNoStr.contains(",")) {
			String[] mailNoArr = mailNoStr.split(",");
			for(int i = 0; i < mailNoArr.length; i++) {
				result = dao.deleteSendingMail(session, mailNoArr[i]);
			}
		} else {
			result = dao.deleteSendingMail(session, mailNoStr);			
		}
		return result;
	}
	
	public List<Mail> getTrashMailByMailNoForRestore(String mailNoStr) {
		List<Mail> trashMailListByMailNo = new ArrayList<Mail>();
		if(mailNoStr.contains(",")) {
			String[] mailNoArr = mailNoStr.split(",");
			for(int i = 0; i < mailNoArr.length; i++) {
				trashMailListByMailNo.add(dao.getTrashMailByMailNoForRestore(session, mailNoArr[i]));
			}
		} else {
			trashMailListByMailNo.add(dao.getTrashMailByMailNoForRestore(session, mailNoStr));	
		}
		return trashMailListByMailNo;
	}
	
	@Transactional
	public int moveMailToTrashMailBox(List<Mail> mailInMyMailBox) {
		int result = 0;
		for(Mail mail : mailInMyMailBox) {
			result = dao.moveMailToTrashMailBox(session, mail.getMailNo());
		}
		return result;
		
	}
	
	@Transactional
	public int deleteMyMailBox(int myMailBoxNo) {
		return dao.deleteMyMailBox(session, myMailBoxNo);
	}

	public List<Mail> jointrashmailbox(String mailAddress, Map<String, Integer> pagingParam) {
		return dao.jointrashmailbox(session, mailAddress, pagingParam);
	}
	
	@Transactional
	public void senderPerfectlyDeleteMail(int mailNo) {
		dao.senderPerfectlyDeleteMail(session, mailNo);
	}
	
//	@Transactional
//	public void deleteAllMailInfo(int mailNo) {
//		dao.deleteAllMailInfo(session, mailNo);
//	}보류
	
	public void receiverPerfectlyDeleteMail(int mailReceiverNo) {
		dao.receiverPerfectlyDeleteMail(session, mailReceiverNo);
	}
	
	
	public List<Mail> getMailForDelete(Map<String, String> sqlParam) {
		String mailNoStr = sqlParam.get("mailNoStr");
		List<Mail> getMailForDelete = new ArrayList<Mail>();
		if(mailNoStr.contains(",")) {
			String[] mailNoArr = mailNoStr.split(",");
			for(int i = 0; i < mailNoArr.length; i++) {
				sqlParam.put("mailNoStr", mailNoArr[i]);
				getMailForDelete.add(dao.getMailForDelete(session, sqlParam));
			}
		}
		return getMailForDelete;
	}

	public List<Mail> joinSendingMailBox(long empNo, Map<String, Integer> pagingParam) {
		return dao.joinSendingMailBox(session, empNo, pagingParam);
	}

	public List<Mail> searchMail(Map<String, Object> searchParam, Map<String, Integer> pagingParam) {
		return dao.searchMail(session, searchParam, pagingParam);
	}
	
	@Transactional
	public int updateFile(MailFile mailFile) {
		return dao.updateFile(session, mailFile);
	}
	
	@Transactional
	public void enrollRecentSearchKeyword(RecentSearch recentSearch) {
		dao.enrollRecentSearchKeyword(session, recentSearch);
	}

	public int joinSendingMailBoxTotalData(long empNo) {
		return dao.joinSendingMailBoxData(session, empNo);
	}

	public int trashMailBoxTotalData(String receiverMailAddress) {
		return dao.trashMailBoxTotalData(session, receiverMailAddress);
	}

	public int notReadDataCount(Map<String, Object> mailSettings) {
		return dao.notReadDataCount(session, mailSettings);
	}

	public int getSpamMailCount(Map<String, Object> param) {
		return dao.getSpamMailCount(session, param);
	}

	public int getSearchMailTotalData(Map<String, Object> searchParam) {
		return dao.getSearchMailTotalData(session, searchParam);
	}

	

	

}
