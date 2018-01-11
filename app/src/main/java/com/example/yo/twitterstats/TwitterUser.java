package com.example.yo.twitterstats;

/**
 * Created by Ivan on 11/01/2018.
 */

public class TwitterUser {

    private Long userId;
    private String screename;
    private String profilePicURL;
    private boolean following;

    public TwitterUser(Long userId, String screename, String profilePicURL, boolean following) {
        this.userId = userId;
        this.screename = screename;
        this.profilePicURL = profilePicURL;
        this.following = following;
    }

    public Long getUserId() {
        return userId;
    }

    public String getScreename() {
        return screename;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public boolean isFollowing() {
        return following;
    }

}
