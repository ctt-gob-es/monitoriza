/*
/*******************************************************************************
 * Copyright (C) 2018 MINHAFP, Gobierno de España
 * This program is licensed and may be used, modified and redistributed under the  terms
 * of the European Public License (EUPL), either version 1.1 or (at your option)
 * any later version as soon as they are approved by the European Commission.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * more details.
 * You should have received a copy of the EUPL1.1 license
 * along with this program; if not, you may find it at
 * http:joinup.ec.europa.eu/software/page/eupl/licence-eupl
 ******************************************************************************/

/**
 * <b>File:</b><p>es.gob.monitoriza.spring.config.MultiFieldAuthenticationProvider.java.</p>
 * <b>Description:</b><p> .</p>
 * <b>Project:</b><p>Application for monitoring the services of @firma suite systems</p>
 * <b>Date:</b><p>30/07/2018.</p>
 * @author Gobierno de España.
 * @version 1.1, 25/01/2019.
 */
package es.gob.monitoriza.spring.config;

import java.security.KeyStore;
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

import es.gob.monitoriza.crypto.keystore.IKeystoreFacade;
import es.gob.monitoriza.crypto.keystore.KeystoreFacade;
import es.gob.monitoriza.persistence.configuration.model.entity.KeystoreMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.entity.SystemCertificate;
import es.gob.monitoriza.persistence.configuration.model.entity.UserMonitoriza;
import es.gob.monitoriza.persistence.configuration.model.repository.KeystoreRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.SystemCertificateRepository;
import es.gob.monitoriza.persistence.configuration.model.repository.UserMonitorizaRepository;
import es.gob.monitoriza.utilidades.UtilsCertificate;

/**
 * <p>Class .</p>
 * <b>Project:</b><p>Application for monitoring services of @firma suite systems.</p>
 * @version 1.1, 25/01/2019.
 */
@Component
public class MultiFieldAuthenticationProvider implements AuthenticationProvider {

	/**
	 * Attribute that represents the keystore of users
	 */
	public static final String USER_KEYSTORE = "UserStore";

	/**
	 * Attribute that represents the incorrect user.
	 */
	private static final String USER_INCORRECT = "Usuario incorrecto";

	/**
	 * Attribute that represents the interface that provides access to the CRUD
	 * operations for the UserMonitoriza entity.
	 */
	private final UserMonitorizaRepository userRepository;

	/**
	 * Attribute that represents the interface that provides access to the CRUD
	 * operations for the Keystore entity.
	 */
	private final KeystoreRepository keystoreRepo;

	/**
	 * Attribute that represents the interface that provides access to the CRUD
	 * operations for the SystemCertificate entity.
	 */
	private final SystemCertificateRepository systemCertRepoy;

	/**
	 * Constructor method for the class UserDetailServiceImpl.java.
	 *
	 * @param repository
	 */
	@Autowired
	public MultiFieldAuthenticationProvider(final UserMonitorizaRepository userRepository, final KeystoreRepository keystoreRepo, final SystemCertificateRepository systemCertRepoy) {
		this.userRepository = userRepository;
		this.keystoreRepo = keystoreRepo;
		this.systemCertRepoy = systemCertRepoy;
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		Authentication auth = null;
		MultiFieldLoginUserDetails customUser = (MultiFieldLoginUserDetails) authentication.getPrincipal();
		String name = customUser.getUsername();
		String password = authentication.getCredentials().toString();
		String signatureBase64 = customUser.getSignatureBase64();

		UserMonitoriza curruser = null;

		if (!StringUtils.isEmpty(signatureBase64)) {
			byte[ ] signBase64Bytes = Base64.getDecoder().decode(signatureBase64.getBytes());
			try {
				Security.addProvider(new BouncyCastleProvider());
				CMSSignedData cms = new CMSSignedData(signBase64Bytes);
				Store store = cms.getCertificates();
				SignerInformationStore signers = cms.getSignerInfos();
				Iterator<?> itSigner = signers.getSigners().iterator();
				X509CertificateHolder certHolder = null;
				X509Certificate certificate = null;
				JcaX509CertificateConverter jcaX509CertConv = new JcaX509CertificateConverter();
				JcaSimpleSignerInfoVerifierBuilder jcaSimpSigInfoVer = new JcaSimpleSignerInfoVerifierBuilder();
				KeystoreMonitoriza keystoreUser = keystoreRepo.findByIdKeystore(KeystoreMonitoriza.ID_USER_STORE);
				IKeystoreFacade keyStoreFacade = new KeystoreFacade(keystoreUser);
				while (itSigner.hasNext()) {
					SignerInformation signer = (SignerInformation) itSigner.next();
					Collection<?> certCollection = store.getMatches(signer.getSID());
					Iterator<?> certIt = certCollection.iterator();
					certHolder = (X509CertificateHolder) certIt.next();
					certificate = jcaX509CertConv.setProvider(BouncyCastleProvider.PROVIDER_NAME).getCertificate(certHolder);
					if (signer.verify(jcaSimpSigInfoVer.setProvider(BouncyCastleProvider.PROVIDER_NAME).build(certificate))) {
						KeyStore ksData = KeystoreFacade.getKeystore(keystoreUser.getKeystore(), keystoreUser.getKeystoreType(), keyStoreFacade.getKeystoreDecodedPasswordString(keystoreUser.getPassword()));
						String alias = UtilsCertificate.createCertificateAlias(certificate, null);

						if (ksData.containsAlias(alias)) {
							SystemCertificate systemCertificate = systemCertRepoy.findByAlias(alias);
							curruser = systemCertificate.getUserMonitoriza();
							break;
						}
					}
				}
			} catch (Exception e) {
				auth = null;
			}
		} else {
			if (name != null && password != null) {
				curruser = userRepository.findByLogin(name);
			} else {
				auth = null;
			}
		}
		if (curruser != null) {
			// If password is OK
			if (passwordEncoder().matches(password, curruser.getPassword()) || !StringUtils.isEmpty(signatureBase64)) {
				List<GrantedAuthority> grantedAuths = new ArrayList<>();
				grantedAuths.add(new SimpleGrantedAuthority("USER"));

				auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
			} else {
				throw new BadCredentialsException(USER_INCORRECT);
			}
		} else {
			throw new UsernameNotFoundException(USER_INCORRECT);
		}
		return auth;
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	/** The password encoder */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Get userRepository.
	 * @return userRepository
	 */
	public UserMonitorizaRepository getUserRepository() {
		return userRepository;
	}

	/**
	 * Get keystoreRepo.
	 * @return keystoreRepo
	 */
	public KeystoreRepository getKeystoreRepo() {
		return keystoreRepo;
	}

	/**
	 * Get systemCertRepoy.
	 * @return systemCertRepoy
	 */
	public SystemCertificateRepository getSystemCertRepoy() {
		return systemCertRepoy;
	}
}
