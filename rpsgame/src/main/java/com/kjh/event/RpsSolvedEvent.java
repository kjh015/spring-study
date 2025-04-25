package com.kjh.event;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class RpsSolvedEvent implements Serializable{ //시리얼라이즈 해서 보낼 객체
	private static final long serialVersionUID = -1898504762979315897L;	
	private final Long rpsChallengeId;
	private final Long userId;
	private final String alias;
	private final String outcome;
	
	
}
