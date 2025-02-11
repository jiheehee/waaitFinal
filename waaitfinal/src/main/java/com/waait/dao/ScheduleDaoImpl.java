package com.waait.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.waait.dto.Schedule;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScheduleDaoImpl implements ScheduleDao {
	
	private final SqlSession session;

	@Override
	public List<Schedule> scheduleList(SqlSession session, long empNumber) {
		return session.selectList("schedule.scheduleByempNo",empNumber);
	}


	@Override
	public int insertSchedule(SqlSession session, Schedule s) {
		return session.insert("schedule.insertschedule", s);
	}


	@Override
	public int updateSchedule(SqlSession session, Schedule s) {
		return session.update("schedule.updateSchedule",s);
	}


	@Override
	public int deleteSchedule(SqlSession session, int num) {
		return session.delete("schedule.deleteSchedule", num);
	}


	@Override
	public List<Schedule> teamSchedule(SqlSession session, String deptCode) {
		return session.selectList("schedule.teamSchedule",deptCode);
	}
	
	
	
	
	
	
	

}
