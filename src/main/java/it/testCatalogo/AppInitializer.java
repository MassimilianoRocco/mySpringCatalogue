package it.testCatalogo;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		return new Class<?>[] {AppConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
	
		return new String[]{"/"};
	}

	
	//si aggiunge l'Override di createDispatcherServlet
	//L'override di createDispatcherServlet non è obbligatorio, ma viene fatto quando si desidera avere maggiore controllo sulla configurazione e il comportamento del DispatcherServlet
	// un esempio è throwExceptionIfNoHandlerFound, che però ormai viene automaticamente impostata a true e non si ha più il bisogno di fare per esempio ds.setThrowExceptionIfNoHandlerFound(true);
	@Override
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		
		DispatcherServlet ds = new DispatcherServlet(servletAppContext);
		
		return ds;
	}
	
	

}
