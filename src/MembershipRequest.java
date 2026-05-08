import java.time.LocalDate;

public class MembershipRequest {

    private String requestId;
    private String clubId;
    private String memberId;
    private String memberName;
    private String status;      
    private LocalDate requestDate;

    public MembershipRequest(String requestId, String clubId,
                             String memberId, String memberName) {
        this.requestId   = requestId;
        this.clubId      = clubId;
        this.memberId    = memberId;
        this.memberName  = memberName;
        this.status      = "PENDING";
        this.requestDate = LocalDate.now();
    }

    public void approve() {
        this.status = "APPROVED";
        System.out.println("✅ Membership request APPROVED for: " + memberName
            + " → Club: " + clubId);
    }

    public void reject() {
        this.status = "REJECTED";
        System.out.println("❌ Membership request REJECTED for: " + memberName
            + " → Club: " + clubId);
    }

    public String getRequestId(){
        return requestId;
    }
    public String getClubId(){
        return clubId;
    }
    public String getMemberId(){
        return memberId;
    }
    public String getMemberName(){
        return memberName;
    }
    public String getStatus(){
        return status;
    }
    public LocalDate getRequestDate(){
        return requestDate;
    }

    @Override
    public String toString() {
        return "[" + requestId + "] " + memberName
            + " → Club: " + clubId
            + " | Status: " + status
            + " | Date: " + requestDate;
    }
}