package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sports club in the system.
 *
 * A Club is created by a CLUB_LEADER (User with role "CLUB_LEADER").
 * It holds a list of member user IDs and can host Events.
 *
 * TEAM NOTE: Use leaderId to look up the club leader from your users list.
 * Members are stored as a List of userId Strings to keep it simple.
 */
public class Club {

    // ─── Fields ───────────────────────────────────────────────────────────────

    private String clubId;
    private String name;
    private String description;
    private String leaderId;               // userId of the Club Leader
    private List<String> memberIds;        // list of userId Strings

    // ─── Constructor ──────────────────────────────────────────────────────────

    /**
     * Creates a new Club.
     *
     * @param clubId      Unique ID (e.g. "CLB-001")
     * @param name        Club display name
     * @param description Short description of the club
     * @param leaderId    userId of the user who leads this club
     */
    public Club(String clubId, String name, String description, String leaderId) {
        this.clubId      = clubId;
        this.name        = name;
        this.description = description;
        this.leaderId    = leaderId;
        this.memberIds   = new ArrayList<>();
    }

    // ─── Methods ──────────────────────────────────────────────────────────────

    /**
     * Add a userId to the memberIds list.
     * Checks that the userId is not already in the list before adding.
     *
     * @param userId the ID of the user to add
     */
    public void addMember(String userId) {
        if (userId != null && !memberIds.contains(userId)) {
            memberIds.add(userId);
        }
    }

    /**
     * Remove a userId from the memberIds list.
     * If userId is not found, does nothing.
     *
     * @param userId the ID of the user to remove
     */
    public void removeMember(String userId) {
        memberIds.remove(userId);
    }

    /**
     * Return the count of current members.
     * Useful for displaying "X members" in the GUI club list panel.
     *
     * @return number of members
     */
    public int getMemberCount() {
        return memberIds.size();
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public String getClubId()                      { return clubId; }
    public String getName()                        { return name; }
    public String getDescription()                 { return description; }
    public String getLeaderId()                    { return leaderId; }
    public List<String> getMemberIds()             { return memberIds; }

    public void setClubId(String clubId)           { this.clubId = clubId; }
    public void setName(String name)               { this.name = name; }
    public void setDescription(String desc)        { this.description = desc; }
    public void setLeaderId(String leaderId)       { this.leaderId = leaderId; }

    @Override
    public String toString() {
        return name + " [" + memberIds.size() + " members]";
    }
}
