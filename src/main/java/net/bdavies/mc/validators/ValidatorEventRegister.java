package net.bdavies.mc.validators;

import java.util.Map;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.validation.Validator;

/**
 * Setup registering of validators for the repositories
 *
 * @see ValidatingRepositoryEventListener
 * @see InitializingBean
 *
 * @author ben.davies
 */
@Configuration
public class ValidatorEventRegister implements InitializingBean
{
	@Autowired
	ValidatingRepositoryEventListener validatingRepositoryEventListener;

	@Autowired
	private Map<String, Validator> validators;

	/**
	 * Invoked by the containing {@code BeanFactory} after it has set all bean properties
	 * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
	 * <p>This method allows the bean instance to perform validation of its overall
	 * configuration and final initialization when all bean properties have been set.
	 *
	 * @throws Exception in the event of misconfiguration (such as failure to set an
	 * essential property) or if initialization fails for any other reason
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		validators.forEach((k, v) -> {
			if (k.startsWith("beforeCreate")) {
				validatingRepositoryEventListener.addValidator("beforeCreate", v);
			}
		});
	}
}
