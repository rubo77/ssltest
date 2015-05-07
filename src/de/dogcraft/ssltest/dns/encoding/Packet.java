package de.dogcraft.ssltest.dns.encoding;

public class Packet {

    private final Header header = new Header();

    private final Section questions = new Section(false);

    private final Section answers = new Section(true);

    private final Section authority = new Section(true);

    private final Section additional = new Section(true);

    public Header getHeader() {
        return header;
    }

    public Section getQuestions() {
        return questions;
    }

    public Section getAnswers() {
        return answers;
    }

    public Section getAuthority() {
        return authority;
    }

    public Section getAdditional() {
        return additional;
    }

}
