package com.example.yo.twitterstats.util;

/**
 * Clase que modela usuarios de Twitter con únicamente
 * los datos que son necesarios: su id, su screen name, su nombre
 * y la URL de su foto de perfil.
 */
public class TwitterUser {

    private Long userId;
    private String screenName;
    private String name;
    private String profilePicURL;

    public TwitterUser(Long userId, String screenName, String name, String profilePicURL) {
        this.userId = userId;
        this.screenName = screenName;
        this.name = name;
        this.profilePicURL = profilePicURL;
    }

    public Long getUserId() {
        return userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public String getName() { return name;    }

    /**
     * Comprueba si dos objetos TwitterUser son iguales, esto es,
     * si tienen el mismo userId.
     * @param o el TwitterUser a comparar.
     * @return true si son iguales, false si son distintos
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwitterUser that = (TwitterUser) o;

        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
