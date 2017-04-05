package factory;

import admin.service.ImageService;
import chok.devwork.BeanFactory;


public class MyFactory {
	public static ImageService getImageService(){return (ImageService) BeanFactory.getBean("imageService");}
}
