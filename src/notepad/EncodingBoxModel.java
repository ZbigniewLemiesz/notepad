package notepad;

enum EncodingBoxModel {
    UTF8("UTF-8"), ISO88591("ISO-8859-1"), UTF16("UTF-16");

    private String name;

    EncodingBoxModel(String s) {
        name = s;
    }
}
