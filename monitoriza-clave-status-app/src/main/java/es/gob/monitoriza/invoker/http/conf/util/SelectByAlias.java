package es.gob.monitoriza.invoker.http.conf.util;

import java.net.Socket;
import java.util.Map;

import org.apache.http.ssl.PrivateKeyDetails;
import org.apache.http.ssl.PrivateKeyStrategy;

public final class SelectByAlias implements PrivateKeyStrategy {

	private String keyAlias = null;

	public SelectByAlias(String keyAlias) {
		this.keyAlias = keyAlias;
	}

	@Override
	public String chooseAlias(Map<String, PrivateKeyDetails> aliases, Socket socket) {
		for (String k: aliases.keySet()) {
			if (keyAlias.equals(k)) {
				return k;
			}
		}
		return null;
	}
}
