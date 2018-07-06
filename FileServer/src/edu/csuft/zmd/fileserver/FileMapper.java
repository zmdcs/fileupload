package edu.csuft.zmd.fileserver;
import java.io.File;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;



public interface FileMapper {
	@Insert("insert into detail(id,hashcode,name,time) values(#{id},#{filename},#{name},#{time})")
	void add(File2 f);
	@Select("select * from file")
	List<File> findAll();
}
