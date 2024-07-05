package com.waait.dto;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule {

	private int scheNo;
	private int reserveNo;
	private int empNo;
	private int teamNo;
	private Time scheTime;
	private String startNo;
	private String endNo;
	private String scheTitle;
	private String scheContent;
	private String scheAllDay;
	private String scheColor;
	private String scheWriter;
	private String scheReWriter;
	private String scheType;
	private String schePrivate;
	
}
