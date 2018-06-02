package org.vap.webapp.model;

import org.vap.webapp.data.Persistent;

/**
 * @author Vahe Pezeshkian
 * June 02, 2018
 */
public class UserLogin extends Persistent {
    private String userId;
    private long timestamp;
    private String host;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLogin userLogin = (UserLogin) o;

        if (timestamp != userLogin.timestamp) return false;
        if (!userId.equals(userLogin.userId)) return false;
        return host.equals(userLogin.host);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + host.hashCode();
        return result;
    }
}
