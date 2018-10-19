package es.gob.monitoriza.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import es.gob.monitoriza.constraints.FieldMatch;

/** 
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.0, 09/10/2018.
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	
	/**
	 * Attribute that represents the first field name. 
	 */
	private String firstFieldName;
	
	/**
	 * Attribute that represents the second field name. 
	 */
	private String secondFieldName;

	/**
	 * {@inheritDoc}
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}

	/**
	 * {@inheritDoc}
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		try {
			final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
			final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

			return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
		} catch (final Exception ignore) {
			// ignore
		}
		return true;
	}
}