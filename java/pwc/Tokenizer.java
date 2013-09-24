package pwc;

public class Tokenizer {

  public static final char[] PUNCTUATION = new char[]{'!', '@', '&', '(', ')', '<', '>',
      '-', '_', '`', ',', '.', '?', '/', '"', ';', ':', '|', '\''};

  public static String[] tokenize(String input) {
    String string = replacePunctuation(input.trim());
    char delimiter = ' ';

    int maxNumberSplits = (string.length() / 2) + 2;
    String[] temp = new String[maxNumberSplits];

    int wordCount = 0;
    int i = 0;
    int j = string.indexOf(delimiter);

    while (j >= 0) {
      String substring = string.substring(i, j);
      if (!"".equals(substring)) temp[wordCount++] = substring;
      i = j + 1;
      j = string.indexOf(delimiter, i);
    }

    String substring = string.substring(i);
    if (!"".equals(substring)) temp[wordCount++] = substring;

    String[] result = new String[wordCount];
    System.arraycopy(temp, 0, result, 0, wordCount);
    //debug(result);
    return result;
  }

  private static void debug(String[] results) {
    StringBuffer sb = new StringBuffer();
    for(int i=0; i < results.length; i++) {
      sb.append("[" + results[i] + "], ");
    }
    System.out.println(sb.toString());
  }

  private static String replacePunctuation(String sentence) {
    String result = sentence;
    for (int i = 0; i < PUNCTUATION.length; i++) {
      if(sentence.indexOf(PUNCTUATION[i]) != -1)
        result = result.replace(PUNCTUATION[i], ' ');
    }
    return result.trim();
  }

  public static void main(String[] args) {
    String[] words = Tokenizer.tokenize("...here we've gone... too far.");
    for (int i = 0; i < words.length; i++) {
      System.out.println("[" + i + "]: " + words[i]);
    }

  }

}
