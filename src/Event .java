package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a sports event hosted by a Club.
 *
 * Events belong to a Club (via clubId) and users can register for them.
 * The registeredUserIds list tracks who signed up.
 *
 * TEAM NOTE: Date is java.util.Date for simplicity.
 * You can display it in the GUI using SimpleDateFormat.
 * Example: new SimpleDateFormat("dd/MM/yyyy").format(event.getDate())
 */
public class Event {

    // ─── Fields ───────────────────────────────────────────────────────────────

    private String eventId;
    private String name;
    private Date date;
    private String clubId;                    // which club is hosting this event
    private List<String> registeredUserIds;   // users who signed up
    private boolean cancelled;               // true if the event has been cancelled

    // ─── Constructor ──────────────────────────────────────────────────────────

    /**
     * Creates a new Event.
     *
     * @param eventId Unique ID (e.g. "EVT-001")
     * @param name    Event display name
     * @param date    Date of the event (java.util.Date)
     * @param clubId  ID of the club hosting this event
     */
    public Event(String eventId, String name, Date date, String clubId) {
        this.eventId            = eventId;
        this.name               = name;
        this.date               = date;
        this.clubId             = clubId;
        this.registeredUserIds  = new ArrayList<>();
        this.cancelled          = false;
    }

    // ─── Methods ──────────────────────────────────────────────────────────────

    /**
     * Register a user for this event.
     * Adds userId to registeredUserIds only if not already registered
     * and the event has not been cancelled.
     *
     * @param userId the ID of the user registering
     */
    public void register(String userId) {
        if (userId != null && !cancelled && !registeredUserIds.contains(userId)) {
            registeredUserIds.add(userId);
        }
    }

    /**
     * Cancel this event.
     * Sets the cancelled flag to true and clears all registered users.
     * Tip: send a Notification to all registered users before clearing the list.
     */
    public void cancel() {
        this.cancelled = true;
        registeredUserIds.clear();
    }

    /**
     * Check if a user is already registered.
     * Useful in the GUI to toggle the "Register" button to "Unregister".
     *
     * @param userId the ID of the user to check
     * @return true if registered, false otherwise
     */
    public boolean isRegistered(String userId) {
        return registeredUserIds.contains(userId);
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public String getEventId()                        { return eventId; }
    public String getName()                           { return name; }
    public Date getDate()                             { return date; }
    public String getClubId()                         { return clubId; }
    public List<String> getRegisteredUserIds()        { return registeredUserIds; }
    public boolean isCancelled()                      { return cancelled; }

    public void setEventId(String eventId)            { this.eventId = eventId; }
    public void setName(String name)                  { this.name = name; }
    public void setDate(Date date)                    { this.date = date; }
    public void setClubId(String clubId)              { this.clubId = clubId; }

    @Override
    public String toString() {
        return name + " on " + date + (cancelled ? " [CANCELLED]" : "");
    }
}
