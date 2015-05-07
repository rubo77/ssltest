package de.dogcraft.ssltest.dns.encoding;

public class Header {

    private short id;

    private boolean isResponse;

    private Opcode opcode;

    private boolean isAuthoritative;

    private boolean isTruncated;

    private boolean isRecursionDesired;

    private boolean isRecursionAvailable;

    private boolean reserved;

    private ResponseCode rcode;

    private int questionCount;

    private int answersCount;

    private int authorityCount;

    private int additionalCount;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public boolean isResponse() {
        return isResponse;
    }

    public void setResponse(boolean isResponse) {
        this.isResponse = isResponse;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public void setOpcode(Opcode opcode) {
        this.opcode = opcode;
    }

    public boolean isAuthoritative() {
        return isAuthoritative;
    }

    public void setAuthoritative(boolean isAuthoritative) {
        this.isAuthoritative = isAuthoritative;
    }

    public boolean isTruncated() {
        return isTruncated;
    }

    public void setTruncated(boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    public boolean isRecursionDesired() {
        return isRecursionDesired;
    }

    public void setRecursionDesired(boolean isRecursionDesired) {
        this.isRecursionDesired = isRecursionDesired;
    }

    public boolean isRecursionAvailable() {
        return isRecursionAvailable;
    }

    public void setRecursionAvailable(boolean isRecursionAvailable) {
        this.isRecursionAvailable = isRecursionAvailable;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public ResponseCode getRcode() {
        return rcode;
    }

    public void setRcode(ResponseCode rcode) {
        this.rcode = rcode;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getAnswersCount() {
        return answersCount;
    }

    public void setAnswersCount(int answersCount) {
        this.answersCount = answersCount;
    }

    public int getAuthorityCount() {
        return authorityCount;
    }

    public void setAuthorityCount(int authorityCount) {
        this.authorityCount = authorityCount;
    }

    public int getAdditionalCount() {
        return additionalCount;
    }

    public void setAdditionalCount(int additionalCount) {
        this.additionalCount = additionalCount;
    }

}
