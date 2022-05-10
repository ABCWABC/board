package com.doccomsa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter,setter, toString, hashCode, equals
@AllArgsConstructor  // 모든필드를 이용한 생성자메서드
@NoArgsConstructor // 기본생성자 메서드
public class SampleVO {

	private Integer mno;
	private String firstName;
	private String lastName;
}
