package com.example.yo.twitterstats;

/**
 * Created by Ivan on 11/01/2018.
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
