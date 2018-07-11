package es.gob.monitoriza.spring.config;

import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import es.gob.monitoriza.persistence.configuration.model.entity.Keystore;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.KeystoreRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.UserMonitorizaRepository;
import es.gob.monitoriza.utilidades.UtilsCertificate;

@Component
public class MultiFieldAuthenticationProvider implements AuthenticationProvider {

	/**
	 * Attribute that represents the keystore of users
	 */
	public static final String USER_KEYSTORE = "UserStore";

	/**
	 * Attribute that represents the interface that provides access to the CRUD
	 * operations for the UserMonitoriza entity.
	 */
	private final UserMonitorizaRepository userRepository;

	/**
	 * Attribute that represents the interface that provides access to the CRUD
	 * operations for the Keystore entity.
	 */
	private final KeystoreRepository keystoreRepository;

	/**
	 * Constructor method for the class UserDetailServiceImpl.java.
	 * 
	 * @param repository
	 */
	@Autowired
	public MultiFieldAuthenticationProvider(UserMonitorizaRepository userRepository,
			KeystoreRepository keystoreRepository) {
		this.userRepository = userRepository;
		this.keystoreRepository = keystoreRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		Authentication auth = null;
		MultiFieldLoginUserDetails customUser = (MultiFieldLoginUserDetails) authentication.getPrincipal();
		String name = customUser.getUsername();
		String password = authentication.getCredentials().toString();
		String signatureBase64 = customUser.getSignatureBase64();

		UserMonitoriza curruser = null;

		if (!StringUtils.isEmpty(signatureBase64)) {
			byte[] signatureBase64Bytes = Base64.getDecoder().decode(signatureBase64.getBytes());
			try {
				Security.addProvider(new BouncyCastleProvider());
				CMSSignedData cms = new CMSSignedData(signatureBase64Bytes);
				Store store = cms.getCertificates();
				SignerInformationStore signers = cms.getSignerInfos();
				Iterator<?> it = signers.getSigners().iterator();
				while (it.hasNext()) {
					SignerInformation signer = (SignerInformation) it.next();
					Collection<?> certCollection = store.getMatches(signer.getSID());
					Iterator<?> certIt = certCollection.iterator();
					X509CertificateHolder certHolder = (X509CertificateHolder) certIt.next();
					X509Certificate cert = new JcaX509CertificateConverter()
							.setProvider(BouncyCastleProvider.PROVIDER_NAME).getCertificate(certHolder);
					if (signer.verify(new JcaSimpleSignerInfoVerifierBuilder()
							.setProvider(BouncyCastleProvider.PROVIDER_NAME).build(cert))) {
						Keystore keyUserStore = keystoreRepository.findByName(USER_KEYSTORE);

						for (SystemCertificate systemCertificate : keyUserStore.getListSystemCertificates()) {
							if (systemCertificate.getIssuer()
									.equalsIgnoreCase(UtilsCertificate.getCertificateIssuerId(cert))
									&& systemCertificate.getSerialNumber()
											.equals(UtilsCertificate.getCertificateSerialNumber(cert))) {
								curruser = systemCertificate.getUserMonitoriza();
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				throw new BadCredentialsException("Usuario incorrecto");
			}
		} else {
			if (name != null && password != null) {
				curruser = userRepository.findByLogin(name);
			} else {
				throw new BadCredentialsException("Usuario incorrecto");
			}
		}
		if (curruser != null) {
			// If password is OK
			if (passwordEncoder().matches(password, curruser.getPassword()) || !StringUtils.isEmpty(signatureBase64)) {
				List<GrantedAuthority> grantedAuths = new ArrayList<>();
				grantedAuths.add(new SimpleGrantedAuthority("USER"));

				auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
			} else {
				throw new BadCredentialsException("Usuario incorrecto");
			}
		} else {
			throw new UsernameNotFoundException("Usuario incorrecto");
		}
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	/** The password encoder */
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
