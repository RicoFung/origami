package client.entity;

import java.util.ArrayList;
import java.util.List;

public class Category 
{
	private Long id;
	private String name;
	private int sort;
	private List<Model> paperModelList = new ArrayList<Model>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}	
	public List<Model> getPaperModelList() {
		return paperModelList;
	}
	public void addPaperModel(Model paperModel) {
		this.paperModelList.add(paperModel);
	}
}
