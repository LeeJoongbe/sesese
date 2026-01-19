package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePageDTO<T> {

	
	private List<T> dtoList;		//리스트 데이터


	
	private int page;
	
	

	private String keyword; 		//키워드
	
	private int start;				//시작
	private int end;				//끝페이지
	private int total;				//전체게시물수
	private int lastEnd;			//진짜 마지막
	
	private Boolean prev;
	private Boolean next;


	public ResponsePageDTO(List<T> dtoList, int page, int total) {

		this.dtoList = dtoList;
		this.page = page;
		this.total = total;
		
		
		this.end =     (int)Math.ceil(page/10.0) * 10;
		this.start = this.end - 9;
		//실제 마지막 페이지

		this.lastEnd = (int) (Math.ceil(total / 10.0));
		if( this.lastEnd < this.end  ) {
			
			this.end = this.lastEnd;
		}
		
		this.prev = this.start > 1;		//이전으로 돌아가는 버튼 있냐?
		this.next = this.lastEnd > this.end ; //다음페이지를 보여주는 버튼있냐?
		
	}


	
	
	
}
