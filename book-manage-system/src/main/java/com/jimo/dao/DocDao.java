package com.jimo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jimo.model.doc.BookChartDTO;
import com.jimo.model.doc.BookEnterDTO;
import com.jimo.model.doc.BookInfoDTO;

@Repository
public class DocDao {
	@Autowired
	JdbcTemplate jt;

	/**
	 * 查出图书分类
	 * 
	 * @return
	 */
	public List<String> getBookType() {
		String sql = "select DISTINCT tsfl from book_info";
		List<String> re = null;
		try {
			re = jt.queryForList(sql, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 数据一览表
	 * 
	 * @param type
	 * 
	 * @return
	 */
	public List<BookInfoDTO> getBookInfos(String type) {
		String sql = "select bi.*,bs.cs from book_info bi left join book_storage bs on bi.shuh=bs.shuh";
		Object[] obj = null;
		if (type != null && !type.equals("")) {
			sql += " where bi.tsfl=?";
			obj = new Object[1];
			obj[0] = type;
		} else {
			obj = new Object[0];
		}
		List<BookInfoDTO> re = null;
		try {
			re = jt.query(sql, obj, new BeanPropertyRowMapper<BookInfoDTO>(BookInfoDTO.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 取得统计波报表数据，按年
	 * 
	 * @param year
	 * @return
	 */
	public List<BookEnterDTO> getBookEntersByYear(String year) {
		String sql = "select be.*,bi.shum,bi.zuozhe,bi.dj from book_enter be right join book_info bi on be.shuh=bi.shuh where be.rkrq like ?";
		List<BookEnterDTO> re = null;
		try {
			re = jt.query(sql, new Object[] { "%" + year + "%" },
					new BeanPropertyRowMapper<BookEnterDTO>(BookEnterDTO.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public BookChartDTO getChartData(String year) {
		String sql = "select DATE_FORMAT(rkrq,'%Y%m')months,DATE_FORMAT(rkrq,'%c')m, sum(rkcs) cs "
				+ "from book_enter where rkrq like ? group by months";
		BookChartDTO re = null;
		List<String> m = null;
		List<Integer> cs = null;
		List<Map<String, Object>> maps = null;
		try {
			maps = jt.queryForList(sql, "%" + year + "%");
			if (maps != null) {
				re = new BookChartDTO();
				m = new ArrayList<>();
				cs = new ArrayList<>();
				for (Map<String, Object> map : maps) {
					m.add(String.valueOf(map.get("m")));
					cs.add(Integer.parseInt(String.valueOf(map.get("cs"))));
				}
				re.setCs(cs);
				re.setMonth(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
}