package common;

import admin.service.CategoryService;
import admin.service.ImageService;
import admin.service.ModelService;
import chok.devwork.BeanFactory;


public class Factory {
	public static CategoryService getCategoryService(){return (CategoryService) BeanFactory.getBean("categoryService");}
	public static ModelService getModelService(){return (ModelService) BeanFactory.getBean("modelService");}
	public static ImageService getImageService(){return (ImageService) BeanFactory.getBean("imageService");}
}
